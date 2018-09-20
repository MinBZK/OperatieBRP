DROP TABLE mig_fouten;

CREATE TABLE mig_fouten(
   id                    serial,
   tijdstip              timestamp     not null,
   proces                varchar(30)   not null,
   process_instance_id   bigint        not null,
   proces_init_gemeente  varchar(4)    ,
   proces_doel_gemeente  varchar(4)    ,
   code                  varchar(60)   not null,
   melding               text,
   resolutie             varchar(40),
   constraint fout_pk primary key(id)
);

CREATE INDEX on mig_fouten(tijdstip);
CREATE INDEX on mig_fouten(proces);
CREATE INDEX on mig_fouten(code);
