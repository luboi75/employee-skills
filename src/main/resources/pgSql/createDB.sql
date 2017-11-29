create table employees (
  id          BIGSERIAL,
  first_name  VARCHAR(30),
  last_name   VARCHAR(30),
  CONSTRAINT employees_pk PRIMARY KEY (id)
);

create table skills (
  id          BIGSERIAL,
  skill_name  VARCHAR(30),
  CONSTRAINT skills_pk PRIMARY KEY (id)
);

create table employees2skills (
  employee_id BIGINT NOT NULL,
  skill_id    BIGINT NOT NULL,
  skill_level BIGINT,
  CONSTRAINT fk_employee  FOREIGN KEY (employee_id) REFERENCES employees (id),
  CONSTRAINT fk_skill     FOREIGN KEY (skill_id)    REFERENCES skills (id),
  CONSTRAINT employees2skills_pk PRIMARY KEY (employee_id, skill_id)
);

create table db_version (
  major       SMALLINT NOT NULL,
  minor       SMALLINT NOT NULL,
  build       SMALLINT NOT NULL,
  updated     TIMESTAMP,
  CONSTRAINT  db_version_pk PRIMARY KEY (major, minor, build)
)