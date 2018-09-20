select
  p.id p_id,
  trim(to_char(p.datoverlijden,'0000-00-00'))datoverlijden,
  p.gemoverlijden,
  p.wplnaamoverlijden,
  p.blplaatsoverlijden,
  p.blregiooverlijden,
  p.landgebiedoverlijden,
  p.omslocoverlijden,
  p.naderebijhaard,
  hpov.pers hpov_pers,
  trim(to_char(hpov.datoverlijden,'0000-00-00'))datoverlijden,
  hpov.gemoverlijden,
  hpov.wplnaamoverlijden,
  hpov.blplaatsoverlijden,
  hpov.blregiooverlijden,
  hpov.landgebiedoverlijden,
  hpov.omslocoverlijden,
  hpb.pers hpb_pers,
  trim(to_char(hpb.dataanvgel,'0000-00-00'))dataanvgel,
  trim(to_char(hpb.dateindegel,'0000-00-00'))dateindegel,
  hpb.tsverval,
  hpb.actieinh,
  hpb.actieverval,
  hpb.actieaanpgel,
  hpb.naderebijhaard
from
  kern.pers p,
  kern.his_persoverlijden hpov,
  kern.his_persbijhouding hpb,
  kern.naderebijhaard nb
where
  bsn =${DataSource Values#|bsn_opr1|} AND
  p.id = hpov.pers AND
  p.id = hpb.pers AND
  hpb.naderebijhaard = nb.id AND
  nb.code = 'O'
