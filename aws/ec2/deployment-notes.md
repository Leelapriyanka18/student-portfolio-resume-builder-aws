# Amazon EC2 Deployment

Status: Verified Implemented; final screenshot evidence required.

## Repository Implementation

The active backend is `student-portfolio-springboot/`. It builds a Spring Boot executable jar and is configured to listen on all interfaces:

```properties
server.address=0.0.0.0
server.port=${SERVER_PORT:8080}
```

## Build Artifact

```powershell
cd student-portfolio-springboot
mvn clean package
```

Expected artifact:

```text
student-portfolio-springboot/target/student-portfolio-springboot-0.0.1-SNAPSHOT.jar
```

## systemd Deployment

Create a service such as `/etc/systemd/system/student-portfolio.service`:

```ini
[Unit]
Description=Student Portfolio Spring Boot API
After=network.target

[Service]
User=ec2-user
WorkingDirectory=/opt/student-portfolio
Environment=SERVER_PORT=8080
Environment=SPRING_DATASOURCE_URL=jdbc:mysql://<rds-endpoint>:3306/resumebuilder
Environment=SPRING_DATASOURCE_USERNAME=<db-user>
Environment=SPRING_DATASOURCE_PASSWORD=<db-password>
Environment=JWT_SECRET=<strong-32-byte-or-longer-secret>
Environment=APP_CORS_ALLOWED_ORIGINS=http://student-portfolio-priyanka-4501.s3-website-us-east-1.amazonaws.com
ExecStart=/usr/bin/java -jar /opt/student-portfolio/student-portfolio-springboot-0.0.1-SNAPSHOT.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

Enable and start:

```bash
sudo systemctl daemon-reload
sudo systemctl enable student-portfolio
sudo systemctl start student-portfolio
```

## Final Evidence Checklist

1. EC2 instance is running.
2. Elastic IP is associated with this EC2 instance.
3. Security group allows inbound TCP `8080` from the required client range.
4. RDS security group allows inbound TCP `3306` from the EC2 security group.
5. `systemctl status student-portfolio` is active.
6. `journalctl -u student-portfolio` shows successful startup and no secret leakage.
7. `curl http://localhost:8080/api/auth/login` returns `405 Method Not Allowed`, proving the API is reachable and login expects `POST`.

## AWS Academy Limitations

If `systemd`, package installation, or network rules are restricted by the lab, document the exact error and classify it as an AWS Academy limitation, not a repository defect.
