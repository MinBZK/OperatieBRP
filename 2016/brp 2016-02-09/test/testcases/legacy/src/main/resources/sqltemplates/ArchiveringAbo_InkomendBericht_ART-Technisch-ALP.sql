select distinct
   b.id,
   b.srt,
   b.richting,
   b.admhnd,
   b.antwoordop,
   b.zendendepartij,
   b.zendendesysteem,
   b.ontvangendepartij,
   b.ontvangendesysteem,
   b.referentienr,
   b.crossreferentienr,
   b.tsverzending,
   b.tsontv,
   b.verwerkingswijze,
   b.srtsynchronisatie,
   b.abonnement,
   b.categoriedienst,
   b.verwerking,
   b.bijhouding,
   b.hoogstemeldingsniveau,
   bp.pers berpers
FROM
	ber.ber b 
	left join ber.berpers bp on (b.id = bp.ber)
WHERE
-- ID is hoogste id waarbij srt gelijk is aan de srt bij het verzonden request 
-- omdat art's tegelijk gerund kunnen worden is referentienummer aan query toegevoegd om zeker te zijn dat het juiste resultaat wordt opgehaald
  b.id = (select max(id) 
		from ber.ber 
		where referentienr = '${referentienr_id}'
		  AND srt = 
			(select id 
				from ber.srtber 
				where naam = '${inkomendbericht_naam}'))
order by b.id desc;