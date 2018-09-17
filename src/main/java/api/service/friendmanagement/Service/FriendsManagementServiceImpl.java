package api.service.friendmanagement.Service;

import api.service.friendmanagement.Dto.*;
import api.service.friendmanagement.Entity.*;
import api.service.friendmanagement.Repository.FriendManagementRepository;
import api.service.friendmanagement.Repository.NotificationRepository;
import api.service.friendmanagement.Repository.UserDetailsRepository;
import api.service.friendmanagement.Util.DataException;

import java.util.ArrayList;
import java.util.List;

public class FriendsManagementServiceImpl implements FriendsManagementService {

    private FriendManagementRepository friendManagementRepository;

    private UserDetailsRepository userDetailsRepository;

    private NotificationRepository notificationRepository;

    public FriendsManagementServiceImpl(FriendManagementRepository friendManagementRepository,UserDetailsRepository userDetailsRepository,NotificationRepository notificationRepository){
        this.friendManagementRepository = friendManagementRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.notificationRepository = notificationRepository;
    }

    /**
     *
     * @param friendsList
     * Connects two users as friends with their email address
     * @return JSONSuccessResponseDTO
     */
    @Override
    public JSONSuccessResponseDTO connectFriends(JSONFriendsRequestDTO friendsList) {
        JSONSuccessResponseDTO JSONSuccessResponseDTO = new JSONSuccessResponseDTO();
        JSONSuccessResponseDTO.setSuccess("false");
        String userEmail = null;
        userEmail = friendsList.getFriends().get(0);
        friendsList.getFriends().remove(0);
        User user = userDetailsRepository.searchUserByEmailAddress(userEmail);
        if(null == user){
            throw new DataException("No User found to connect for Email "+userEmail);
            // No such user found
        }else{
            // check for an existing connection
            for(String friendEmail : friendsList.getFriends()){
               Friend friend = userDetailsRepository.searchFriendByUserFriendEmail(userEmail,friendEmail);
               if(null == friend){
                   JSONSuccessResponseDTO = friendManagementRepository.connectFriends(userEmail,friendsList.getFriends());
                   JSONSuccessResponseDTO = new JSONSuccessResponseDTO();
                   JSONSuccessResponseDTO.setSuccess("true");
               }else{
                   //already friends
                   if(friend.getSubscribed().equals("Blocked")){
                       throw new DataException("User "+friendsList.getFriends().get(1) + " is found to be blocked for "+userEmail);
                       //Message that the user is already blocked to be added as friend
                   }else{
                       throw new DataException("User "+friendsList.getFriends().get(1) + " is already found to be a friend of "+userEmail);
                       // Already friends
                   }
               }
            }
        }
        return JSONSuccessResponseDTO;
    }

    /**
     * Subscribes and blocks two users with their email address
     * @param friendsList
     * @param subscription
     * @return JSONSuccessResponseDTO
     */
    @Override
    public JSONSuccessResponseDTO subscribeUnsubscribeUsers(JSONSubscriptionRequestDTO friendsList, String subscription) {
        JSONSuccessResponseDTO JSONSuccessResponseDTO = new JSONSuccessResponseDTO();
        JSONSuccessResponseDTO.setSuccess("false");
        int i = 0;
        String userEmail = null;
        userEmail = friendsList.getRequestor();
        String friendEmail = friendsList.getTarget();
        User user = userDetailsRepository.searchUserByEmailAddress(userEmail);
        User userFriend = userDetailsRepository.searchUserByEmailAddress(friendEmail);
        if(null == user || null == userFriend){
            // No such user found
            throw new DataException("No User found to connect for Email "+userEmail + " or "+friendEmail);
        }else{
            // check for an existing connection
                Friend friend = userDetailsRepository.searchFriendByUserFriendEmail(userEmail,friendEmail);
                if(null != friend){
                    JSONSuccessResponseDTO = friendManagementRepository.subscribeUnsubscribeUsers(friend,subscription);
                    JSONSuccessResponseDTO = new JSONSuccessResponseDTO();
                    JSONSuccessResponseDTO.setSuccess("true");
                }else{
                    if(subscription.equals("blocked")){
                        //create a record and make it blocked
                        Friend blockedFriend = new Friend();
                        blockedFriend.setFriendEmailAddress(friendEmail);
                        blockedFriend.setUserEmailAddress(userEmail);
                        blockedFriend.setSubscribed("Blocked");
                        friendManagementRepository.createRecord(blockedFriend);
                    }else{
                        // They are not friends. Make them friends here.
                        JSONFriendsRequestDTO friendRequest = new JSONFriendsRequestDTO();
                        List<String> friendsStrList = new ArrayList<String>();
                        friendsStrList.add(friendsList.getRequestor());
                        friendsStrList.add(friendsList.getTarget());
                        friendRequest.setFriends(friendsStrList);
                        connectFriends(friendRequest);
                        friend = userDetailsRepository.searchFriendByUserFriendEmail(userEmail,friendEmail);
                        if(null != friend){
                            JSONSuccessResponseDTO = friendManagementRepository.subscribeUnsubscribeUsers(friend,subscription);
                            JSONSuccessResponseDTO = new JSONSuccessResponseDTO();
                            JSONSuccessResponseDTO.setSuccess("true");
                        }else{
                            throw new DataException("Error on subscribing "+userEmail + " and "+friendEmail);
                        }
                    }
                }
        }
        return JSONSuccessResponseDTO;
    }

    /**
     * Retrieves the friendsList based on the user detail
     * @param user
     * @return JSONFriendResponseDTO
     */
    @Override
    public JSONFriendResponseDTO getFriendsList(User user) {
       JSONFriendResponseDTO jsonFriendResponseDTO = new JSONFriendResponseDTO();
       List<Friend> friends =  friendManagementRepository.fetchFriendsList(user);
       if(null!=friends){
           List<String> friendsStringList = new ArrayList<String>();
           for(Friend fr : friends){
               friendsStringList.add(new String(fr.getFriendEmailAddress()));
           }
           jsonFriendResponseDTO.setFriends(friendsStringList);
           jsonFriendResponseDTO.setSuccess("true");
       }else{
           jsonFriendResponseDTO.setFriends(null);
           jsonFriendResponseDTO.setSuccess("false");
       }
        return jsonFriendResponseDTO;
    }

    @Override
    public JSONFriendResponseDTO searchCommonFriends(JSONFriendsRequestDTO friendsList) {
        JSONFriendResponseDTO jsonFriendResponseDTO = new JSONFriendResponseDTO();
        String userEmailAddress = null;
        String friendEmailAddress = null;
        if(null!=friendsList && null!=friendsList.getFriends() && friendsList.getFriends().size()>0){
            userEmailAddress = friendsList.getFriends().get(0);
            friendEmailAddress = friendsList.getFriends().get(1);
        }
        List<String>  friends = userDetailsRepository.searchCommonFriends(userEmailAddress,friendEmailAddress);
        if(null!=friends){
            jsonFriendResponseDTO.setSuccess("true");
            jsonFriendResponseDTO.setFriends(friends);
            jsonFriendResponseDTO.setCount(friends.size());
        }else{
            jsonFriendResponseDTO.setSuccess("false");
            jsonFriendResponseDTO.setCount(0);
        }
        return jsonFriendResponseDTO;
    }

    @Override
    public JSONNotificationResponsetDTO searchNotificationEligibleFriendsList(JSONNotificationRequestDTO userMsgDetail) {
        JSONNotificationResponsetDTO jsonNotificationResponsetDTO = new JSONNotificationResponsetDTO();
        List<Friend> friends =  friendManagementRepository.searchNotificationEligibleFriendsList(userMsgDetail.getSender());
        if(null!=friends){
            List<String> friendsStringList = new ArrayList<String>();
            for(Friend fr : friends){
                friendsStringList.add(new String(fr.getFriendEmailAddress()));
            }
            jsonNotificationResponsetDTO.setRecipients(friendsStringList);
            jsonNotificationResponsetDTO.setSuccess("true");
            notificationRepository.createNotificationDetail(userMsgDetail.getSender(),userMsgDetail.getText());
        }else{
            jsonNotificationResponsetDTO.setRecipients(null);
            jsonNotificationResponsetDTO.setSuccess("false");
        }
        return jsonNotificationResponsetDTO;
    }
}
