package api.service.friendmanagement.Dto;

import java.util.List;

public class JSONFriendsRequestDTO {

    private List<String> friends = null;


    public List<String> getFriends() {
        return friends;
    }


    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}

