# AWS Screenshot Evidence

Capture AWS screenshots from the AWS Console using the final deployed resources.

| AWS Service | What To Capture | Why It Is Important | Expected Status |
| --- | --- | --- | --- |
| EC2 | EC2 instance details page | Proves backend hosting environment exists | Instance state: running |
| EC2 | Public IPv4 or Elastic IP association | Proves stable backend access | Elastic IP attached |
| RDS | RDS DB instance dashboard | Proves managed MySQL database exists | Instance state: available |
| RDS | Connectivity and security tab | Proves database networking is configured | Endpoint visible and accessible from EC2 |
| S3 | S3 bucket overview | Proves static website bucket exists | Bucket visible |
| S3 | Objects tab with `index.html`, `assets/`, and `pages/` | Proves frontend files are uploaded | Required objects present |
| S3 | Static website hosting settings | Proves website hosting is enabled | Hosting enabled with endpoint |
| SNS | SNS topic page | Proves notification service is configured | Topic available |
| SNS | SNS subscription page | Proves notification target exists | Subscription confirmed |
| CloudWatch | Log groups page | Proves application monitoring evidence exists | Relevant log group visible |
| CloudWatch | Recent log events | Proves runtime logs are captured | Recent log entries visible |
| CloudWatch | Metrics dashboard | Proves infrastructure monitoring | Metrics visible |
| IAM | IAM role or policy used by project | Proves access control design | Least required permissions documented |
| Security Groups | EC2 security group inbound rules | Proves backend network access configuration | Required ports open only as needed |
| Security Groups | RDS security group inbound rules | Proves database access is restricted | MySQL access restricted to allowed source |
| Elastic IP | Elastic IP allocation page | Proves stable public IP allocation | Associated with EC2 |

## Capture Notes

- Hide or blur sensitive values if screenshots are shared publicly.
- Do not expose passwords, secret keys, access keys, or private credentials.
- Keep timestamps visible where possible.
