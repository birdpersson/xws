INSERT INTO USERS (username, password, name, email, enabled, role, gender, birthday, website, bio, phone, privacy) VALUES ('user@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'User', 'isa59202021@gmail.com', true,
 'USER', 'Male','2020/5/12', 'dadwdaw', 'qwdqdqd', '2212312312', false);
 INSERT INTO USERS (username, password, name, email, enabled, role, gender, birthday, website, bio, phone, privacy) VALUES ('user2@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'User', 'isa59202021@gmail.com', true,
  'USER', 'Male','2020/5/12', 'dadwdaw', 'qwdqdqd', '2212312312', true);
  INSERT INTO USERS (username, password, name, email, enabled, role, gender, birthday, website, bio, phone, privacy) VALUES ('user3@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'User', 'isa59202021@gmail.com', true,
   'USER', 'Male','2020/5/12', 'dadwdaw', 'qwdqdqd', '2212312312', true);
INSERT INTO USERS (username, password, name, email, enabled, role) VALUES ('admin', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Admin', 'isa59202021@gmail.com', true, 'ADMIN');

INSERT INTO AUTHORITY (name) VALUES ('ROLE_USER');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 2);

INSERT INTO USERS_FOLLOWING (user_id, following_id) VALUES (1, 2);
INSERT INTO USERS_FOLLOWING (user_id, following_id) VALUES (1, 3);


