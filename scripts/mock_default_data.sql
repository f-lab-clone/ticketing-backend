INSERT INTO HALL (id, name) VALUES (1, 'Main Hall');

INSERT INTO EVENT (id, hall_id, name, event_start_date, event_end_date) VALUES (1, 1, 'Concert A', '2023-10-01', '2023-10-10');

INSERT INTO STAGE (id, hall_id, name) VALUES (1, 1, 'Stage 1');

INSERT INTO STAGE_SEAT (id, `row`, col, `limit`, stage_id) VALUES (1, 1, 1, 2, 1);
INSERT INTO STAGE_SEAT (id, `row`, col, `limit`, stage_id) VALUES (2, 1, 2, 2, 1);

INSERT INTO SEAT_CLASS (id, name, price, event_id) VALUES (1, 'VIP', 100000, 1);
INSERT INTO SEAT_CLASS (id, name, price, event_id) VALUES (2, 'Regular', 50000, 1);

INSERT INTO USER (id) VALUES (1);

INSERT INTO EVENT_TIME_TABLE (id, event_id, stage_id, start_datetime, end_datetime) VALUES (1, 1, 1, '2023-10-01 19:00:00', '2023-10-01 21:00:00');

INSERT INTO RESERVATION (id, user_id, event_time_id) VALUES (1, 1, 1);

-- VIP 좌석 예매 정보 추가
INSERT INTO AVAILABLE_SEAT (id, seat_class_id, stage_seat_id, stage_id) VALUES (1, 1, 1, 1);
INSERT INTO SEAT_RESERVATION_MAP (id, available_seat_id, reservation_id, stage_id) VALUES (1, 1, 1, 1);

-- Regular 좌석 예매 정보 추가
INSERT INTO AVAILABLE_SEAT (id, seat_class_id, stage_seat_id, stage_id) VALUES (2, 2, 2, 1);
INSERT INTO SEAT_RESERVATION_MAP (id, available_seat_id, reservation_id, stage_id) VALUES (2, 2, 1, 1);