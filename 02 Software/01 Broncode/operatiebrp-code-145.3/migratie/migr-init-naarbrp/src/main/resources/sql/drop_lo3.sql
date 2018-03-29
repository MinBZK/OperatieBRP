--
-- GBA-V lo3 drop database
--
-- IMPORTANT NOTICE!
--	Each change to the database schema requires an updated version number on delivery.
--	E.g. use the version of the formally delivered software!
--

DROP TABLE spg_schema CASCADE;
DROP TABLE SPG_mailbox CASCADE;
DROP TABLE lo3_adres CASCADE;
DROP TABLE lo3_adres_afnemer_ind CASCADE;
DROP TABLE lo3_afnemers_verstrekking_aut CASCADE;
DROP TABLE lo3_akte_aand CASCADE;
DROP TABLE afnemer CASCADE;
DROP TABLE lo3_autorisatie CASCADE;
DROP TABLE lo3_gemeente CASCADE;
DROP TABLE lo3_land CASCADE;
DROP TABLE lo3_categorie CASCADE;
DROP TABLE lo3_categorie_groep CASCADE;
DROP TABLE lo3_element CASCADE;
DROP TABLE lo3_gba_deelnemer CASCADE;
DROP TABLE lo3_groep CASCADE;
DROP TABLE lo3_nationaliteit CASCADE;
DROP TABLE lo3_nl_nat_verkrijg_verlies_reden CASCADE;
DROP TABLE lo3_nl_reis_doc_autoriteit CASCADE;
DROP TABLE lo3_nl_reis_doc_soort CASCADE;
DROP TABLE lo3_pl CASCADE;
DROP TABLE lo3_pl_afnemer_ind CASCADE;
DROP TABLE lo3_pl_gezagsverhouding CASCADE;
DROP TABLE lo3_pl_nationaliteit CASCADE;
DROP TABLE lo3_pl_overlijden CASCADE;
DROP TABLE lo3_pl_persoon CASCADE;
DROP TABLE lo3_pl_paw_index_2 CASCADE;
DROP TABLE lo3_pl_reis_doc CASCADE;
DROP TABLE lo3_pl_verblijfplaats CASCADE;
DROP TABLE lo3_pl_verblijfstitel CASCADE;
DROP TABLE lo3_pl_serialized CASCADE;
DROP TABLE lo3_relatie_eind_reden CASCADE;
DROP TABLE lo3_rni_deelnemer CASCADE;
DROP TABLE lo3_rubriek_aut CASCADE;
DROP TABLE lo3_titel_predikaat CASCADE;
DROP TABLE lo3_verblijfstitel_aand CASCADE;
DROP TABLE lo3_voorvoegsel CASCADE;
DROP TABLE lo3_voorwaarde_regel_aut CASCADE;
DROP TABLE activiteit CASCADE;
DROP TABLE gebeurtenis CASCADE;
DROP TABLE gebeurtenis_data CASCADE;
DROP TABLE lo3_bericht CASCADE;
DROP TABLE lookup_codering CASCADE;
DROP TABLE lookup_codewaarde CASCADE;
DROP TABLE toestand_overgang CASCADE;
DROP TABLE lo3_mailbox CASCADE;
DROP TABLE lo3_vospg_instructie CASCADE;
DROP TABLE miteller CASCADE;
DROP TABLE miteller_marker CASCADE;
DROP TABLE selectie_instelling CASCADE;
DROP TABLE selectie_enumeratie CASCADE;
DROP TABLE monitor CASCADE;
DROP TABLE herindeling CASCADE;
DROP TABLE anonimiseer CASCADE;
DROP TABLE tmv_dossier CASCADE;
DROP TABLE tmv_dossier_afnemer_melding CASCADE;
DROP TABLE tmv_rubriek_waarde CASCADE;
DROP TABLE tmv_annotatie CASCADE;

DROP SEQUENCE lo3_pl_id_sequence;
DROP SEQUENCE lo3_bericht_id_sequence;
DROP SEQUENCE adres_id_sequence;
DROP SEQUENCE activiteit_id_sequence;
DROP SEQUENCE toestand_overgang_id_sequence;
DROP SEQUENCE gebeurtenis_id_sequence;
DROP SEQUENCE gebeurtenis_data_id_sequence;
DROP SEQUENCE lo3_autorisatie_id_sequence;
DROP SEQUENCE lo3_vospg_instructie_id_sequence;
DROP SEQUENCE afnemer_id_sequence;
DROP SEQUENCE selectie_instelling_id_sequence;
DROP SEQUENCE monitor_id_sequence;
DROP SEQUENCE anonimiseer_id_sequence;
DROP SEQUENCE tmv_rubriek_waarde_id_sequence;
DROP SEQUENCE tmv_dossier_afnemer_melding_id_sequence;

VACUUM FULL;
