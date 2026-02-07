# Mech Sim

**Mech Sim** is a modular and extensible simulation framework for BattleMech combat based on the BattleMech Manual (BMM)
ruleset. The system is designed to simulate 1v1 and team-based battles using real unit data, log combat results for
analysis, and support machine learning for mech evaluation and performance prediction.

---

## Features

- Turn-based simulation engine following BMM rules
- Support for 1v1 and N vs N battles
- `.mtf` file parsing for existing MegaMek mech definitions
- Structured combat logging (JSON-based)
- Batch execution for large-scale experiments
- Designed for extensibility: AI, rule variants, ML integration

---

## Project Structure

```
mechSim/
 src/
    main/java/mechsim/       # Source code
    test/java/mechsim/       # Unit tests
 docs/                            # Design documentation
 data/                            # Mech files, scenario configs
 logs/                            # Output combat logs
 scripts/                         # Future ML or analytics helpers
 build.gradle                     # Gradle build file
 settings.gradle                  # Gradle project settings
 README.md                        # Project overview (this file)
 .gitignore
```

---

## Getting Started

### Prerequisites

- Java 21+ installed
- IntelliJ IDEA (recommended)
- Git

### Build & Run

1. Clone the repository:
   ```bash
   git clone https://github.com/YOUR_USERNAME/mechSim.git
   cd mechSim
   ```
2. Open in IntelliJ and allow it to import the Gradle project
3. Build and run using IntelliJ or:
   ```bash
   ./gradlew run
   ```

> By default, the main entry point is `mechsim.Main`.

---

## Documentation

Design and architecture documents can be found in the `docs/` folder:

- `architecture.md`
- `design_decisions.md`

---

## Future Goals

- Team-based simulations (e.g., 4v4, 4v4v4)
- Tactical AI behavior and ML-driven opponents
- Full support for optional/special rules and advanced equipment
- GUI-based visualizations and replay tools
- Feature extraction and ML analytics pipeline

---

## License

TBD (Private for now or insert open-source license here)



