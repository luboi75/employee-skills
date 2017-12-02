create table employees (
  id          BIGSERIAL,
  first_name  VARCHAR(30),
  last_name   VARCHAR(30),
  CONSTRAINT employees_pk PRIMARY KEY (id)
);

