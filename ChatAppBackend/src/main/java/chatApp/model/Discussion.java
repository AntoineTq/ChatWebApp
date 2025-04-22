package chatApp.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Discussion {


    @Id
    @GeneratedValue()
    private Long id;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @OneToMany(mappedBy = "discussion")
    @OrderBy("createdDate desc ")
    private List<Message> messages;

    @ManyToMany(mappedBy = "discussions")
    private List<Person> members;

    public Discussion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<Person> getMembers() {
        return members;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }

    public String getDiscussionName(Long currUserId){
        if (members.size()==2) {
            return members.getFirst().getId().equals(currUserId) ? members.getLast().getEmail() : members.getFirst().getEmail();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Group with ");
        for (int i = 0; i < members.size(); i++) {
            if (!Objects.equals(members.get(i).getId(), currUserId)) {
                sb.append(members.get(i).getEmail());
                if (i < members.size() - 1) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }
}
