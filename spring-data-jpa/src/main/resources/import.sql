insert into book (id, title, authors, status) values (1, 'Code Complete', 'Steve McConnell', 'FREE');
insert into book (id, title, authors, status) values (2, 'Kuchenne rewolucje Nowe przepisy Magdy Gessler', 'Omar Danielle', 'LOAN');
insert into book (id, title, authors, status) values (3, 'Kuchenne rewolucje', 'Gessler Magda', 'LOAN');
insert into book (id, title, authors, status) values (4, 'Python. Wprowadzenie', 'Mark Lutz, David Ascher', 'FREE');
insert into book (id, title, authors, status) values (5, 'Sztuka programowania', 'Donald Knuth', 'FREE');
insert into book (id, title, authors, status) values (6, 'Domofon', 'Miloszewski Zygmunt', 'LOAN');
insert into book (id, title, authors, status) values (7, 'The Pragmatic Bookshelf', 'Andy Hunt, Dave Thomas', 'FREE');
insert into book (id, title, authors, status) values (8, 'Wzorce projektowe', 'Erich Gamma', 'LOAN');
insert into book (id, title, authors, status) values (9, 'Zmierzch', 'Meyer Stephenie', 'FREE');
insert into book (id, title, authors, status) values (10, 'Miasteczko Salem', 'King Stephen', 'LOAN');

insert into userentity (id, user_name, password) values (1, 'tech_admin', 'tech');
insert into userentity (id, user_name, password) values (2, 'admin', 'admin');
insert into userentity (id, user_name, password) values (3, 'user', 'user');

insert into user_roles (id, user_name, role) values (1, 'tech_admin', 'ROLE_TECH_ADMIN');
insert into user_roles (id, user_name, role) values (2, 'admin', 'ROLE_ADMIN');
insert into user_roles (id, user_name, role) values (3, 'user', 'ROLE_USER');