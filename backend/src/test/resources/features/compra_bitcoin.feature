Feature: Compra de Bitcoin

  Como usuario de Crypto Bank
  quiero realizar operaciones de compra de Bitcoin
  para invertir mi saldo disponible en criptomonedas.

  Scenario: Rechazar compra inferior al monto minimo
    Given que el servicio de trading esta disponible
    When intento comprar BTC por 999 CLP
    Then la operacion debe ser rechazada por monto minimo

  Scenario Outline: Validar montos de compra de Bitcoin
    Given que el servicio de trading esta disponible
    When solicito una cotizacion de compra de BTC por <monto> CLP
    Then el resultado esperado debe ser "<resultado>"

    Examples:
      | monto | resultado |
      | 1000  | aceptado  |
      | 5000  | aceptado  |
      | 999   | rechazado |