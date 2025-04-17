package chatApp.controller;

import chatApp.dto.MessageDTO;
import chatApp.model.Person;
import chatApp.service.DiscussionService;
import chatApp.dto.DiscussionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DiscussionController {

    private final DiscussionService discussionService;

    public DiscussionController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @PostMapping("/discussion")
    @ResponseStatus(HttpStatus.CREATED)
    public void createDiscussion(@RequestBody List<Long> userIds){
        discussionService.createDiscussion(userIds);
    }

    @DeleteMapping("/discussions/{discussionId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "ressource deleted successfully")
    public void deleteDiscussion(@PathVariable Long discussionId){
        discussionService.deleteDiscussion(discussionId);
    }

    @GetMapping("/discussions")
    public ResponseEntity<List<DiscussionDTO>> getDiscussionsOfUser(@AuthenticationPrincipal Person principal){
        List<DiscussionDTO> discussionDTOList = discussionService.getDiscussionsByUserId(principal.getId());
        return ResponseEntity.ok(discussionDTOList);
    }

    @GetMapping("/discussions/{discussionId}")
    public ResponseEntity<List<MessageDTO>> getMessagesOfDiscussion(@PathVariable Long discussionId){
        List<MessageDTO> messageDTOList = discussionService.getMessagesOfDiscussion(discussionId);
        return ResponseEntity.ok(messageDTOList);
    }
}
