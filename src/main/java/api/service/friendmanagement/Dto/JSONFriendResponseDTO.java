package api.service.friendmanagement.Dto;

import java.util.List;

public class JSONFriendResponseDTO {

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    String success;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    int count;

    private List<String> friends = null;


    public List<String> getFriends() {
        return friends;
    }


    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}
