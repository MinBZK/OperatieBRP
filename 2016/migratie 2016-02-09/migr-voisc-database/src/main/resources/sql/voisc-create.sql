create schema voisc;

-- Hieronder worden alle tabellen aangemaakt:
create table voisc.bericht(
          id                               bigint          not null,
          originator                       varchar(7),
          recipient                        varchar(7),
          status                           varchar(30)     not null,
          bericht_data                     varchar(19000),
          message_id                       varchar(12),
          correlation_id                   varchar(12),
          tijdstip_ontvangst               timestamp       not null,
          tijdstip_mailbox                 timestamp,
          tijdstip_in_verwerking           timestamp,
          tijdstip_verzonden               timestamp,
          dispatch_sequence_number         integer,
          non_delivery_reason              varchar(4),
          notification_type                varchar(1),
          version                          bigint          not null
);

alter table voisc.bericht add constraint bericht_pk primary key (id);

create index bericht_idx_1 on voisc.bericht (status);
create index bericht_idx_2 on voisc.bericht (originator);
create index bericht_idx_3 on voisc.bericht (dispatch_sequence_number);

create sequence voisc.bericht_id_sequence start with 1 increment by 1;

create table voisc.lo3_mailbox(
          id                       bigint        not null,
          instantietype            char(1)       not null,
          instantiecode            integer       not null,
          mailboxnr                varchar(7)    not null,
          mailboxpwd               varchar(8),
          limitNumber              integer       default 171 not null,
          blokkering_start_dt      timestamp,
          blokkering_eind_dt       timestamp,
          laatste_wijziging_pwd_dt timestamp,
          laatste_msseqnumber      bigint
);

alter table voisc.lo3_mailbox add constraint lo3_mailbox_pk primary key (id);
alter table voisc.lo3_mailbox add constraint lo3_mailbox_uk1 unique (mailboxnr);

create index lo3_mailbox_idx_1 on voisc.lo3_mailbox (instantiecode);

create sequence voisc.lo3_mailbox_id_sequence start with 1 increment by 1;

--alter table voisc.lo3_mailbox add constraint instantietype_check check(instantietype in ( 'A', 'G', 'C'));
--alter table voisc.lo3_mailbox add constraint pwd_check check(mailboxpwd is not null = laatste_wijziging_pwd_dt is not null);
