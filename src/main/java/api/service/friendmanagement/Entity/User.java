package api.service.friendmanagement.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * Entity Object to represent the User Table
 * @Author Salma
 */
@SequenceGenerator
        (name="t_user_seq", initialValue=7, allocationSize=100)
@Entity
@Table(name = "T_USER")
public class User {

    @Column(name = "ID")
    @GeneratedValue (strategy=GenerationType.SEQUENCE, generator="t_user_seq")
    private Long Id;

    @Id
    @Column(name = "USER_EMAIL_ADDRESS")
    private String userEmailAddress;

    @OneToMany
    @JoinColumn(name = "USER_EMAIL_ADDRESS")
    private List<Friend> friendsList;


    public List<Friend> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<Friend> friendsList) {
        this.friendsList = friendsList;
    }

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
