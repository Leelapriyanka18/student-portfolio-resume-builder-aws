# Amazon RDS MySQL

Status: Verified implemented.

## Repository Implementation

The backend uses Spring `JdbcTemplate` with MySQL. Schema is maintained in:

```text
database/schema.sql
```

The schema includes tables for:

- `users`
- `profiles`
- `projects`
- `certificates`
- `resumes`
- `contacts`

Foreign-key lookup indexes are included for `user_id` columns.

The Spring Boot backend also verifies the schema at startup through:

```text
student-portfolio-springboot/src/main/java/com/studentportfolio/config/DatabaseSchemaInitializer.java
```

This initializer is enabled by default and is controlled by:

```bash
APP_DATABASE_SCHEMA_INITIALIZER_ENABLED=true
```

It creates missing tables, adds missing endpoint columns such as
`certificates.certificate_url`, `certificates.issue_date`,
`contacts.user_id`, and `contacts.created_at`, and creates lookup indexes.
Foreign keys are added when the existing data allows them; orphaned legacy rows
must be cleaned manually before adding constraints.

## Connection Configuration

Set these environment variables on EC2:

```bash
SPRING_DATASOURCE_URL=jdbc:mysql://<rds-endpoint>:3306/resumebuilder
SPRING_DATASOURCE_USERNAME=<db-user>
SPRING_DATASOURCE_PASSWORD=<db-password>
```

The repository intentionally does not store the RDS password.

## Security Configuration

1. RDS should not be publicly open unless the lab requires it.
2. Prefer allowing inbound TCP `3306` only from the EC2 security group.
3. Use a non-root application database user for production.
4. Rotate DB credentials before public submission.

## Final Evidence Checklist

1. RDS instance status is `Available`.
2. Database `resumebuilder` exists.
3. `database/schema.sql` has been applied.
4. Latest Spring Boot jar has been redeployed to EC2.
5. Backend logs show `Database schema verification completed`.
6. EC2 can connect to RDS on TCP `3306`.
7. Register and login perform real database reads/writes.
8. `POST /api/certificates` returns `201 Created`.
9. `POST /api/contact` returns `201 Created`.

## 2026-07-04 Live Smoke Evidence

Against `http://34.199.78.202:8080` from this workstation:

- `POST /api/auth/register`: `201 Created`
- `POST /api/auth/login`: success, returned JWT/user id
- `POST /api/profile`: `201 Created`
- `POST /api/projects`: `201 Created`
- `POST /api/certificates`: `201 Created`
- `POST /api/resume`: `201 Created`
- `POST /api/contact`: `201 Created`

Conclusion: live RDS-backed writes are verified for all core modules. Certificate and contact schema drift blockers are resolved.
