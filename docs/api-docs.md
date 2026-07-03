# API Documentation

Base URL is configured in `frontend/assets/js/config.js`.

All endpoints except register and login require:

```text
Authorization: Bearer <jwt>
Content-Type: application/json
```

## Authentication

### Register

`POST /api/auth/register`

Request:

```json
{
  "fullName": "Student Name",
  "email": "student@example.com",
  "password": "StrongPass#123"
}
```

Success: `201 Created` with text `Registration Successful`.

### Login

`POST /api/auth/login`

Request:

```json
{
  "email": "student@example.com",
  "password": "StrongPass#123"
}
```

Success response:

```json
{
  "token": "jwt-token",
  "userId": 1,
  "fullName": "Student Name",
  "email": "student@example.com"
}
```

## Profile

`POST /api/profile` saves or updates the authenticated user's profile.

`GET /api/profile` returns the authenticated user's profile. The server uses the JWT identity for ownership and ignores client-supplied user ownership data.

## Projects

`POST /api/projects` creates a project for the authenticated user.

`GET /api/projects` returns projects for the authenticated user.

Backward-compatible route: `/api/project`.

## Certificates

`POST /api/certificates` creates a certificate for the authenticated user.

`GET /api/certificates` returns certificates for the authenticated user.

Backward-compatible route: `/api/certificate`.

## Resume

`POST /api/resume` saves resume-builder form data for the authenticated user.

`GET /api/resume` returns saved resumes for the authenticated user.

PDF generation is performed in the browser with jsPDF.

## Contact

`POST /api/contact` saves a contact message for the authenticated user.

`GET /api/contact` returns contact messages for the authenticated user.

## Error Handling

- `400` validation or malformed request.
- `401` missing/invalid/expired JWT.
- `403` forbidden.
- `404` resource not found.
- `500` database or unexpected server error. Sensitive stack traces are not returned to clients.
