\pset tuples_only on
select 'truncate mig_verzender;';
select 'insert into mig_verzender(instantiecode, verzendende_instantiecode) values('||lo3_mailbox.code_instantie||','||spg_mailbox.spg_mailbox_nummer||');'
from lo3_mailbox join spg_mailbox on spg_mailbox.spg_mailbox_instantie = lo3_mailbox.spg_mailbox_instantie;
