# IAM Permissions

Status: Verified Implemented; final screenshot evidence required.

## Repository Implementation

The repository does not require local AWS access keys for runtime. EC2 should use an attached instance profile.

## AWS Academy Role

AWS Academy labs commonly provide `LabInstanceProfile`. Use the lab-provided role when available.

## Required Permissions Discussion

Minimum intended permissions:

- EC2 instance role for CloudWatch log/metric publishing if the CloudWatch Agent is used.
- SNS permissions only for users/operators creating topics and subscriptions.
- S3 deployment permissions for the operator uploading frontend files.
- RDS access is controlled through network security groups and database credentials, not IAM for this application.

## Least Privilege

Production IAM should separate:

- Frontend deploy permissions.
- Backend EC2 runtime role.
- Monitoring setup permissions.
- Database administration permissions.

## Final Evidence Checklist

1. EC2 instance has the expected role attached.
2. No long-lived AWS keys are stored on EC2 or in the repository.
3. IAM permissions are sufficient for CloudWatch/SNS only if those services are configured.
4. Permissions are not broader than required for the project.

## Repository Status

No AWS access keys were found during repository secret scanning.
