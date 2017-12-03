create table db_version (
  major       SMALLINT NOT NULL,
  minor       SMALLINT NOT NULL,
  build       SMALLINT NOT NULL,
  updated     TIMESTAMP,
  CONSTRAINT  db_version_pk PRIMARY KEY (major, minor, build)
);

