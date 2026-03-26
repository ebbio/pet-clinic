# Business Rules — PetClinic

> **Location note**: This file belongs in `docs/business/business-rules.md`.
> Create the `docs/` directory structure to reorganize it there.

## Domain Overview

The PetClinic platform manages interactions between **pet owners**, **pets**, **veterinarians**,
and **visits** at a veterinary clinic.

---

## Core Entities

| Entity | Description |
|--------|-------------|
| Owner | A registered person who owns one or more pets |
| Pet | An animal registered under an Owner |
| PetType | A classification of pet (e.g., cat, dog, bird) — lookup data |
| Vet | A veterinarian registered in the system |
| Specialty | A medical specialty a Vet can hold (e.g., radiology, surgery) — lookup data |
| Visit | A recorded appointment for a Pet with a Vet |

---

## Business Rules

### Owner Rules

- BR-001: An Owner must have: first name, last name, address, city, and telephone.
- BR-002: Telephone must be a valid numeric string (7–10 digits). [TO BE CONFIRMED: exact format]
- BR-003: An Owner may own zero or more pets.
- BR-004: Owner first name and last name must each be between 1 and 30 characters.

### Pet Rules

- BR-005: A Pet must have: name, birth date, PetType, and an owning Owner.
- BR-006: Pet name must be between 1 and 30 characters.
- BR-007: Pet birth date must be in the past (cannot be a future date).
- BR-008: A Pet must be assigned a valid PetType from the configured list.
- BR-009: A Pet may have zero or more Visits.

### Visit Rules

- BR-010: A Visit must have: a date and a description.
- BR-011: Visit description must be between 1 and 255 characters. [TO BE CONFIRMED]
- BR-012: A Visit must be associated with a Pet.
- BR-013: Visit date defaults to today if not provided. [TO BE CONFIRMED]

### Vet Rules

- BR-014: A Vet must have: first name and last name.
- BR-015: A Vet may have zero or more Specialties.
- BR-016: Vet data is read-only through the standard UI (no create/update/delete via Vet UI). [TO BE CONFIRMED]

### PetType / Specialty Rules

- BR-017: PetTypes are pre-configured lookup values. [TO BE CONFIRMED: whether user-manageable]
- BR-018: Specialties are pre-configured lookup values. [TO BE CONFIRMED: whether user-manageable]

---

## Authentication Rules (TO BE CONFIRMED — JWT not yet implemented)

- BR-019: All endpoints except login and public health checks require a valid JWT token.
- BR-020: JWT tokens expire after 1 hour. [TO BE CONFIRMED: configurable via property]
- BR-021: JWT secret must be provided via environment variable; never hardcoded.
- BR-022: Expired or tampered tokens return HTTP 401.

---

## Open Questions

- [OQ-001] Should Owners be able to self-register, or is registration admin-only?
- [OQ-002] Should Vets be manageable (CRUD) by admin users?
- [OQ-003] Should PetTypes and Specialties be configurable by admins at runtime?
- [OQ-004] Are there role-based access control (RBAC) requirements (e.g., admin vs. owner)?
- [OQ-005] Is there a requirement to audit changes (who modified what and when)?
