# Tenant App

A Spring Boot 4 web application with JPA and PostgreSQL.

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
