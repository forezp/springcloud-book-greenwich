
INSERT INTO user (id, username, password) VALUES (1, 'forezp', '$2a$10$bMyZbbveh9e6oszmL1PpT.eseXkZA0H/fMyRn.AoV0KhAuCrl4Qb6');
INSERT INTO user (id, username, password)  VALUES (2, 'admin', '$2a$10$bMyZbbveh9e6oszmL1PpT.eseXkZA0H/fMyRn.AoV0KhAuCrl4Qb6');

INSERT INTO role (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO role (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);
