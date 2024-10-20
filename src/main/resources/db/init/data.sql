insert into users (id, username, first_name, last_name, email, password, created_at, updated_at, created_by, updated_by)
    values (1, 'admin', 'System', 'Administrator', 'admin@localhost', 'test', now(), now(), 1, 1);
insert into users (id, username, first_name, last_name, email, password, created_at, updated_at, created_by, updated_by)
    values (2, 'demo_incident_reader', 'Demo', 'Reader', 'demo_incident_reader@localhost', 'test', now(), now(), 1, 1);
insert into users (id, username, first_name, last_name, email, password, created_at, updated_at, created_by, updated_by)
    values (3, 'demo_incident_reporter', 'Demo', 'Reporter', 'demo_incident_reporter@localhost', 'test', now(), now(), 1, 1);
insert into users (id, username, first_name, last_name, email, password, created_at, updated_at, created_by, updated_by)
    values (4, 'demo_incident_reporter', 'Demo', 'Manager', 'demo_incident_manager@localhost', 'test', now(), now(), 1, 1);

insert into roles (id, name, created_at, updated_at, created_by, updated_by)
    values (1, 'SYSTEM_ADMIN', now(), now(), 1, 1);
insert into roles (id, name, created_at, updated_at, created_by, updated_by)
    values (2, 'INCIDENT_MANAGER', now(), now(), 1, 1);
insert into roles (id, name, created_at, updated_at, created_by, updated_by)
    values (3, 'INCIDENT_REPORTER', now(), now(), 1, 1);
insert into roles (id, name, created_at, updated_at, created_by, updated_by)
    values (4, 'INCIDENT_READER', now(), now(), 1, 1);

insert into role_permissions (role_id, permission) values (1, 'READ_USER');
insert into role_permissions (role_id, permission) values (1, 'LIST_USER');
insert into role_permissions (role_id, permission) values (1, 'CREATE_USER');
insert into role_permissions (role_id, permission) values (1, 'UPDATE_USER');
insert into role_permissions (role_id, permission) values (1, 'DELETE_USER');
insert into role_permissions (role_id, permission) values (1, 'READ_ROLE');
insert into role_permissions (role_id, permission) values (1, 'LIST_ROLE');
insert into role_permissions (role_id, permission) values (1, 'UPDATE_ROLE');
insert into role_permissions (role_id, permission) values (1, 'DELETE_ROLE');
insert into role_permissions (role_id, permission) values (1, 'READ_INCIDENT');
insert into role_permissions (role_id, permission) values (1, 'LIST_INCIDENT');
insert into role_permissions (role_id, permission) values (1, 'CREATE_INCIDENT');
insert into role_permissions (role_id, permission) values (1, 'UPDATE_INCIDENT');
insert into role_permissions (role_id, permission) values (1, 'DELETE_INCIDENT');

insert into role_permissions (role_id, permission) values (2, 'READ_INCIDENT');
insert into role_permissions (role_id, permission) values (2, 'LIST_INCIDENT');
insert into role_permissions (role_id, permission) values (2, 'CREATE_INCIDENT');
insert into role_permissions (role_id, permission) values (2, 'UPDATE_INCIDENT');

insert into role_permissions (role_id, permission) values (3, 'READ_INCIDENT');
insert into role_permissions (role_id, permission) values (3, 'LIST_INCIDENT');
insert into role_permissions (role_id, permission) values (3, 'CREATE_INCIDENT');

insert into role_permissions (role_id, permission) values (4, 'READ_INCIDENT');
insert into role_permissions (role_id, permission) values (4, 'LIST_INCIDENT');

insert into user_roles (user_id, role_id) values (1, 1);
insert into user_roles (user_id, role_id) values (2, 2);
insert into user_roles (user_id, role_id) values (3, 3);
insert into user_roles (user_id, role_id) values (4, 4);
