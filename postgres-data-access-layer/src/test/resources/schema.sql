-- Create sequence for customer ID
CREATE SEQUENCE IF NOT EXISTS s_customer START WITH 1 INCREMENT BY 1;

-- Create customer table
CREATE TABLE IF NOT EXISTS customer
(
    id BIGINT NOT NULL,
    name VARCHAR NOT NULL,
    phone_number VARCHAR NOT NULL UNIQUE,
    CONSTRAINT customer_pkey PRIMARY KEY (id)
);

-- Create sequence for meeting ID
CREATE SEQUENCE IF NOT EXISTS s_meeting START WITH 1 INCREMENT BY 1;

-- Create meeting table
CREATE TABLE IF NOT EXISTS meeting
(
    id BIGINT NOT NULL,
    customer_id BIGINT,
    phone_number VARCHAR NOT NULL,
    source_calendar VARCHAR NOT NULL,
    start_time TIMESTAMP NOT NULL,
    meeting_day DATE NOT NULL,
    notification_state VARCHAR NOT NULL,
    CONSTRAINT meeting_pkey PRIMARY KEY (id),
    CONSTRAINT meeting_customer_fk FOREIGN KEY (customer_id) REFERENCES customer(id)
);
