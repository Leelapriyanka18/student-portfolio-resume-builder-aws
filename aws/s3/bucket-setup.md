# Amazon S3 Static Website Hosting

Status: Verified Implemented; final screenshot evidence required.

## Repository Implementation

The active frontend is `frontend/`. It is a static HTML/CSS/JavaScript site and reads the API base URL from:

```text
frontend/assets/js/config.js
```

The repository root `index.html` redirects to `frontend/index.html` for local convenience.

## Deployment Steps

1. Verify `frontend/assets/js/config.js` points to the current EC2 Elastic IP backend.
2. Upload all files under `frontend/` to the S3 website bucket.
3. Set S3 static website index document to `index.html`.
4. Configure bucket policy for intended public read access.
5. Confirm backend CORS includes the S3 website endpoint.

## Final Evidence Checklist

1. S3 static website hosting is enabled.
2. `frontend/index.html`, `frontend/pages/**`, `frontend/assets/css/**`, `frontend/assets/js/**`, and `frontend/assets/images/**` are uploaded.
3. Browser loads HTML, CSS, JavaScript, and SVG assets without `404`.
4. Browser network tab shows API calls going to the EC2 backend URL.
5. Register/login and protected pages work from the S3 website origin.

## Security Notes

- Do not store AWS credentials in frontend files.
- Keep CORS restricted to the S3 website origin and local development origins.
- Use CloudFront and HTTPS for production.
