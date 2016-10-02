# --- !Ups
CREATE TABLE user(
    id                  bigint not null AUTO_INCREMENT,
    name                varchar(255),
    username            varchar(255),
    email               varchar(255),
    updated_timestamp   timestamp,
    created_timestamp   timestamp,
    deleted_timestamp   timestamp,
    constraint  pk_primary  primary key(id)
);

# --- !Downs
DROP TABLE user;