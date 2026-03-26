# Non-Functional Requirements — PetClinic

> **Location note**: This file belongs in `docs/nfr/quality.md`.
> Create the `docs/` directory structure to reorganize it there.

---

## Performance

| Requirement | Target | Notes |
|-------------|--------|-------|
| API response time (read) | ≤ 200 ms at p95 under normal load | Cached data (pet types, specialties) must hit cache |
| API response time (write) | ≤ 500 ms at p95 | Includes DB transaction commit |
| Concurrent users | ≥ 100 simultaneous users | [TO BE CONFIRMED: baseline load estimate] |
| Cache hit rate | ≥ 90% for pet types and vet specialties | Caffeine in-memory cache |
| Build time | ≤ 5 minutes for full `./mvnw verify` | [TO BE CONFIRMED: CI baseline] |

---

## Security

| Requirement | Constraint |
|-------------|-----------|
| Authentication | JWT bearer token required on all non-public endpoints |
| Token validity | JWT must be validated for signature, expiry, and issuer on every request |
| Transport | HTTPS enforced in all non-local environments [TO BE CONFIRMED: at infra level] |
| Secrets | No credentials or secrets in source code; all from environment variables |
| Input validation | All user inputs validated with Bean Validation annotations at controller layer |
| SQL injection | Prevented via Spring Data JPA (no raw string SQL in application code) |
| Error exposure | No internal stack traces returned to clients; use `@ControllerAdvice` |
| Session | Stateless — no `HttpSession` created at any point |
| Dependency scanning | Dependabot enabled (`.github/dependabot.yml`) for automated vulnerability alerts |

---

## Availability

| Requirement | Target | Notes |
|-------------|--------|-------|
| Uptime | 99.5% monthly [TO BE CONFIRMED] | Excludes planned maintenance windows |
| Health check | `/livez` and `/readyz` Actuator endpoints | Used by Kubernetes liveness/readiness probes |
| Graceful shutdown | Spring Boot graceful shutdown enabled | In-flight requests complete before shutdown |
| Database failover | Relies on PostgreSQL availability [TO BE CONFIRMED: replica/failover setup] | |

---

## Maintainability

| Requirement | Constraint |
|-------------|-----------|
| Code coverage | ≥ 80% line coverage for new code [TO BE CONFIRMED] |
| Code format | All code must pass Spring Java Format validation (`spring-javaformat-maven-plugin`) |
| Code style | All code must pass Checkstyle (nohttp rules) |
| Documentation | Architecture docs updated for any structural changes |
| ADR | Architectural Decision Records created for significant decisions |

---

## Scalability

| Requirement | Constraint |
|-------------|-----------|
| Stateless design | Application must be horizontally scalable (no server-side state) |
| Session storage | No requirement for distributed session (JWT eliminates this) |
| Kubernetes | Deployment supports multiple replicas (`k8s/petclinic.yml`) |

---

## Assumptions

- Performance targets are initial estimates pending load testing. [TO BE CONFIRMED]
- Uptime SLA is not formally defined yet. [TO BE CONFIRMED]
- HTTPS termination is handled at the infrastructure level (load balancer/ingress). [TO BE CONFIRMED]
