package fr.persistence.copy;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

@Configuration
public class EclipseLinkConfiguration {

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new EclipseLinkJpaVendorAdapter();
    }

    @Bean
    public Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put("eclipselink.weaving", "false");
        return properties;
    }
}
