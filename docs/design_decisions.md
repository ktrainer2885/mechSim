# BattleTechSim Design Decisions

This document outlines the key decisions and reasoning behind the architecture and implementation strategy of
BattleTechSim. It complements the design document by explaining **why** we structured the system the way we did, with
context for both current choices and future flexibility.

---

## Language and Ecosystem

**Decision:** Use Java for the prototype implementation.

**Rationale:**
Java offers strong object-oriented capabilities, a large ecosystem of tools, and sufficient performance for simulating
thousands of battles in memory. It also makes multithreading and modularization relatively straightforward. While other
languages (e.g., Rust, C++) may offer higher performance, the tradeoff in development speed and complexity is not
justified for the initial phase. Java strikes a good balance between productivity and power.

---

## Source Format: `.mtf` Files

**Decision:** Use MegaMek `.mtf` files as the source of mech definitions.

**Rationale:**
These files are already widely available, well-structured, and accepted in the community. Reusing them eliminates the
need to build a new schema or database of mechs. It also allows quick integration of over 4,000 unit variants without
manual conversion.

---

## Simulation Architecture

**Decision:** Modular turn-based simulation engine with support for N vs N battles.

**Rationale:**
While the prototype focuses on 1v1 battles on a flat map, the engine is designed from the start to handle teams and
multiple sides. This simplifies future expansion and avoids needing to rewrite core systems. Each combat phase (
initiative, movement, combat, heat, resolution) is a modular unit.

---

## AI Control Strategy

**Decision:** Abstract all player decisions behind a `Controller` interface.

**Rationale:**
This approach decouples the simulation logic from how decisions are made. It allows interchangeable control sources:
humans, scripted bots, tactical engines, or ML models. AI complexity can grow without touching core combat logic.

---

## Logging Format

**Decision:** Use JSON for structured combat logs.

**Rationale:**
JSON is human-readable, easy to parse in most languages, and widely compatible with data pipelines. Logging every
action, state change, and outcome enables replays, audit trails, and training data generation. The format is designed to
be stable, versioned, and extendable.

---

## Feature Extraction and ML

**Decision:** Build a post-processing pipeline that converts logs into feature vectors.

**Rationale:**
This separates simulation from analysis. Logs remain the authoritative record; separate code extracts weapon
effectiveness, heat efficiency, win rates, and other features. This architecture allows statistical evaluation and
machine learning without modifying the simulation itself.

---

## Parallelism and Performance

**Decision:** Batch simulations via `SimulationRunner`, with optional parallel execution.

**Rationale:**
Running thousands of simulations requires performance awareness. By batching matchups and enabling parallel execution,
the system can scale with hardware. Deterministic seeds ensure reproducibility for experiments.

---

## Future-Proofing

**Decision:** Design core systems with modularity and pluggability.

**Rationale:**
Future plans include alternate rulebooks, custom mechs, GUI visualization, AI evaluation, and large-scale simulations.
By using interfaces and composition patterns, the system can evolve without breaking past features. Optional rules and
new unit types can be added incrementally.

---

This document will be updated as new tradeoffs emerge or core systems change.

## 2025-06-28: Switched to JavaFX UI Prototype

- Created branch `mechSim-1`
- Reason: CLI not user-friendly, planning to expand to GUI anyway
- Decision: Use JavaFX with Gradle, FXML for clean separation
- Alternatives considered: Swing, external web frontend, CLI only