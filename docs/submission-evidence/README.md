# Final Submission Evidence Package

Date: 2026-07-04

This package organizes final evidence for the Cloud-Based Student Portfolio and Resume Builder System Using AWS.

No screenshots are fabricated in this repository. Attach evaluator screenshots externally if required by the college.

## EC2 Evidence

Status: PASS by manual AWS audit.

Repository evidence:

- Backend base URL: `http://34.199.78.202:8080`
- Deployment guide: `aws/ec2/deployment-notes.md`
- Elastic IP guide: `aws/ec2/elastic-ip.md`

Screenshot artifact in repository: not present.

## RDS Evidence

Status: PASS by manual AWS audit and live API write/readback.

Repository evidence:

- Schema: `database/schema.sql`
- RDS setup: `aws/rds/database-setup.md`
- Schema verification/repair: `database/queries.sql`
- Runtime schema initializer: `student-portfolio-springboot/src/main/java/com/studentportfolio/config/DatabaseSchemaInitializer.java`

Screenshot artifact in repository: not present.

## S3 Evidence

Status: PASS by manual AWS audit and HTTP page verification.

Repository evidence:

- S3 setup: `aws/s3/bucket-setup.md`
- Frontend source: `frontend/`
- API config: `frontend/assets/js/config.js`

Screenshot artifact in repository: not present.

Current deployment note:

- Browser verification found deployed S3 still requests missing `/favicon.ico`.
- Repository fix is present through `frontend/assets/images/icons/favicon.svg` and HTML favicon links.
- AWS CLI is not installed in this environment, so publishing that static-asset fix to S3 cannot be completed here.

## CloudWatch Evidence

Status: PASS by manual AWS audit.

Repository evidence:

- Monitoring guide: `aws/cloudwatch/monitoring.md`

Screenshot artifact in repository: not present.

## SNS Evidence

Status: PASS by manual AWS audit.

Repository evidence:

- SNS guide: `aws/sns/notifications.md`

Screenshot artifact in repository: not present.

## IAM Evidence

Status: PASS by manual AWS audit.

Repository evidence:

- IAM guide: `aws/iam/permissions.md`

Screenshot artifact in repository: not present.

## Security Groups Evidence

Status: PASS by manual AWS audit.

Repository evidence:

- Security group requirements are documented in `aws/ec2/deployment-notes.md`, `aws/rds/database-setup.md`, and `docs/aws-review.md`.

Screenshot artifact in repository: not present.

## Elastic IP Evidence

Status: PASS by manual AWS audit.

Repository evidence:

- Elastic IP guide: `aws/ec2/elastic-ip.md`
- Backend URL: `http://34.199.78.202:8080`

Screenshot artifact in repository: not present.

## API Verification Evidence

Status: PASS.

Verified live API results:

- Register: `201 Created`
- Login: `200 OK` with JWT
- Profile: `201 Created`
- Projects: `201 Created`
- Certificates: `201 Created`
- Resume: `201 Created`
- Contact: `201 Created`
- Protected endpoint without JWT: `401 Unauthorized`

## Browser Verification Evidence

Status: PARTIAL until updated frontend is redeployed to S3.

Headless Chrome verification:

- Home: loaded, but deployed site produced `404` for `/favicon.ico`.
- About: loaded.
- Register: loaded.
- Login: loaded.
- Dashboard: redirected to login without JWT as expected.
- Profile: redirected to login without JWT as expected.
- Projects: redirected to login without JWT as expected.
- Certificates: redirected to login without JWT as expected.
- Resume Builder: redirected to login without JWT as expected.
- Contact: loaded.

API-backed browser smoke actions passed for register, login, profile, projects, certificates, resume, and contact through live backend calls.

Updated repository frontend verification:

- Local headless Chrome verification passed for home, about, register, login, dashboard, profile, projects, certificates, resume builder, and contact pages.
- Result: no console errors, no failed requests, and no 404/500 statuses against the updated source.
- Protected pages redirected to login without JWT as expected.

## Build Verification Evidence

Status: PARTIAL due local Java/Maven TLS trust.

- `mvn clean test`: main/test compilation passed, then Maven failed resolving `org.apache.maven.surefire:surefire-junit-platform:3.2.5` from Maven Central because of Java TLS certificate trust.
- `mvn clean package`: failed at the same Surefire dependency-resolution step.
- `mvn -DskipTests package`: previously passed and produced the deployable Spring Boot jar.

## GitHub Verification Evidence

Status: PASS after final commit/push.

Repository status should be verified with:

```bash
git status --short
git log --oneline -3
```

## Architecture Evidence

Status: PASS.

Architecture:

```text
Browser
  -> Amazon S3 Static Website
  -> Amazon EC2 Spring Boot API on Elastic IP
  -> Amazon RDS MySQL
  -> Amazon CloudWatch
  -> Amazon SNS
```

## Deployment Evidence

Status: PASS for working application; PARTIAL for final favicon static-asset redeploy.

Deployment works for all verified business modules. The only unresolved deployment evidence item is publishing the favicon link/static asset to S3 after the repository fix.
