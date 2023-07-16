INSERT INTO BOARDGAMES(NAME, PUBLISHED, RATING, WEIGHT, DURATION, PRICE)
VALUES ('Agricola', 2016, 8.0, 3.47, 120, 50);

INSERT INTO BOARDGAMES(NAME, PUBLISHED, RATING, WEIGHT, DURATION, PRICE)
VALUES ('7 Wonders', 2020, 7.9, 2.17, 30, 60);

INSERT INTO BOARDGAMES(NAME, PUBLISHED, RATING, WEIGHT, DURATION, PRICE)
VALUES ('Ankh', 2021, 7.9, 3.08, 90, 100);

INSERT INTO BOARDGAMES(NAME, PUBLISHED, RATING, WEIGHT, DURATION, PRICE)
VALUES ('Dune: Imperium', 2020, 8.9, 3.07, 120, 50);

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
