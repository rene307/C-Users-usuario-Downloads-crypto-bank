package cl.cryptobank.model;

public record TradeRequest(Asset asset, TradeType type, double amountClp) {}
