package api.service.friendmanagement.Repository;

import api.service.friendmanagement.Dto.JSONNotificationResponsetDTO;
import api.service.friendmanagement.Entity.Friend;
import api.service.friendmanagement.Dto.JSONSuccessResponseDTO;
import api.service.friendmanagement.Entity.Notification;
import api.service.friendmanagement.Entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

 public class FriendManagementRepositoryImpl implements FriendManagementRepository{

    private EntityManager entityManager;
    public FriendManagementRepositoryImpl() {
    }

    public FriendManagementRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

     @Override
     @Transactional
     public Friend createRecord(Friend friend) {
         entityManager.persist(friend);
         return friend;
     }

    @Override
    @Transactional
    public JSONSuccessResponseDTO connectFriends(String userEmail,List<String> friendsList) {
        // Check whether the friend is already connected to the user
        JSONSuccessResponseDTO jsonSuccessResponseDTO = new JSONSuccessResponseDTO();
        Friend friend = null;
        for(String friendStr : friendsList){
            friend = new Friend();
            friend.setFriendEmailAddress(friendStr);
            friend.setUserEmailAddress(userEmail);
            friend.setSubscribed("No");
            createRecord(friend);
        }
        jsonSuccessResponseDTO.setSuccess("true");
        return jsonSuccessResponseDTO;
    }

     @Override
     @Transactional
     public JSONSuccessResponseDTO subscribeUnsubscribeUsers(Friend friend, String subscription) {
         JSONSuccessResponseDTO jsonSuccessResponseDTO = new JSONSuccessResponseDTO();
         friend.setSubscribed(subscription);
         entityManager.merge(friend);
         jsonSuccessResponseDTO.setSuccess("true");
         return jsonSuccessResponseDTO;
     }


    @Override
    public List<Friend> fetchFriendsList(User user)
    {
        List<Friend> friendsList = entityManager.createQuery("select friendDetails from Friend friendDetails WHERE friendDetails.userEmailAddress = :userEmailAddress")
                .setParameter("userEmailAddress", user.getUserEmailAddress())
                .getResultList();
        return friendsList;
    }

     @Override
     @Transactional
     public JSONNotificationResponsetDTO notifyAndFetchFriendsList(Notification notification) {
         JSONNotificationResponsetDTO jsonNotificationResponsetDTO = new JSONNotificationResponsetDTO();
        User user = new User();
        user.setUserEmailAddress(notification.getUserEmailAddress());
        user = entityManager.find(User.class,user);
        if(null!=user){
            List<Friend> friendsList =  fetchFriendsList(user);
            List<String> friendsStringList = new ArrayList<String>();
            for(Friend fr : friendsList){
                friendsStringList.add(new String(fr.getFriendEmailAddress()));
            }
            jsonNotificationResponsetDTO.setRecipients(friendsStringList);
            jsonNotificationResponsetDTO.setSuccess("true");
        }else{
            // No such user to post message
            jsonNotificationResponsetDTO.setRecipients(null);
            jsonNotificationResponsetDTO.setSuccess("false");
            return null;
        }
        return jsonNotificationResponsetDTO;
     }

     @Override
     public List<Friend> searchNotificationEligibleFriendsList(String userEmailAddress) {
         List<Friend> friendsList = entityManager.createQuery("select friendDetails from Friend friendDetails WHERE friendDetails.userEmailAddress = :userEmailAddress and " +
                 " friendDetails.Subscribed = :subscribed")
                 .setParameter("userEmailAddress", userEmailAddress)
                 .setParameter("subscribed", "Yes")
                 .getResultList();
         return friendsList;
     }
 }
