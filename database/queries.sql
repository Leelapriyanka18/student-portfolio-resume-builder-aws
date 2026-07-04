-- RDS schema verification and repair script for the active Spring Boot app.
-- Prefer deploying the latest backend first: DatabaseSchemaInitializer performs
-- these checks automatically at startup when APP_DATABASE_SCHEMA_INITIALIZER_ENABLED=true.
-- Use this file manually only when you need direct SQL evidence or startup DDL is disabled.

-- Current table audit
SELECT table_name, column_name, column_type, is_nullable, column_default
FROM information_schema.columns
WHERE table_schema = DATABASE()
  AND table_name IN ('users', 'profiles', 'projects', 'certificates', 'resumes', 'contacts')
ORDER BY table_name, ordinal_position;

SELECT table_name, index_name, column_name, non_unique
FROM information_schema.statistics
WHERE table_schema = DATABASE()
  AND table_name IN ('users', 'profiles', 'projects', 'certificates', 'resumes', 'contacts')
ORDER BY table_name, index_name, seq_in_index;

SELECT table_name, constraint_name, constraint_type
FROM information_schema.table_constraints
WHERE table_schema = DATABASE()
  AND table_name IN ('users', 'profiles', 'projects', 'certificates', 'resumes', 'contacts')
ORDER BY table_name, constraint_name;

-- Apply database/schema.sql first if any table is missing.
-- For older RDS tables, run only the ALTER statements needed after checking
-- information_schema above. MySQL versions before 8.0.29 may not support
-- ADD COLUMN IF NOT EXISTS; in that case, check information_schema first.

ALTER TABLE certificates
    ADD COLUMN IF NOT EXISTS user_id INT NULL AFTER id,
    ADD COLUMN IF NOT EXISTS certificate_name VARCHAR(200) NULL AFTER user_id,
    ADD COLUMN IF NOT EXISTS issuer VARCHAR(200) NULL AFTER certificate_name,
    ADD COLUMN IF NOT EXISTS issue_date VARCHAR(30) NULL AFTER issuer,
    ADD COLUMN IF NOT EXISTS certificate_url VARCHAR(500) NULL AFTER issue_date,
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE contacts
    ADD COLUMN IF NOT EXISTS user_id INT NULL AFTER id,
    ADD COLUMN IF NOT EXISTS name VARCHAR(150) NULL AFTER user_id,
    ADD COLUMN IF NOT EXISTS email VARCHAR(150) NULL AFTER name,
    ADD COLUMN IF NOT EXISTS subject VARCHAR(200) NULL AFTER email,
    ADD COLUMN IF NOT EXISTS message TEXT NULL AFTER subject,
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE resumes
    ADD COLUMN IF NOT EXISTS certificate_details TEXT NULL AFTER certificates,
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Create these only if they are missing in the index audit above.
CREATE INDEX idx_certificates_user_id ON certificates (user_id);
CREATE INDEX idx_contacts_user_id ON contacts (user_id);
CREATE INDEX idx_resumes_user_id ON resumes (user_id);
CREATE INDEX idx_projects_user_id ON projects (user_id);

-- Add these foreign keys after confirming there are no orphaned non-null user_id values.
ALTER TABLE certificates
    ADD CONSTRAINT fk_certificates_user
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE contacts
    ADD CONSTRAINT fk_contacts_user
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE resumes
    ADD CONSTRAINT fk_resumes_user
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE projects
    ADD CONSTRAINT fk_projects_user
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

-- Endpoint verification queries
SELECT id, full_name, email, created_at FROM users ORDER BY id DESC LIMIT 10;
SELECT * FROM profiles WHERE user_id = ? ORDER BY id DESC;
SELECT * FROM projects WHERE user_id = ? ORDER BY id DESC;
SELECT * FROM certificates WHERE user_id = ? ORDER BY id DESC;
SELECT * FROM resumes WHERE user_id = ? ORDER BY id DESC;
SELECT * FROM contacts WHERE user_id = ? ORDER BY id DESC;
