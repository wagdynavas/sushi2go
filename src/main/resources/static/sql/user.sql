create table USER (
   USER_ID int not null primary key auto_increment,
   USER_USERNAME varchar(50) not null,
   USER_PASSWORD varchar(200) not null,
   USER_ROLE varchar(50) not null,
   USER_EMAIL varchar(200) not null,
   USER_CREATION_DATE date,
   USER_RESTAURANT_BRANCH_PROFILE varchar(50) not null
);