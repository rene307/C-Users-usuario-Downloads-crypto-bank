package cl.cryptobank.bdd;

import org.junit.jupiter.api.Assertions;

import cl.cryptobank.model.Asset;
import cl.cryptobank.model.TradeRequest;
import cl.cryptobank.model.TradeType;
import cl.cryptobank.service.TradingService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CompraBitcoinSteps {

    private TradingService service;
    private boolean operacionRechazada;
    private String resultadoActual;

    @Given("que el servicio de trading esta disponible")
    public void servicioTradingDisponible() {
        service = new TradingService();
        operacionRechazada = false;
        resultadoActual = "";
    }

    @When("intento comprar BTC por {int} CLP")
    public void intentoComprarBTC(int monto) {
        TradeRequest request =
                new TradeRequest(Asset.BTC, TradeType.BUY, monto);

        try {
            service.quote(request);
        } catch (IllegalArgumentException e) {
            operacionRechazada = true;
        }
    }

    @Then("la operacion debe ser rechazada por monto minimo")
    public void operacionRechazadaPorMontoMinimo() {
        Assertions.assertTrue(operacionRechazada);
    }

    @When("solicito una cotizacion de compra de BTC por {int} CLP")
    public void solicitoCotizacionCompraBTC(int monto) {
        TradeRequest request =
                new TradeRequest(Asset.BTC, TradeType.BUY, monto);

        try {
            service.quote(request);
            resultadoActual = "aceptado";
        } catch (IllegalArgumentException e) {
            resultadoActual = "rechazado";
        }
    }

    @Then("el resultado esperado debe ser {string}")
    public void validarResultado(String resultadoEsperado) {
        Assertions.assertEquals(resultadoEsperado, resultadoActual);
    }
}