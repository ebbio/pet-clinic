# Skill: generate-docs

> **Location note**: This file belongs in `skills/generate-docs.md`.
> Create the `skills/` directory to organize skill files there.

---

## Purpose

Generate or update documentation files to reflect changes in the codebase,
API, or architecture. Ensures documentation stays in sync with the implementation.

---

## When to Use

- A new module, controller, or endpoint has been added
- An existing API contract has changed (parameters, response structure, HTTP status codes)
- A new architectural decision has been made
- A new business rule has been identified or confirmed
- A non-functional requirement has been clarified or updated

---

## Actions

### 1. Detect Impact
Identify which documentation files are affected by the current change:
- New module → update `ARCHITECTURE.md` (module structure section)
- New/changed API → add API contract section to relevant business rule or architecture doc
- New architectural decision → create or update `ADR_TEMPLATE.md` based entry
- New business rule → update `BUSINESS_RULES.md`
- New NFR constraint → update `QUALITY.md`

### 2. Update Architecture Doc (`ARCHITECTURE.md`)
- Add new component to the components table
- Update the module structure diagram
- Update request flow if a new layer was added
- Update constraints table if new constraints apply

### 3. Update Business Rules (`BUSINESS_RULES.md`)
- Add new rule with next sequential ID (BR-NNN)
- Mark assumptions as `[TO BE CONFIRMED]`
- Add open questions if business intent is unclear

### 4. Create ADR (if architectural decision made)
- Copy `ADR_TEMPLATE.md`
- Fill in: date, status, context, decision, rationale, alternatives
- Name file: `ADR-NNN-short-title.md` in `docs/adr/` (once directory exists)

### 5. Update Copilot Instructions (if coding standards changed)
- Update `.github/copilot-instructions.md`
- Add new constraints, forbidden practices, or consistency rules

---

## Output

- Updated markdown documentation file(s)
- New ADR file (if applicable)
- PR description section listing documentation changes

---

## Constraints

- Do not invent business rules or architecture decisions
- Mark all inferred content as `[TO BE CONFIRMED]`
- Keep documentation minimal and structured — avoid verbose prose
- Do not overwrite content that has been explicitly validated
