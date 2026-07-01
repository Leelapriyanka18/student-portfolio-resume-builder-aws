# Deployment Readiness Report

Date: 2026-07-01

## Current Architecture

Amazon S3 static website frontend
|
v
Amazon EC2 Spring Boot backend on current auto-assigned Public IPv4
|
v
Amazon RDS MySQL

## Backend URL

Current backend base URL:

```text
http://52.91.250.168:8080
```

Frontend switch point:

```text
frontend/assets/js/config.js
```

When Elastic IP access becomes available, update only `apiBase` in this file.

## Completed Code Stabilization

- Centralized frontend API base URL through `window.APP_CONFIG.apiBase` and normalized `window.API_BASE`.
- Removed old active frontend fallback IP usage.
- Updated active frontend API calls to use `window.API_BASE`.
- Fixed login session flow so the backend returns `userId`, `email`, and `fullName`.
- Updated frontend login to call `Auth.setSession(...)`.
- Fixed certificate save flow to use `POST /api/certificate`.
- Added missing `GET /api/profile?userId=...`.
- Removed temporary hardcoded `userId = 1` from active profile, certificate, and resume save logic.
- Added user-specific reads for projects, certificates, and resumes.
- Fixed invalid validation annotation on project `userId`.

## Verification

- Active backend folder: `student-portfolio-springboot`
- Build command: `mvn -q -DskipTests package`
- Build status: Passed
- Generated artifact: `student-portfolio-springboot/target/student-portfolio-springboot-0.0.1-SNAPSHOT.jar`

## Live AWS Verification

Live checks from this workstation:

```text
GET http://52.91.250.168:8080/api/project
TCP 52.91.250.168:8080
```

Result: Reachable from this workstation on 2026-07-01.

If this changes later, likely causes to check in AWS Academy:

- EC2 instance stopped or restarted and public IPv4 changed.
- Spring Boot service not running on EC2.
- Security Group does not allow inbound TCP 8080.
- EC2 operating system firewall blocks port 8080.
- Application is bound to localhost instead of all interfaces.
- RDS connectivity failure prevents backend startup.

## CORS

The active backend controllers allow the S3 static website origin:

```text
http://student-portfolio-priyanka-4501.s3-website-us-east-1.amazonaws.com
```

Because the backend is currently unreachable on port 8080, live CORS behavior could not be verified end to end.

## Remaining AWS Tasks

- Confirm the current EC2 Public IPv4 is still `52.91.250.168`.
- Confirm Spring Boot is running on EC2.
- Confirm inbound Security Group rule allows TCP 8080 from the required source.
- Confirm EC2 can connect to Amazon RDS MySQL.
- Confirm application logs have no startup errors.
- Upload the updated frontend files to S3.
- Redeploy the updated Spring Boot jar to EC2.

## Production Readiness

Not production-ready yet.

Reason: local code builds and the frontend is centralized around one backend URL. Final readiness still depends on redeploying the updated frontend/backend artifacts and completing full end-to-end data verification against Amazon RDS.
