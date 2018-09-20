select distinct
   b.richting
from 
	ber.ber b
where 
	b.id > ${Pre Query#result}
order by b.richting