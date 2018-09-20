INSERT INTO initvul.initvullingresult_afnind (
	pl_id,
	a_nr,
	conversie_resultaat
)
    SELECT DISTINCT
    	afnemer.pl_id,
    	a_nr,
    	'TE_VERZENDEN'
   	FROM lo3_pl_afnemer_ind afnemer
   	JOIN lo3_pl_persoon persoon
   		ON  persoon.pl_id = afnemer.pl_id
   			AND persoon.persoon_type = 'P'
   			AND persoon.volg_nr = 0
   			AND persoon.stapel_nr = 0
   			AND afnemer.volg_nr = 0
   			AND afnemer.stapel_nr = 0;

INSERT INTO initvul.initvullingresult_afnind_stapel (
    pl_id,
    stapel_nr,
    conversie_resultaat
)
    SELECT DISTINCT
    afnind.pl_id,
    afnind.stapel_nr,
    'TE_VERWERKEN'
	FROM lo3_pl_afnemer_ind afnind
	INNER JOIN initvul.initvullingresult_afnind
	ON afnind.pl_id = initvullingresult_afnind.pl_id;

INSERT INTO initvul.initvullingresult_afnind_regel (
    pl_id,
    stapel_nr,
    volg_nr,
    afnemer_code,
    geldigheid_start_datum
)
    SELECT
    afnind.pl_id,
    afnind.stapel_nr,
    afnind.volg_nr,
    afnind.afnemer_code,
    afnind.geldigheid_start_datum
	FROM lo3_pl_afnemer_ind afnind
	INNER JOIN initvul.initvullingresult_afnind
	ON afnind.pl_id = initvullingresult_afnind.pl_id;
