package ie.spring.labs

import ie.spring.labs.repository.WeddingRepository
import ie.spring.labs.repository.entities.Person
import ie.spring.labs.repository.entities.Wedding
import ie.spring.labs.service.WeddingCostServiceImplementation
import ie.spring.labs.service.WeddingNotFoundException
import ie.spring.labs.service.dto.WeddingCostBreakdown
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Optional

class WeddingCostServiceImplementationTest {
    private val mockRepo: WeddingRepository = mock()
    private val service = WeddingCostServiceImplementation(mockRepo)

    @Test
    fun `calculateWeddingCost returns correct cost breakdown`() {
        val weddingId = "wedding123"
        val costPerGuest = 100.0
        val guestCount = 10

        val wedding =
            Wedding(
                weddingId,
                Person(1, "Jane", "Doe"),
                Person(2, "John", "Smith"),
                costPerGuest,
                emptyList(), // Guests not used directly here
            )

        whenever(mockRepo.findById(weddingId)).thenReturn(Optional.of(wedding))
        whenever(mockRepo.getNumberOfGuests(weddingId)).thenReturn(guestCount)

        val result: WeddingCostBreakdown = service.calculateWeddingCost(weddingId)

        val expectedBase = 1000.0
        val expectedVat = 150.0
        val expectedTotal = 1150.0

        assertEquals(expectedBase, result.baseCost, 0.001)
        assertEquals(expectedVat, result.vatCost, 0.001)
        assertEquals(expectedTotal, result.totalCost, 0.001)

        verify(mockRepo).findById(weddingId)
        verify(mockRepo).getNumberOfGuests(weddingId)
    }

    @Test
    fun `calculateWeddingCost throws exception for missing wedding`() {
        val weddingId = "missingWedding"
        whenever(mockRepo.findById(weddingId)).thenReturn(Optional.empty())
        val exception =
            assertThrows<WeddingNotFoundException> {
                service.calculateWeddingCost(weddingId)
            }
        assertEquals("No wedding found for ID: missingWedding", exception.message)
    }
}
