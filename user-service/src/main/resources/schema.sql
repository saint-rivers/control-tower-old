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
    group_id   uuid references groups (id) on delete cascade,
    user_id    bigint references app_users (id) on delete cascade,
    date_added timestamp not null default now(),
    primary key (group_id, user_id)
);