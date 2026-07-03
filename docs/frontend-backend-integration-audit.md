# Frontend Backend Integration Audit

Date: 2026-07-03

Scope: active `frontend/` and `student-portfolio-springboot/` directories only.

## Status Summary

| Feature | Page | Status | Backend endpoint |
|---|---|---|---|
| Register | `frontend/pages/register.html` | Fully integrated with backend API | `POST /api/auth/register` |
| Login | `frontend/pages/login.html` | Fully integrated with backend API | `POST /api/auth/login` |
| Dashboard | `frontend/pages/dashboard.html` | Fully integrated with backend API | `GET /api/projects`, `GET /api/certificates`, `GET /api/resume`, `GET /api/contact` |
| Profile view | `frontend/pages/profile.html` | Fully integrated with backend API | `GET /api/profile`, `GET /api/projects` |
| Profile edit | `frontend/pages/edit-profile.html` | Fully integrated with backend API | `GET /api/profile`, `POST /api/profile` |
| Projects | `frontend/pages/projects.html` | Fully integrated with backend API | `GET /api/projects`, `POST /api/projects` |
| Certificates | `frontend/pages/certificates.html` | Fully integrated with backend API | `GET /api/certificates`, `POST /api/certificates` |
| Resume Builder | `frontend/pages/resume-builder.html` | Partially integrated | `POST /api/resume`; PDF generation is browser-only by design |
| Contact Form | `frontend/pages/contact.html` | Fully integrated with backend API | `POST /api/contact` |
| Landing contact card | `frontend/index.html` | Fully integrated with backend API for logged-in users | `POST /api/contact` |

## Notes

- Resume PDF download remains frontend-side because the existing backend API stores resume data but does not generate PDFs.
- The landing contact card uses existing fields and the authenticated session to call `POST /api/contact`; no new backend API was added.
- All protected API calls use `Auth.authFetch`, which attaches the JWT token and handles `401` logout.
- Protected frontend requests rely on the JWT-bearing session. The frontend no longer sends `userId` query parameters for owned resources.

## Removed Mock Behavior

Removed the old shared `contactSendBtn` toast-only simulation and replaced it with a real `POST /api/contact` call.
