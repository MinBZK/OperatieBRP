\pset tuples_only on
SELECT 'truncate voisc.lo3_mailbox cascade;';
SELECT 'insert into voisc.lo3_mailbox(id, instantietype, instantiecode, mailboxnr) '
|| 'values(nextval(''voisc.lo3_mailbox_id_sequence''), '''||soort_instantie||''', '||code_instantie||', '''||to_char(cast(lo3_mailbox_nummer as integer), 'FM0000000')||''');'
FROM lo3_mailbox
ORDER BY cast(lo3_mailbox_nummer as integer);
SELECT 'update voisc.lo3_mailbox set blokkering_start_dt=to_timestamp('''||to_char(blokkade_start_dt, 'YYYY-MM-DD HH24:MI:SS.US') ||''', ''YYYY-MM-DD HH24:MI:SS.US'') where instantiecode='''||code_instantie||''';'
FROM lo3_mailbox WHERE blokkade_start_dt IS NOT NULL
ORDER BY cast(lo3_mailbox_nummer as integer);
SELECT 'update voisc.lo3_mailbox set blokkering_eind_dt=to_timestamp('''||to_char(blokkade_eind_dt, 'YYYY-MM-DD HH24:MI:SS.US') ||''', ''YYYY-MM-DD HH24:MI:SS.US'') where instantiecode='''||code_instantie||''';'
FROM lo3_mailbox WHERE blokkade_eind_dt IS NOT NULL
ORDER BY cast(lo3_mailbox_nummer as integer);
SELECT 'insert into voisc.lo3_mailbox(id, instantietype, instantiecode, mailboxnr) '
|| 'values(nextval(''voisc.lo3_mailbox_id_sequence''), ''C'', '||spg_mailbox_nummer||', '''||to_char(cast(spg_mailbox_nummer as integer), 'FM0000000')||''');'
FROM spg_mailbox
ORDER BY spg_mailbox_nummer;
