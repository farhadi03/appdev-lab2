package ie.spring.labs.repository;

import ie.spring.labs.repository.entities.Person;
import ie.spring.labs.repository.entities.Guest;
import ie.spring.labs.repository.entities.Wedding;

import java.util.*;

public class InMemoryWeddingRepoImpl implements WeddingRepository {
    private final Map<String, Wedding> weddings = new HashMap<>();

    public InMemoryWeddingRepoImpl() {
        // ===== Wedding 1 ====

        Guest guest1 = new Guest(1L, new Person(3, "Aoife", "Kelly"), null,
                "087-111-2233", "aoife.kelly@example.ie", "12 Green Road, Galway", "wedding1");

        Guest guest2 = new Guest(2L,
                new Person(4, "Sean", "Doyle"),
                new Person(5, "Orla", "Byrne"),
                "085-998-7766", "sean.doyle@example.ie", "44 Oak Lane, Cork", "wedding1");

        List<Guest> guests1 = new ArrayList<>(Arrays.asList(guest1, guest2));
        Person gettingMarried1 = new Person(1, "Sinead", "OConnor");
        Person gettingMarried2 = new Person(2, "Padraig", "Murphy");
        Wedding wedding1 = new Wedding("wedding1", gettingMarried1, gettingMarried2, 90.00, guests1);
        weddings.put("wedding1", wedding1);

        // ===== Wedding 2 =====
        Guest guest3 = new Guest(3L,
                new Person(8, "Cian", "Foley"),
                null,
                "083-222-3344", "cian.foley@example.ie", "78 Harbour St, Limerick", "wedding2");

        Guest guest4 = new Guest(4L,
                new Person(9, "Gillian", "Walsh"),
                new Person(10, "Thomas", "Keane"),
                "086-777-8899", "grainne.walsh@example.ie", "9 Riverbank, Waterford", "wedding2");

        Guest guest5 = new Guest(5L,
                new Person(11, "Ruth", "McGrath"),
                new Person(12, "Marie", "Buckley"),
                "089-345-6789", "ronan.mcgrath@example.ie", "34 Pine Crescent, Sligo", "wedding2");

        List<Guest> guests2 = new ArrayList<>(Arrays.asList(guest3, guest4, guest5));
        Person gettingMarried3 = new Person(6, "Niamh", "Brennan");
        Person gettingMarried4 = new Person(7, "Eoin", "OBrien");
        Wedding wedding2 = new Wedding("wedding2", gettingMarried3, gettingMarried4, 110.00, guests2);
        weddings.put("wedding2", wedding2);
    }

    @Override
    public int getNumberOfGuests(String id) {
        Wedding wedding = weddings.get(id);
        if (wedding == null) return 0;

        return wedding.getGuests().stream()
                .mapToInt(g -> g.getPlusOne() != null ? 2 : 1)
                .sum();
    }


    // Not finding a wedding is not an error with the database — it simply means the ID does not exist.
    // It is now considered best practice to return Optional<> thus allowing the service layer
    // designers using this repo layer to handle the issue however they see fit. However there
    // is no problem with the database hence the Optional is returned
    @Override
    public Optional<Wedding> findById(String id) {
        return Optional.ofNullable(weddings.get(id));
    }


    public void addWedding(Wedding wedding) {
        weddings.put(wedding.getWeddingId(), wedding);
    }

    public void clear() {
        weddings.clear();
    }
}
