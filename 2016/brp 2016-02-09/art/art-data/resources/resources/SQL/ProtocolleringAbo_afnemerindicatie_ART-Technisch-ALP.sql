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
  lp.id in (select id
		from lev.levpers
		where pers = 
			(select id 
				from kern.pers
				where bsn = ${DataSource Values#burgerservicenummer_ipr1}))

	AND l.toegangabonnement =
	  (SELECT id
	  FROM autaut.toegangabonnement
	  WHERE abonnement = ${DataSource Values#abonnement_id})
order by tsklaarzettenlev desc
Limit 1;	  
