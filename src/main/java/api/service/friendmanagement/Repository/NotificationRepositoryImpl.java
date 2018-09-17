package api.service.friendmanagement.Repository;

import api.service.friendmanagement.Entity.Notification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class NotificationRepositoryImpl implements NotificationRepository{
    private EntityManager entityManager;
    public NotificationRepositoryImpl() {
    }

    public NotificationRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void createNotificationDetail(String userEmailAddress, String msg) {
        Notification notification = new Notification();
        notification.setUserEmailAddress(userEmailAddress);
        notification.setUserMessage(msg);
        entityManager.persist(notification);

    }
}
