package fr.persistence.copy;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter;

@Configuration
public class OpenJpaConfiguration {

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new OpenJpaVendorAdapter();
    }

    @Bean
    public Properties jpaProperties() {
        return new Properties();
    }
}
