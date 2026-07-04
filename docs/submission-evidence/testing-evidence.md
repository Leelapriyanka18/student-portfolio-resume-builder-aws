# Testing Evidence Checklist

Use this checklist to document final testing evidence for the project.

## Functional Testing

- [ ] Register user
- [ ] Login user
- [ ] Logout user
- [ ] Open dashboard after login
- [ ] Save profile
- [ ] Save project
- [ ] Save certificate
- [ ] Save resume
- [ ] Submit contact form
- [ ] Refresh browser and confirm session behavior

## API Testing

- [ ] `POST /api/auth/register`
- [ ] `POST /api/auth/login`
- [ ] Profile API create/update/read
- [ ] Projects API create/read
- [ ] Certificates API create/read
- [ ] Resume API create/read
- [ ] Contact API create
- [ ] Protected endpoint without JWT returns unauthorized
- [ ] Protected endpoint with JWT succeeds

## Database Testing

- [ ] User row inserted
- [ ] Profile row inserted or updated
- [ ] Project row inserted
- [ ] Certificate row inserted
- [ ] Resume row inserted
- [ ] Contact row inserted
- [ ] Foreign key relationships verified
- [ ] Required columns reject invalid null data where applicable

## AWS Testing

- [ ] EC2 instance reachable
- [ ] Spring Boot service running on EC2
- [ ] RDS reachable from backend
- [ ] S3 static website endpoint opens
- [ ] S3 assets load with HTTP 200
- [ ] CloudWatch logs show application activity
- [ ] SNS configuration visible
- [ ] Security groups allow required traffic only

## Security Testing

- [ ] Password is not stored as plain text
- [ ] JWT received after login
- [ ] JWT required for protected APIs
- [ ] Invalid or missing JWT is rejected
- [ ] CORS allows frontend origin as configured
- [ ] SQL operations use parameterized access through backend code
- [ ] Secrets are not committed in public documentation or screenshots

## Browser Testing

- [ ] Chrome page load
- [ ] Browser console has no JavaScript errors
- [ ] Network tab has no missing CSS files
- [ ] Network tab has no missing JavaScript files
- [ ] Network tab has no missing image/icon files
- [ ] Forms submit correctly
- [ ] Navigation links work
- [ ] Refresh behavior works
