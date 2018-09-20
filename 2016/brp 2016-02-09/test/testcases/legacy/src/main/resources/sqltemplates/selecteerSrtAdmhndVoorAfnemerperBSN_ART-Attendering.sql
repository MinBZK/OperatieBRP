select distinct
   adm.srt
from 
	kern.pers p, 
	kern.admhnd adm
where 
	(p.admhnd-1 = adm.id or p.admhnd = adm.id or p.admhnd+1 = adm.id )
	and p.bsn = ${_objectid$persoon1_}
	and adm.partij = ${partij_id}