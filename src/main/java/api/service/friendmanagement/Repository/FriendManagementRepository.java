package api.service.friendmanagement.Repository;

import api.service.friendmanagement.Dto.JSONNotificationResponsetDTO;
import api.service.friendmanagement.Entity.Friend;
import api.service.friendmanagement.Dto.JSONSuccessResponseDTO;
import api.service.friendmanagement.Entity.Notification;
import api.service.friendmanagement.Entity.User;

import java.util.List;

public interface FriendManagementRepository {
    Friend createRecord(Friend friend);
    JSONSuccessResponseDTO connectFriends(String userEmail,List<String> friendsList);
    JSONSuccessResponseDTO subscribeUnsubscribeUsers(Friend friend, String subscription);
    List<Friend> fetchFriendsList(User user);
    JSONNotificationResponsetDTO notifyAndFetchFriendsList(Notification notification);
    List<Friend> searchNotificationEligibleFriendsList(String userEmailAddress);

}
