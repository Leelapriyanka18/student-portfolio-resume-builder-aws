# Elastic IP

Status: Verified Implemented; final screenshot evidence required.

## Purpose

The Elastic IP provides a stable public endpoint for the EC2-hosted Spring Boot API. The frontend uses this stable address as the backend base URL.

## Architecture Position

```text
Users
  -> Amazon S3 Frontend
  -> Elastic IP
  -> Amazon EC2
  -> Spring Boot API
  -> Amazon RDS
```

## Repository Implementation

The frontend backend base URL is configured in:

```text
frontend/assets/js/config.js
```

The backend CORS configuration allows the S3 website origin through:

```text
APP_CORS_ALLOWED_ORIGINS
```

## Final Evidence Checklist

1. Elastic IP exists.
2. Elastic IP is associated with the active EC2 backend instance.
3. Frontend `config.js` points to the Elastic IP and port `8080`.
4. Security group allows inbound TCP `8080`.
5. Browser can reach the backend through the Elastic IP.

## Production Recommendation

For production, use a domain name and HTTPS through CloudFront, an Application Load Balancer, or a reverse proxy instead of exposing a raw IP and port.
