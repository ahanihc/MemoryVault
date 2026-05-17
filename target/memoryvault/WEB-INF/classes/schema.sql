CREATE DATABASE IF NOT EXISTS memoryvault_db;
USE memoryvault_db;

DROP TABLE IF EXISTS access_logs;
DROP TABLE IF EXISTS media_files;
DROP TABLE IF EXISTS capsules;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE capsules (
    capsule_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(150) NOT NULL,
    secret_letter TEXT,
    unlock_date DATETIME NOT NULL,
    capsule_type VARCHAR(50),
    is_unlocked BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id)
    REFERENCES users(user_id)
    ON DELETE CASCADE
);

CREATE TABLE media_files (
    media_id INT AUTO_INCREMENT PRIMARY KEY,
    capsule_id INT NOT NULL,
    file_name VARCHAR(255),
    file_type VARCHAR(100),
    file_data LONGBLOB,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (capsule_id)
    REFERENCES capsules(capsule_id)
    ON DELETE CASCADE
);

CREATE TABLE access_logs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    capsule_id INT,
    action VARCHAR(100),
    accessed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (capsule_id)
    REFERENCES capsules(capsule_id)
    ON DELETE CASCADE
);

CREATE INDEX idx_unlock_date ON capsules(unlock_date);
CREATE INDEX idx_user_id ON capsules(user_id);