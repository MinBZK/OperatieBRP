INSERT INTO initvul.initvullingresult_afnind (
    pl_id,
    a_nr,
    bericht_resultaat)
VALUES 
(4, 8409813281, 'TE_VERZENDEN');

INSERT INTO initvul.initvullingresult_afnind_stapel (
    pl_id,
    stapel_nr,
    conversie_resultaat)
VALUES 
(4, 0, 'TE_VERWERKEN');

INSERT INTO initvul.initvullingresult_afnind_regel (
    pl_id,
    stapel_nr,
    volg_nr,
    afnemer_code,
    geldigheid_start_datum)
VALUES 
(4, 0, 0, 900050, 20120101);
