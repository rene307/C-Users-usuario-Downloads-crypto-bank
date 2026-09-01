# BitBank – prototipo BTC/ETH

Frontend: Ionic + Angular standalone  
Backend: Java 17 + Spring Boot  
Persistencia: ninguna (datos en memoria)  
Comisión demo: 1,25% por compra o venta

## 1. Levantar backend

```bash
cd backend
mvn spring-boot:run
```

API: http://localhost:8080/api

## 2. Levantar frontend

```bash
cd frontend
npm install
npm start
```

Frontend: http://localhost:8100

## Funciones incluidas

- BTC y ETH solamente.
- Saldo demo en CLP.
- Compra y venta.
- Comisión visible antes de ejecutar.
- Cotización previa.
- Historial de movimientos.
- Responsive para escritorio y celular.
- Backend REST Java 17.

## Importante

Este proyecto es un prototipo de interfaz y lógica. No ejecuta operaciones reales, no custodia dinero ni criptomonedas y no incluye autenticación, KYC/AML, seguridad bancaria, conexión a exchange, persistencia, auditoría ni cumplimiento regulatorio. Para operar con dinero real hay que agregar esos componentes antes de producción.
# C-Users-usuario-Downloads-crypto-bank

## 4. Control de versiones

El proyecto utiliza Git y GitHub para el control de versiones.

Ramas utilizadas:

- main: versión principal.
- develop: rama utilizada para desarrollo y pruebas.

Comandos principales:

```bash
git init
git add .
git commit -m "first commit"
git branch -M main
git push -u origin main
git checkout -b develop
git push -u origin develop