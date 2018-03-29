--
-- GBA-V lo3 truncate database
--
-- IMPORTANT NOTICE!
--  Each change to the database schema requires an updated version number on delivery.
--  E.g. use the version of the formally delivered software!
--

truncate table spg_schema cascade;
truncate table SPG_mailbox cascade;
truncate table lo3_adres cascade;
truncate table lo3_adres_afnemer_ind cascade;
truncate table lo3_afnemers_verstrekking_aut cascade;
truncate table lo3_akte_aand cascade;
truncate table afnemer cascade;
truncate table lo3_autorisatie cascade;
truncate table lo3_gemeente cascade;
truncate table lo3_land cascade;
truncate table lo3_categorie cascade;
truncate table lo3_categorie_groep cascade;
truncate table lo3_element cascade;
truncate table lo3_gba_deelnemer cascade;
truncate table lo3_groep cascade;
truncate table lo3_nationaliteit cascade;
truncate table lo3_nl_nat_verkrijg_verlies_reden cascade;
truncate table lo3_nl_reis_doc_autoriteit cascade;
truncate table lo3_nl_reis_doc_soort cascade;
truncate table lo3_pl cascade;
truncate table lo3_pl_afnemer_ind cascade;
truncate table lo3_pl_gezagsverhouding cascade;
truncate table lo3_pl_nationaliteit cascade;
truncate table lo3_pl_overlijden cascade;
truncate table lo3_pl_persoon cascade;
truncate table lo3_pl_paw_index_2 cascade;
truncate table lo3_pl_reis_doc cascade;
truncate table lo3_pl_verblijfplaats cascade;
truncate table lo3_pl_verblijfstitel cascade;
truncate table lo3_pl_serialized cascade;
truncate table lo3_relatie_eind_reden cascade;
truncate table lo3_rni_deelnemer cascade;
truncate table lo3_rubriek_aut cascade;
truncate table lo3_titel_predikaat cascade;
truncate table lo3_verblijfstitel_aand cascade;
truncate table lo3_voorvoegsel cascade;
truncate table lo3_voorwaarde_regel_aut cascade;
truncate table activiteit cascade;
truncate table gebeurtenis cascade;
truncate table gebeurtenis_data cascade;
truncate table lo3_bericht cascade;
truncate table lookup_codering cascade;
truncate table lookup_codewaarde cascade;
truncate table toestand_overgang cascade;
truncate table lo3_mailbox cascade;
truncate table lo3_vospg_instructie cascade;
truncate table miteller cascade;
truncate table miteller_marker cascade;

alter sequence lo3_pl_id_sequence restart 1;
alter sequence lo3_bericht_id_sequence restart 1;
alter sequence adres_id_sequence restart 1;
alter sequence activiteit_id_sequence restart 1;
alter sequence toestand_overgang_id_sequence restart 1;
alter sequence gebeurtenis_id_sequence restart 1;
alter sequence gebeurtenis_data_id_sequence restart 1;
alter sequence lo3_autorisatie_id_sequence restart 1;
alter sequence lo3_vospg_instructie_id_sequence restart 1;
alter sequence afnemer_id_sequence restart 1;
