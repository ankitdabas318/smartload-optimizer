## Notes
- Uses bitmask Dynamic Programming (2^n) optimized with early pruning
- Handles hazmat isolation and time-window compatibility
- Stateless service (safe for horizontal scaling)
- Money handled strictly in integer cents (long)
- In-memory computation only (no database)

## How to run
```bash
git clone https://github.com/ankitdabas318/smartload-optimizer
cd smartload-optimizer
docker compose up --build
