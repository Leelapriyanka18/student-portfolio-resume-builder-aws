# Architecture

## Components

- Frontend: static HTML, CSS, and JavaScript in `frontend/`.
- Backend: Spring Boot REST API in `student-portfolio-springboot/`.
- Authentication: JWT bearer tokens.
- Persistence: Amazon RDS MySQL accessed through Spring `JdbcTemplate`.
- Hosting: S3 static website for frontend and EC2 systemd service for backend.

## Request Flow

```text
Users
  |
  v
Amazon S3 (Frontend)
  |
  v
Elastic IP
  |
  v
Amazon EC2
  |
  v
Spring Boot
  |
  v
Amazon RDS
```

## Monitoring Flow

```text
Amazon CloudWatch
  |
  v
CloudWatch Alarm
  |
  v
Amazon SNS
  |
  v
Email Notification
```

Status: Implemented in AWS Console; final screenshot evidence required.

CloudWatch native EC2 metrics, CloudWatch alarms, dashboard, SNS topic, and email subscription must be shown with screenshots before final submission.

## Security Boundaries

- Browser stores JWT session data in `localStorage`.
- Backend validates JWT on protected `/api/**` endpoints.
- Controllers attach the authenticated user ID from the JWT identity instead of trusting client-supplied ownership data.
- SQL access uses parameterized `JdbcTemplate` queries.
- Production secrets must be supplied through environment variables.

## Active Directories

- Active frontend: `frontend/`
- Active backend: `student-portfolio-springboot/`
- Active database scripts: `database/`

Duplicate nested project copies are not part of the active architecture.
