# --- !Ups

create table user (
  id                            integer auto_increment not null,
  name                          varchar(255),
  username                      varchar(255) not null,
  email                         varchar(255),
  deleted_timestamp             TIMESTAMP ,
  created_timestamp             TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_timestamp             TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  constraint uq_user_username unique (username),
  constraint pk_user primary key (id)
);


# --- !Downs
