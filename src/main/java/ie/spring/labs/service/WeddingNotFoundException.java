package ie.spring.labs.service;

public class WeddingNotFoundException extends RuntimeException {
    public WeddingNotFoundException(String id) {
        super("No wedding found for ID: " + id);
    }
}
