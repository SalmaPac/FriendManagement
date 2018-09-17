package api.service.friendmanagement.Entity;

import javax.persistence.*;

/**
 * Entity Object to represent the NotificationDetails Table
 * @Author Salma
 */
@SequenceGenerator
        (name="T_USER_NOTIFICATION_SEQ",sequenceName = "T_USER_NOTIFICATION_SEQ", initialValue = 2, allocationSize=100)
@Entity
@Table(name ="T_USER_NOTIFICATION")

public class Notification {


    @javax.persistence.Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_USER_NOTIFICATION_SEQ")
    private Long Id;

    @Column(name = "USER_EMAIL_ADDRESS")
    private String userEmailAddress;

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    @Column(name = "USER_MESSAGE")
    private String userMessage;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }

}
