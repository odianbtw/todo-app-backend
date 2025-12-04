
create type user_role_enum as enum (
    'ADMIN',
    'USER'
);

create table if not exists user_account (
    id uuid primary key,
    username varchar(20) not null unique,
    email varchar(255) not null unique,
    password varchar(255) not null,
    user_role user_role_enum not null,
    created_at timestamp not null,
    updated_at timestamp not null
);

create table if not exists password_reset (
    token varchar(43) primary key,
    user_account_id uuid not null,
    expires_at timestamp not null,
    used boolean not null,
    created_at timestamp not null,
    foreign key (user_account_id) references user_account(id) on delete cascade
);