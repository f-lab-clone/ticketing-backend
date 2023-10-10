USE ticketingdb;

SET foreign_key_checks = 0;

TRUNCATE bookmark; 
TRUNCATE reservation;

DELETE FROM user WHERE id > 1000000;
ALTER TABLE user AUTO_INCREMENT=1000001;

DELETE FROM event WHERE id > 10000000;
ALTER TABLE event AUTO_INCREMENT=10000001;

-- UPDATE event SET current_reservation_count = 0;

SET foreign_key_checks = 1;