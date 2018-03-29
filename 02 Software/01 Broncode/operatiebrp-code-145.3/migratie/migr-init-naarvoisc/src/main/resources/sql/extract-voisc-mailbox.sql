\pset tuples_only on
SELECT 'truncate voisc.lo3_mailbox cascade;';

-- Centrale mailboxen
SELECT 'insert into voisc.lo3_mailbox(id, mailboxnr, partijcode)';
SELECT CASE WHEN mailbox.row_number = 1 THEN 'values' ELSE ',     ' END
    || '(nextval(''voisc.lo3_mailbox_id_sequence''), '
    || '''' || mailbox.mailbox_nummer || ''', '
    || '''199902'')'
FROM   ( SELECT row_number() over () as row_number,
         to_char(cast(spg_mailbox_nummer as integer), 'FM0000000') as mailbox_nummer
         FROM   spg_mailbox
         ORDER BY
                row_number
       ) as mailbox;
SELECT ';';

-- Afnemer/gemeente mailboxen
SELECT 'insert into voisc.lo3_mailbox(id, mailboxnr, verzender, partijcode, blokkering_start_dt, blokkering_eind_dt) ';
SELECT CASE WHEN mailbox.row_number = 1 THEN 'values' ELSE ',     ' END
    || '(nextval(''voisc.lo3_mailbox_id_sequence''), '
    || '''' || mailbox || ''', '
    || '''' || verzender || ''', '
    || '''' || partijcode || ''', '
    || blokkade_start_dt || ', '
    || blokkade_eind_dt || ')'
FROM ( SELECT mailbox.row_number
       , to_char(mailbox.lo3_mailbox_nummer, 'FM0000000') as mailbox
       ,      coalesce( to_char(afnemer.code_instantie, 'FM000000')
                      , to_char(gemeente.code_instantie, 'FM0000')||'01'
                      ) as partijcode
       ,      to_char(cast(spg_mailbox.spg_mailbox_nummer as integer), 'FM0000000') as verzender
       ,      coalesce( 'to_timestamp('''
                     || to_char(coalesce( afnemer.blokkade_start_dt
                                        , gemeente.blokkade_start_dt
                                        )
                               , 'YYYY-MM-DD HH24:MI:SS'
                               )
                     || ''', ''YYYY-MM-DD HH24:MI:SS'')'
                      , 'null') as blokkade_start_dt
       ,      coalesce( 'to_timestamp('''
                     || to_char(coalesce( afnemer.blokkade_eind_dt
                                        , gemeente.blokkade_eind_dt
                                        )
                               , 'YYYY-MM-DD HH24:MI:SS'
                               )
                     || ''', ''YYYY-MM-DD HH24:MI:SS'')'
                      , 'null') as blokkade_eind_dt
       FROM   ( SELECT row_number() over () as row_number
                ,      lo3_mailbox_nummer
                FROM   ( SELECT DISTINCT cast(lo3_mailbox_nummer as integer) as lo3_mailbox_nummer
                         FROM   lo3_mailbox
                       ) as mailbox
                ORDER BY
                       mailbox.lo3_mailbox_nummer ASC
              ) AS mailbox
       LEFT OUTER JOIN
              ( SELECT *
                FROM   lo3_mailbox
                WHERE  soort_instantie = 'G'
              ) as gemeente
       ON     cast(gemeente.lo3_mailbox_nummer as integer) = mailbox.lo3_mailbox_nummer
       LEFT OUTER JOIN
              ( SELECT *
                FROM   lo3_mailbox
                WHERE  soort_instantie = 'A'
              ) as afnemer
       ON     cast(afnemer.lo3_mailbox_nummer as integer) = mailbox.lo3_mailbox_nummer
       LEFT OUTER JOIN
              spg_mailbox
       ON     spg_mailbox.spg_mailbox_instantie = coalesce(gemeente.spg_mailbox_instantie, afnemer.spg_mailbox_instantie)

     ) as mailbox
ORDER BY
       row_number;
SELECT ';';
