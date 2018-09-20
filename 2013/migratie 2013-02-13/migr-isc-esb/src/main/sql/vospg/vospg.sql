create table lo3_bericht(
          lo3_bericht_id                          bigint        not null,
          aanduiding_in_uit                       character(1)  not null,
          bericht_activiteit_id                   bigint	references activiteit (activiteit_id),
          medium                                  character(1)  not null,
          originator_or_recipient                 varchar(7),
          spg_mailbox_instantie                   integer,
          eref                                    varchar(12),
          bref                                    varchar(12),
          eref2                                   varchar(12),
          berichtcyclus_id                        bigint,
          tijdstip_verzending_ontvangst           timestamp,
          dispatch_sequence_number                integer,
          report_delivery_time                    timestamp,
          non_delivery_reason                     varchar(4),
          non_receipt_reason                      varchar(4),
          bericht_data                            text,
          kop_random_key                          integer,
          kop_berichtsoort_nummer                 varchar(4),
          kop_a_nummer                            bigint,
          kop_oud_a_nummer                        bigint,
          kop_herhaling                           character(1),
          kop_foutreden                           character(1),
          kop_datum_tijd                          bigint,
          creatie_dt                              timestamp     not null default now());
alter table lo3_bericht add
    constraint lo3_bericht_pk primary key (lo3_bericht_id);

create        index lo3_bericht_idx_1 on lo3_bericht (eref);
create        index lo3_bericht_idx_2 on lo3_bericht (bref);
create        index lo3_bericht_idx_3 on lo3_bericht (bericht_activiteit_id);
create        index lo3_bericht_idx_4 on lo3_bericht (originator_or_recipient, dispatch_sequence_number);
create        index lo3_bericht_idx_5 on lo3_bericht (berichtcyclus_id);