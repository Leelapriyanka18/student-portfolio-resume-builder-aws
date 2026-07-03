# Project Report

## Problem Statement

Students need a simple web application to manage portfolio details, projects, certificates, resume information, and contact messages in a cloud-hosted environment suitable for internship submission.

## Objectives

- Provide authenticated student registration and login.
- Save and display profile information.
- Manage projects and certificates.
- Build and store resume data.
- Generate a resume PDF in the browser.
- Deploy frontend and backend using AWS services.
- Document setup, testing, and operational checks.

## Technologies Used

- HTML, CSS, JavaScript
- Spring Boot 3
- Spring Security with JWT
- JdbcTemplate
- MySQL on Amazon RDS
- Amazon S3 static website hosting
- Amazon EC2 backend hosting
- CloudWatch and SNS planned for monitoring and alerts

## Current Result

The active repository builds successfully with Maven when dependency resolution is available. Frontend pages are integrated with existing backend APIs. AWS resources still require live manual verification before they can be marked complete.

## Future Scope

- Add automated backend tests.
- Add frontend smoke tests.
- Move frontend hosting to CloudFront with HTTPS.
- Add backend HTTPS through a load balancer or reverse proxy.
- Complete and verify CloudWatch/SNS alarms in AWS.
