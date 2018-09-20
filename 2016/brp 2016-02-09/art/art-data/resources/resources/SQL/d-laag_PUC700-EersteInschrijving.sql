select
	hpa.id,
	p.voornamen,
	p.geslnaamstam,
	hpa.dataanvgel,
	hpa.dateindegel,
	hpa.tsreg,
	hpa.tsverval hpatsverval,
	hpa.dataanvadresh,
	g.naam gemeente,
	pl.naam plaats,
	hpa.postcode,
	hpa.nor,
	hpa.huisnr,
	hpa.huisletter,
	hpa.huisnrtoevoeging,
	l.naam landgebied
from
	kern.his_persadres hpa,
	kern.persadres pa,
	kern.pers p,
	kern.partij g,
	kern.plaats pl,
	kern.landgebied l
where
	hpa.tsverval is not null
	and hpa.persadres = pa.id
	and pa.pers = p.id
	and hpa.gem = g.id
	and hpa.wplnaam = pl.naam
	and hpa.landgebied = l.id
	and p.bsn = ${DataSource Values#db_bsn}
	--and hpa.tsverval >= formeelOpvraagTijdstip
order by hpa.tsverval asc;
