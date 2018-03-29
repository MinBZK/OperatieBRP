-- SQL voor toevoegen van berichten aan GBA-V db

UPDATE lo3_bericht SET bericht_data='{bericht}',creatie_dt='{mutatie_dt}' 
WHERE bericht_activiteit_id in 
(
	SELECT pl.mutatie_activiteit_id FROM lo3_pl pl, lo3_pl_persoon persoon 
	WHERE pl.pl_id=persoon.pl_id 
	AND persoon.persoon_type='P' 
	AND persoon.a_nr='{anr}' 
	AND persoon.burger_service_nr='{bsn}'
);

