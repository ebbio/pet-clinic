# PetClinic — Copilot Instructions

## Project Context

- **Platform**: PetClinic — a Spring Boot application for managing pet owners, pets, veterinarians, and visits.
- **Language**: Java 17
- **Framework**: Spring Boot 3.5.x
- **Database**: PostgreSQL (primary), H2 (development/test)
- **Auth**: JWT-based stateless authentication (TO BE CONFIRMED: not yet implemented in codebase)
- **Architecture**: Stateless REST + Thymeleaf MVC, JPA, Caffeine cache
- **Build tools**: Maven (`./mvnw`) and Gradle (`./gradlew`)
- **Infrastructure**: Docker Compose, Kubernetes (k8s/)

---

## Coding Standards

- Follow existing package structure: `org.springframework.samples.petclinic.<module>`
- Use Spring conventions: `@Controller`, `@Service`, `@Repository`, `@Entity`
- Use constructor injection; avoid field injection with `@Autowired`
- All public methods in services and controllers must be documented with Javadoc when non-trivial
- Follow the Spring Java Format (enforced by `spring-javaformat-maven-plugin`); run `./mvnw spring-javaformat:apply` before committing
- Use Bean Validation annotations (`@NotBlank`, `@Size`, etc.) for input validation
- Prefer `Optional<T>` over null returns in repositories and services

---

## Architecture Constraints

- **Stateless**: No server-side session state. Use JWT tokens for authentication context.
- **JWT Auth**: All protected endpoints must validate a JWT bearer token. Do not store auth state in the application.
- **Database**: Use PostgreSQL profile (`spring.profiles.active=postgres`) in production and staging. H2 is only for local development.
- **Schema management**: SQL scripts in `src/main/resources/db/` manage schema. Do not use `ddl-auto=create` in production profiles.
- **Caching**: Use Caffeine cache for read-heavy data (e.g., vet specialties, pet types). Cache must be invalidated on write operations.
- **No breaking changes** to existing REST API contracts without an ADR.

---

## Testing Requirements

- Every new feature must include unit tests in `src/test/java/`
- Integration tests that require a database must use Testcontainers (PostgreSQL) or the existing `docker-compose` test setup
- Minimum test coverage target: **80%** for new code (TO BE CONFIRMED)
- Use `@SpringBootTest` for integration tests; `@WebMvcTest` for controller-only tests
- Mock external dependencies with Mockito in unit tests
- Test class naming: `<ClassName>Tests.java`

---

## Security Rules

- Never log JWT tokens, passwords, or sensitive user data
- Validate all inputs at the controller layer using Bean Validation
- Sanitize user-provided data before using it in queries (use Spring Data JPA to avoid SQL injection)
- Do not expose internal stack traces to the client; use `@ControllerAdvice` for global error handling
- Use HTTPS in all non-local environments (TO BE CONFIRMED: enforced at infra level)

---

## Forbidden Practices

- No `System.out.println` — use SLF4J logger (`private static final Logger log = LoggerFactory.getLogger(...)`)
- No hardcoded credentials or secrets in source code
- No `@SuppressWarnings("unchecked")` without inline justification comment
- No direct SQL strings outside of `src/main/resources/db/` scripts
- No commented-out dead code in PRs
- No use of `@Transactional` on controller methods

---

## Consistency Rules

- New modules must follow the `owner`, `vet` module patterns (controller + repository + entity)
- File naming: `<Entity>.java`, `<Entity>Controller.java`, `<Entity>Repository.java`
- REST endpoints: use lowercase kebab-case paths (e.g., `/api/pet-types`)
- Error responses must use a consistent structure (TO BE CONFIRMED: define standard error DTO)
- Database table names: lowercase with underscores (e.g., `pet_types`)
- Use `@Transactional(readOnly = true)` for read-only service methods

---

## Documentation Rules

- Update `ARCHITECTURE.md` (or `docs/architecture/overview.md` once reorganized) when adding new components or changing data flow
- Update `BUSINESS_RULES.md` (or `docs/business/business-rules.md` once reorganized) when implementing domain rules
- Create an ADR based on `ADR_TEMPLATE.md` (or in `docs/adr/` once reorganized) for any significant architectural decision
- Keep `README.md` updated with any new run instructions or configuration changes
