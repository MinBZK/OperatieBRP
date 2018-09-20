DROP TABLE mig_correlatie;

CREATE TABLE mig_correlatie(
   message_id            varchar(100) not null,
   process_instance_id   int8 not null,
   token_id              int8 not null,
   node_id               int8 not null,
   counter_name          varchar(100) not null,
   counter_value         int4 not null,
   bron_gemeente         varchar(4),
   doel_gemeente         varchar(4),
   
   constraint cor_pk primary key(message_id)
);

