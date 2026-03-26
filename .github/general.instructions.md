---
applyTo: "**"
---

# General Domain Instructions — PetClinic

> **Location note**: This file belongs in `.github/instructions/general.instructions.md`.
> Create the `.github/instructions/` directory to organize instruction files there.

---

## Domain Model

- **Owner**: A person who owns one or more pets. Has: first name, last name, address, city, telephone.
- **Pet**: An animal owned by an Owner. Has: name, birth date, type (e.g., cat, dog).
- **PetType**: Lookup entity for pet categories (e.g., cat, dog, bird).
- **Vet**: A veterinarian with a set of specialties.
- **Specialty**: A medical specialty a vet can have (e.g., radiology, surgery).
- **Visit**: A recorded veterinary visit for a pet. Has: date, description.

---

## Business Invariants

- An Owner must have at least one valid contact (telephone).
- A Pet must have a name, a birth date, and a PetType.
- A Visit must be associated with a Pet and must have a date and description.
- A Vet may have zero or more Specialties.
- Pet birth date must be in the past.

---

## Naming Conventions

- Entity classes: singular PascalCase (e.g., `Owner`, `Pet`, `Visit`)
- Repository interfaces: `<Entity>Repository` extending `JpaRepository` or `CrudRepository`
- Controller classes: `<Entity>Controller`
- Service classes (if added): `<Entity>Service`
- Test classes: `<ClassName>Tests.java`

---

## API Design Rules

- RESTful resource paths: `/owners`, `/owners/{id}`, `/owners/{ownerId}/pets/{petId}/visits`
- HTTP verbs: GET (read), POST (create), PUT (update), DELETE (remove)
- JWT bearer token required on all non-public endpoints [TO BE CONFIRMED: not yet implemented]
- Return HTTP 404 when a requested resource does not exist
- Return HTTP 400 for validation failures with field-level error details
- Return HTTP 409 for conflict situations (e.g., duplicate resource)

---

## Spring Boot Conventions

- Use `application-postgres.properties` for PostgreSQL configuration
- Credentials via env vars: `POSTGRES_URL`, `POSTGRES_USER`, `POSTGRES_PASS`
- Actuator endpoints exposed at `/actuator/*`; restrict in production

---

## Statelessness Rules

- No `HttpSession` usage in controllers or services
- `SessionCreationPolicy.STATELESS` when Spring Security is added
- All request context derived from the JWT token in the `Authorization` header

---

## Testing Domain Rules

- Use realistic test data (valid pet names, owner addresses, vet specialties)
- Do not use production credentials in tests
- Integration tests use an isolated PostgreSQL instance via Testcontainers
- Unit tests use Mockito for mocking dependencies
