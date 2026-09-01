package cl.cryptobank.service;

import cl.cryptobank.model.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class TradingService {

    private static final double COMMISSION_RATE = 0.0125; // 1.25%
    private double balanceClp = 5_000_000;
    private double btc = 0.0;
    private double eth = 0.0;

    // Precios de DEMO. En producción reemplazar por un proveedor de mercado.
    private double btcPriceClp = 95_000_000;
    private double ethPriceClp = 3_600_000;

    private final List<TradeResult> movements = new ArrayList<>();

    public synchronized PortfolioResponse portfolio() {
        return new PortfolioResponse(
                balanceClp,
                btc,
                eth,
                btcPriceClp,
                ethPriceClp,
                COMMISSION_RATE,
                Collections.unmodifiableList(new ArrayList<>(movements))
        );
    }

    public synchronized QuoteResponse quote(TradeRequest request) {
        validateRequest(request);
        double price = priceFor(request.asset());
        double commission = request.amountClp() * COMMISSION_RATE;

        if (request.type() == TradeType.BUY) {
            double net = request.amountClp() - commission;
            double crypto = net / price;
            return new QuoteResponse(request.asset(), request.type(), price, request.amountClp(), commission, net, crypto);
        }

        double gross = request.amountClp();
        double net = gross - commission;
        double crypto = gross / price;
        return new QuoteResponse(request.asset(), request.type(), price, gross, commission, net, crypto);
    }

    public synchronized TradeResult trade(TradeRequest request) {
        QuoteResponse quote = quote(request);

        if (request.type() == TradeType.BUY) {
            if (balanceClp < request.amountClp()) {
                throw new IllegalArgumentException("Saldo CLP insuficiente");
            }
            balanceClp -= request.amountClp();
            addCrypto(request.asset(), quote.cryptoAmount());
        } else {
            if (cryptoBalance(request.asset()) < quote.cryptoAmount()) {
                throw new IllegalArgumentException("Saldo de " + request.asset() + " insuficiente");
            }
            addCrypto(request.asset(), -quote.cryptoAmount());
            balanceClp += quote.netClp();
        }

        TradeResult result = new TradeResult(
                UUID.randomUUID().toString(),
                Instant.now(),
                request.asset(),
                request.type(),
                quote.priceClp(),
                quote.amountClp(),
                quote.commissionClp(),
                quote.cryptoAmount(),
                balanceClp,
                btc,
                eth
        );
        movements.add(0, result);
        return result;
    }

    private void validateRequest(TradeRequest request) {
        if (request == null || request.asset() == null || request.type() == null) {
            throw new IllegalArgumentException("Solicitud inválida");
        }
        if (request.amountClp() < 1_000) {
            throw new IllegalArgumentException("El mínimo es $1.000 CLP");
        }
    }

    private double priceFor(Asset asset) {
        return asset == Asset.BTC ? btcPriceClp : ethPriceClp;
    }

    private double cryptoBalance(Asset asset) {
        return asset == Asset.BTC ? btc : eth;
    }

    private void addCrypto(Asset asset, double amount) {
        if (asset == Asset.BTC) btc += amount;
        else eth += amount;
    }
}
