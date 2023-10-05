USE ticketingdb;

INSERT INTO event (title, date, reservation_start_time, reservation_end_time, max_attendees, current_reservation_count) VALUES ('CONCERT1000', NOW(), NOW(), NOW(), 1000, 0);
INSERT INTO event (title, date, reservation_start_time, reservation_end_time, max_attendees, current_reservation_count) VALUES ('CONCERT10000', NOW(), NOW(), NOW(), 10000, 0);
INSERT INTO event (title, date, reservation_start_time, reservation_end_time, max_attendees, current_reservation_count) VALUES ('CONCERT100000', NOW(), NOW(), NOW(), 100000, 0);