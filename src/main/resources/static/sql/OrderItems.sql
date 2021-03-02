create table orderItems (

    ORD_ITEM_ID int not null primary key auto_increment,
    ORD_ITEM_ORDER_NUMBER int not null,
    ORD_ITEM_QUANTITY int not null ,
    ORD_ITEM_NAME varchar(80) not null ,
    ORD_ITEM_SPECIAL_INSTRUCTIONS varchar(500),
    FOREIGN KEY (ORD_ITEM_ORDER_NUMBER) REFERENCES ORDERS(ORD_ID)
);
