package ie.spring.labs

import ie.spring.labs.repository.InMemoryWeddingRepoImpl
import ie.spring.labs.repository.entities.Guest
import ie.spring.labs.repository.entities.Person
import ie.spring.labs.repository.entities.Wedding
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InMemoryWeddingRepoImplTests {
    private lateinit var repo: InMemoryWeddingRepoImpl

    @BeforeEach
    fun setUp() {
        repo = InMemoryWeddingRepoImpl()
    }

    @Test
    fun `findById should return existing wedding`() {
        val weddingOpt = repo.findById("wedding1")
        Assertions.assertTrue(weddingOpt.isPresent)
        Assertions.assertEquals("wedding1", weddingOpt.get().weddingId)
    }

    @Test
    fun `findById should return empty for non-existent wedding`() {
        val weddingOpt = repo.findById("nonexistent")
        Assertions.assertFalse(weddingOpt.isPresent)
    }

    @Test
    fun `getNumberOfGuests should count correctly`() {
        val guests = repo.getNumberOfGuests("wedding1")
        // guest1 has no plus-one, guest2 has one -> total = 1 + 2 = 3
        Assertions.assertEquals(3, guests)

        val guests2 = repo.getNumberOfGuests("wedding2")
        // guest3 has no plus-one, guest4 and guest5 do -> 1 + 2 + 2 = 5
        Assertions.assertEquals(5, guests2)
    }

    @Test
    fun `getNumberOfGuests returns 0 for non-existent wedding`() {
        val count = repo.getNumberOfGuests("fake")
        Assertions.assertEquals(0, count)
    }

    @Test
    fun `addWedding should add and retrieve a new wedding`() {
        val person1 = Person(13, "Laura", "Healy")
        val person2 = Person(14, "Derek", "O'Donnell")
        val guest = Guest(6L, person1, null, "087-555-1234", "laura@example.ie", "1 Someplace", "wedding3")
        val wedding = Wedding("wedding3", person1, person2, 150.0, listOf(guest))

        repo.addWedding(wedding)

        val result = repo.findById("wedding3")
        Assertions.assertTrue(result.isPresent)
        Assertions.assertEquals(1, result.get().guests.size)
    }

    @Test
    fun `clear should remove all weddings`() {
        repo.clear()
        Assertions.assertFalse(repo.findById("wedding1").isPresent)
        Assertions.assertFalse(repo.findById("wedding2").isPresent)
    }
}
