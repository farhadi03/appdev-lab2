package ie.spring.labs.config;

import ie.spring.labs.repository.InMemoryWeddingRepoImpl;
import ie.spring.labs.repository.WeddingRepository;
import ie.spring.labs.service.WeddingCostService;
import ie.spring.labs.service.WeddingCostServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class SpringConfigTest {

    @Test
    public void testConfig() throws NoSuchFieldException, IllegalAccessException {
        // Bootstrap the application
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        SpringConfig springConfig = applicationContext.getBean(SpringConfig.class);
        assertNotNull(springConfig);
        
        WeddingRepository weddingRepository = applicationContext.getBean(WeddingRepository.class);
        assertNotNull(weddingRepository);
        // Which implementation is used?
        assertInstanceOf(InMemoryWeddingRepoImpl.class, weddingRepository);
        
        WeddingCostService weddingCostService = applicationContext.getBean(WeddingCostService.class);
        assertNotNull(weddingCostService);
        // Which implementation is used?
        assertInstanceOf(WeddingCostServiceImplementation.class, weddingCostService);
        
        // ADVANCED
        // Using reflection to check if the WeddingRepository is injected into WeddingCostService
        // Access the class' field and set it from private to public
        Field repositoryField = WeddingCostServiceImplementation.class.getDeclaredField("weddingRepository");
        repositoryField.setAccessible(true);
        // Now that the field has been changed to public, we can access it here.
        // This retrieves the actual value of weddingRepository in weddingCostService.
        WeddingRepository injectedRepository = (WeddingRepository) repositoryField.get(weddingCostService);
        // We can now check that it is not null and is the correct type
        assertNotNull(injectedRepository);
        assertInstanceOf(InMemoryWeddingRepoImpl.class, injectedRepository);
    }
}

