## 💍 Wedding Planner Application

A Java application for managing weddings, tracking guest lists (including plus-ones), and calculating total wedding costs with VAT.

## 📦 Project Structure

ie.spring.labs
├── repository
│   ├── WeddingRepository.java
│   ├── MockWeddingRepoImpl.java
│   └── WeddingNotFoundException.java
│
├── repository.entities
│   ├── Person.java
│   ├── Guest.java
│   └── Wedding.java
│
├── service
│   ├── CostService.java
│   ├── CostServiceImpl.java
│   └── dto
│       └── WeddingCostBreakdown.java

## 🚀 Features
- Add and store mock wedding data in memory to simulate the repository layer.
- Handle guest and plus-one tracking.
- Calculate total number of attendees per wedding.
- Compute total cost including VAT.
- Clean output formatting using Java record types.
- Designed with separation of concerns (Repository vs Service).

## 🛠 Technologies Used
- Java 17+ but Kotlin could easily be used. However as most systems are currently in Java we are sticking with that.
- Lombok (for boilerplate reduction)
- Modern Java Records
- Optional API
- Layered architecture (Repository → Service → DTO)

## Unit Testing
Unit tests have been provided in both Java and Kotlin.

## ⚙ Configuration
By default, the VAT rate is hardcoded in the service layer (`0.15` or 15%). You can extract it to a config class or properties file for better flexibility (especially when using frameworks like Spring Boot).

## 🙋‍♀️ Author
Created for educational purposes to demonstrate good practices around layering, DTOs, and testable design.

###############################################################################################################################################################################
## TODO ##
By the end of this lab, you should know
- how to create beans using Java with @Configuration and @Bean
- how to access the beans from the application context

[ ] Create a new package for the configurations
[ ] Add a new Java class for the beans configurations annotating it as @Configuration.
[ ] Add the following code to main()
// Creates a Spring application context using the specified Java-based configuration class
ApplicationContext context = new AnnotationConfigApplicationContext(YourConfigurationClass.class);

// Retrieves all the bean definition names loaded into the context and prints them to the console.
Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);

[ ] Run main and you should see the names of the Spring managed beans including the configuration bean you just created.

[ ] Create a method that returns a WeddingRepository interface, returning the in-memory implementation we worked on.
    Annotate this method as @Bean. Run main() again to confirm that Spring is managing this bean.

    The bean's name is the name of method. If you don't like that name use @Bean("aDifferentName")

[ ] Using the same approach, create a WeddingCostService bean implementated using WeddingCostServiceImplementation.
    This requires that the WeddingRepository bean be injected so go ahead and do that.
    Run main to confirm that Spring is managing this bean.

[ ] in main(), retrieve the Spring-managed beans from the context, by replacing
        WeddingRepository weddingRepository = new InMemoryWeddingRepoImpl();
        WeddingCostService costService = new WeddingCostServiceImplementation(weddingRepository);
    with
        WeddingCostService costService = (WeddingCostService) context.getBean("yourBeanName");

    The weddingRepository object was only needed in POJO to inject into the WeddingCostServiceImplementation object
    but as that has already been done by Spring, we don't need it here.

    This accesses the bean by its name, but if there is only one such bean then it can be accessed by its types
        WeddingCostService costService = context.getBean(WeddingCostService.class);


[ ] Add @Slf4j as a class-level annotation to the WeddingCostServiceImplementation class to create a classs-level
    logger (Lombok).
    Add a method to this class annotated as @PostConstruct and use log.info() to log that the bean has been constructed.
    Add a method to this class annotated as @PreDestroy and use log.info() to log that the bean has been constructed.
    The post-construct logging should work but perhaps the pre-destroy doesn't work?
    This relates to the lifecycle of the spring beans. @PreDestroy methods are triggered when the context shuts down. Without
    explicit shutdown this doesn't work.
    Add context.close() as the last line of the main() method. Run the application to see the logging information.

[ ] Add postconstruct and predestroy methods with logging to the other beans.