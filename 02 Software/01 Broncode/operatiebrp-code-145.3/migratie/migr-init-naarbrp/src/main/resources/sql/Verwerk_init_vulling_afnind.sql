select *
from initvul.initvullingresult_afnind
where pl_id in ( select pl_id
                 from initvul.initvullingresult_afnind
                 where bericht_resultaat = :conversie_resultaat
                 LIMIT :batch_grootte
               )
