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
          request_non_receipt_notification boolean,
          tijdstip_ontvangst               timestamp with time zone not null,
          tijdstip_mailbox                 timestamp with time zone,
          tijdstip_in_verwerking           timestamp with time zone,
          tijdstip_verzonden               timestamp with time zone,
          dispatch_sequence_number         integer,
          non_delivery_reason              varchar(4),
          notification_type                varchar(1),
          verwerking_code                  varchar(38),
          version                          bigint          not null
);

alter table voisc.bericht add constraint bericht_pk primary key (id);

create index bericht_idx_1 on voisc.bericht (status);
create index bericht_idx_2 on voisc.bericht (originator);
create index bericht_idx_3 on voisc.bericht (dispatch_sequence_number);

create sequence voisc.bericht_id_sequence start with 1 increment by 1;

create table voisc.lo3_mailbox(
          id                       bigint        not null,
          mailboxnr                char(7)       not null,
          verzender                char(7),
          partijcode               char(6)       not null,
          -- blokkering (verzenden naar)
          blokkering_start_dt      timestamp with time zone,
          blokkering_eind_dt       timestamp with time zone,
          -- overnemen mailbox
          mailboxpwd               varchar(8),
          limitNumber              integer       default 171 not null,
          laatste_wijziging_pwd_dt timestamp with time zone,
          laatste_msseqnumber      bigint
);

alter table voisc.lo3_mailbox add constraint lo3_mailbox_pk primary key (id);
alter table voisc.lo3_mailbox add constraint lo3_mailbox_uk1 unique (mailboxnr);

create index lo3_mailbox_idx_1 on voisc.lo3_mailbox (partijcode);

create sequence voisc.lo3_mailbox_id_sequence start with 1 increment by 1;
