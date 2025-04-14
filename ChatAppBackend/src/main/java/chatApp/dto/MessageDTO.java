package chatApp.dto;

import java.time.LocalDateTime;

public class MessageDTO {

    private LocalDateTime date;
    private Long senderId;
    private Long discussionId;
    private String content;

    public MessageDTO(LocalDateTime date, Long senderId, Long discussionId, String content) {
        this.date = date;
        this.senderId = senderId;
        this.discussionId = discussionId;
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getDiscussion() {
        return discussionId;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "date=" + date +
                ", senderId=" + senderId +
                ", discussionId=" + discussionId +
                ", content='" + content + '\'' +
                '}';
    }
}
