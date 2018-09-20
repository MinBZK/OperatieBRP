--Zet de expressies om naar de nieuwe syntax, zonder dubbele quotes voor codes
update lev.abonnement set populatiebeperking = replace(populatiebeperking,'"','');

--Zet de namen van de (indicatie-) abonnementen om naar partijcodes met voorloopnullen
update lev.abonnement as a
set naam = (
	select concat('Synchroniseren via indicatie voor partij ', lpad(p.code::text, 6, '0')) from kern.partij p where p.id = p_u.id
)
from lev.toegangabonnement ta_u, kern.partij p_u, lev.dienst d_u
where ta_u.abonnement = a.id and ta_u.partij = p_u.id and a.id = d_u.abonnement and d_u.srtdienst = 2;

