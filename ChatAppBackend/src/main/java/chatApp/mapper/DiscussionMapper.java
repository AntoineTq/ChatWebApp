package chatApp.mapper;

import chatApp.dto.DiscussionDTO;
import chatApp.model.Discussion;

public class DiscussionMapper {

    public static DiscussionDTO toDto(Discussion discussion, Long currentUserId){
        return new DiscussionDTO(
                discussion.getId(),
                discussion.getDiscussionName(currentUserId)
        );
    }
}
