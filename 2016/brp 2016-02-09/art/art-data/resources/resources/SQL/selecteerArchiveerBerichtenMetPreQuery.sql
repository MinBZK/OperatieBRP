select
   b.richting,
   b.SRT,
   b.ADMHND,
   b.ANTWOORDOP,
   b.ZENDENDEPARTIJ,
   b.ZENDENDESYSTEEM,
   b.ONTVANGENDEPARTIJ,
   b.ONTVANGENDESYSTEEM,
   b.REFERENTIENR,
   b.CROSSREFERENTIENR,
   b.TSVERZENDING,
   b.TSONTV,
   b.VERWERKINGSWIJZE,
   b.SRTSYNCHRONISATIE,
   b.ABONNEMENT,
   b.CATEGORIEDIENST,
   b.VERWERKING,
   b.BIJHOUDING,
   b.HOOGSTEMELDINGSNIVEAU
from 
	ber.ber b
where 
    (b.crossreferentienr = '${DataSource Values#referentienummer_slB1}' 
	or b.referentienr = '${DataSource Values#referentienummer_slB1}')
	and b.id > ${Pre Query#result}

order by b.richting asc	