# Testing Report

Date: 2026-07-04

## Verified In This Final Pass

Commands run in `student-portfolio-springboot`:

```powershell
mvn -DskipTests package
mvn test
```

Result from this workstation:

- `mvn -DskipTests package`: passed and produced `target/student-portfolio-springboot-0.0.1-SNAPSHOT.jar`.
- `mvn test`: blocked before executing tests because the local Java trust store rejected Maven Central while resolving `org.apache.maven.surefire:surefire-junit-platform:3.2.5` (`PKIX path building failed` / `certificate_unknown`).
- This is an environment dependency-resolution issue for the test plugin, not a Java compilation failure.

After fixing local Java/Maven certificate trust, rerun:

```powershell
mvn clean test
mvn clean package
```

## Live API Smoke Test

Against `http://34.199.78.202:8080` on 2026-07-04:

- `GET /`: reachable, returned `401 Unauthorized`, confirming the Spring Security chain is active on port `8080`.
- `POST /api/auth/register`: `201 Created`
- `POST /api/auth/login`: success
- `POST /api/certificates`: failed with backend database error
- `POST /api/contact`: failed with backend database error

The certificate/contact failures match the known RDS schema drift blocker. The
repository now includes `DatabaseSchemaInitializer` plus `database/queries.sql`
to repair the missing live columns/indexes after redeploy.

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
