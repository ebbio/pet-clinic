# Skill: validate-doc-impact

> **Location note**: This file belongs in `skills/validate-doc-impact.md`.
> Create the `skills/` directory to organize skill files there.

---

## Purpose

Detect when a code change requires corresponding documentation updates.
Prevents documentation drift by proactively flagging impacted docs before a PR is merged.

---

## When to Use

- Before finalizing a PR that includes code changes
- After implementing a feature from a GitHub issue
- When reviewing a PR as part of the Definition of Done checklist

---

## Actions

### 1. Scan Changed Files
For each changed source file, check the following triggers:

| Change Type | Documentation Impact |
|-------------|---------------------|
| New `@Controller` class or endpoint mapping | Update `ARCHITECTURE.md` (request flow, module structure) |
| New `@Entity` class or field | Update `BUSINESS_RULES.md` (entity rules), `ARCHITECTURE.md` (module structure) |
| New database migration / SQL script | Update `ARCHITECTURE.md` (database section), `BUSINESS_RULES.md` if schema reflects new rules |
| New configuration property | Update `README.md` (how to run / configuration section) |
| New environment variable required | Update `README.md` and `ARCHITECTURE.md` |
| Security constraint added or changed | Update `QUALITY.md` (security section), `.github/copilot-instructions.md` |
| New architectural pattern introduced | Create new ADR in `docs/adr/` (file: `ADR-NNN-title.md`) |
| Breaking API change | Update `ARCHITECTURE.md`, create ADR, update PR description |
| New NFR confirmed | Update `QUALITY.md` |

### 2. Check Each Impacted Doc

For each flagged documentation file:
- Open the file
- Check if the change is already reflected
- If not, update the relevant section using the `generate-docs` skill

### 3. Verify Copilot Instructions
If the change affects:
- Coding patterns or conventions → update `.github/copilot-instructions.md`
- Forbidden or required practices → update `.github/copilot-instructions.md`
- Agent behavior → update relevant agent file in `.github/agents/`

### 4. Report Impact

Produce a brief impact report:
```
Documentation Impact Report
============================
Changed files: [list]
Docs requiring update:
  - ARCHITECTURE.md: [section affected]
  - BUSINESS_RULES.md: [rule(s) affected]
  - QUALITY.md: [section affected]
  - README.md: [section affected]
  - New ADR required: [yes/no] — [topic if yes]
Action taken: [updated / flagged for review]
```

---

## Output

- Updated documentation files (via `generate-docs` skill)
- Impact report summarising what was checked and what was updated
- PR description updated with "Documentation updated" checklist item

---

## Constraints

- Do not update docs with unverified or invented content
- Mark any inferred updates as `[TO BE CONFIRMED]`
- Do not remove existing validated documentation
- Flag open questions rather than guessing answers
