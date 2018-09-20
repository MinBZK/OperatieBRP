select
	pers.bsn 						burgerservicenummer,
	pers.datgeboorte 				geboortedatum,
	pers.datinschr					datinschr,
	pers.voornamen 				    voornamen,
	persvoornaam.volgnr 			persvoornaamvolgnr,
	persvoornaam.naam 				persvoornaamnaam
from
	kern.pers						pers,
	kern.persvoornaam 				persvoornaam
where
	persvoornaam.pers 				= pers.id and
	(pers.bsn 						= ${DataSource Values#|objectid.persoon0|})
order by pers.bsn, persvoornaam.volgnr ASC;
