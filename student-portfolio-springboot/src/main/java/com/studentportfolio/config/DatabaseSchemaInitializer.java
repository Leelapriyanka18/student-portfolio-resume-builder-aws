package com.studentportfolio.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSchemaInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseSchemaInitializer.class);

    private final JdbcTemplate jdbcTemplate;
    private final boolean enabled;

    public DatabaseSchemaInitializer(
            JdbcTemplate jdbcTemplate,
            @Value("${app.database.schema-initializer.enabled:true}") boolean enabled) {
        this.jdbcTemplate = jdbcTemplate;
        this.enabled = enabled;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeSchema() {
        if (!enabled) {
            LOGGER.info("Database schema initializer is disabled");
            return;
        }

        createTables();
        addMissingColumns();
        addIndexes();
        addForeignKeys();

        LOGGER.info("Database schema verification completed");
    }

    private void createTables() {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    full_name VARCHAR(150) NOT NULL,
                    email VARCHAR(150) NOT NULL UNIQUE,
                    password VARCHAR(255) NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """);

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS profiles (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT NOT NULL,
                    headline VARCHAR(255) NOT NULL,
                    bio TEXT,
                    phone VARCHAR(30) NOT NULL,
                    address TEXT,
                    linkedin_url VARCHAR(500),
                    github_url VARCHAR(500),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    UNIQUE KEY uq_profiles_user_id (user_id),
                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                )
                """);

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS projects (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT NOT NULL,
                    title VARCHAR(200) NOT NULL,
                    description TEXT NOT NULL,
                    github_link VARCHAR(500),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    INDEX idx_projects_user_id (user_id),
                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                )
                """);

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS certificates (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT NOT NULL,
                    certificate_name VARCHAR(200) NOT NULL,
                    issuer VARCHAR(200) NOT NULL,
                    issue_date VARCHAR(30),
                    certificate_url VARCHAR(500),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    INDEX idx_certificates_user_id (user_id),
                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                )
                """);

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS resumes (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT NOT NULL,
                    resume_name VARCHAR(200) NOT NULL,
                    email VARCHAR(150),
                    phone VARCHAR(30),
                    address TEXT,
                    role VARCHAR(150),
                    summary TEXT,
                    college VARCHAR(200),
                    degree VARCHAR(150),
                    branch VARCHAR(150),
                    graduation_year VARCHAR(20),
                    cgpa VARCHAR(20),
                    skills TEXT,
                    projects TEXT,
                    project_description TEXT,
                    certificates TEXT,
                    certificate_details TEXT,
                    experience TEXT,
                    github VARCHAR(500),
                    linkedin VARCHAR(500),
                    languages VARCHAR(255),
                    hobbies VARCHAR(255),
                    file_path VARCHAR(500),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    INDEX idx_resumes_user_id (user_id),
                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                )
                """);

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS contacts (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT NOT NULL,
                    name VARCHAR(150) NOT NULL,
                    email VARCHAR(150) NOT NULL,
                    subject VARCHAR(200) NOT NULL,
                    message TEXT NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    INDEX idx_contacts_user_id (user_id),
                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                )
                """);
    }

    private void addMissingColumns() {
        addColumnIfMissing("certificates", "user_id", "INT NULL AFTER id");
        addColumnIfMissing("certificates", "certificate_name", "VARCHAR(200) NULL AFTER user_id");
        addColumnIfMissing("certificates", "issuer", "VARCHAR(200) NULL AFTER certificate_name");
        addColumnIfMissing("certificates", "issue_date", "VARCHAR(30) NULL AFTER issuer");
        addColumnIfMissing("certificates", "certificate_url", "VARCHAR(500) NULL AFTER issue_date");
        addColumnIfMissing("certificates", "created_at", "TIMESTAMP DEFAULT CURRENT_TIMESTAMP");

        addColumnIfMissing("contacts", "user_id", "INT NULL AFTER id");
        addColumnIfMissing("contacts", "name", "VARCHAR(150) NULL AFTER user_id");
        addColumnIfMissing("contacts", "email", "VARCHAR(150) NULL AFTER name");
        addColumnIfMissing("contacts", "subject", "VARCHAR(200) NULL AFTER email");
        addColumnIfMissing("contacts", "message", "TEXT NULL AFTER subject");
        addColumnIfMissing("contacts", "created_at", "TIMESTAMP DEFAULT CURRENT_TIMESTAMP");

        addColumnIfMissing("profiles", "created_at", "TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
        addColumnIfMissing("projects", "created_at", "TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
        addColumnIfMissing("resumes", "certificate_details", "TEXT NULL AFTER certificates");
        addColumnIfMissing("resumes", "created_at", "TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
    }

    private void addIndexes() {
        addIndexIfMissing("projects", "idx_projects_user_id", "user_id");
        addIndexIfMissing("certificates", "idx_certificates_user_id", "user_id");
        addIndexIfMissing("resumes", "idx_resumes_user_id", "user_id");
        addIndexIfMissing("contacts", "idx_contacts_user_id", "user_id");
    }

    private void addForeignKeys() {
        addForeignKeyIfMissing("profiles", "fk_profiles_user", "user_id");
        addForeignKeyIfMissing("projects", "fk_projects_user", "user_id");
        addForeignKeyIfMissing("certificates", "fk_certificates_user", "user_id");
        addForeignKeyIfMissing("resumes", "fk_resumes_user", "user_id");
        addForeignKeyIfMissing("contacts", "fk_contacts_user", "user_id");
    }

    private void addColumnIfMissing(String tableName, String columnName, String definition) {
        if (columnExists(tableName, columnName)) {
            return;
        }

        jdbcTemplate.execute("ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + definition);
        LOGGER.info("Added missing database column {}.{}", tableName, columnName);
    }

    private void addIndexIfMissing(String tableName, String indexName, String columnName) {
        Integer count = jdbcTemplate.queryForObject("""
                SELECT COUNT(1)
                FROM information_schema.statistics
                WHERE table_schema = DATABASE()
                  AND table_name = ?
                  AND index_name = ?
                """, Integer.class, tableName, indexName);

        if (count != null && count > 0) {
            return;
        }

        jdbcTemplate.execute("CREATE INDEX " + indexName + " ON " + tableName + " (" + columnName + ")");
        LOGGER.info("Added missing database index {} on {}", indexName, tableName);
    }

    private void addForeignKeyIfMissing(String tableName, String constraintName, String columnName) {
        Integer count = jdbcTemplate.queryForObject("""
                SELECT COUNT(1)
                FROM information_schema.table_constraints
                WHERE table_schema = DATABASE()
                  AND table_name = ?
                  AND constraint_name = ?
                  AND constraint_type = 'FOREIGN KEY'
                """, Integer.class, tableName, constraintName);

        if (count != null && count > 0) {
            return;
        }

        try {
            jdbcTemplate.execute("ALTER TABLE " + tableName
                    + " ADD CONSTRAINT " + constraintName
                    + " FOREIGN KEY (" + columnName + ") REFERENCES users(id) ON DELETE CASCADE");
            LOGGER.info("Added missing database foreign key {} on {}", constraintName, tableName);
        } catch (RuntimeException ex) {
            LOGGER.warn("Could not add foreign key {} on {}. Existing orphaned rows may need cleanup.",
                    constraintName, tableName, ex);
        }
    }

    private boolean columnExists(String tableName, String columnName) {
        Integer count = jdbcTemplate.queryForObject("""
                SELECT COUNT(1)
                FROM information_schema.columns
                WHERE table_schema = DATABASE()
                  AND table_name = ?
                  AND column_name = ?
                """, Integer.class, tableName, columnName);

        return count != null && count > 0;
    }
}
