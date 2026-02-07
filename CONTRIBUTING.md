# Contributing

Thanks for contributing. This project aims to keep `master` always runnable.

## Workflow

1. Create an issue for each task or bug.
2. Create a branch from `master` named like `issue-12-short-description`.
3. Make small commits that each do one thing.
4. Open a PR and link the issue with `Fixes #12`.
5. Run tests: `./gradlew test`.
6. Merge with squash or rebase and delete the branch.

## Commit messages

Use clear, small commits. Include the issue number when possible:

```
Fix #12: Parse armor section correctly
```

## Code style

- Prefer readability over cleverness.
- Keep changes minimal and focused.
- Update docs when behavior changes.
