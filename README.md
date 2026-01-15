# SmartLoad Optimization API

A stateless Spring Boot service that selects the optimal combination of shipment orders for a truck while respecting weight, volume, route, hazmat, and time-window constraints.

---

## How to Run
```bash
git clone https://github.com/ankitdabas318/smartload-optimizer
cd smartload-optimizer
docker compose up --build

Service runs at:
http://localhost:8080

API
POST /api/v1/load-optimizer/optimize

Notes
Uses bitmask Dynamic Programming (2ⁿ) with early pruning
Ensures hazmat isolation and time-window compatibility
Stateless and horizontally scalable
Money handled strictly in integer cents (long)
In-memory processing only (no database)

Tech Stack

Java 17
Spring Boot 3
Docker
