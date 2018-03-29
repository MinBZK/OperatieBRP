--Relatie en betrokkenheden
select relatie.id, srtrelatie.naam, betr.id, srtbetr.naam, pers.id, srtpers.naam, pers.anr, pers.bsn, pers.voornamen, pers.voorvoegsel, pers.geslnaam
from kern.relatie as relatie
left outer join kern.srtrelatie on relatie.srt = srtrelatie.id
left outer join kern.betr on betr.relatie = relatie.id
left outer join kern.srtbetr on betr.rol = srtbetr.id
left outer join kern.pers on betr.pers = pers.id
left outer join kern.srtpers on srtpers.id = pers.srt
order by relatie.id, pers.id;
