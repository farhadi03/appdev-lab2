package ie.spring.labs.service.dto;

import org.jetbrains.annotations.NotNull;

public record WeddingCostBreakdown(
        double baseCost,
        double vatCost,
        double totalCost
) {
    @Override
    @NotNull
    public String toString() {
        return String.format("""
                Wedding Cost Breakdown:
                Base Cost: €%.2f
                VAT Amount: €%.2f
                Total Cost (incl. VAT): €%.2f
                """, baseCost, vatCost, totalCost);
    }
}
