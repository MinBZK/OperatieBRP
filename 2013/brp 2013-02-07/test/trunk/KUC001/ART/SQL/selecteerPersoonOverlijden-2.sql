select p.id p_id, p.datoverlijden, p.gemoverlijden, p.wploverlijden, p.blplaatsoverlijden, p.blregiooverlijden, p.landoverlijden, p.omslocoverlijden, p.rdnopschortingbijhouding
from kern.pers p 
where bsn =${burgerservicenummer_ipr0}