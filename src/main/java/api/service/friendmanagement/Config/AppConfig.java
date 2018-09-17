package api.service.friendmanagement.Config;

import api.service.friendmanagement.Repository.*;
import api.service.friendmanagement.Service.FriendsManagementService;
import api.service.friendmanagement.Service.FriendsManagementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class AppConfig {

    @Autowired
    DataSource dataSource;

    /**
     * References to the *Repository classes, for creating a FriendsManagementService instance
     * @Return new repository instance
     */

    @Bean
    public FriendManagementRepository friendManagementRepository(){
        return new FriendManagementRepositoryImpl();
    }

    @Bean
    public UserDetailsRepository userManagementRepository(){
        return new UserDetailsRepositoryImpl();
    }

    @Bean
    public NotificationRepository notificationRepository(){return new NotificationRepositoryImpl();}

    /**
     * A new service has been created for accessing User and friends information.
     *
     * @return The new friendsManagementService instance.
     */

    @Bean
    public FriendsManagementService friendsManagementService() {
        return new FriendsManagementServiceImpl(friendManagementRepository(),userManagementRepository(),notificationRepository());
    }

}
