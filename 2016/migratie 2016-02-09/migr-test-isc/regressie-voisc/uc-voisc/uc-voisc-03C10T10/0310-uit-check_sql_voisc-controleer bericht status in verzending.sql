select * 
from   voisc.bericht 
where  status = 'RECEIVED_FROM_ISC' 
and    tijdstip_in_verwerking IS NOT NULL
;