-- Development-only sample data for local testing and demonstrations.
-- Do not run this script against production or shared live data.
-- The script is idempotent: it removes the existing demo account and
-- related demo-owned records before inserting a fresh sample dataset.
-- Demo credentials: demo.student@example.com / DemoPass#123

START TRANSACTION;

SET @demo_email = 'demo.student@example.com';
SET @demo_user_id = (
    SELECT id
    FROM users
    WHERE email = @demo_email
    LIMIT 1
);

-- Delete child records first so the script also works if cascade rules are
-- missing in an older local database created before the latest schema.
DELETE FROM contacts WHERE user_id = @demo_user_id;
DELETE FROM resumes WHERE user_id = @demo_user_id;
DELETE FROM certificates WHERE user_id = @demo_user_id;
DELETE FROM projects WHERE user_id = @demo_user_id;
DELETE FROM profiles WHERE user_id = @demo_user_id;
DELETE FROM users WHERE id = @demo_user_id;

INSERT INTO users (full_name, email, password)
VALUES (
    'Demo Student',
    @demo_email,
    'PBKDF2$100000$MDEyMzQ1Njc4OWFiY2RlZg==$oqL1GY+WCIfvJCb6iqvuhy/lqblBNReN4JRtlKqFraY='
);

SET @demo_user_id = LAST_INSERT_ID();

INSERT INTO profiles (user_id, headline, bio, phone, address, linkedin_url, github_url)
VALUES (
    @demo_user_id,
    'Computer Science Student',
    'Aspiring full-stack developer with interests in Java, cloud deployment, and clean web interfaces.',
    '+91 98765 43210',
    'India',
    'https://www.linkedin.com/in/demo-student',
    'https://github.com/demo-student'
);

INSERT INTO projects (user_id, title, description, github_link)
VALUES (
    @demo_user_id,
    'Student Portfolio Resume Builder',
    'A Spring Boot and AWS based student portfolio and resume management project.',
    'https://github.com/demo-student/student-portfolio-resume-builder-aws'
);

INSERT INTO certificates (user_id, certificate_name, issuer, issue_date, certificate_url)
VALUES (
    @demo_user_id,
    'AWS Cloud Foundations',
    'AWS Academy',
    '2026',
    'https://example.com/certificate'
);

INSERT INTO resumes (
    user_id,
    resume_name,
    email,
    phone,
    address,
    role,
    summary,
    college,
    degree,
    branch,
    graduation_year,
    cgpa,
    skills,
    projects,
    project_description,
    certificates,
    certificate_details,
    experience,
    github,
    linkedin,
    languages,
    hobbies,
    file_path
)
VALUES (
    @demo_user_id,
    'Demo Resume',
    'demo.student@example.com',
    '+91 98765 43210',
    'India',
    'Full Stack Developer Intern',
    'Student developer focused on Java, REST APIs, MySQL, and AWS deployment.',
    'Demo Engineering College',
    'B.Tech',
    'Computer Science and Engineering',
    '2026',
    '8.5',
    'Java, Spring Boot, MySQL, JavaScript, AWS',
    'Student Portfolio Resume Builder',
    'Built authenticated portfolio and resume workflows deployed with AWS services.',
    'AWS Cloud Foundations',
    'Completed AWS Academy cloud fundamentals coursework.',
    'Academic project and internship-ready portfolio development.',
    'https://github.com/demo-student',
    'https://www.linkedin.com/in/demo-student',
    'English, Hindi',
    'Coding, technical writing',
    NULL
);

INSERT INTO contacts (user_id, name, email, subject, message)
VALUES (
    @demo_user_id,
    'Demo Recruiter',
    'recruiter@example.com',
    'Portfolio Review',
    'Your portfolio project is ready for review.'
);

COMMIT;
