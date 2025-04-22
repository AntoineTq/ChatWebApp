package chatApp.service;

import chatApp.dto.DiscussionDTO;
import chatApp.dto.MessageContent;
import chatApp.dto.MessageDTO;
import chatApp.mapper.DiscussionMapper;
import chatApp.mapper.MessageMapper;
import chatApp.model.Discussion;
import chatApp.model.Message;
import chatApp.model.Person;
import chatApp.repository.DiscussionRepository;
import chatApp.repository.MessageRepository;
import chatApp.repository.PersonRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiscussionService {

    private final DiscussionRepository discussionRepository;
    private final MessageRepository messageRepository;
    private final PersonRepository personRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public DiscussionService(DiscussionRepository discussionRepository,
                             MessageRepository messageRepository,
                             PersonRepository personRepository,
                             SimpMessagingTemplate simpMessagingTemplate) {
        this.discussionRepository = discussionRepository;
        this.messageRepository = messageRepository;
        this.personRepository = personRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    public void createDiscussion(List<Long> userIds) {
        Discussion discussion = new Discussion();
        List<Person> members = userIds.stream().map(id -> personRepository.findById(id).orElseThrow()).toList();
        discussion.setMembers(members);
        members.forEach(member -> member.getDiscussions().add(discussion));
        discussionRepository.save(discussion);
    }

    public void deleteDiscussion(Long discussionId) {
        Optional<Discussion> discussionOptional = discussionRepository.findById(discussionId);
        if (discussionOptional.isPresent()){
            Discussion discussion = discussionOptional.get();
            for( Person p:discussion.getMembers()){
                p.getDiscussions().remove(discussion);
            }
        }
        discussionRepository.deleteById(discussionId);
    }

    @Transactional
    public void sendMessage(MessageContent messageContent, String principalName) {
        Discussion discussion = discussionRepository.findById(messageContent.getDiscussionId()).orElseThrow();
        Person sender = personRepository.findById(Long.valueOf(principalName)).orElseThrow();
        LocalDateTime messageDate = LocalDateTime.now();
        List<Person> persons = discussion.getMembers();

        discussion.setLastModified(messageDate);
        discussionRepository.save(discussion);

        Message newMessage = new Message(sender.getId(), messageDate, messageContent.getContent(), discussion);
        messageRepository.save(newMessage);

        MessageDTO messageDTO = MessageMapper.toDTO(newMessage);
        for (Person person : persons){
            System.out.println("sending to "+ person.getEmail());
            simpMessagingTemplate.convertAndSendToUser(
                    person.getId().toString(),
                    "/queue/messages",
                    messageDTO
            );

        }

    }

    public List<DiscussionDTO> getDiscussionsByUserId(Long id) {
        List<Discussion> discussions = discussionRepository.findAllByMembers_IdOrderByLastModifiedDesc(id);
        if (discussions.isEmpty()){
            return null;
        }
        return new ArrayList<>(discussions).stream().map(discussion -> DiscussionMapper.toDto(discussion,id)).toList();
    }


    public List<MessageDTO> getMessagesOfDiscussion(Long discussionId) {
        List<Message> messages = messageRepository.findByDiscussionIdOrderByCreatedDateAsc(discussionId);
        return messages.stream().map(MessageMapper::toDTO).toList();
    }
}
