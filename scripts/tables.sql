CREATE TABLE `EVENT` (
                         `id`	int	NOT NULL,
                         `hall_id`	int	NOT NULL,
                         `name`	varchar(255)	NOT NULL,
                         `event_start_date`	date	NOT NULL,
                         `event_end_date`	date	NOT NULL
);

CREATE TABLE `STAGE` (
                         `id`	int	NOT NULL,
                         `hall_id`	int	NOT NULL,
                         `name`	varchar(255)	NOT NULL
);

CREATE TABLE `STAGE_SEAT` (
                              `id`	int	NOT NULL,
                              `row`	int	NULL,
                              `col`	int	NULL,
                              `limit`	int	NOT NULL,
                              `stage_id`	int	NOT NULL
);

CREATE TABLE `SEAT_CLASS` (
                              `id`	int	NOT NULL,
                              `name`	varchar(10)	NOT NULL,
                              `price`	int	NOT NULL,
                              `event_id`	int	NOT NULL
);

CREATE TABLE `RESERVATION` (
                               `id`	int	NOT NULL,
                               `user_id`	int	NOT NULL,
                               `event_time_id`	int	NOT NULL
);

CREATE TABLE `USER` (
    `id`	int	NOT NULL
);

CREATE TABLE `EVENT_TIME_TABLE` (
                                    `id`	int	NOT NULL,
                                    `event_id`	int	NOT NULL,
                                    `stage_id`	int	NOT NULL,
                                    `start_datetime`	datetime	NOT NULL,
                                    `end_datetime`	datetime	NOT NULL
);

CREATE TABLE `SEAT_TAG` (
                            `id`	int	NOT NULL,
                            `hall_id`	int	NOT NULL,
                            `name`	varchar(20)	NOT NULL
);

CREATE TABLE `SEAT_TAG_MAP` (
                                `id`	int	NOT NULL,
                                `stage_seat_id`	int	NOT NULL,
                                `tag_id`	int	NOT NULL,
                                `stage_id`	int	NOT NULL
);

CREATE TABLE `AVAILABLE_SEAT` (
                                  `id`	int	NOT NULL,
                                  `seat_class_id`	int	NOT NULL,
                                  `stage_seat_id`	int	NOT NULL,
                                  `stage_id`	int	NOT NULL
);

CREATE TABLE `SEAT_RESERVATION_MAP` (
                                        `id`	int	NOT NULL,
                                        `available_seat_id`	int	NOT NULL,
                                        `reservation_id`	int	NOT NULL,
                                        `stage_id`	int	NOT NULL
);

CREATE TABLE `HALL` (
                        `id`	int	NOT NULL,
                        `name`	varchar(255)	NOT NULL
);

CREATE TABLE `admin` (
                         `id`	INT	NOT NULL,
                         `hall_id`	int	NOT NULL
);

ALTER TABLE `EVENT` ADD CONSTRAINT `PK_EVENT` PRIMARY KEY (
                                                           `id`
    );

ALTER TABLE `STAGE` ADD CONSTRAINT `PK_STAGE` PRIMARY KEY (
                                                           `id`
    );

ALTER TABLE `STAGE_SEAT` ADD CONSTRAINT `PK_STAGE_SEAT` PRIMARY KEY (
                                                                     `id`
    );

ALTER TABLE `SEAT_CLASS` ADD CONSTRAINT `PK_SEAT_CLASS` PRIMARY KEY (
                                                                     `id`
    );

ALTER TABLE `RESERVATION` ADD CONSTRAINT `PK_RESERVATION` PRIMARY KEY (
                                                                       `id`
    );

ALTER TABLE `USER` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
                                                         `id`
    );

ALTER TABLE `EVENT_TIME_TABLE` ADD CONSTRAINT `PK_EVENT_TIME_TABLE` PRIMARY KEY (
                                                                                 `id`
    );

ALTER TABLE `SEAT_TAG` ADD CONSTRAINT `PK_SEAT_TAG` PRIMARY KEY (
                                                                 `id`
    );

ALTER TABLE `SEAT_TAG_MAP` ADD CONSTRAINT `PK_SEAT_TAG_MAP` PRIMARY KEY (
                                                                         `id`
    );

ALTER TABLE `AVAILABLE_SEAT` ADD CONSTRAINT `PK_AVAILABLE_SEAT` PRIMARY KEY (
                                                                             `id`
    );

ALTER TABLE `SEAT_RESERVATION_MAP` ADD CONSTRAINT `PK_SEAT_RESERVATION_MAP` PRIMARY KEY (
                                                                                         `id`
    );

ALTER TABLE `HALL` ADD CONSTRAINT `PK_HALL` PRIMARY KEY (
                                                         `id`
    );

ALTER TABLE `admin` ADD CONSTRAINT `PK_ADMIN` PRIMARY KEY (
                                                           `id`
    );
