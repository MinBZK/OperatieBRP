select substr(bericht,9,4) as bericht_type, *
from  mig_bericht
where kanaal = 'Levering'