package api.service.friendmanagement.Service;

import api.service.friendmanagement.Dto.*;
import api.service.friendmanagement.Entity.*;

import java.util.List;

public interface FriendsManagementService {

    JSONSuccessResponseDTO connectFriends(JSONFriendsRequestDTO friendsList);
    JSONSuccessResponseDTO subscribeUnsubscribeUsers(JSONSubscriptionRequestDTO friendsList, String subscription);
    JSONFriendResponseDTO getFriendsList(User user);
    JSONFriendResponseDTO searchCommonFriends(JSONFriendsRequestDTO friendsList);
    JSONNotificationResponsetDTO searchNotificationEligibleFriendsList(JSONNotificationRequestDTO userMsgDetail);
}
