drop schema if exists voisc;
create schema voisc;

drop sequence if exists voisc.bericht_id_sequence;
drop table if exists voisc.bericht;

drop sequence if exists voisc.logboek_id_sequence;
drop table if exists voisc.logboek;

drop sequence if exists voisc.lo3_mailbox_id_sequence;
drop table if exists voisc.lo3_mailbox;

drop table if exists voisc.properties;

-- Hieronder worden alle tabellen aangemaakt:
create table voisc.bericht(
          id                               bigint          not null,
          aanduiding_in_uit                character(1)    not null,
          originator                       varchar(7)      not null,
          recipient                        varchar(7)      not null,
          status                           varchar(16)     not null,
          eref                             varchar(12),
          bref                             varchar(12),
          eref2                            varchar(12),
          esbCorrelationId                 varchar(36),
          esbMessageId                     varchar(36),
          tijdstip_verzending_ontvangst    timestamp,
          dispatch_sequence_number         integer,
          report_delivery_time             timestamp,
          non_delivery_reason              varchar(4),
          non_receipt_reason               varchar(4),
          bericht_data                     varchar(19000)  not null,
          creatie_dt                       timestamp       not null default now()
);
alter table voisc.bericht add constraint bericht_pk primary key (id);

create index bericht_idx_1 on voisc.bericht (eref);
create index bericht_idx_2 on voisc.bericht (bref);
create index bericht_idx_4 on voisc.bericht (originator,recipient, dispatch_sequence_number);
create sequence voisc.bericht_id_sequence start with 1 increment by 1;

create table voisc.lo3_mailbox(
          id                       bigint        not null,
          gemeentecode             varchar(4)    not null,
          mailboxnr                varchar(7)    not null,
          mailboxpwd               varchar(8),
          limitNumber              integer       not null,
          blokkering_start_dt      timestamp,
          blokkering_eind_dt       timestamp,
          laatste_wijziging_pwd_dt timestamp     not null
);
alter table voisc.lo3_mailbox add constraint lo3_mailbox_pk primary key (id);
create index lo3_mailbox_idx_1 on voisc.lo3_mailbox (gemeentecode);
create sequence voisc.lo3_mailbox_id_sequence start with 1 increment by 1;

create table voisc.logboek(
        id                  bigint    not null,
        mailbox_id          bigint    not null references voisc.lo3_mailbox(id),
        start_dt            timestamp not null,
        eind_dt             timestamp not null,
        aantalVerzonden     integer   not null,
        aantalOntvangen     integer   not null,
        aantalVerzondenOK   integer   not null,
        aantalVerzondenNOK  integer   not null,
        aantalOntvangenOK   integer   not null,
        aantalOntvangenNOK  integer   not null,
        foutmelding         text
);
alter table voisc.logboek add constraint logboek_pk primary key (id);
create index logboek_idx_1 on voisc.logboek (mailbox_id);
create sequence voisc.logboek_id_sequence start with 1 increment by 1;

create table voisc.properties(
          propertyKey                 varchar(255) not null,
          propertyValue               varchar(255)
);
alter table voisc.properties add constraint properties_pk primary key (propertyKey);
create index properties_idx_1 on voisc.properties (propertyKey);
