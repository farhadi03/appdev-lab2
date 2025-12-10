package ie.spring.labs.service;

import ie.spring.labs.service.dto.WeddingCostBreakdown;

public interface WeddingCostService {
    WeddingCostBreakdown calculateWeddingCost(String weddingId) throws Exception;
}
