
INSERT INTO USERS (username, password, name, email, enabled, role, gender, birthday, website, bio, phone,privacy,
verified, allow_messages, allow_tags)
VALUES ('user@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'User', 'isa59202021@gmail.com',
true, 'USER', 'Male','2020/5/12', 'dadwdaw', 'qwdqdqd', '2212312312',true, true, false, false);
INSERT INTO USERS (username, password, name, email, enabled, role, gender, birthday, website, bio, phone,privacy,
verified, allow_messages, allow_tags)
VALUES ('user2@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'User', 'isa59202021@gmail.com',
true, 'USER', 'Male','2020/5/12', 'dadwdaw', 'qwdqdqd', '2212312312',false,true, true, true);
INSERT INTO USERS (username, password, name, email, enabled, role, gender, birthday, website, bio, phone,privacy,
verified, allow_messages, allow_tags)
VALUES ('user3@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'User', 'isa59202021@gmail.com',
true, 'USER', 'Male','2020/5/12', 'dadwdaw', 'qwdqdqd', '2212312312',false,false,false,false);
--INSERT INTO USERS (username, password, name, email, enabled, role) VALUES ('admin', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Admin', 'isa59202021@gmail.com', true, 'ADMIN');
--INSERT INTO USERS (username, password, name, email, enabled, role) VALUES ('admin2', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Admin', 'isa59202021@gmail.com', true, 'ADMIN');

INSERT INTO USERS (username, password, name, email, enabled, privacy, role, verified, allow_messages, allow_tags) VALUES ('user', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'User', 'isa59202021@gmail.com', true, false, 'USER', true, true, true);
INSERT INTO USERS (username, password, name, email, enabled, privacy, role, verified, allow_messages, allow_tags) VALUES ('admin', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Admin', 'isa59202021@gmail.com', true, true, 'ADMIN', false, false, false);


INSERT INTO AUTHORITY (name) VALUES ('ROLE_USER');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 2);

--INSERT INTO USERS_FOLLOWING (user_id, following_id) VALUES (1, 2);
--INSERT INTO USERS_FOLLOWING (user_id, following_id) VALUES (1, 3);

--INSERT INTO USERS_FOLLOWERS (user_id, followers_id) VALUES (1, 2);
--INSERT INTO USERS_FOLLOWERS (user_id, followers_id) VALUES (1, 3);
--ALTER TABLE USERS_FOLLOWERS DROP CONSTRAINT IF EXISTS uk_qsiry2t1gl8p1v4er0emu1sht;
