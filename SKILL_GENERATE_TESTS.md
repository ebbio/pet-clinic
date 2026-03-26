# Skill: generate-tests

> **Location note**: This file belongs in `skills/generate-tests.md`.
> Create the `skills/` directory to organize skill files there.

---

## Purpose

Generate automated tests (unit, controller, integration) for new or changed code
in the PetClinic Spring Boot application.

---

## When to Use

- A new feature has been implemented and lacks test coverage
- A bug fix needs a regression test
- Acceptance criteria from a GitHub issue are not covered by existing tests
- A refactoring requires existing tests to be updated
- Coverage gaps are found during a test review

---

## Actions

### 1. Analyse the Feature
- Read the acceptance criteria (Given/When/Then) from the issue
- Identify the class(es) that changed or were created
- Check existing test files for coverage gaps

### 2. Select Test Category

| Category | When to use | Annotation |
|----------|-------------|------------|
| Unit test | Business logic, validators, formatters, utilities | `@ExtendWith(MockitoExtension.class)` |
| Controller test | HTTP layer: mappings, validation, error responses | `@WebMvcTest` + `MockMvc` |
| Integration test | Full flow with real DB, caching, JPA | `@SpringBootTest` + Testcontainers |

### 3. Write Tests

**Unit test structure**:
```java
@ExtendWith(MockitoExtension.class)
class PetValidatorTests {
    @InjectMocks PetValidator validator;

    @Test
    void shouldRejectEmptyName() {
        // given
        Pet pet = new Pet();
        pet.setName("");
        Errors errors = new BeanPropertyBindingResult(pet, "pet");
        // when
        validator.validate(pet, errors);
        // then
        assertThat(errors.getFieldError("name")).isNotNull();
    }
}
```

**Controller test structure**:
```java
@WebMvcTest(OwnerController.class)
class OwnerControllerTests {
    @Autowired MockMvc mockMvc;
    @MockBean OwnerRepository ownerRepository;

    @Test
    void shouldReturn404WhenOwnerNotFound() throws Exception {
        given(ownerRepository.findById(99)).willReturn(Optional.empty());
        mockMvc.perform(get("/owners/99"))
               .andExpect(status().isNotFound());
    }
}
```

**Integration test structure**:
```java
@SpringBootTest
@ActiveProfiles("postgres")
@Testcontainers
class OwnerRepositoryIntegrationTests {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.5");

    @Autowired OwnerRepository ownerRepository;

    @Test
    @Transactional
    void shouldPersistOwner() {
        // given / when / then
    }
}
```

### 4. Apply Conventions
- File name: `<ClassName>Tests.java`
- Method name: descriptive, e.g., `shouldReturnOwnerWhenValidIdProvided()`
- Assertions: use AssertJ (`assertThat(...)`)
- No `Thread.sleep()` — use Awaitility if needed
- No production credentials in tests

### 5. Run and Validate
```bash
# Run specific test
./mvnw test -Dtest=<TestClassName>

# Run all tests
./mvnw test

# Check coverage
./mvnw verify
# Report at: target/site/jacoco/index.html
```

---

## Output

- New test file in `src/test/java/org/springframework/samples/petclinic/<module>/`
- Updated existing test file (if behavior changed)
- Coverage summary for the changed class(es)

---

## Constraints

- No empty test methods
- No disabled tests (`@Disabled`) without a tracking issue reference
- No secrets or real credentials in test code
- Test data must not depend on database seeding order
- Mock only external dependencies, not the class under test
