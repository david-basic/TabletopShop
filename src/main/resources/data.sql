INSERT INTO ITEMS(NAME, CATEGORY, PRICE)
VALUES ('Agricola', 'BOARDGAME', 50);

INSERT INTO ITEMS(NAME, CATEGORY, PRICE)
VALUES ('7 Wonders', 'BOARDGAME', 60);

INSERT INTO ITEMS(NAME, CATEGORY, PRICE)
VALUES ('Ankh', 'BOARDGAME', 100);

INSERT INTO ITEMS(NAME, CATEGORY, PRICE)
VALUES ('Dune: Imperium', 'BOARDGAME', 50);

INSERT INTO users
values ('user', '$2a$12$vsMr3Tphql9JXmA/3Ta2fuia0g1X4pUYkZo6C.b8CzXsUXEe34dga', true); -- password: password

INSERT INTO users
values ('admin', '$2a$12$vsMr3Tphql9JXmA/3Ta2fuia0g1X4pUYkZo6C.b8CzXsUXEe34dga', true); -- password: password

insert into authorities
values ('user', 'ROLE_USER');

insert into authorities
values ('admin', 'ROLE_ADMIN');

insert into authorities
values ('admin', 'ROLE_USER');
