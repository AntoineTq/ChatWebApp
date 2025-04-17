package chatApp.controller;

import chatApp.dto.MessageContent;
import chatApp.model.Person;
import chatApp.service.DiscussionService;
import chatApp.dto.MessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class SocketController {

    private DiscussionService discussionService;

    public SocketController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public MessageDTO sendMessage(MessageContent messageContent, UsernamePasswordAuthenticationToken authToken) throws Exception {
        Person sender = (Person) authToken.getPrincipal();
        return discussionService.sendMessage(messageContent, sender);
    }

}