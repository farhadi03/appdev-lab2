package ie.spring.labs;

import ie.spring.labs.config.SpringConfig;
import ie.spring.labs.repository.WeddingRepository;
import ie.spring.labs.service.WeddingCostService;
import ie.spring.labs.service.dto.WeddingCostBreakdown;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

public class MainApp {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(SpringConfig.class, args);
        
        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
        
        // Access bean by name
        WeddingRepository weddingRepository = (WeddingRepository) context.getBean("weddingRepository");
        System.out.println("\nWedding details for wedding1:");
        weddingRepository.findById("wedding1").ifPresent(wedding -> System.out.println(wedding));
        
        // Access bean by type
        WeddingCostService costService = context.getBean(WeddingCostService.class);
        WeddingCostBreakdown breakdown = costService.calculateWeddingCost("wedding1");
        System.out.println(breakdown);
        System.out.println();
        breakdown = costService.calculateWeddingCost("wedding2");
        System.out.println(breakdown);
    }
}
