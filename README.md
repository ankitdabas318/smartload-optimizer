## Notes
- Uses bitmask Dynamic Programming (2^n) optimized with early pruning
- Handles hazmat isolation and time-window compatibility
- Stateless service (safe for horizontal scaling)
- Money handled strictly in integer cents (long)
- In-memory computation only (no database)
