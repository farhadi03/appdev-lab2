package ie.spring.labs.config;

import ie.spring.labs.repository.InMemoryWeddingRepoImpl;
import ie.spring.labs.repository.WeddingRepository;
import ie.spring.labs.service.WeddingCostService;
import ie.spring.labs.service.WeddingCostServiceImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public WeddingRepository weddingRepository() {
        return new InMemoryWeddingRepoImpl();
    }

    @Bean
    public WeddingCostService weddingCostService(WeddingRepository weddingRepository) {
        return new WeddingCostServiceImplementation(weddingRepository);
    }
}

