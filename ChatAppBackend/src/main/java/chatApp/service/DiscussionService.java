package chatApp.service;

import chatApp.dto.MessageContent;
import chatApp.mapper.DiscussionMapper;
import chatApp.dto.DiscussionDTO;
import chatApp.model.Discussion;
import chatApp.model.Message;
import chatApp.dto.MessageDTO;
import chatApp.mapper.MessageMapper;
import chatApp.repository.DiscussionRepository;
import chatApp.repository.MessageRepository;
import chatApp.model.Person;
import chatApp.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class DiscussionService {

    private final DiscussionRepository discussionRepository;
    private final MessageRepository messageRepository;
    private final PersonRepository personRepository;

    public DiscussionService(DiscussionRepository discussionRepository, MessageRepository messageRepository, PersonRepository personRepository) {
        this.discussionRepository = discussionRepository;
        this.messageRepository = messageRepository;
        this.personRepository = personRepository;
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

    public MessageDTO sendMessage(MessageContent messageContent, Person sender) {
        Discussion discussion = discussionRepository.findById(messageContent.getDiscussion()).orElseThrow();
        Message newMessage = new Message(sender.getId(), LocalDateTime.now(), messageContent.getContent(), discussion);
        messageRepository.save(newMessage);
        return MessageMapper.toDTO(newMessage);
    }

    public List<DiscussionDTO> getDiscussionsByUserId(Long id) {
        List<Discussion> discussions = discussionRepository.findAllByMembers_Id(id);
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
