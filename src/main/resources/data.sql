INSERT INTO ITEMS(NAME, CATEGORY, DESCRIPTION, PRICE)
VALUES ('Agricola', 'BOARDGAME', 'Great game!', 50);

INSERT INTO ITEMS(NAME, CATEGORY, DESCRIPTION, PRICE)
VALUES ('7 Wonders', 'BOARDGAME', 'Complete novelty on the market!', 60);

INSERT INTO ITEMS(NAME, CATEGORY, DESCRIPTION, PRICE)
VALUES ('Ankh', 'BOARDGAME', 'Best old Egypt game out there!', 100);

INSERT INTO ITEMS(NAME, CATEGORY, DESCRIPTION, PRICE)
VALUES ('Dune: Imperium', 'BOARDGAME', 'It is just like the movies!', 50);

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
