create table PRODUCT (
	PRODUCT_ID int not null primary key auto_increment,
    PRODUCT_NAME varchar(250) not null,
    PRODUCT_DESCRIPTION varchar(1000) not null,
    PRODUCT_PRICE decimal(8,2) not null,
    PRODUCT_IMG_PATH varchar(2000),
    PRODUCT_CATEGORY varchar(100) not null
);