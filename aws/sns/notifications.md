# Amazon SNS Notifications

Status: Implemented in AWS Console; final email-delivery evidence required.

## Purpose

Amazon SNS is part of the intended monitoring architecture. It sends email notifications when CloudWatch alarms enter an alarm state.

## Architecture

```text
Amazon CloudWatch
  -> CloudWatch Alarm
  -> Amazon SNS Topic
  -> Email Subscription
  -> Email Notification
```

## Repository Implementation

The repository documents SNS setup. The project status reports that SNS notifications were created and connected to CloudWatch alarms. Repository files alone cannot independently verify live AWS resources, so final screenshots remain required.

## Implemented AWS Console Setup

1. Standard SNS topic: `portfolio-alerts`.
2. Email subscription created for project notifications.
3. CloudWatch alarms connected to the SNS topic.
4. Notification testing must be preserved as final evidence.

## Verification Checklist

1. SNS topic exists.
2. Email subscription status is `Confirmed`.
3. CloudWatch alarms target the SNS topic.
4. Test notification email is received.

Do not submit the project without screenshots showing every checklist item.
