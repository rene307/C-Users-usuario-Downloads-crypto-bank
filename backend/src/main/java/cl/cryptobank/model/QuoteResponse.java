package cl.cryptobank.model;

public record QuoteResponse(
        Asset asset,
        TradeType type,
        double priceClp,
        double amountClp,
        double commissionClp,
        double netClp,
        double cryptoAmount
) {}
