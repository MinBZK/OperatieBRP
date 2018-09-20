
CREATE TABLE message 
( 
  uuid varchar2(128) NOT NULL, 
  type varchar2(128) NOT NULL, 
  message clob NOT NULL, 
  delivered varchar2(10) NOT NULL, 
  classification varchar2(10), 
  PRIMARY KEY (uuid) 
);
