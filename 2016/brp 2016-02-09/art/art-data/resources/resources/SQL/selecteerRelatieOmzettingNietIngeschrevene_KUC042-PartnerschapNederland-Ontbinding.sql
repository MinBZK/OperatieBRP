select r.id,r.dateinde eindeHuwelijk, substring(sr.naam,0,9) relatie , b.id betr_id, substring(sb.naam,0,8) rol, b.relatie rel_id, p.id, p.bsn, p.voornamen, p.geslnaamstam,  hsn.dataanvgel,
  hsn.dateindegel,
  to_char(hsn.tsreg, 'YYYYMMDD') hsn_tsreg,
  to_char(hsn.tsverval, 'YYYYMMDD') hsn_tsverval,
  hsn.indafgeleid,
  hsn.indnreeks,
  hsn.predicaat,
  hsn.voornamen,
  hsn.adellijketitel,
  hsn.voorvoegsel,
  hsn.scheidingsteken,
  hsn.geslnaamstam
from kern.pers p, kern.betr b, kern.relatie r, kern.srtbetr sb, kern.srtrelatie sr,   kern.his_perssamengesteldenaam hsn
where r.id = b.relatie and b.pers = p.id and sb.id = b.rol and sr.id = r.srt and b.rol = 3
and r.id in (select r1.id  from kern.relatie r1, kern.betr b1, kern.pers p1 where p1.id = b1.pers and b1.relatie = r1.id and p1.bsn =  ${DataSource Values#|objectid.persoon8|}) and   hsn.pers = p.id order by b.pers ASC, r.srt DESC, hsn_tsverval ASC;
