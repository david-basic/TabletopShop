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

INSERT INTO ITEMS(NAME, CATEGORY, DESCRIPTION, QUANTITY, PRICE)
VALUES ('Agricola', 'BOARDGAME', 'Great game!', 40, 50);
INSERT INTO ITEMS(NAME, CATEGORY, DESCRIPTION, QUANTITY, PRICE)
VALUES ('7 Wonders', 'BOARDGAME', 'Complete novelty on the market!', 20, 60);
INSERT INTO ITEMS(NAME, CATEGORY, DESCRIPTION, QUANTITY, PRICE)
VALUES ('Ankh', 'BOARDGAME', 'Best old Egypt game out there!', 10, 100);
INSERT INTO ITEMS(NAME, CATEGORY, DESCRIPTION, QUANTITY, PRICE)
VALUES ('Dune: Imperium', 'BOARDGAME', 'Jason Momoa ftw!', 10, 50);
INSERT INTO ITEMS(NAME, CATEGORY, DESCRIPTION, QUANTITY, PRICE)
VALUES ('US Army starter pack', 'WARGAMING', 'It is just like the movies!', 5, 50);
INSERT INTO ITEMS(NAME, CATEGORY, DESCRIPTION, QUANTITY, PRICE)
VALUES ('Olive Drab Color', 'DICE', 'Best rolling experience ever!', 200, 14.99);
INSERT INTO ITEMS(NAME, CATEGORY, DESCRIPTION, QUANTITY, PRICE)
VALUES ('Space Marines x2', 'MINI', 'For the EMPEROR', 10, 160);
INSERT INTO ITEMS(NAME, CATEGORY, DESCRIPTION, QUANTITY, PRICE)
VALUES ('Calico', 'BOARDGAME', 'Cute and fuzzy!', 20, 60);
INSERT INTO ITEMS(NAME, CATEGORY, DESCRIPTION, QUANTITY, PRICE)
VALUES ('German Army starter pack', 'WARGAMING', 'Panzer to the rescue!', 10, 100);
INSERT INTO ITEMS(NAME, CATEGORY, DESCRIPTION, QUANTITY, PRICE)
VALUES ('Terra-forming Mars', 'BOARDGAME', 'Eat your heart out Musk!', 10, 50);
INSERT INTO ITEMS(NAME, CATEGORY, DESCRIPTION, QUANTITY, PRICE)
VALUES ('Witcher 3 Ciri dice', 'DICE', 'They glow in the dark!', 50, 14.99);
INSERT INTO ITEMS(NAME, CATEGORY, DESCRIPTION, QUANTITY, PRICE)
VALUES ('Witcher 3 Geralt', 'MINI', 'A beautiful collectible statuette!', 2, 299.99);

