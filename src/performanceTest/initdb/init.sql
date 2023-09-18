-- SELECT 'START init.sql';

-- SELECT 'SLEEP', SLEEP(5);

-- SELECT * FROM event;


USE ticketingdb;

SET foreign_key_checks = 0;

TRUNCATE user; 
TRUNCATE event; 
TRUNCATE bookmark; 
TRUNCATE reservation;

SET foreign_key_checks = 1;

INSERT INTO event (title, date, reservation_start_time, reservation_end_time, max_attendees, current_reservation_count) VALUES ('Concert', NOW(), NOW(), NOW(), 10, 0);
INSERT INTO event (title, date, reservation_start_time, reservation_end_time, max_attendees, current_reservation_count) VALUES ('Concert', NOW(), NOW(), NOW(), 10, 0);