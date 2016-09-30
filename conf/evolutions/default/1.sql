# --- !Ups
CREATE TABLE user(
    id                  bigint not null,
    name                varchar(255),
    username            varchar(255),
    email               varchar(255),
    updated_timestamp   timestamp,
    created_timestamp   timestamp,
    deleted_timestamp   timestamp,
    constraint  pk_primary  primary key(id)
);

CREATE SEQUENCE user_seq;

--DELIMITER //
--DROP TRIGGER IF EXISTS user_create_insert_trigger//
--CREATE TRIGGER user_create_insert_trigger
--BEFORE INSERT ON user
--FOR EACH ROW
--BEGIN
--IF NEW.createdTimestamp = '0000-00-00 00:00:00' THEN
--SET NEW.createdTimestamp = NOW();
--END IF;
--END;//
--DELIMITER ;
--
--DELIMITER //
--DROP TRIGGER IF EXISTS user_update_insert_trigger//
--CREATE TRIGGER user_update_insert_trigger
--BEFORE INSERT ON user
--FOR EACH ROW
--BEGIN
--IF NEW.updatedTimestamp = '0000-00-00 00:00:00' THEN
--SET NEW.updatedTimestamp = NOW();
--END IF;
--END;//
--DELIMITER ;


