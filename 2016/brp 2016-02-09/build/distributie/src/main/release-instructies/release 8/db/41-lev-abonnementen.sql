delete from lev.abonnement;
delete from autaut.doelbinding;
delete from autaut.autorisatiebesluit;

insert into autaut.autorisatiebesluit (id, srt, besluittekst, autoriseerder, indmodelbesluit, indingetrokken,
	datbesluit, datingang, autorisatiebesluitstatushis, toestand, bijhautorisatiebesluitstatus)
select
	100 + p.id,
	1,
	'Proeftuin-besluit: ontvang levering van mutatie op personen van wie u de bijhoudingsverantwoording heeft.',
	(SELECT p.id from kern.partij p where p.srt=1 LIMIT 1),
	false,
	false,
	20130601,
	20130601,
	'A',
	4,
	'A'
from kern.partij p
where p.srt = 3
order by p.id;

insert into autaut.doelbinding (id, levsautorisatiebesluit, geautoriseerde, protocolleringsniveau, tekstdoelbinding, populatiecriterium, indverstrbeperkinghonoreren, doelbindingstatushis)
select
	100 + p.id,
	100 + p.id,
	p.id,
	1,
	'Proeftuin-doelbinding: die personen waarvan u de bijhoudingsveratnwoording heeft.',
	concat('persoon.bijhoudingsgemeente = "', CAST( p.code AS text ), '"'),
	true,
	'A'
from kern.partij p
where p.srt = 3
order by p.id;

insert into lev.abonnement (id, doelbinding, srtabonnement, populatiecriterium, abonnementstatushis)
select
	100 + p.id,
	100 + p.id,
	1,
	concat('persoon.bijhoudingsgemeente = "', CAST( p.code AS text ), '"'),
	'A'
from kern.partij p
where p.srt = 3
order by p.id;
