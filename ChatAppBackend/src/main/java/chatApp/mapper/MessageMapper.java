package chatApp.mapper;

import chatApp.dto.MessageDTO;
import chatApp.model.Message;

public class MessageMapper {

    public static MessageDTO toDTO(Message message) {
        return new MessageDTO(
                message.getCreatedDate(),
                message.getSender(),
                message.getDiscussion().getId(),
                message.getContent()
                );
    }
}
