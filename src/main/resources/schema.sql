create table surfing_location (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    city varchar(100) not null,
    longitude double not null,
    latitude double not null
);