
CREATE sequence if not exists geren_dev.s_customer increment by 1 start with 1;

create table IF NOT EXISTS geren_dev.customer
(
    id bigint NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    phone_number character varying COLLATE pg_catalog."default" NOT NULL unique,
    CONSTRAINT customer_pkey PRIMARY KEY (id)
);

CREATE sequence if not exists geren_dev.s_meeting increment by 1 start with 1;

create table IF NOT EXISTS geren_dev.meeting
(
    id bigint NOT NULL,
    customer_id bigint,
    phone_number character varying COLLATE pg_catalog."default" NOT NULL,
    source_calendar character varying COLLATE pg_catalog."default" NOT NULL,
	start_time TIMESTAMP WITH TIME ZONE not null,
	meeting_day TIMESTAMP WITH TIME ZONE not null,
	state character varying COLLATE pg_catalog."default" NOT NULL,
    calendar_id character varying COLLATE pg_catalog."default" NOT NULL,
    event_id character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT meeting_pkey PRIMARY KEY (id),
	CONSTRAINT meeting_customer_fk FOREIGN KEY (customer_id) REFERENCES geren_dev.customer(id)
);

COMMIT;
