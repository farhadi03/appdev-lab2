package ie.spring.labs.repository;

import ie.spring.labs.repository.entities.Wedding;

import java.util.Optional;

public interface WeddingRepository {
    int getNumberOfGuests(String id) ;
    Optional<Wedding> findById(String id);
}
