package ie.spring.labs;

import ie.spring.labs.repository.InMemoryWeddingRepoImpl;
import ie.spring.labs.repository.entities.Guest;
import ie.spring.labs.repository.entities.Person;
import ie.spring.labs.repository.entities.Wedding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryWeddingRepositoryImplJavaTests {

    private InMemoryWeddingRepoImpl repo;

    @BeforeEach
    public void setUp() {
        repo = new InMemoryWeddingRepoImpl();
    }

    @Test
    public void findById_shouldReturnExistingWedding() {
        Optional<Wedding> weddingOpt = repo.findById("wedding1");
        assertTrue(weddingOpt.isPresent());
        assertEquals("wedding1", weddingOpt.get().getWeddingId());
    }

    @Test
    public void findById_shouldReturnEmptyForNonExistentWedding() {
        Optional<Wedding> weddingOpt = repo.findById("nonexistent");
        assertFalse(weddingOpt.isPresent());
    }

    @Test
    public void getNumberOfGuests_shouldCountCorrectly() {
        int guests1 = repo.getNumberOfGuests("wedding1");
        // guest1 has no plus-one, guest2 has one -> total = 1 + 2 = 3
        assertEquals(3, guests1);

        int guests2 = repo.getNumberOfGuests("wedding2");
        // guest3 has no plus-one, guest4 and guest5 do -> 1 + 2 + 2 = 5
        assertEquals(5, guests2);
    }

    @Test
    public void getNumberOfGuests_shouldReturnZeroForNonExistentWedding() {
        int count = repo.getNumberOfGuests("fake");
        assertEquals(0, count);
    }

    @Test
    public void addWedding_shouldAddAndRetrieveNewWedding() {
        Person person1 = new Person(13, "Laura", "Healy");
        Person person2 = new Person(14, "Derek", "O'Donnell");
        Guest guest = new Guest(6L, person1, null, "087-555-1234", "laura@example.ie", "1 Someplace", "wedding3");
        Wedding wedding = new Wedding("wedding3", person1, person2, 150.0, List.of(guest));

        repo.addWedding(wedding);

        Optional<Wedding> result = repo.findById("wedding3");
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getGuests().size());
    }

    @Test
    public void clear_shouldRemoveAllWeddings() {
        repo.clear();
        assertFalse(repo.findById("wedding1").isPresent());
        assertFalse(repo.findById("wedding2").isPresent());
    }
}
