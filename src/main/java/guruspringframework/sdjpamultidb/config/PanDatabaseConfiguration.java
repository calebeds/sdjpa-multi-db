package guruspringframework.sdjpamultidb.config;

import com.zaxxer.hikari.HikariDataSource;
import guruspringframework.sdjpamultidb.domain.creditcard.CreditCard;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

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
    public DataSource cardPanDataSource(@Qualifier("cardPanDataSourceProperties") DataSourceProperties cardPanDataSourceProperties) {
        return this.cardPanDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean cardPanEntityManagerFactory(
            @Qualifier("cardPanDataSource") DataSource cardPanDatasource,
            EntityManagerFactoryBuilder builder) {
        return builder.dataSource(cardPanDatasource)
                .packages(CreditCard.class)
                .persistenceUnit("pan")
                .build();
    }
}
