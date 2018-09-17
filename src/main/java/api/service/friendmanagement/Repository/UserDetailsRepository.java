package api.service.friendmanagement.Repository;

import api.service.friendmanagement.Entity.Friend;
import api.service.friendmanagement.Entity.User;

import java.util.List;

public interface UserDetailsRepository {
    User searchUserByEmailAddress(String userEmailAddress);
    Friend searchFriendByUserFriendEmail(String userEmailAddress, String friendEmailAddress);
    List<String> searchCommonFriends(String userEmailAddress, String friendEmailAddress);
}
