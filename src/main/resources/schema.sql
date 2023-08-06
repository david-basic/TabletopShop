CREATE TABLE ITEMS
(
    ID          BIGINT GENERATED ALWAYS AS IDENTITY,
    NAME        VARCHAR(30)   NOT NULL,
    CATEGORY    VARCHAR(30)   NOT NULL,
    DESCRIPTION VARCHAR(200)  NOT NULL,
    PRICE       DECIMAL(5, 2) NOT NULL,
    PRIMARY KEY (ID)
);

create table users
(
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(500) not null,
    enabled  boolean not null
);

create table authorities
(
    username  varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key (username) references users (username)
);
create unique index ix_auth_username on authorities (username, authority);