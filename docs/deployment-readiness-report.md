# Deployment Readiness Report

Date: 2026-07-02

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

## Verification

- Active backend folder: `student-portfolio-springboot`
- Build command: `mvn clean test`
- Build status from this workstation: `mvn clean test` is blocked before compilation because Maven cannot resolve the Spring Boot parent POM from Maven Central. The local Java trust store rejects Maven Central's TLS certificate (`PKIX path building failed` / `certificate_unknown`). This is an environment dependency-resolution issue, not a repository code failure.
- Generated artifact: run `mvn clean package` after fixing local Maven/Java certificate trust and after tests pass.

## Live AWS Verification

Live checks from this workstation:

```text
GET http://34.199.78.202:8080/
GET http://34.199.78.202:8080/api/auth/login
```

Expected result:

- `/` returns Whitelabel 404, confirming Spring Boot is running.
- `GET /api/auth/login` returns 405 Method Not Allowed, confirming the endpoint exists and expects POST.

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
- Upload the updated frontend files to S3.
- Redeploy the updated Spring Boot jar to EC2.
- Capture CloudWatch dashboard and alarm screenshots.
- Capture SNS topic, confirmed subscription, alarm action, and received email notification screenshots.

CloudWatch and SNS status: Implemented in AWS Console; final evidence collection required.

## Production Readiness

Not production-ready yet.

Reason: the frontend is centralized around one backend URL, but final readiness still depends on fixing local Maven certificate trust, generating a verified backend artifact, redeploying the updated frontend/backend artifacts, and completing full end-to-end data verification against Amazon RDS.
