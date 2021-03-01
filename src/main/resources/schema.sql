create table surfing_location (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    city varchar(100) not null,
    longitude double not null,
    latitude double not null
);

create table users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username varchar ( 50 ) not null UNIQUE,
    password varchar ( 100 ) not null,
    enabled boolean not null
);
create table authorities (
    username varchar ( 50 ) not null,
    authority varchar ( 50 ) not null,
    constraint fk_authorities_users foreign key (username) references users(username),
    UNIQUE KEY username_authority (username, authority)
);