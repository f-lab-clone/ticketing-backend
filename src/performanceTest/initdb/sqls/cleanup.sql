USE ticketingdb;

SET foreign_key_checks = 0;

TRUNCATE user; 
TRUNCATE event; 
TRUNCATE bookmark; 
TRUNCATE reservation;

SET foreign_key_checks = 1;