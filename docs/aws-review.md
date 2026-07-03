# AWS Review

Date: 2026-07-03

Scope: repository evidence and documented deployment model. Live AWS console/resource verification was not performed from this workspace.

## Architecture Under Review

```text
User Browser
  -> Amazon S3 static website
  -> EC2 Elastic IP on port 8080
  -> Spring Boot REST API
  -> Amazon RDS MySQL
```

## Repository Implementation

- Frontend backend URL is centralized in `frontend/assets/js/config.js`.
- Backend binds to `0.0.0.0` and configurable `SERVER_PORT`.
- RDS connection is configurable through `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, and `SPRING_DATASOURCE_PASSWORD`.
- RDS password is not stored in source.
- CORS is configurable through `APP_CORS_ALLOWED_ORIGINS` and includes the current S3 website origin by default.
- JWT secret is configurable through `JWT_SECRET`.
- CloudWatch/SNS implementation and verification evidence requirements are documented in `aws/cloudwatch/monitoring.md` and `aws/sns/notifications.md`.

## Must Be Manually Verified In AWS

### EC2

1. Instance is running.
2. Elastic IP is attached to the backend instance.
3. Security group allows inbound TCP `8080` from the required source.
4. Spring Boot systemd service is active.
5. Application logs show successful startup.

### S3

1. Static website hosting is enabled.
2. Latest `frontend/` files are uploaded.
3. Bucket policy allows intended public read access.
4. `frontend/assets/js/config.js` points to the active backend URL.

### RDS

1. DB instance is available.
2. EC2 security group can reach RDS on TCP `3306`.
3. Schema has been applied.
4. Application environment contains the real DB password.

### IAM

1. EC2 role exists and is attached where needed.
2. Permissions are least-privilege for the lab/project.
3. No long-lived AWS access keys are stored in the repository.

### CloudWatch and SNS

1. Dashboard exists.
2. CPU alarm exists.
3. Status-check alarm exists.
4. Network alarm exists.
5. SNS topic exists.
6. Email subscription is confirmed.
7. Alarm notification test has been performed.

Status: Implemented in AWS Console; final screenshot and notification evidence required.

Do not submit the project until the AWS Console verification above is captured as screenshots or viva evidence.

## Classification

- Repository issue: missing or incorrect source/config/documentation in this repository.
- Environment limitation: local machine, network, Java trust store, certificate, or dependency access issue.
- AWS Academy limitation: lab permissions, account restrictions, stopped lab resources, unsupported agent install, or unavailable console actions.

Do not mark AWS implementation complete until the manual checks above are actually verified in the AWS account.
