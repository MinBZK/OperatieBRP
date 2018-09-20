SELECT  l.toegangabonnement,
        cd.naam dienstnaam,
        l.tsklaarzettenlev,
	l.datmaterieelselectie,
        l.dataanvmaterieleperioderes,
	l.dateindematerieleperioderes,
	l.tsaanvformeleperioderes,
	l.tseindeformeleperioderes,
	l.admhnd,
        l.srtsynchronisatie,
        p.bsn
FROM
	lev.lev l
	JOIN lev.levpers lp ON (lp.lev = l.id)
	JOIN kern.pers p ON (p.id = lp.pers)
        JOIN autaut.dienst d ON (d.id = l.dienst)
        JOIN autaut.catalogusoptie co ON (co.id = d.catalogusoptie)
	JOIN autaut.categoriedienst cd ON (cd.id = co.categoriedienst)
WHERE
  l.srtsynchronisatie = ${srtsynchronisatie} AND
  lp.id in (select id
		from lev.levpers
		where pers = 
			(select id 
				from kern.pers
				where bsn = ${_objectid$burgerservicenummer_ipv1_}))
	AND l.toegangabonnement =
	  (SELECT id
	  FROM autaut.toegangabonnement
	  WHERE abonnement = ${abonnement_id})
order by tsklaarzettenlev desc
Limit 1; 