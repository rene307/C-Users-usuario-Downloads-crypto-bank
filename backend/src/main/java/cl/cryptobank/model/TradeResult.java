package cl.cryptobank.model;

import java.time.Instant;

public record TradeResult(
        String id,
        Instant date,
        Asset asset,
        TradeType type,
        double priceClp,
        double amountClp,
        double commissionClp,
        double cryptoAmount,
        double balanceClp,
        double btc,
        double eth
) {}
