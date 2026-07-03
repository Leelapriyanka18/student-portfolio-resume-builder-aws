# Amazon RDS MySQL

Status: Verified Implemented; final screenshot evidence required.

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
4. EC2 can connect to RDS on TCP `3306`.
5. Backend logs show successful datasource initialization.
6. Register and login perform real database reads/writes.
