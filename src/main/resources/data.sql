insert into surfing_location (id, city, longitude, latitude) values (1, 'Le Gros-Morne', -61.0, 14.7);
insert into surfing_location (id, city, longitude, latitude) values (2, 'Pissouri', 32.70132, 34.66942);
insert into surfing_location (id, city, longitude, latitude) values (3, 'Fortaleza', -38.54306, -3.71722);
insert into surfing_location (id, city, longitude, latitude) values (4, 'Bridgetown', -84.63717, 39.15311);
insert into surfing_location (id, city, longitude, latitude) values (5, 'Jastarnia', 18.67873, 54.69606);

insert into users (id, username, password, enabled)
values (1, 'test', '{bcrypt}$2a$10$upzXFsFUOClFRR69OMKF8eajGMRs0vhcSHqvNDKy9yfW45w7o9z6O', true);
insert into authorities (username, authority) values ('test','USER');