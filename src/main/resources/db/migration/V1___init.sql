CREATE TABLE ticket (
	id BIGSERIAL NOT NULL,
	uuid VARCHAR(64) NOT NULL,
	name VARCHAR(16) NOT NULL,
	type VARCHAR(16) NOT NULL,
    state INTEGER NOT NULL DEFAULT 0,
  PRIMARY KEY (id)
);
