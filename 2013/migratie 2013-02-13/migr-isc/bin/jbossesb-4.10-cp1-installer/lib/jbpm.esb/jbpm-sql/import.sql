insert into JBPM_ID_USER (ID_,CLASS_,NAME_,EMAIL_,PASSWORD_) values (1,'U','user','user@sample.domain','user');
insert into JBPM_ID_USER (ID_,CLASS_,NAME_,EMAIL_,PASSWORD_) values (2,'U','manager','manager@sample.domain','manager');
insert into JBPM_ID_USER (ID_,CLASS_,NAME_,EMAIL_,PASSWORD_) values (3,'U','admin','admin@sample.domain','admin');
insert into JBPM_ID_USER (ID_,CLASS_,NAME_,EMAIL_,PASSWORD_) values( 4,'U','shipper','shipper@sample.domain','shipper');

insert into JBPM_ID_GROUP (ID_,CLASS_,NAME_,TYPE_,PARENT_) values (1,'G','sales','organisation',NULL);
insert into JBPM_ID_GROUP (ID_,CLASS_,NAME_,TYPE_,PARENT_) values (2,'G','admin','security-role',NULL);
insert into JBPM_ID_GROUP (ID_,CLASS_,NAME_,TYPE_,PARENT_) values (3,'G','user','security-role',NULL);
insert into JBPM_ID_GROUP (ID_,CLASS_,NAME_,TYPE_,PARENT_) values (4,'G','hr','organisation',NULL);
insert into JBPM_ID_GROUP (ID_,CLASS_,NAME_,TYPE_,PARENT_) values (5,'G','manager','security-role',NULL);

insert into JBPM_ID_MEMBERSHIP (ID_,CLASS_,NAME_,ROLE_,USER_,GROUP_) values (1,'M',NULL,NULL,2,4);
insert into JBPM_ID_MEMBERSHIP (ID_,CLASS_,NAME_,ROLE_,USER_,GROUP_) values (2,'M',NULL,NULL,3,4);
insert into JBPM_ID_MEMBERSHIP (ID_,CLASS_,NAME_,ROLE_,USER_,GROUP_) values (3,'M',NULL,NULL,4,4);
insert into JBPM_ID_MEMBERSHIP (ID_,CLASS_,NAME_,ROLE_,USER_,GROUP_) values (4,'M',NULL,NULL,4,3);
insert into JBPM_ID_MEMBERSHIP (ID_,CLASS_,NAME_,ROLE_,USER_,GROUP_) values (5,'M',NULL,NULL,1,3);
insert into JBPM_ID_MEMBERSHIP (ID_,CLASS_,NAME_,ROLE_,USER_,GROUP_) values (6,'M',NULL,NULL,2,3);
insert into JBPM_ID_MEMBERSHIP (ID_,CLASS_,NAME_,ROLE_,USER_,GROUP_) values (7,'M',NULL,NULL,3,3);
insert into JBPM_ID_MEMBERSHIP (ID_,CLASS_,NAME_,ROLE_,USER_,GROUP_) values (8,'M',NULL,NULL,3,2);
insert into JBPM_ID_MEMBERSHIP (ID_,CLASS_,NAME_,ROLE_,USER_,GROUP_) values (9,'M',NULL,NULL,2,2);
insert into JBPM_ID_MEMBERSHIP (ID_,CLASS_,NAME_,ROLE_,USER_,GROUP_) values (10,'M',NULL,NULL,2,5);
insert into JBPM_ID_MEMBERSHIP (ID_,CLASS_,NAME_,ROLE_,USER_,GROUP_) values (11,'M',NULL,NULL,2,1);
insert into JBPM_ID_MEMBERSHIP (ID_,CLASS_,NAME_,ROLE_,USER_,GROUP_) values (12,'M',NULL,NULL,1,1);
