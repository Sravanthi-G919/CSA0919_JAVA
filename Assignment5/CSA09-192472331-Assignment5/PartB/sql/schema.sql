CREATE DATABASE IF NOT EXISTS co5_assignment;
USE co5_assignment;

CREATE TABLE IF NOT EXISTS bus_pass (
    pass_id VARCHAR(32) PRIMARY KEY,
    reg_no VARCHAR(32) NOT NULL,
    name VARCHAR(100) NOT NULL,
    route_no INT NOT NULL,
    amount DOUBLE NOT NULL CHECK (amount >= 0)
);

CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(100) PRIMARY KEY,
    pwd VARCHAR(100) NOT NULL
);

-- Disposable assignment-only test account. Never use real credentials.
INSERT INTO users(id, pwd) VALUES ('demo', 'demo123')
ON DUPLICATE KEY UPDATE pwd = VALUES(pwd);
