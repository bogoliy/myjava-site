
DROP TABLE Opinion IF EXISTS;
DROP TABLE Article IF EXISTS;
DROP TABLE User IF EXISTS;

CREATE TABLE User (
       us_id INT NOT NULL
     , us_name VARCHAR(128) NOT NULL
     , us_login VARCHAR(32) NOT NULL
     , us_password VARCHAR(256) NOT NULL
     , PRIMARY KEY (us_id)
);

CREATE TABLE Article (
       ar_id INT NOT NULL
     , ar_title VARCHAR(512) NOT NULL
     , ar_filename VARCHAR(255) NOT NULL
     , us_id INT NOT NULL
     , ar_date DATETIME
     , PRIMARY KEY (ar_id)
);

CREATE TABLE Opinion (
       op_id INT NOT NULL
     , op_text VARCHAR(1024) NOT NULL
     , us_id INT NOT NULL
     , op_date DATETIME
     , PRIMARY KEY (op_id)
);

ALTER TABLE Article
  ADD CONSTRAINT FK_Article_1
      FOREIGN KEY (us_id)
      REFERENCES User (us_id);

ALTER TABLE Opinion
  ADD CONSTRAINT FK_Opinion_1
      FOREIGN KEY (us_id)
      REFERENCES User (us_id);

