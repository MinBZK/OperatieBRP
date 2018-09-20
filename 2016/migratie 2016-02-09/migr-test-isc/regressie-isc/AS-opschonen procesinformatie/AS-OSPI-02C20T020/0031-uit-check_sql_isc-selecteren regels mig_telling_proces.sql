select proces_naam
,      bericht_type
,      kanaal
,      aantal_gestarte_processen
,      aantal_beeindigde_processen
from   mig_telling_proces
order by proces_naam
,      bericht_type
,      kanaal
;
