CREATE TABLE IF NOT EXISTS email (
    id SERIAL PRIMARY KEY,
    uuid INTEGER NOT NULL,
    email_from VARCHAR(254),
    creation_time TIMESTAMP,
    last_modified_time TIMESTAMP,
    state VARCHAR(7),
    body TEXT
);

CREATE TABLE IF NOT EXISTS email_receivers (
    id SERIAL PRIMARY KEY,
    email_id INT NOT NULL CONSTRAINT id REFERENCES email,
    email_to VARCHAR(254),
    hidden boolean
);
