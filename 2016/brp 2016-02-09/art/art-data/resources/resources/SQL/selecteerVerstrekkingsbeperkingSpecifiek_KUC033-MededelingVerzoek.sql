select 
p.id as p_id,
p.bsn,
pv.id as pv_id,
pv.pers,
pv.partij,
pv.omsderde,
pv.gemverordening,
hpv.persverstrbeperking,
hpv.tsreg,
hpv.tsverval,
hpv.actieinh,
hpv.actieverval,
hpv.nadereaandverval

from kern.pers p, kern.persverstrbeperking pv, kern.his_persverstrbeperking hpv
where pv.pers = p.id AND hpv.persverstrbeperking = pv.id AND p.bsn = ${DataSource Values#objectid.persoon1}
order by hpv.tsreg;
