insert into AUTHOR (id, `name`) values (1, 'Author1');
insert into AUTHOR (id, `name`) values (2, 'Author2');
insert into AUTHOR (id, `name`) values (3, 'Author3');

insert into GENRE (id, `name`) values (1, 'Genre1');
insert into GENRE (id, `name`) values (2, 'Genre2');
insert into GENRE (id, `name`) values (3, 'Genre3');

insert into BOOK (id, `title`) values (1, 'Book1');
insert into BOOK (id, `title`) values (2, 'Book2');
insert into BOOK (id, `title`) values (3, 'Book3');
insert into BOOK (id, `title`) values (4, 'Book4');

INSERT INTO BOOK_AUTHOR_REL (BOOK_ID, AUTHOR_ID) VALUES (1,1);
INSERT INTO BOOK_AUTHOR_REL (BOOK_ID, AUTHOR_ID) VALUES (2,2);
INSERT INTO BOOK_AUTHOR_REL (BOOK_ID, AUTHOR_ID) VALUES (3,3);
INSERT INTO BOOK_AUTHOR_REL (BOOK_ID, AUTHOR_ID) VALUES (3,1);

INSERT INTO BOOK_GENRE_REL (BOOK_ID, GENRE_ID) VALUES (1,1);
INSERT INTO BOOK_GENRE_REL (BOOK_ID, GENRE_ID) VALUES (2,2);
INSERT INTO BOOK_GENRE_REL (BOOK_ID, GENRE_ID) VALUES (3,3);
INSERT INTO BOOK_GENRE_REL (BOOK_ID, GENRE_ID) VALUES (3,1);