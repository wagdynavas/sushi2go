  create table orders (ORD_ID int not null primary key auto_increment,
   ORD_STATUS varchar(50) not null,
   ORD_DATE date not null,
   ORD_CUSTOMER_INSTRUCTIONS varchar(500));


  ALTER TABLE orders ADD column ORDER_USER_ID int;

  ALTER TABLE orders ADD CONSTRAINT FK_USER FOREIGN KEY (ORDER_USER_ID) references USER(USER_ID);

  select * from orders;

  insert into orders (ORD_STATUS, ORD_DATE, ORD_CUSTOMER_INSTRUCTIONS, ORDER_USER_ID)
  values(
            'NEW', now(), 'Extra  soy sauce', 4
        );


  select * from orders where ORDER_USER_id = (select USER_ID from user where USER_USERNAME = 'wagdynavas');

  ALTER TABLE orders ADD column ORD_PRODUCT_ID int;

  ALTER TABLE orders ADD CONSTRAINT FK_PRODUCT FOREIGN KEY (ORD_PRODUCT_ID) references PRODUCT(PRODUCT_ID);


  ALTER TABLE ORDERS ADD column RESTAURANT_BRANCH varchar(50);

  ALTER TABLE ORDERS ADD column ORD_DELIVER_ADDRESS varchar(200);

  ALTER TABLE ORDERS ADD column ORD_DELIVER_ADDRESS_COMPLEMENT varchar(50);

  ALTER TABLE ORDERS ADD column ORD_DELIVER_INSTRUCTIONS varchar(200);

  ALTER TABLE ORDERS change RESTAURANT_BRANCH ORD_RESTAURANT_BRANCH varchar(50);

  ALTER TABLE ORDERS DROP CONSTRAINT FK_USER;

  ALTER TABLE ORDERS DROP column ORDER_USER_ID;

  ALTER TABLE ORDERS ADD COLUMN  ORD_SUB_TOTAL_AMOUNT DECIMAL(6,2);
  ALTER TABLE ORDERS ADD column  ORD_TOTAL_AMOUNT DECIMAL(6,2);
  ALTER TABLE ORDERS ADD column  ORD_TIP DECIMAL(6,2);
  ALTER TABLE ORDERS ADD column  ORD_TAX DECIMAL(6,2);
  ALTER TABLE ORDERS ADD COLUMN ORD_CUSTOMER_ID int;
  ALTER TABLE ORDERS ADD FOREIGN KEY (ORD_CUSTOMER_ID)  REFERENCES CUSTOMER(CUSTOMER_ID);

  ALTER TABLE ORDERS DROP FOREIGN KEY FK_PRODUCT;
  ALTER TABLE ORDERS DROP COLUMN ORD_PRODUCT_ID;

  ALTER TABLE ORDERS MODIFY COLUMN ORD_DATE DATETIME NOT NULL;

