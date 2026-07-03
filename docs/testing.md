# Testing Report

Date: 2026-07-03

## Verified In This Final Pass

Commands run in `student-portfolio-springboot`:

```powershell
mvn clean test
```

Result from this workstation:

- Maven could not resolve the Spring Boot parent POM from Maven Central.
- The failure occurred before source compilation or test execution.
- Root cause: the local Java trust store rejected Maven Central's TLS certificate (`PKIX path building failed` / `certificate_unknown`).
- This is an environment dependency-resolution issue, not a repository code failure.

After fixing local Java/Maven certificate trust, rerun:

```powershell
mvn clean test
mvn clean package
```

## Repository Test Coverage

Current automated test source files:

- `PasswordUtilTest`
- `JwtUtilTest`
- `UserServiceTest`
- `AuthRateLimitFilterTest`
- `AuthenticatedRequestValidationTest`

These tests verify password hashing, JWT generation/validation, user registration/login behavior, authentication rate limiting, and authenticated DTO validation without client-supplied user IDs.

Recommended next tests:

- Controller tests for auth, profile, projects, certificates, resume, and contact endpoints.
- DAO integration tests against a test MySQL container or dedicated test database.
- Frontend smoke tests for register, login, dashboard, profile, projects, certificates, resume save, and contact save.

## Environment Limitations

- Local Java/Maven limitation: Maven Central access can fail when the Java trust store cannot validate Maven Central's TLS certificate. This must be fixed on the workstation before the final `mvn clean test` gate can execute tests.
- AWS Academy limitation: live AWS verification may be blocked by lab account state, instance lifecycle, IAM restrictions, or missing console access. These must be reported separately from repository defects.
