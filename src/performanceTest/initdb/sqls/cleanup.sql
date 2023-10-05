USE ticketingdb;

SET foreign_key_checks = 0;

TRUNCATE bookmark; 
TRUNCATE reservation;
DELETE FROM event WHERE id > 10000000;
DELETE FROM user WHERE id > 1000000;
-- UPDATE event SET current_reservation_count = 0;

SET foreign_key_checks = 1;