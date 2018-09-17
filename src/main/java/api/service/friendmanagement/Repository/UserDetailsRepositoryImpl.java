package api.service.friendmanagement.Repository;

import api.service.friendmanagement.Entity.Friend;
import api.service.friendmanagement.Entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class UserDetailsRepositoryImpl implements UserDetailsRepository {
    private EntityManager entityManager;
    public UserDetailsRepositoryImpl() {
    }

    public UserDetailsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public User searchUserByEmailAddress(String userEmailAddress) {
       TypedQuery<User> query = entityManager.createQuery("select userDetail from User userDetail WHERE userDetail.userEmailAddress = :userEmailAddress",User.class);
        query.setParameter("userEmailAddress", userEmailAddress);
        User user = query.getSingleResult();
        return user;
    }

    @Override
    public Friend searchFriendByUserFriendEmail(String userEmailAddress, String friendEmailAddress) {
        TypedQuery<Friend> query = entityManager.createQuery("select friendDetail from Friend friendDetail WHERE friendDetail.userEmailAddress = :userEmailAddress and friendDetail.friendEmailAddress = :friendEmailAddress",Friend.class);
        query.setParameter("userEmailAddress", userEmailAddress);
        query.setParameter("friendEmailAddress", friendEmailAddress);
        Friend friend = null;
        try {
            friend = query.getSingleResult();
        }catch(NoResultException e) {
            return null;
        }
        return friend;
    }

    @Override
    public List<String> searchCommonFriends(String userEmailAddress, String friendEmailAddress) {
        List<String> friendStrList = new ArrayList<String>();
        Query query = entityManager.createNativeQuery
                (" SELECT f.FRIEND_EMAIL_ADDRESS from T_USER_FRIENDS f WHERE f.USER_EMAIL_ADDRESS = ? " +
                " INTERSECT " +
                " SELECT cf.FRIEND_EMAIL_ADDRESS from T_USER_FRIENDS cf WHERE cf.USER_EMAIL_ADDRESS = ? ");
        query.setParameter(1, userEmailAddress);
        query.setParameter(2, friendEmailAddress);

        try {
            friendStrList = query.getResultList();
        }catch(NoResultException e) {
            return null;
        }
        return friendStrList;
    }
}
