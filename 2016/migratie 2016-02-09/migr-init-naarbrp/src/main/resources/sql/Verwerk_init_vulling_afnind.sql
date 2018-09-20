select * 
from initvul.initvullingresult_afnind_regel
join initvul.initvullingresult_afnind
on initvul.initvullingresult_afnind.pl_id = initvul.initvullingresult_afnind_regel.pl_id
and initvul.initvullingresult_afnind.conversie_resultaat = 'TE_VERZENDEN'
order by initvul.initvullingresult_afnind.a_nr
