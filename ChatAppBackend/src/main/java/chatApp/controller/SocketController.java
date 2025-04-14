package chatApp.controller;

import chatApp.service.DiscussionService;
import chatApp.dto.MessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    private DiscussionService discussionService;

    public SocketController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public MessageDTO sendMessage(MessageDTO messageDTO) throws Exception {
        return discussionService.sendMessage(messageDTO);
    }

}