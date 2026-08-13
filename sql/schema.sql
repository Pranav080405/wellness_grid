-- Run this in MySQL Workbench BEFORE starting Spring Boot
CREATE DATABASE IF NOT EXISTS trexova_db;
USE trexova_db;

-- Tables are auto-created by JPA, but here for reference:
CREATE TABLE IF NOT EXISTS retreats (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
    location        VARCHAR(255) NOT NULL,
    country         VARCHAR(100),
    type            VARCHAR(50),
    description     TEXT,
    price           DOUBLE,
    duration        VARCHAR(100),
    image_url       VARCHAR(500),
    rating          DOUBLE,
    review_count    INT,
    featured        BOOLEAN DEFAULT FALSE,
    active          BOOLEAN DEFAULT TRUE,
    amenities       TEXT,
    available_dates TEXT
);

CREATE TABLE IF NOT EXISTS leads (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name      VARCHAR(100) NOT NULL,
    last_name       VARCHAR(100) NOT NULL,
    email           VARCHAR(255) NOT NULL,
    phone           VARCHAR(50),
    retreat_id      BIGINT,
    retreat_title   VARCHAR(255),
    preferred_date  VARCHAR(50),
    guest_count     VARCHAR(50),
    message         TEXT,
    status          VARCHAR(50) DEFAULT 'NEW',
    created_at      DATETIME
);