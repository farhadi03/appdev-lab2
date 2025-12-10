package ie.spring.labs;

import ie.spring.labs.repository.WeddingRepository;
import ie.spring.labs.repository.entities.Person;
import ie.spring.labs.repository.entities.Wedding;
import ie.spring.labs.service.WeddingCostServiceImplementation;
import ie.spring.labs.service.WeddingNotFoundException;
import ie.spring.labs.service.dto.WeddingCostBreakdown;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// The question here is 'Does the cost calculation work?'
// To test this we
// 1. Mock the repository layer's behaviour for findById() (WeddingRepository)
// 2. Calculate the cost of this wedding using the method
// 3. Compare the calculation of the method with what we know to be correct

public class WeddingCostServiceImplementationJavaTest {

    private WeddingRepository mockRepo;
    private WeddingCostServiceImplementation service;

    @BeforeEach
    public void setUp() {
        mockRepo = mock(WeddingRepository.class);
        service = new WeddingCostServiceImplementation(mockRepo);
    }

    // Testing the maths for costing the wedding is correct. This is not testing getNumberOfGuests() is correct
    // so I am hardcoding the number of guests
    @Test
    public void calculateWeddingCost_returnsCorrectCostBreakdown() throws WeddingNotFoundException {
        String weddingId = "wedding123";
        double costPerGuest = 100.0;
        int guestCount = 10;

        Wedding wedding = new Wedding(
                weddingId,
                new Person(1, "Jane", "Doe"),
                new Person(2, "John", "Smith"),
                costPerGuest,
                java.util.Collections.emptyList()
        );
        // set up the mockRepo's behaviour so when the WeddingService
        when(mockRepo.findById(weddingId)).thenReturn(Optional.of(wedding));
        when(mockRepo.getNumberOfGuests(weddingId)).thenReturn(guestCount);

        // Call the calculateWeddingCost() method which in turn calls the findById() method of WeddingService,
        // whose behaviour we have mocked.
        WeddingCostBreakdown result = service.calculateWeddingCost(weddingId);
        // Define the expected values
        double expectedBase = 1000.0;
        double expectedVat = 150;
        double expectedTotal = 1150;
        WeddingCostBreakdown expectedBreakdown = new WeddingCostBreakdown(expectedBase, expectedVat, expectedTotal);
        // Check individual values
        assertEquals(expectedBase, result.baseCost(), 0.001);
        assertEquals(expectedVat, result.vatCost(), 0.001);
        assertEquals(expectedTotal, result.totalCost(), 0.001);

        // Check whole object
        assertEquals(expectedBreakdown, result);
//        assert result.equals(expectedBreakdown);

        // verify each of the following methods happened once
        verify(mockRepo).findById(weddingId);
        verify(mockRepo).getNumberOfGuests(weddingId);
    }

    @Test
    public void calculateWeddingCost_throwsExceptionForMissingWedding() {
        String weddingId = "missingWedding";

        when(mockRepo.findById(weddingId)).thenReturn(Optional.empty());

        WeddingNotFoundException exception = assertThrows(WeddingNotFoundException.class, () -> {
            service.calculateWeddingCost(weddingId);
        });

        assertEquals("No wedding found for ID: missingWedding", exception.getMessage());
    }
}

