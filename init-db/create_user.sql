CREATE TABLE IF NOT EXISTS users(
    user_id BIGSERIAL PRIMARY KEY, 
    username VARCHAR(50) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    enabled BOOLEAN


);


CREATE TABLE IF NOT EXISTS roles(
    role_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50)
);


CREATE TABLE IF NOT EXISTS users_roles(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);


INSERT INTO roles(name) VALUES('ROLE_ADMIN'), ('ROLE_USER'), ('ROLE_MOD');