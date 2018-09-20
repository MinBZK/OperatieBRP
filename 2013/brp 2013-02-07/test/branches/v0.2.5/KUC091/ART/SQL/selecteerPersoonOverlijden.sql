select p.id p_id, p.datoverlijden, p.gemoverlijden, p.wploverlijden, p.blplaatsoverlijden, p.blregiooverlijden, p.landoverlijden, p.omslocoverlijden, p.rdnopschortingbijhouding, hpov.pers hpov_pers, hpov.datoverlijden, hpov.gemoverlijden, hpov.wploverlijden, hpov.blplaatsoverlijden, hpov.blregiooverlijden, hpov.landoverlijden, hpov.omslocoverlijden, hpop.pers hpop_pers, hpop.dataanvgel, hpop.dateindegel, hpop.tsverval, hpop.actieinh, hpop.actieverval, hpop.actieaanpgel, hpop.RdnOpschortingBijhouding
from kern.pers p , kern.his_persoverlijden hpov, kern.his_persopschorting hpop 
where bsn =${burgerservicenummer_ipr0} AND p.id = hpov.pers AND p.id = hpop.pers

