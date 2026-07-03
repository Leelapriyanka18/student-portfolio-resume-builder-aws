# Amazon CloudWatch and Amazon SNS

Overall status: Implemented in AWS Console; final screenshot evidence required.

Live AWS CloudWatch and SNS resources cannot be independently verified from repository files alone. This document reflects the AWS Console implementation status reported for the project.

## Repository Implementation

The repository contains documentation for monitoring setup. It does not include infrastructure-as-code for CloudWatch dashboards, alarms, or SNS topics.

## Native EC2 Metrics

Native EC2 metrics can be used without installing the CloudWatch Agent:

- `CPUUtilization`
- `StatusCheckFailed`
- `StatusCheckFailed_Instance`
- `StatusCheckFailed_System`
- `NetworkIn`
- `NetworkOut`

Status: Implemented for the EC2 instance using native EC2 metrics.

## CloudWatch Dashboard

Purpose:

- Show EC2 CPU usage.
- Show EC2 status checks.
- Show network traffic.
- Provide a simple operational view for internship demonstration.

Implemented AWS Console setup:

1. Dashboard name: `StudentPortfolio-EC2-Dashboard`.
2. EC2 instance: `i-0b10ba968707f711c`.
3. Region: `us-east-1`.
4. Widgets:
   - `CPUUtilization`
   - `StatusCheckFailed`
   - `NetworkIn`
   - `NetworkOut`
   - `DiskReadBytes`
   - `DiskWriteBytes`
   - `CPUCreditUsage` for the `t3.micro` instance

Status: Implemented; capture dashboard screenshot for final submission evidence.

## CloudWatch Alarms

Implemented alarms:

- High CPU alarm: `CPUUtilization > 80%`.
- Status check alarm: `StatusCheckFailed >= 1`.
- Network alarm: threshold based on observed baseline traffic.

Verification checklist:

1. CPU alarm exists and targets the EC2 instance.
2. Status check alarm exists and targets the EC2 instance.
3. Network alarm exists and targets the EC2 instance.
4. Alarms are connected to the SNS topic.
5. Alarm screenshots are captured for project evidence.

Status: Implemented; final screenshot and alert-test evidence required.

## CloudWatch Agent Status

CloudWatch Agent is not required for native EC2 metrics. It is only required for memory, disk, application logs, or custom metrics.

Status: Attempted.

Implementation notes:

- CloudWatch Agent was installed.
- Configuration was created.
- Configuration was verified.
- Startup failed because of the Amazon Linux 2023 / collectd `socket_listener` issue.
- Multiple troubleshooting attempts were performed.
- Native EC2 metrics are used as the recommended alternative.

This is an AWS environment/runtime limitation, not a repository defect.

## Amazon SNS

Status: Implemented in AWS Console; final email-delivery evidence required.

Purpose:

- Receive CloudWatch alarm notifications by email.
- Demonstrate operational alerting for the deployed backend.

Architecture:

```text
Amazon CloudWatch
  -> CloudWatch Alarm
  -> Amazon SNS Topic
  -> Email Subscription
  -> Email Notification
```

Implemented AWS Console setup:

1. SNS topic: `portfolio-alerts`.
2. Email subscription created.
3. CloudWatch alarms connected to SNS.
4. Notification delivery must be captured as final evidence.

Verification checklist:

1. SNS topic exists.
2. Email subscription status is `Confirmed`.
3. CloudWatch alarm has SNS action configured.
4. Test notification email is received.

Do not submit the project without screenshots showing the SNS topic, confirmed email subscription, alarm action, and a received notification email.
