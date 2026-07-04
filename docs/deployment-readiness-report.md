# Deployment Readiness Report

Date: 2026-07-04

## Current Architecture

Amazon S3 static website frontend
|
v
Amazon EC2 Spring Boot backend on Elastic IP
|
v
Amazon RDS MySQL

## Backend URL

Current backend base URL:

```text
http://34.199.78.202:8080
```

Frontend switch point:

```text
frontend/assets/js/config.js
```

Elastic IP is already assigned. Do not allocate another Elastic IP.

## Completed Code Stabilization

- Centralized frontend API base URL through `window.APP_CONFIG.apiBase` and normalized `window.API_BASE`.
- Removed old active frontend fallback IP usage.
- Updated active frontend API calls to use `window.API_BASE`.
- Fixed login session flow so the backend returns `userId`, `email`, and `fullName`.
- Updated frontend login to call `Auth.setSession(...)`.
- Fixed certificate save flow to use `POST /api/certificates`.
- Added authenticated `GET /api/profile`.
- Removed temporary hardcoded `userId = 1` from active profile, certificate, resume, project, and contact save logic.
- Added user-specific reads for projects, certificates, and resumes.
- Bound Spring Boot to `0.0.0.0:8080` for EC2 network access.
- Added environment-driven CORS for the S3 static website, `127.0.0.1:5500`, and `localhost:5500`.
- Removed client-side `userId` validation from authenticated request DTOs so the backend can attach the JWT user after validation.
- Added startup database schema verification/repair for missing live RDS columns used by certificate and contact endpoints.
- Updated RDS audit SQL with information-schema checks for columns, indexes, and constraints.

## Verification

- Active backend folder: `student-portfolio-springboot`
- Package command: `mvn -DskipTests package`
- Package status from this workstation: passed and produced `student-portfolio-springboot/target/student-portfolio-springboot-0.0.1-SNAPSHOT.jar`.
- Test command: `mvn test`
- Test status from this workstation: blocked because Java/Maven cannot validate Maven Central while resolving `org.apache.maven.surefire:surefire-junit-platform:3.2.5`.
- Generated artifact is ready for EC2 redeploy, but the automated test gate still requires local Java/Maven certificate trust repair.

## Live AWS Verification

Live checks from this workstation on 2026-07-04:

```text
GET http://34.199.78.202:8080/
POST http://34.199.78.202:8080/api/auth/register
POST http://34.199.78.202:8080/api/auth/login
POST http://34.199.78.202:8080/api/certificates
POST http://34.199.78.202:8080/api/contact
```

Observed result:

- `/` returned `401 Unauthorized`, confirming Spring Boot and Spring Security are running.
- Register returned `201 Created`.
- Login succeeded and returned a JWT/user id.
- Certificates returned `Database error. Please try again later.`
- Contact returned `Database error. Please try again later.`

Conclusion: EC2 backend is reachable and authentication works. RDS schema drift
is still active on the deployed environment until the latest jar is redeployed
or the manual SQL repair is applied.

If this changes later, likely causes to check in AWS Academy:

- EC2 instance stopped or Spring Boot service stopped.
- Spring Boot service not running on EC2.
- Security Group does not allow inbound TCP 8080.
- EC2 operating system firewall blocks port 8080.
- Application is bound to localhost instead of all interfaces.
- RDS connectivity failure prevents backend startup.

## CORS

The active backend global CORS configuration allows:

```text
http://student-portfolio-priyanka-4501.s3-website-us-east-1.amazonaws.com
http://127.0.0.1:5500
http://localhost:5500
```

Do not use wildcard CORS for this deployment.

## Remaining AWS Tasks

- Confirm Elastic IP `34.199.78.202` is still associated with the backend EC2 instance.
- Confirm Spring Boot is running on EC2.
- Confirm inbound Security Group rule allows TCP 8080 from the required source.
- Confirm EC2 can connect to Amazon RDS MySQL.
- Confirm application logs have no startup errors.
- Redeploy the updated Spring Boot jar to EC2.
- Confirm `Database schema verification completed` appears in `journalctl`.
- Retest `POST /api/certificates` and `POST /api/contact` until both return `201`.
- Upload the updated frontend files to S3 if S3 still contains stale assets.
- Capture CloudWatch dashboard and alarm screenshots.
- Capture SNS topic, confirmed subscription, alarm action, and received email notification screenshots.

CloudWatch and SNS status: Implemented in AWS Console; final evidence collection required.

## Production Readiness

Not production-ready yet.

Reason: the backend artifact now packages successfully, but live certificate and contact endpoints still fail with database errors until the updated jar/schema repair is deployed to EC2/RDS. Full AWS console verification also requires AWS CLI/console credentials that were not available in this workspace.
