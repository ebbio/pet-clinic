# Architecture Overview — PetClinic

> **Location note**: This file belongs in `docs/architecture/overview.md`.
> Create the `docs/` directory structure to reorganize it there.

## Classification

- **Type**: Backend service with server-rendered frontend (Thymeleaf MVC)
- **Pattern**: Spring MVC + Repository pattern (no dedicated service layer currently)

---

## Components

| Component | Technology | Responsibility |
|-----------|-----------|----------------|
| Web Layer | Spring MVC + Thymeleaf | HTTP request handling, HTML view rendering |
| Data Layer | Spring Data JPA + Hibernate | Database access, ORM, query abstraction |
| Cache Layer | Caffeine (via Spring Cache) | In-memory caching of read-heavy reference data |
| Database | PostgreSQL (prod), H2 (dev/test) | Persistent storage |
| Auth | JWT — **TO BE CONFIRMED: not yet implemented** | Stateless authentication |
| Build | Maven (`./mvnw`) / Gradle (`./gradlew`) | Build, test, package |
| CI/CD | GitHub Actions (`.github/workflows/`) | Automated build and test |
| Container | Docker Compose (`docker-compose.yml`) | Local dev PostgreSQL setup |
| Orchestration | Kubernetes (`k8s/`) | Production deployment |

---

## Module Structure

```
org.springframework.samples.petclinic
├── PetClinicApplication.java            # Spring Boot entry point
├── PetClinicRuntimeHints.java           # AOT/GraalVM hints
├── owner/                               # Owner, Pet, PetType, Visit management
│   ├── Owner.java / OwnerController.java / OwnerRepository.java
│   ├── Pet.java / PetController.java / PetType.java
│   ├── PetTypeFormatter.java / PetTypeRepository.java / PetValidator.java
│   └── Visit.java / VisitController.java
├── vet/                                 # Veterinarian and Specialty management
│   ├── Vet.java / VetController.java / VetRepository.java
│   └── Specialty.java / Vets.java
├── model/                               # Shared base entities
│   ├── BaseEntity.java / NamedEntity.java / Person.java
└── system/                              # System config (cache, error handling)
```

---

## Request Flow

```
HTTP Request
  → Spring DispatcherServlet
    → @Controller (Bean Validation on input)
      → Spring Data JPA Repository
        → PostgreSQL (Hibernate ORM)
      ← Entity
    ← Spring Model + Thymeleaf template
  ← HTTP Response (HTML)
```

---

## Authentication Flow (TO BE CONFIRMED — not yet implemented)

```
HTTP Request with: Authorization: Bearer <JWT>
  → JWT Filter (validates and parses token)
    → SecurityContextHolder populated with authenticated user
      → @Controller executes with auth context
```

Design constraints when implementing:
- Use `SessionCreationPolicy.STATELESS` — no `HttpSession`
- JWT secret from environment variable (never hardcoded)
- Validate token signature, expiry, and issuer on every request
- HTTP 401 for missing/invalid token; HTTP 403 for insufficient permissions

---

## Database

- **Development**: H2 in-memory (default profile, auto-initialised via `schema.sql` + `data.sql`)
- **Production**: PostgreSQL — activate with `spring.profiles.active=postgres`
- **Schema management**: SQL scripts in `src/main/resources/db/postgres/`; `ddl-auto=none`
- **Credentials**: via env vars `POSTGRES_URL`, `POSTGRES_USER`, `POSTGRES_PASS`

---

## Constraints

| Constraint | Detail |
|------------|--------|
| Stateless | No server-side session. Auth state in JWT only. |
| No DDL auto | Schema managed via SQL scripts only. |
| Profile-based DB | Use `postgres` profile in staging/production. H2 for dev only. |
| Code format | Must pass Spring Java Format on every commit. |

---

## Assumptions

- JWT implementation is planned but not yet present in the codebase. [TO BE CONFIRMED]
- Spring Security dependency will be added when JWT is implemented. [TO BE CONFIRMED]
- A REST/JSON API layer may be added alongside Thymeleaf MVC. [TO BE CONFIRMED]
- Production Actuator endpoint restriction is handled at infra/gateway level. [TO BE CONFIRMED]
