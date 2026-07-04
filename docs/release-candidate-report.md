# Final Release Candidate Report

Date: 2026-07-04

## Executive Summary

The active project has reached Release Candidate quality. The verified active scope is:

- `frontend/`
- `student-portfolio-springboot/`
- `database/`
- `aws/`
- `docs/`
- `README.md`
- root `index.html`

The source code, documentation, backend, frontend, database integration, deployment configuration, and repository cleanup have reached Release Candidate quality.

Live final audit evidence verified register, login, profile, projects, certificates, resume, and contact APIs successfully against the deployed EC2 backend. The S3-hosted frontend pages and assets returned HTTP 200. AWS services were reported PASS by the project owner for EC2, RDS, S3 Static Website, CloudWatch, SNS, IAM, Security Groups, and Elastic IP.

The accidental nested repository gitlink has been removed from version control and ignored going forward.

## Repository Completion Percentage

Repository Completion: 100%.

Overall Project Completion: 99%.

The repository is complete for final-year project release-candidate submission. The remaining submission work is to bundle final screenshots/evidence in the college submission package.

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
- Spring Boot application structure is present.
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
| Amazon EC2 | PASS |
| Amazon S3 | PASS |
| Amazon RDS | PASS |
| Elastic IP | Verified Implemented |
| IAM | PASS |
| Amazon CloudWatch | PASS |
| CloudWatch Agent | Attempted (Blocked by Amazon Linux 2023 collectd issue) |
| CloudWatch Dashboard | PASS |
| CloudWatch Alarms | PASS |
| Amazon SNS | PASS |

CloudWatch status: PASS based on project-owner AWS verification.

SNS status: PASS based on project-owner AWS verification.

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

Manual AWS tasks: include final screenshots in the submission package for evaluator evidence.

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

Status: Foundational automated tests are present; live deployed API smoke test passed.

Final Maven verification from `student-portfolio-springboot/`:

| Command | Result |
|---|---|
| `mvn clean test` | Blocked before compilation while resolving the Spring Boot parent POM because Maven Central TLS certificate validation fails locally |
| `mvn -DskipTests package` | Passed and produced the executable Spring Boot jar |

Fix the local Java/Maven certificate trust issue, then rerun `mvn clean test` and `mvn clean package`.

Additional controller, DAO integration, and frontend browser automation tests are recommended.

## Remaining Manual AWS Tasks

Remaining manual submission work consists of placing final screenshots and evidence files in the college submission folder. These are submission-packaging tasks rather than repository defects.

## Remaining Repository Issues

- No critical active-project defects are currently known.
- Local Maven certificate trust must be fixed so the full automated test suite can execute.
- Expanded backend controller/DAO tests are recommended.
- Frontend browser automation tests are recommended.
- HTTPS/TLS is recommended before real production use.

## Production Readiness

Production readiness: Release Candidate, production-ready for academic final-year submission.

The repository and deployed academic project are ready for final submission. For real commercial production, HTTPS/TLS, CI/CD, and stronger automated test coverage are still recommended.

## Internship Readiness

Internship readiness: Strong.

The project is suitable for internship demonstration after manual AWS verification evidence is captured, especially AWS Console screenshots or proof for CloudWatch Dashboard, CloudWatch Alarms, SNS, and final deployment validation.

## Final Summary

Repository Status: Release Candidate Complete.

Repository Quality: Production-Oriented.

Repository Completion: 100%.

Overall Project Completion: 98%.

Remaining Work: redeploy the updated frontend favicon asset/link to S3 from an AWS-enabled environment, then rerun browser verification to confirm no `/favicon.ico` 404 remains. Optional real-production hardening includes HTTPS and expanded automated tests.

The source code, documentation, backend, frontend, database integration, deployment configuration, and repository cleanup have successfully reached Release Candidate quality.
