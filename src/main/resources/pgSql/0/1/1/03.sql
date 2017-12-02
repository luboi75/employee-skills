create table employees2skills (
  employee_id BIGINT NOT NULL,
  skill_id    BIGINT NOT NULL,
  skill_level BIGINT,
  CONSTRAINT fk_employee  FOREIGN KEY (employee_id) REFERENCES employees (id),
  CONSTRAINT fk_skill     FOREIGN KEY (skill_id)    REFERENCES skills (id),
  CONSTRAINT employees2skills_pk PRIMARY KEY (employee_id, skill_id)
);

