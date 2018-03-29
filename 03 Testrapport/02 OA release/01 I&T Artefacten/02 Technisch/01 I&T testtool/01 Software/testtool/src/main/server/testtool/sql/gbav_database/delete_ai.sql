-- Verwijderen van afnemer indicatie uit GBA-V 

DELETE FROM lo3_pl_afnemer_ind WHERE pl_id IN 
(
	SELECT pl_id FROM lo3_pl_persoon persoon 
	WHERE persoon.a_nr={anr} AND persoon.burger_service_nr={bsn} AND persoon.persoon_type='P'
);
