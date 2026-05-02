# Tenant App

A multi-tenant web application built with:

- **Java 25** (Temurin)
- **Spring Boot 4.0** (Web MVC, JPA, DevTools, Docker Compose)
- **PostgreSQL** (via Docker Compose)
- **Gradle** (Kotlin DSL)

## Prerequisites

- **SDKMAN!** — used to install the required Java version
- **A container runtime** — such as [Docker Desktop](https://www.docker.com/products/docker-desktop/), [Podman](https://podman.io/), [Rancher Desktop](https://rancherdesktop.io/), or [OrbStack](https://orbstack.dev/) (macOS) — needed to run the PostgreSQL database via `compose.yaml`

## Getting Started

### 1. Install SDKMAN!

```bash
curl -s "https://get.sdkman.io" | bash
```

See [sdkman.io](https://sdkman.io/install) for full instructions.

### 2. Install Java

The project requires **Java 25** (Temurin). With SDKMAN! installed, simply run:

```bash
sdk env install
```

This reads the `.sdkmanrc` file and installs the correct JDK automatically.

### 3. Start the application

The project uses [Spring Boot Docker Compose support](https://docs.spring.io/spring-boot/reference/features/dev-services.html#features.dev-services.docker-compose), so PostgreSQL starts automatically when you run the app — no manual `docker compose up` needed.

```bash
./gradlew bootRun
```

### 4. Run tests

```bash
./gradlew test
```

## Code Formatting

This project uses [Spotless](https://github.com/diffplug/spotless) as the **single source of truth** for code formatting. IDE or editor formatters must not override Spotless — disable any conflicting formatter.

A **Git pre-commit hook** runs `spotlessCheck` and `spotbugsMain` before every commit and **rejects the commit if formatting is incorrect or bugs are found**. Run `./gradlew spotlessApply` to fix formatting, then commit again. All hooks are installed via the [gradle-pre-commit-git-hooks](https://github.com/DanySK/gradle-pre-commit-git-hooks) plugin and set up on the first Gradle invocation — no manual configuration needed.

## Static Analysis

This project uses [SpotBugs](https://spotbugs.github.io/) for static bytecode analysis to detect potential bugs in Java code.

Run manually:

```bash
./gradlew spotbugsMain
```

## Architecture Rules

This project uses [ArchUnit](https://www.archunit.org/) to enforce architectural constraints via unit tests. The codebase follows a **feature-based package structure** (e.g. `tenant/`, `config/`) rather than layer-based packages. The rules are defined in `SpringBootArchitectureTest` and enforce:

- **No circular dependencies** between feature packages
- **Naming conventions** — controllers suffixed with `Controller`, services with `Service`, repositories with `Repository`
- **Best practices** — no `@Autowired` field injection (use constructor injection), controllers must use `@RestController`, controller methods must be public, repositories must be interfaces

The rules run as part of `./gradlew test`.

## Commit Convention

This project enforces [Conventional Commits](https://www.conventionalcommits.org/). A **commit-msg hook** validates every commit message and rejects it if it doesn't follow the format:

```
<type>[optional scope]: <description>
```

Common types: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`, `build`, `ci`, `perf`.

| File type       | Formatter                                                          |
|-----------------|--------------------------------------------------------------------|
| Java            | [Google Java Format](https://github.com/google/google-java-format) |
| Gradle (`.kts`) | [ktfmt](https://github.com/facebook/ktfmt)                         |
| YAML            | Jackson                                                            |
| Markdown        | Trailing whitespace, newline, tabs → spaces                        |
| Misc            | Trailing whitespace, newline, tabs → spaces                        |

Check formatting:

```bash
./gradlew spotlessCheck
```

Auto-fix formatting:

```bash
./gradlew spotlessApply
```
