create table employees (
  id          BIGSERIAL,
  firstName  VARCHAR(30),
  lastName   VARCHAR(30),
  CONSTRAINT employees_pk PRIMARY KEY (id)
);

