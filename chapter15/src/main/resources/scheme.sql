DROP TABLE IF EXISTS 'role';
CREATE TABLE 'role' (
  'id' bigint(20) NOT NULL AUTO_INCREMENT,
  'name' varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY ('id')
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
DROP TABLE IF EXISTS 'user';
CREATE TABLE 'user' (
  'id' bigint(20) NOT NULL AUTO_INCREMENT,
  'password' varchar(255) COLLATE utf8_bin DEFAULT NULL,
  'username' varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY ('id'),
  UNIQUE KEY 'UK_sb8bbouer5wak8vyiiy4pf2bx' ('username')
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS 'user_role';
CREATE TABLE 'user_role' (
  'user_id' bigint(20) NOT NULL,
  'role_id' bigint(20) NOT NULL,
  KEY 'FKa68196081fvovjhkek5m97n3y' ('role_id'),
  KEY 'FK859n2jvi8ivhui0rl0esws6o' ('user_id'),
  CONSTRAINT 'FK859n2jvi8ivhui0rl0esws6o' FOREIGN KEY ('user_id') REFERENCES 'user' ('id'),
  CONSTRAINT 'FKa68196081fvovjhkek5m97n3y' FOREIGN KEY ('role_id') REFERENCES 'role' ('id')
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin; 

INSERT INTO user (id, username, password) VALUES (1, 'forezp', '123456');
INSERT INTO user (id, username, password)  VALUES (2, 'admin', '123456');

INSERT INTO role (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO role (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);
