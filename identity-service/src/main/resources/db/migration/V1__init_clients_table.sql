CREATE TABLE clients (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         created_at DATETIME(6) NOT NULL,
                         is_deleted BIT NOT NULL DEFAULT 0,
                         updated_at DATETIME(6),
                         name VARCHAR(255),
                         password VARCHAR(255) NOT NULL,
                         user_name VARCHAR(255) NOT NULL,
                         PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;