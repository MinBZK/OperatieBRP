INSERT INTO initvul.initvullingresult_aut (
        autorisatie_id,
        afnemer_code,
        afnemer_naam,
        indicatie_geheimhouding,
        verstrekkings_beperking,
        conditionele_verstrekking,
        spontaan_medium,
        selectie_soort,
        bericht_aand,
        eerste_selectie_datum,
        selectie_periode,
        selectie_medium,
        pl_plaatsings_bevoegdheid,
        adres_vraag_bevoegdheid,
        adhoc_medium,
        adres_medium,
        tabel_regel_start_datum,
        tabel_regel_eind_datum,
        sleutel_rubrieken,
        spontaan_rubrieken,
        voorwaarde_regel_spontaan,
        selectie_rubrieken,
        voorwaarde_regel_selectie,
        adhoc_rubrieken,
        voorwaarde_regel_adhoc,
        adres_rubrieken,
        voorwaarde_regel_adres,
        afnemers_verstrekkingen,
        conversie_resultaat
)
    SELECT
        aut.autorisatie_id,
        lpad(cast(aut.afnemer_code as VARCHAR(6)), 6, '0'),
        aut.afnemer_naam,
        aut.geheimhouding_ind,
        aut.verstrekkings_beperking,
        aut.conditionele_verstrekking,
        aut.spontaan_medium,
        aut.selectie_soort,
        aut.bericht_aand,
        aut.eerste_selectie_datum,
        aut.selectie_periode,
        aut.selectie_medium,
        aut.pl_plaatsings_bevoegdheid,
        aut.adres_vraag_bevoegdheid,
        aut.ad_hoc_medium,
        aut.adres_medium,
        aut.tabel_regel_start_datum,
        aut.tabel_regel_eind_datum,
        aut.sleutel_rubrieken,
        aut.spontaan_rubrieken,
        voorwaarde_regel_spontaan.voorwaarde_regel,
        aut.selectie_rubrieken,
        voorwaarde_regel_selectie.voorwaarde_regel,
        aut.ad_hoc_rubrieken,
        voorwaarde_regel_adhoc.voorwaarde_regel,
        aut.adres_rubrieken,
        voorwaarde_regel_adres.voorwaarde_regel,
        aut.afnemers_verstrekkingen,
        'TE_VERZENDEN'
    FROM lo3_autorisatie aut
        LEFT OUTER JOIN lo3_voorwaarde_regel_aut voorwaarde_regel_spontaan
            ON aut.autorisatie_id = voorwaarde_regel_spontaan.autorisatie_id AND voorwaarde_regel_spontaan.voorwaarde_type = '4'
        LEFT OUTER JOIN lo3_voorwaarde_regel_aut voorwaarde_regel_selectie
            ON aut.autorisatie_id = voorwaarde_regel_selectie.autorisatie_id AND voorwaarde_regel_selectie.voorwaarde_type = '5'
        LEFT OUTER JOIN lo3_voorwaarde_regel_aut voorwaarde_regel_adhoc
            ON aut.autorisatie_id = voorwaarde_regel_adhoc.autorisatie_id AND voorwaarde_regel_adhoc.voorwaarde_type = '6'
        LEFT OUTER JOIN lo3_voorwaarde_regel_aut voorwaarde_regel_adres
            ON aut.autorisatie_id = voorwaarde_regel_adres.autorisatie_id AND voorwaarde_regel_adres.voorwaarde_type = '7';
