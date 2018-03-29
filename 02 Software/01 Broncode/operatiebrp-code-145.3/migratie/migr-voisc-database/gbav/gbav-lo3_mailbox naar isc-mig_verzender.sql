select 'insert into mig_verzender(instantiecode, verzendende_instantiecode) values ('||code_instantie||','|| 
case when spg_mailbox_instantie = 1 then 3000200 else 
case when spg_mailbox_instantie = 2 then 3000210 else 
case when spg_mailbox_instantie = 3 then 3000220 else 
case when spg_mailbox_instantie = 4 then 3000230 else 
case when spg_mailbox_instantie = 10 then 3000250 
end
end
end
end
end
||');'
from  lo3_mailbox order by code_instantie;