package chatApp.controller;

import chatApp.dto.MessageContent;
import chatApp.service.DiscussionService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class SocketController {

    private final DiscussionService discussionService;

    public SocketController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @MessageMapping("/message")
    public void sendMessage(MessageContent messageContent,
                            Principal principal) throws Exception {
        discussionService.sendMessage(messageContent, principal.getName());
    }

}