package chatApp.dto;

import java.time.LocalDateTime;

public class MessageContent {

    private LocalDateTime date;
    private Long discussionId;
    private String content;

    public MessageContent(LocalDateTime date, Long discussionId, String content) {
        this.date = date;
        this.discussionId = discussionId;
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Long getDiscussion() {
        return discussionId;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "MessageContent{" +
                "date=" + date +
                ", discussionId=" + discussionId +
                ", content='" + content + '\'' +
                '}';
    }


}
