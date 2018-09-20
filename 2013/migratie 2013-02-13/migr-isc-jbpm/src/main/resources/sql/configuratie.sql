DROP TABLE mig_configuratie;

CREATE TABLE mig_configuratie(
   configuratie varchar(20) not null,
   waarde varchar(20),

   constraint cfg_pk primary key(configuratie)
);


insert into mig_configuratie(configuratie, waarde) values ('brp.timeout', '4 hours');
insert into mig_configuratie(configuratie, waarde) values ('brp.herhalingen', '2');
insert into mig_configuratie(configuratie, waarde) values ('vospg.timeout', '8 hours');
insert into mig_configuratie(configuratie, waarde) values ('vospg.herhalingen', '5');
insert into mig_configuratie(configuratie, waarde) values ('sync.timeout', '12 hours');
insert into mig_configuratie(configuratie, waarde) values ('sync.herhalingen', '3');

commit;
