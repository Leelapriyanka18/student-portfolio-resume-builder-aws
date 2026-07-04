# Student Portfolio & Resume Builder on AWS

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen)
![AWS](https://img.shields.io/badge/AWS-EC2%20%7C%20S3%20%7C%20RDS%20%7C%20CloudWatch%20%7C%20SNS-orange)
![Status](https://img.shields.io/badge/Status-Release%20Candidate-success)

Cloud-based student portfolio and resume builder with an HTML/CSS/JavaScript frontend, a Spring Boot REST API, JWT authentication, JdbcTemplate persistence, and AWS deployment notes for S3, EC2, and RDS.

## Features

- Responsive student portfolio website.
- Register/login flow with JWT authentication.
- Profile, projects, certificates, resume, and contact modules.
- Browser-side resume PDF generation.
- Spring Boot REST API with DTO validation and centralized error handling.
- MySQL schema for users, profiles, projects, certificates, resumes, and contacts.
- AWS deployment using S3, EC2, RDS, Elastic IP, CloudWatch, and SNS.
- Native EC2 CloudWatch dashboard, alarms, and SNS notification flow.

## Architecture

```text
User Browser
  -> Amazon S3 Static Website
  -> Spring Boot REST API on Amazon EC2
  -> Amazon RDS MySQL

Amazon EC2 native metrics
  -> Amazon CloudWatch Dashboard and Alarms
  -> Amazon SNS Email Notifications
```

## Active Project Layout

- `frontend/` - active static website deployed to S3.
- `student-portfolio-springboot/` - active Spring Boot backend.
- `database/` - MySQL schema and sample SQL.
- `aws/` - AWS setup and operations notes.
- `docs/` - architecture, API, testing, and audit documentation.

Only `student-portfolio-springboot/` is treated as the active backend and only `frontend/` is treated as the active frontend. Other folders are not modified unless explicitly approved.

## Local Verification

```powershell
cd student-portfolio-springboot
mvn clean test
mvn clean package
```

If Maven fails only because Java cannot validate Maven Central certificates, fix the local Java/Maven trust store and rerun the verification commands.

## Runtime Configuration

Configure production values through environment variables:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `JWT_SECRET`
- `APP_CORS_ALLOWED_ORIGINS`
- `SERVER_PORT`
- `AUTH_RATE_LIMIT_MAX_REQUESTS`
- `AUTH_RATE_LIMIT_WINDOW_MS`

The repository does not store the RDS password.

## AWS Services

| Service | Purpose |
|---|---|
| Amazon S3 | Hosts the static frontend |
| Amazon EC2 | Runs the Spring Boot backend |
| Amazon RDS | Hosts the MySQL database |
| Elastic IP | Provides a stable backend IP |
| IAM | Provides least-privilege AWS access for deployment/monitoring |
| Amazon CloudWatch | Dashboard and alarms for EC2 native metrics |
| Amazon SNS | Email notifications for CloudWatch alarms |

## Final Evidence Checklist

- S3 static website screenshot.
- EC2 running instance screenshot.
- Elastic IP association screenshot.
- RDS database availability/connectivity screenshot.
- CloudWatch dashboard screenshot.
- CloudWatch alarms screenshot.
- SNS topic, confirmed subscription, and received email notification screenshots.
- Application screenshots for login, dashboard, profile, projects, certificates, resume builder, and contact.

## Documentation

- [Frontend/backend integration audit](docs/frontend-backend-integration-audit.md)
- [API documentation](docs/api-docs.md)
- [Deployment readiness report](docs/deployment-readiness-report.md)
- [Testing report](docs/testing.md)
- [CloudWatch and SNS guide](aws/cloudwatch/monitoring.md)
- [Release candidate report](docs/release-candidate-report.md)
- [Final project audit](docs/final-project-audit.md)
- [Final certification report](docs/final-certification-report.md)
