# Architecture Diagram And Component Explanation

## High-Level Architecture

```text
Browser
  |
  v
Amazon S3 Static Website
  |
  v
Amazon EC2
  |
  v
Spring Boot REST API
  |
  v
Amazon RDS MySQL
  |
  v
Amazon SNS
  |
  v
Amazon CloudWatch
```

## Component Explanation

### Browser

The browser is used by students, faculty, and evaluators to access the web application. It loads HTML, CSS, JavaScript, and image assets from Amazon S3 and sends API requests to the Spring Boot backend.

### Amazon S3

Amazon S3 hosts the static frontend website. The bucket serves `index.html`, page files, CSS, JavaScript, and image/icon assets through the S3 static website endpoint.

### Amazon EC2

Amazon EC2 hosts the Spring Boot backend application. The backend runs as a deployed Java application and exposes REST APIs for authentication, profile, projects, certificates, resume, and contact modules.

### Spring Boot

Spring Boot provides the REST API layer, authentication logic, validation, business logic, and database access. It receives requests from the frontend and communicates with Amazon RDS.

### Amazon RDS

Amazon RDS MySQL stores persistent project data including users, profiles, projects, certificates, resumes, and contacts. It provides a managed database layer for the application.

### Amazon SNS

Amazon SNS supports notification capability in the AWS architecture. It is used as the notification service component for cloud-based alerting or communication workflows.

### Amazon CloudWatch

Amazon CloudWatch provides monitoring evidence through logs and metrics. It supports operational visibility for the deployed application and AWS resources.

## Architecture Validation Points

- Frontend and backend are separated.
- Static hosting is handled by S3.
- Backend compute is handled by EC2.
- Persistent storage is handled by RDS MySQL.
- Monitoring is handled by CloudWatch.
- Notifications are represented through SNS.
- Access control and networking are supported by IAM and Security Groups.
