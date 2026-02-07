# Roadmap

This roadmap is based on `docs/architecture.md` and `docs/design_decisions.md`.
It focuses on building a small, reliable core before expanding features.

## Short Term (next 2-4 weeks)

- Stabilize project workflow: `./gradlew run` and `./gradlew test` always work.
- Finish parser correctness for one mech file end-to-end.
- Add domain stubs for `Team`, `Pilot`, and `Battle`.
- Create a minimal simulation shell that runs a single round with dummy actions.
- Define a first log format stub and write one log file for a dummy round.

## Medium Term (next 2-3 months)

- Implement the turn loop skeleton: initiative, movement, attack, heat, resolution.
- Build a scenario loader that reads a minimal config and loads mechs into teams.
- Expand parser coverage with tests for 2-3 different `.mtf` files.
- Add per-phase combat logging (JSON) and a tiny summary parser.
- Introduce a simple bot controller with legal, naive decisions.
- Add a CLI entry point (or wire UI buttons) to run a sample battle and show a log summary.
