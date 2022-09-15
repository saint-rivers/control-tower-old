CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS task_status
(
    id   serial4 primary key,
    name varchar(11) not null unique
);

INSERT INTO task_status (name)
VALUES ('COMPLETE')
ON CONFLICT DO NOTHING;

INSERT INTO task_status (name)
VALUES ('ON_PROGRESS')
ON CONFLICT DO NOTHING;

INSERT INTO task_status (name)
VALUES ('REQUESTING')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS tasks
(
    id          uuid primary key default uuid_generate_v4(),
    title       text not null,
    description text not null,
    created_by  uuid not null,
    assigned_to uuid not null,
    group_id    uuid not null,
    status      integer references task_status (id),
    created_date  timestamp    not null,
    last_modified timestamp    not null
);