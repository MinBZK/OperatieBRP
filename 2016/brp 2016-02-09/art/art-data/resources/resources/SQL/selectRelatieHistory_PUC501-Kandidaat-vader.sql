select r.id, substring(sr.naam,0,9) relatie , b.id, substring(sb.naam,0,8) rol,  b.relatie, hhp.dataanv relatiebegin,hhp.dateinde einderelatie, p.id, p.bsn, p.voornamen, p.geslnaamstam
from kern.pers p, kern.betr b, kern.relatie r, kern.srtbetr sb, kern.srtrelatie sr, kern.his_relatie hhp
where r.id = b.relatie and b.pers = p.id and sb.id = b.rol and sr.id = r.srt and hhp.relatie = b.relatie
and r.id in (select r1.id  from kern.relatie r1, kern.betr b1, kern.pers p1 where p1.id = b1.pers and b1.relatie = r1.id and p1.bsn =  400183870) order by r.id,b.id,hhp.dateinde desc;


