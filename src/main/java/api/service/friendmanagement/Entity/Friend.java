package api.service.friendmanagement.Entity;

import javax.persistence.*;

/**
 * Entity Object to represent the Friends Table
 * @Author Salma
 */
@SequenceGenerator
        (name="T_USER_FRIENDS_SEQ",sequenceName = "T_USER_FRIENDS_SEQ" ,initialValue = 9 , allocationSize=100)
@Entity
@Table(name = "T_USER_FRIENDS")
public class Friend {

    @javax.persistence.Id
    @Column(name = "ID")
    @GeneratedValue (strategy=GenerationType.SEQUENCE, generator="T_USER_FRIENDS_SEQ")
    private Long Id;


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    @Column(name = "USER_EMAIL_ADDRESS")
    private String userEmailAddress;
    @Column(name = "FRIEND_EMAIL_ADDRESS")
    private String friendEmailAddress;
    @Column(name = "SUBSCRIBED")
    private String Subscribed;


    public String getSubscribed() {
        return Subscribed;
    }

    public void setSubscribed(String subscribed) {
        Subscribed = subscribed;
    }

    public String getFriendEmailAddress() {
        return friendEmailAddress;
    }

    public void setFriendEmailAddress(String friendEmailAddress) {
        this.friendEmailAddress = friendEmailAddress;
    }

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }
}
