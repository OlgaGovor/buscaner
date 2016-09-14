INSERT INTO country (country_id, country_name) VALUES (1, 'Poland');

INSERT INTO city (city_id, city_name, country_id) VALUES (3, 'Krakow', 1);
INSERT INTO city (city_id, city_name, country_id) VALUES (231, 'Warszawa', 1);

INSERT INTO company (company_id, company_name, company_url) VALUES (1, 'PolskiBus', 'polskiBus.pl');
INSERT INTO company (company_id, company_name, company_url) VALUES (2, 'Luxexpress', 'luxexpress.pl');


INSERT INTO destination (destination_id, company_id, city_id, request_value, destination_name) VALUES (49, 1, 3, 'krakow', 'Krakow');
INSERT INTO destination (destination_id, company_id, city_id, request_value, destination_name) VALUES (84, 1, 231, '22002', 'Warszawa');

INSERT INTO route (route_id, from_destination_id, to_destination_id, company_id, is_active, from_city_id, to_city_id, has_changes)
VALUES (6, 49, 84, 1, 1, 3, 231, 0);




/*INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-08-29', '01:35:00', 38.00, '2016-08-17 14:36:23', '06:30:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-08-29', '05:00:00', 25.00, '2016-08-17 14:36:23', '10:00:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-08-29', '07:00:00', 22.00, '2016-08-17 14:36:23', '11:55:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-08-29', '08:30:00', 20.00, '2016-08-17 14:36:23', '13:35:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-08-29', '10:25:00', 25.00, '2016-08-17 14:36:23', '15:20:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-08-29', '12:20:00', 38.00, '2016-08-17 14:36:23', '17:15:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-08-29', '14:10:00', 25.00, '2016-08-17 14:36:23', '19:05:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-08-29', '16:00:00', 33.00, '2016-08-17 14:36:23', '20:55:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-08-29', '17:15:00', 25.00, '2016-08-17 14:36:23', '22:10:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-08-29', '18:10:00', 30.00, '2016-08-17 14:36:23', '23:05:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-08-29', '19:00:00', 20.00, '2016-08-17 14:36:23', '23:55:00', 'zl');

INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-01', '01:35:00', 22.00, '2016-08-17 14:12:16', '06:30:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-01', '05:00:00', 15.00, '2016-08-17 14:12:16', '10:00:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-01', '07:00:00', 17.00, '2016-08-17 14:12:16', '11:55:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-01', '08:30:00', 14.00, '2016-08-17 14:12:16', '13:35:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-01', '10:25:00', 14.00, '2016-08-17 14:12:16', '15:20:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-01', '12:20:00', 10.00, '2016-08-17 14:12:16', '17:15:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-01', '14:10:00', 10.00, '2016-08-17 14:12:16', '19:05:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-01', '16:00:00', 14.00, '2016-08-17 14:12:16', '20:55:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-01', '17:15:00', 15.00, '2016-08-17 14:12:16', '22:10:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-01', '18:10:00', 10.00, '2016-08-17 14:12:16', '23:05:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-01', '19:00:00', 10.00, '2016-08-17 14:12:16', '23:55:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-02', '01:35:00', 10.00, '2016-08-17 14:12:21', '06:30:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-02', '05:00:00', 15.00, '2016-08-17 14:12:21', '10:00:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-02', '07:00:00', 14.00, '2016-08-17 14:12:21', '11:55:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-02', '08:30:00', 14.00, '2016-08-17 14:12:21', '13:35:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-02', '10:25:00', 10.00, '2016-08-17 14:12:21', '15:20:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-02', '12:20:00', 17.00, '2016-08-17 14:12:21', '17:15:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-02', '14:10:00', 14.00, '2016-08-17 14:12:21', '19:05:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-02', '16:00:00', 17.00, '2016-08-17 14:12:21', '20:55:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-02', '17:15:00', 15.00, '2016-08-17 14:12:21', '22:10:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-02', '18:10:00', 17.00, '2016-08-17 14:12:21', '23:05:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-02', '19:00:00', 14.00, '2016-08-17 14:12:21', '23:55:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-03', '01:35:00', 14.00, '2016-08-17 14:12:26', '06:30:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-03', '05:00:00', 5.00, '2016-08-17 14:12:26', '10:00:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-03', '07:00:00', 14.00, '2016-08-17 14:12:26', '11:55:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-03', '08:30:00', 10.00, '2016-08-17 14:12:26', '13:35:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-03', '10:25:00', 10.00, '2016-08-17 14:12:26', '15:20:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-03', '12:20:00', 10.00, '2016-08-17 14:12:26', '17:15:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-03', '14:10:00', 5.00, '2016-08-17 14:12:26', '19:05:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-03', '16:00:00', 10.00, '2016-08-17 14:12:26', '20:55:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-03', '17:15:00', 15.00, '2016-08-17 14:12:26', '22:10:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-03', '18:10:00', 10.00, '2016-08-17 14:12:26', '23:05:00', 'zl');
INSERT INTO price (route_id, departure_date, departure_time, price, last_update, arrival_time, currency) VALUES (6, '2016-10-03', '19:00:00', 10.00, '2016-08-17 14:12:26', '23:55:00', 'zl');*/

