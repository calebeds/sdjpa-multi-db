package guruspringframework.sdjpamultidb.config;

import com.zaxxer.hikari.HikariDataSource;
import guruspringframework.sdjpamultidb.domain.creditcard.CreditCard;
import guruspringframework.sdjpamultidb.domain.pan.CreditCardPAN;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@EnableJpaRepositories(
        basePackages = "guruspringframework.sdjpamultidb.repositories.pan",
        entityManagerFactoryRef = "cardPanEntityManagerFactory",
        transactionManagerRef = "cardPanTransactionManager"
)
@Configuration
public class PanDatabaseConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties("spring.pan.datasource")
    public DataSourceProperties cardPanDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    @ConfigurationProperties("spring.pan.datasource.hikari")
    public DataSource cardPanDataSource(@Qualifier("cardPanDataSourceProperties") DataSourceProperties cardPanDataSourceProperties) {
        return cardPanDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean cardPanEntityManagerFactory(
            @Qualifier("cardPanDataSource") DataSource cardPanDataSource,
            EntityManagerFactoryBuilder builder) {

        Properties props = new Properties();
        props.put("hibernate.hbm2ddl.auto", "validate");
        props.put("hibernate.physical_naming_strategy",
                "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");

        LocalContainerEntityManagerFactoryBean efb = builder.dataSource(cardPanDataSource)
                .packages(CreditCardPAN.class)
                .persistenceUnit("pan")
                .build();

        efb.setJpaProperties(props);

        return efb;
    }

    @Primary
    @Bean
    public PlatformTransactionManager cardPanTransactionManager(
            @Qualifier("cardPanEntityManagerFactory") LocalContainerEntityManagerFactoryBean cardPanEntityManagerFactory) {
        return new JpaTransactionManager(cardPanEntityManagerFactory.getObject());
    }
}
