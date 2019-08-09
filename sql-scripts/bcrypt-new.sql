DROP TABLE IF EXISTS
    ssd.users,
    ssd.authorities
;

CREATE TABLE ssd.users (
    username varchar(50) PRIMARY KEY,
    password varchar(80) NOT NULL,
    enabled boolean NOT NULL
);

-- Default password: fun123
INSERT INTO ssd.users VALUES
    ('john', '{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', true),
    ('mary', '{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', true),
    ('susan', '{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', true)
;

CREATE TABLE ssd.authorities (
    username varchar(50) NOT NULL REFERENCES ssd.users (username),
    authority varchar(50) NOT NULL,
    UNIQUE (username, authority)
);

INSERT INTO ssd.authorities VALUES
    ('john', 'ROLE_EMPLOYEE'),
    ('mary', 'ROLE_EMPLOYEE'),
    ('mary', 'ROLE_MANAGER'),
    ('susan', 'ROLE_EMPLOYEE'),
    ('susan', 'ROLE_ADMIN')
;
