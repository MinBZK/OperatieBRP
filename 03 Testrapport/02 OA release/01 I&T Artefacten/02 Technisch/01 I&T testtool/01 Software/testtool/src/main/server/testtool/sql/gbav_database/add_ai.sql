-- Afnemer indicatie toevoegen aan GBA-V database

INSERT INTO lo3_pl_afnemer_ind (pl_id, stapel_nr, volg_nr, afnemer_code, geldigheid_start_datum) 
SELECT DISTINCT pl_id, {stapel}, {volgnummer}, CAST({afnemer} AS INT), {ingangsdatum} FROM lo3_pl_persoon persoon 
WHERE persoon.a_nr={anr} 
AND persoon.burger_service_nr='{bsn}'
AND persoon.persoon_type='P';
