package chatApp.controller;

import chatApp.service.DiscussionService;
import chatApp.dto.DiscussionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discussions")
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

    @DeleteMapping("/{discussionId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "ressource deleted successfully")
    public void deleteDiscussion(@PathVariable Long discussionId){
        discussionService.deleteDiscussion(discussionId);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DiscussionDTO>> getDiscussionsOfUser(@PathVariable Long userId){
        List<DiscussionDTO> discussionDTOList = discussionService.getDiscussionsByUserId(userId);
        return ResponseEntity.ok(discussionDTOList);
    }
}
