package api.service.friendmanagement;

import api.service.friendmanagement.Config.SystemConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SystemConfig.class)
@EnableConfigurationProperties
@EntityScan("friendmanagement")
public class FriendManagementApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(FriendManagementApplication.class, args);
	}
}
