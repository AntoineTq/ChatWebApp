package chatApp.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "discussion_id")
    private Discussion discussion;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    public Message(){}

    public Message(Long senderId, LocalDateTime createdDate, String content, Discussion discussion) {
        this.senderId = senderId;
        this.createdDate = createdDate;
        this.content = content;
        this.discussion = discussion;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public Discussion getDiscussion() {
        return discussion;
    }

    public Long getSender() {
        return senderId;
    }
}
