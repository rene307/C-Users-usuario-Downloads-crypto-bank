package cl.cryptobank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cl.cryptobank.model.Asset;
import cl.cryptobank.model.TradeRequest;
import cl.cryptobank.model.TradeType;

public class TradingServiceTest {

    @Test
    public void quoteDebeRechazarSolicitudNula() {

        TradingService service = new TradingService();

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.quote(null)
        );
    }

    @Test
    public void quoteDebeRechazarMontoMenorAlMinimo() {

        TradingService service = new TradingService();

        TradeRequest request = new TradeRequest(
                Asset.BTC,
                TradeType.BUY,
                999
        );

        IllegalArgumentException exception =
                Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> service.quote(request)
                );

        Assertions.assertTrue(
                exception.getMessage().contains("1.000")
        );
    }
}