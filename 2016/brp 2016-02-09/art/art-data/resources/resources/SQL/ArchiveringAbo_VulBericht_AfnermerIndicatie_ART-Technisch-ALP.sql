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
-- ID is hoger dan id waarbij srt gelijk is aan de srt bij het verzonden request
  b.id > (select max(id)
		from ber.ber
		where referentienr = '${DataSource Values#referentienr_id}'
		   AND srt =
			(select id
				from ber.srtber
				where naam = '${DataSource Values#inkomendbericht_naam}'))

AND
-- srt is de srt van het uitgaande leverbericht
  b.srt = (select id
		from ber.srtber
		where naam = '${DataSource Values#leverbericht_naam}')

AND		-- srtsynchronisatie voor een Volledigbericht=2 en voor een mutatiebericht=1
b.srtsynchronisatie = ${DataSource Values#srtsynchronisatie_id}

AND
b.abonnement = ${DataSource Values#abonnement_id}

order by b.id desc;
