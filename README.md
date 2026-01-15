# Hytale Plugin Template

A minimal template for developing plugins for **Hytale**

---

## Requirements

- **Java SDK 25**
- A valid **Hytale Server** distribution (`HytaleServer.jar`)

---

## Project Structure

Before running the project, your root directory should look like this:

```
.
├── server/
│   └── HytaleServer.jar
├── src/
├── build.gradle(.kts)
└── settings.gradle(.kts)
```

---

## Pre‑Installation ⚙️

1. **Create a `server` directory** in the project root:
   ```bash
   mkdir server
   ```

2. **Copy `HytaleServer.jar`** into the `server` directory:
   ```
   server/HytaleServer.jar
   ```

---

## Game Assets Location

By default, the game assets path is resolved from:

```
AppData\Roaming\any
```

> You can change or override this path in the run configuration or Gradle settings (build.gradle.kts) if needed.