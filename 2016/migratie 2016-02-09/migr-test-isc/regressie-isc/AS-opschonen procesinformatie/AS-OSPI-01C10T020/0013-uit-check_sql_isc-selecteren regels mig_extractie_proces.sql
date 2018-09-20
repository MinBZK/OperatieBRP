select proces_naam
,      bericht_type
,      kanaal
,      indicatie_gestart_geteld
,      indicatie_beeindigd_geteld
from   mig_extractie_proces
order by proces_naam
,      bericht_type
,      kanaal
;
