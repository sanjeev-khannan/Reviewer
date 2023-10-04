CREATE TABLE IF NOT EXISTS userdetails (
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    address_line_1 VARCHAR(255),
    address_line_2 VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255),
    zip_code BIGINT,
    mobile_number VARCHAR(255) UNIQUE,
    created_at TIMESTAMP DEFAULT NOW() NOT NULL,
    updated_at TIMESTAMP DEFAULT NOW() NOT NULL
);

CREATE TABLE IF NOT EXISTS representative (
    rep_id SERIAL PRIMARY KEY,
    open_ticket_count INT NOT NULL,
    total_ticket_count INT NOT NULL,
    availability_status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW() NOT NULL,
    updated_at TIMESTAMP DEFAULT NOW() NOT NULL
);

CREATE TABLE IF NOT EXISTS customersupport (
    ticket_id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    subject VARCHAR(255) NOT NULL,
    assigned_rep BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL,
    comments TEXT NOT NULL,
    message TEXT NOT NULL
);

-- Add a foreign key constraint for the user_id column
ALTER TABLE customersupport
ADD CONSTRAINT fk_customersupport_user
FOREIGN KEY (user_id)
REFERENCES userdetails(user_id);

-- Add a foreign key constraint for the assigned_rep column
ALTER TABLE customersupport
ADD CONSTRAINT fk_customersupport_rep
FOREIGN KEY (assigned_rep)
REFERENCES representative(rep_id);
