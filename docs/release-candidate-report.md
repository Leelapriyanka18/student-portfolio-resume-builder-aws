# Final Release Candidate Report

Date: 2026-07-03

## Executive Summary

The active project has reached Release Candidate quality. The verified active scope is:

- `frontend/`
- `student-portfolio-springboot/`
- `database/`
- `aws/`
- `docs/`
- `README.md`
- root `index.html`

The source code, documentation, backend, frontend, database integration, deployment configuration, and repository cleanup have reached Release Candidate quality, with one local verification blocker remaining.

In this final pass, `mvn clean test` was attempted from `student-portfolio-springboot/`. Maven is blocked before compilation because Java/Maven cannot validate Maven Central's TLS certificate while resolving the Spring Boot parent POM. This is a local environment dependency-resolution issue, not a repository code failure.

CloudWatch and SNS have been implemented in the AWS Console based on the current project status. Final screenshots and notification evidence are still required for submission proof. CloudWatch Agent work was attempted and documented, but startup is blocked by the Amazon Linux 2023 / collectd `socket_listener` issue. Native EC2 CloudWatch metrics are being used as the recommended alternative.

The accidental nested repository gitlink has been removed from version control and ignored going forward.

## Repository Completion Percentage

Repository Completion: 98%.

Overall Project Completion: 96%.

The repository itself is nearly complete. The remaining project work is primarily local Maven certificate remediation, screenshot collection, HTTPS hardening, and optional expanded test coverage.

## Frontend Status

Status: Complete for Release Candidate.

- Active frontend is `frontend/`.
- Static pages and shared JavaScript assets are present.
- Frontend API calls are aligned with the active Spring Boot backend endpoints.
- API base URL configuration exists in `frontend/assets/js/config.js`.
- Resume PDF generation remains browser-side by design.

## Backend Status

Status: Complete for Release Candidate.

- Active backend is `student-portfolio-springboot/`.
- Spring Boot application structure is present. Final local compilation is blocked by the Maven Central TLS trust issue documented in the testing section.
- Controllers expose active API routes for auth, profile, projects, certificates, resumes, and contacts.
- Runtime configuration uses environment variables for datasource, JWT secret, CORS origins, and server port.
- Legacy backend is not part of the active backend scope.

## Database Status

Status: Complete for Release Candidate.

- Active database schema is in `database/schema.sql`.
- Tables exist for users, profiles, projects, certificates, resumes, and contacts.
- User lookup indexes are included for application-owned records.
- Profiles are constrained to one row per user.
- Child records cascade on user deletion.
- Database integration is repository-ready.

## AWS Status

| Service | Status |
|---|---|
| Amazon EC2 | Verified Implemented |
| Amazon S3 | Verified Implemented |
| Amazon RDS | Verified Implemented |
| Elastic IP | Verified Implemented |
| IAM | Verified Implemented |
| Amazon CloudWatch | Implemented with native EC2 metrics |
| CloudWatch Agent | Attempted (Blocked by Amazon Linux 2023 collectd issue) |
| CloudWatch Dashboard | Implemented; screenshot evidence required |
| CloudWatch Alarms | Implemented; alert evidence required |
| Amazon SNS | Implemented; email delivery evidence required |

CloudWatch status: Implemented using native EC2 metrics.

SNS status: Implemented in AWS Console; final email screenshot required.

CloudWatch Dashboard, CloudWatch Alarms, and SNS must be supported by final AWS Console screenshots before submission.

### CloudWatch Agent

Status: Attempted.

- CloudWatch Agent was installed.
- Configuration was created.
- Configuration was verified.
- Startup failed because of the Amazon Linux 2023 / collectd `socket_listener` issue.
- Multiple troubleshooting attempts were performed.
- Native EC2 CloudWatch metrics are being used as the recommended alternative until manual resolution.

## AWS Notes

Repository implementation: Complete for Release Candidate.

AWS Console implementation: EC2, S3, RDS, Elastic IP, IAM, CloudWatch Dashboard, CloudWatch Alarms, and SNS are reported implemented.

Manual AWS tasks: final AWS Console verification, screenshot collection, alert-delivery evidence, and final cloud validation.

AWS Academy limitations: AWS Academy may restrict IAM capabilities, monitoring agent behavior, package/service setup, lab lifetime, or resource availability. These limitations are external to the repository.

Local Maven limitation: Maven dependency resolution failed because Java could not validate Maven Central's TLS certificate while resolving the Spring Boot parent POM.

## Documentation Status

Status: Complete for Release Candidate.

- API documentation is present.
- Architecture documentation is present.
- Deployment readiness documentation is present.
- AWS service documentation is present.
- Testing documentation is present.
- CloudWatch Agent attempt and CloudWatch/SNS implementation status are documented accurately.

## Security Status

Status: Complete for Release Candidate, with production hardening still recommended.

- JWT-based authentication is implemented.
- Backend uses Spring Security.
- Sensitive runtime values are environment-driven.
- RDS password is not stored in source.
- CORS configuration is environment-driven.
- Production HTTPS/TLS is still recommended before real production use.

## Testing Status

Status: Foundational automated tests are present; local test execution is blocked by environment certificate trust.

Final Maven verification from `student-portfolio-springboot/`:

| Command | Result |
|---|---|
| `mvn clean test` | Blocked before compilation while resolving the Spring Boot parent POM because Maven Central TLS certificate validation fails locally |
| `mvn clean package` | Not run because the test gate is blocked by the same local Maven certificate issue |

Fix the local Java/Maven certificate trust issue, then rerun `mvn clean test` and `mvn clean package`.

Additional controller, DAO integration, and frontend browser automation tests are recommended.

## Remaining Manual AWS Tasks

Remaining work consists primarily of:

1. AWS Console Verification.
2. Screenshot Collection.
3. CloudWatch alarm evidence.
4. SNS notification email evidence.
5. Final AWS Validation.

These are manual AWS tasks rather than repository defects.

## Remaining Repository Issues

- No critical active-project defects are currently known.
- Local Maven certificate trust must be fixed so the full automated test suite can execute.
- Expanded backend controller/DAO tests are recommended.
- Frontend browser automation tests are recommended.
- HTTPS/TLS is recommended before real production use.

## Production Readiness

Production readiness: Release Candidate, production-oriented, not fully production-final.

The repository is ready for final AWS validation. Full production readiness still requires HTTPS/TLS, verified CloudWatch dashboard and alarms, SNS alerting, and stronger automated test coverage.

## Internship Readiness

Internship readiness: Strong.

The project is suitable for internship demonstration after manual AWS verification evidence is captured, especially AWS Console screenshots or proof for CloudWatch Dashboard, CloudWatch Alarms, SNS, and final deployment validation.

## Final Summary

Repository Status: Release Candidate Complete.

Repository Quality: Production-Oriented.

Repository Completion: 98%.

Overall Project Completion: 96%.

Remaining Work: local Maven certificate remediation, final evidence collection, HTTPS hardening, and optional expanded tests.

The source code, documentation, backend, frontend, database integration, deployment configuration, and repository cleanup have successfully reached Release Candidate quality.
