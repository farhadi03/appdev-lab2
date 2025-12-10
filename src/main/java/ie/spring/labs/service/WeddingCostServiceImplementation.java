package ie.spring.labs.service;

import ie.spring.labs.repository.entities.Wedding;
import ie.spring.labs.repository.WeddingRepository;
import ie.spring.labs.service.dto.WeddingCostBreakdown;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class WeddingCostServiceImplementation implements WeddingCostService {

    private final WeddingRepository weddingRepository;

    // This would be better not hardcoded but suffices for now.
    // We can edit this later.
    final double taxRate = 0.15;

    public WeddingCostServiceImplementation(WeddingRepository weddingRepository) {
        this.weddingRepository = weddingRepository;
    }

    @PostConstruct
    public void init() {
        log.info("WeddingCostServiceImplementation bean has been created and initialized");
    }

    @PreDestroy
    public void cleanup() {
        log.info("WeddingCostServiceImplementation bean is being destroyed");
    }

    @Override
    public WeddingCostBreakdown calculateWeddingCost(String weddingId) throws WeddingNotFoundException {
        Optional<Wedding> optionalWedding = weddingRepository.findById(weddingId);
        if (optionalWedding.isPresent()) {
            Wedding wedding = optionalWedding.get();
            double baseCost = weddingRepository.getNumberOfGuests(weddingId) * wedding.getCostPerGuest();
            double vatCost = baseCost * taxRate;
            double totalCost = baseCost + vatCost;
            return new WeddingCostBreakdown(baseCost,vatCost,totalCost);
        }
        // Here I have decided to handle this with an exception. It could just as easily
        // return null or Optional<WeddingCostBreakdown> or 0 to flag that the wedding does
        // not exist in the database.
        // Remember: a missing wedding is not a design error — it just means the ID was invalid or not present in the data store.
        throw new WeddingNotFoundException(weddingId);
    }


}
