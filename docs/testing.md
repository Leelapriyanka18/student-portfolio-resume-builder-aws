# Testing Report

Date: 2026-07-04

## Verified In This Final Pass

Commands run in `student-portfolio-springboot`:

```powershell
mvn clean test
mvn clean package
```

Result from this workstation:

- `mvn clean test`: main sources and test sources compiled, then Maven failed while resolving `org.apache.maven.surefire:surefire-junit-platform:3.2.5` from Maven Central because the local Java trust store rejected the TLS certificate (`PKIX path building failed` / `certificate_unknown`).
- `mvn clean package`: failed at the same Surefire dependency-resolution step because package runs the test phase by default.
- Classification: environment dependency-resolution problem caused by Java/Maven TLS trust for Maven Central. It is not a repository source, compilation, application dependency declaration, or runtime code failure.
- Previous deployable verification: `mvn -DskipTests package` passed and produced `target/student-portfolio-springboot-0.0.1-SNAPSHOT.jar`.

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
- `POST /api/profile`: `201 Created`
- `POST /api/projects`: `201 Created`
- `POST /api/certificates`: `201 Created`
- `POST /api/resume`: `201 Created`
- `POST /api/contact`: `201 Created`
- Protected endpoint without JWT: `401 Unauthorized`

Certificate and contact API blockers are verified resolved in the deployed environment.

## Browser Verification

Headless Chrome verification was run through the Chrome DevTools Protocol against the S3 website.

Verified pages:

- `/`
- `/pages/about.html`
- `/pages/register.html`
- `/pages/login.html`
- `/pages/dashboard.html`
- `/pages/profile.html`
- `/pages/projects.html`
- `/pages/certificates.html`
- `/pages/resume-builder.html`
- `/pages/contact.html`

Result:

- API-backed browser smoke actions passed through live backend calls for register, login, profile, projects, certificates, resume, and contact.
- Protected pages redirected to login when no JWT session existed.
- One frontend asset issue was found: the deployed site requested missing `/favicon.ico`. The repository now includes `frontend/assets/images/icons/favicon.svg` and links it from HTML pages. Redeploy the updated frontend to S3 so the deployed site no longer requests a missing favicon.
- Local headless Chrome verification against the updated repository frontend passed for home, about, register, login, dashboard, profile, projects, certificates, resume builder, and contact pages with no console errors, no failed requests, and no 404/500 statuses.

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
- Browser verification limitation: DevTools verification can identify console and network failures in this environment, but final faculty submission screenshots must be captured separately from the browser/AWS console.
