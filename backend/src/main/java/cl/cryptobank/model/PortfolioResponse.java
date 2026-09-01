package cl.cryptobank.model;

import java.util.List;

public record PortfolioResponse(
        double balanceClp,
        double btc,
        double eth,
        double btcPriceClp,
        double ethPriceClp,
        double commissionRate,
        List<TradeResult> movements
) {}
