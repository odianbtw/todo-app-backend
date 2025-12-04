
create table if not exists task (
    id uuid primary key,
    name varchar(32) not null,
    description text,
    user_id uuid not null,
    completed boolean not null,
    completed_at timestamp,
    created_at timestamp not null,
    updated_at timestamp not null,
    foreign key (user_id) references user_account(id)
);