# ADR-NNN: [Title of the Architectural Decision]

> **Location note**: ADR files belong in `docs/adr/`. Name them `ADR-NNN-short-title.md`.
> Create the `docs/` directory structure to organize ADRs there.
>
> **How to number**: Use sequential numbers starting from 001 (e.g., ADR-001, ADR-002).

---

**Date**: YYYY-MM-DD
**Status**: [Proposed | Accepted | Deprecated | Superseded by ADR-NNN]
**Deciders**: [List of people or teams involved in the decision]

---

## Context

<!-- Describe the problem or situation that required a decision.
     Include constraints, forces, and the reason this decision was needed. -->

## Decision

<!-- State the decision clearly and concisely.
     Use the format: "We will [do X] because [reason]." -->

## Rationale

<!-- Explain why this option was chosen over alternatives.
     List the factors considered. -->

## Alternatives Considered

| Alternative | Pros | Cons | Reason Rejected |
|-------------|------|------|-----------------|
| Option A | ... | ... | ... |
| Option B | ... | ... | ... |

## Consequences

### Positive
- ...

### Negative
- ...

### Risks
- ...

## Implementation Notes

<!-- Optional: any specific implementation details or constraints that result from this decision. -->

## Related Decisions

- [ADR-NNN: Title](./ADR-NNN-title.md)

---

## Example ADRs for PetClinic

The following ADRs are expected to be created as implementation progresses:

| ID | Title | Status |
|----|-------|--------|
| ADR-001 | Adopt JWT for stateless authentication | Proposed |
| ADR-002 | PostgreSQL as primary database (H2 for dev only) | Accepted (reflected in config) |
| ADR-003 | Caffeine in-memory cache for reference data | Accepted (reflected in code) |
| ADR-004 | Spring Data JPA as ORM framework | Accepted (reflected in code) |
| ADR-005 | No dedicated service layer (controllers → repositories direct) | TO BE CONFIRMED |
