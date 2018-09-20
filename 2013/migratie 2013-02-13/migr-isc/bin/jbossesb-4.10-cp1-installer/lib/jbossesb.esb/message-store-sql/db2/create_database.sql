CREATE TABLE message 
( 
  uuid varchar(128) NOT NULL, 
  type varchar(128) NOT NULL, 
  message clob NOT NULL, 
  delivered varchar(10) NOT NULL, 
  classification varchar(10), 
  PRIMARY KEY(uuid) 
);
