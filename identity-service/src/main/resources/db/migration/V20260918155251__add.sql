
create table clients (
                         failed_login_attempts integer not null,
                         is_deleted bit not null,
                         is_email_verified bit not null,
                         is_phone_verified bit not null,
                         created_at datetime(6) not null,
                         password_change_at datetime(6),
                         updated_at datetime(6),
                         id varchar(32) not null,
                         client_name varchar(100) not null,
                         email varchar(100),
                         first_name varchar(100),
                         last_name varchar(100),
                         phone_number varchar(100),
                         avatar_url varchar(255),
                         password_hash varchar(255) not null,
                         roles ENUM('admin', 'client') NOT NULL,
                         account_status enum ('ACTIVE','BANNED','PENDING_VERIFICATION','SUSPENDED') not null,
                         primary key (id)
) engine=InnoDB;

create index idx_ckient_status
    on clients (account_status);

alter table clients
    add constraint idx_client_email unique (email);

alter table clients
    add constraint UK759udbd1akn2tuipnp89ubjl6 unique (client_name);

alter table clients
    add constraint UKbt1ji0od8t2mhp0thot6pod8u unique (phone_number);
