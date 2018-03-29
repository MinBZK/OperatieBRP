delete from lo3_mailbox;
insert into lo3_mailbox (
    lo3_mailbox_nummer,
    spg_mailbox_instantie,
    soort_instantie,
    code_instantie,
    indicatie_mailbox_actief,
    creatie_dt) 
values (
    '0626',
    5,
    'G',
    '626',
    '1',
    now()
);
