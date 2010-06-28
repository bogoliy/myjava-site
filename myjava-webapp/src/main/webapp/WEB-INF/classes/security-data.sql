insert into User (user_name,user_login, user_password) values ('Alex Bogoley','abogoley','admin');
insert into User (user_name,user_login, user_password) values ('Denis Kuzmenko','dkuzmenko','admin');

insert into Permission_Group (group_name) values ('Users');
insert into Permission_Group (group_name) values ('Administrators');

insert into User_Group (group_id, user_id) values (1,1);
insert into User_Group (group_id, user_id) values (2,2);

insert into Permission (permission) values ('ROLE_USER');
insert into Permission (permission) values ('ROLE_ADMIN');

insert into Group_Permission (group_id, permission_id) values (1,1); 
insert into Group_Permission (group_id, permission_id) values (2,2); 

commit;
