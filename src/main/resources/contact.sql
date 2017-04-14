DROP TABLE IF EXISTS contacts;
CREATE TABLE contacts(
  id SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(20) NOT NULL,
  birthday VARCHAR(30) NOT NULL
);

INSERT INTO contacts (name, birthday) VALUES ('Mike', '1993-12-15');
INSERT INTO contacts (name, birthday) VALUES ('Kate', '1994-12-06');
INSERT INTO contacts (name, birthday) VALUES ('Jerry', '2015-04-01');