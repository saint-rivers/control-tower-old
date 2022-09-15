CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS app_users
(
    id            serial4 primary key,
    auth_id       uuid         not null,
    email         varchar(255) not null unique,
    username      varchar(255) not null unique,
    first_name    varchar(255) not null,
    last_name     varchar(255) not null,
    profile_image text         not null,
    created_date  timestamp    not null,
    last_modified timestamp    not null
);

CREATE TABLE IF NOT EXISTS groups
(
    id           uuid primary key default uuid_generate_v4(),
    group_name   varchar(255) not null,
    group_image  text         not null,
    created_date timestamp    not null
);

CREATE TABLE IF NOT EXISTS group_members
(
    group_id   uuid,
    user_id    integer,
    added_by   integer,
    date_added timestamp not null default now(),

    foreign key (group_id) references groups (id) on delete cascade,
    foreign key (user_id) references app_users (id) on delete cascade,
    foreign key (added_by) references app_users (id) on delete cascade,
    primary key (group_id, user_id)
);