-- =========================================================
-- Database: db_nextfly
-- NextFly Ticketing System
-- =========================================================

CREATE DATABASE IF NOT EXISTS db_nextfly;
USE db_nextfly;

DROP TABLE IF EXISTS transaksi;
DROP TABLE IF EXISTS tiket;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nama_lengkap VARCHAR(100) NOT NULL
);

INSERT INTO user (username, password, nama_lengkap) VALUES
('Yanto Ganteng', '123456', 'Yanto Tarmiji'),
('Bob Sigma', '123456', 'Bob Tamiji');

CREATE TABLE tiket (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama_tiket VARCHAR(100) NOT NULL,
    harga DECIMAL(12,2) NOT NULL DEFAULT 0,
    stok_tiket INT NOT NULL DEFAULT 0
);

INSERT INTO tiket (nama_tiket, harga, stok_tiket) VALUES
('Konser Musik Jazz Malam', 250000.00, 100),
('Festival Kuliner Nusantara', 75000.00, 200),
('Pameran Seni Rupa Kontemporer', 50000.00, 150);

CREATE TABLE transaksi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tiket_id INT NOT NULL,
    jumlah INT NOT NULL,
    total_harga DECIMAL(14,2) NOT NULL,
    tanggal DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_transaksi_tiket FOREIGN KEY (tiket_id) REFERENCES tiket(id)
);
