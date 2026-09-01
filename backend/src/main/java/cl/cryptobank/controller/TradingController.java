package cl.cryptobank.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.cryptobank.model.PortfolioResponse;
import cl.cryptobank.model.TradeRequest;
import cl.cryptobank.service.TradingService;

@RestController
@RequestMapping("/api")
// @CrossOrigin(origins = "http://localhost:8100")
@CrossOrigin(origins = {
    "http://localhost:8100",
    "http://192.168.56.1:8100"
})
public class TradingController {

    private final TradingService tradingService;

    public TradingController(TradingService tradingService) {
        this.tradingService = tradingService;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }

    @GetMapping("/portfolio")
    public PortfolioResponse portfolio() {
        return tradingService.portfolio();
    }

    @PostMapping("/quote")
    public ResponseEntity<?> quote(@RequestBody TradeRequest request) {
        try {
            return ResponseEntity.ok(tradingService.quote(request));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/trade")
    public ResponseEntity<?> trade(@RequestBody TradeRequest request) {
        try {
            return ResponseEntity.ok(tradingService.trade(request));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }
}
