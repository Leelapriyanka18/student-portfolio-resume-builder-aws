-- Run these only if the existing RDS tables were created before user-specific contact
-- data and certificate details were added.

ALTER TABLE contacts
    ADD COLUMN user_id INT NOT NULL AFTER id;

ALTER TABLE contacts
    ADD CONSTRAINT fk_contacts_user
    FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE certificates
    ADD COLUMN issue_date VARCHAR(30) NULL AFTER issuer,
    ADD COLUMN certificate_url VARCHAR(500) NULL AFTER issue_date;

-- Verification queries
SELECT id, full_name, email, created_at FROM users ORDER BY id DESC;
SELECT * FROM profiles WHERE user_id = ? ORDER BY id DESC;
SELECT * FROM projects WHERE user_id = ? ORDER BY id DESC;
SELECT * FROM certificates WHERE user_id = ? ORDER BY id DESC;
SELECT * FROM resumes WHERE user_id = ? ORDER BY id DESC;
SELECT * FROM contacts WHERE user_id = ? ORDER BY id DESC;
