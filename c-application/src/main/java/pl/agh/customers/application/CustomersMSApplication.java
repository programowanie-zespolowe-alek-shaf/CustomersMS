package pl.agh.customers.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(
        basePackages = {
                "pl.agh.customers"
        }
)
@EntityScan("pl.agh.customers")
@EnableJpaRepositories("pl.agh.customers")
public class CustomersMSApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomersMSApplication.class, args);
    }

}
