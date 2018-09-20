select
 p.id p_id,
 trim(to_char(p.datoverlijden,'0000-00-00'))datoverlijden,
 p.gemoverlijden,
 p.wplnaamoverlijden,
 p.blplaatsoverlijden,
 p.blregiooverlijden,
 p.landgebiedoverlijden,
 p.omslocoverlijden,
 p.naderebijhaard
from
 kern.pers p
where
 bsn =${DataSource Values#|bsn_opr1|}
