package api.service.friendmanagement.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import(AppConfig.class)
public class SystemConfig {

    private DataSource dataSource;
    private EntityManagerFactory entityManagerFactory;
    private PlatformTransactionManager transactionManager;


    /**
     * Creates an in-memory "friends" database populated
     * with test data for fast testing
     */
    @Bean
    public DataSource dataSource(){
        return
                (new EmbeddedDatabaseBuilder())
                        .setName("testDb;DB_CLOSE_ON_EXIT=FALSE;MODE=Oracle;INIT=create " +
                                "schema if not exists " +
                                "schema_a\\;create schema if not exists schema_b;" +
                                "DB_CLOSE_DELAY=-1;")
                        .addScript("classpath:/db/schema.sql")
                        .addScript("classpath:/db/data.sql")
                        .build();
    }


    /**
     * @return LocalContainerEntityManagerFactoryBean
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        adapter.setDatabase(Database.H2);

        Properties props = new Properties();
        props.setProperty("hibernate.format_sql", "true");

        LocalContainerEntityManagerFactoryBean emfb =
                new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource());
        emfb.setPackagesToScan("api.service");
        emfb.setJpaProperties(props);
        emfb.setJpaVendorAdapter(adapter);

        return emfb;
    }

    /**
     * create VendorAdapter
     * @return Jpa VendorAdapter instance
     */
    protected JpaVendorAdapter createVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.H2);
        return adapter;
    }

    /**
     * Create JpaProperties
     * @return Properties
     */
    protected Properties createJpaProperties() {
        Properties properties = new Properties();
        // turn on formatted SQL logging (very useful to verify Jpa is
        // issuing proper SQL)
        properties.setProperty("hibenate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        return properties;
    }
    protected final EntityManagerFactory createEntityManagerFactory() {

        // Create a FactoryBean to help create a JPA EntityManagerFactory
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaVendorAdapter(createVendorAdapter());
        factoryBean.setJpaProperties(createJpaProperties());

        // Not using persistence unit or persistence.xml, so need to tell
        // JPA where to find Entities
        factoryBean.setPackagesToScan("api.service");

        // initialize according to the Spring InitializingBean contract
        factoryBean.afterPropertiesSet();

        // get the created session factory
        return (EntityManagerFactory) factoryBean.getObject();
    }

    /**
     * JpaTransactionManager.
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
