select a_nr, stapel_nr, volg_nr, afnemer_code, geldigheid_start_datum
from   initvul.initvullingresult_afnind_regel rgl
join   initvul.initvullingresult_afnind afn on afn.pl_id = rgl.pl_id
order by a_nr, stapel_nr, volg_nr;
