CREATE TABLE uuser(
                      user_id bigint auto_increment primary key,
                      email varchar(255) not null unique,
                      name varchar(255) not null,
                      password varchar(255) not null
);

CREATE TABLE donation_beneficiary(
                                     donation_beneficiary_id bigint auto_increment primary key,
                                     name varchar(255) not null,
                                     contents varchar(1024) not null
);

CREATE TABLE donation_detail(
                                donation_detail_id bigint auto_increment primary key,
                                user_id bigint not null,
                                donation_beneficiary_id bigint not null,
                                amount bigint not null,
                                created_at datetime not null
);

