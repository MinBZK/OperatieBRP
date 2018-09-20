--
-- GBA-V lo3 drop database
--
-- IMPORTANT NOTICE!
--	Each change to the database schema requires an updated version number on delivery.
--	E.g. use the version of the formally delivered software!
--

drop table spg_schema cascade;
drop table SPG_mailbox cascade;
drop table lo3_adres cascade;
drop table lo3_adres_afnemer_ind cascade;
drop table lo3_afnemers_verstrekking_aut cascade;
drop table lo3_akte_aand cascade;
drop table afnemer cascade;
drop table lo3_autorisatie cascade;
drop table lo3_gemeente cascade;
drop table lo3_land cascade;
drop table lo3_categorie cascade;
drop table lo3_categorie_groep cascade;
drop table lo3_element cascade;
drop table lo3_gba_deelnemer cascade;
drop table lo3_groep cascade;
drop table lo3_nationaliteit cascade;
drop table lo3_nl_nat_verkrijg_verlies_reden cascade;
drop table lo3_nl_reis_doc_autoriteit cascade;
drop table lo3_nl_reis_doc_soort cascade;
drop table lo3_pl cascade;
drop table lo3_pl_afnemer_ind cascade;
drop table lo3_pl_gezagsverhouding cascade;
drop table lo3_pl_nationaliteit cascade;
drop table lo3_pl_overlijden cascade;
drop table lo3_pl_persoon cascade;
drop table lo3_pl_paw_index_2 cascade;
drop table lo3_pl_reis_doc cascade;
drop table lo3_pl_verblijfplaats cascade;
drop table lo3_pl_verblijfstitel cascade;
drop table lo3_pl_serialized cascade;
drop table lo3_relatie_eind_reden cascade;
drop table lo3_rni_deelnemer cascade;
drop table lo3_rubriek_aut cascade;
drop table lo3_titel_predikaat cascade;
drop table lo3_verblijfstitel_aand cascade;
drop table lo3_voorvoegsel cascade;
drop table lo3_voorwaarde_regel_aut cascade;
drop table activiteit cascade;
drop table gebeurtenis cascade;
drop table gebeurtenis_data cascade;
drop table lo3_bericht cascade;
drop table lookup_codering cascade;
drop table lookup_codewaarde cascade;
drop table toestand_overgang cascade;
drop table lo3_mailbox cascade;
drop table lo3_vospg_instructie cascade;
drop table miteller cascade;
drop table miteller_marker cascade;

drop sequence lo3_pl_id_sequence;
drop sequence lo3_bericht_id_sequence;
drop sequence adres_id_sequence;
drop sequence activiteit_id_sequence;
drop sequence toestand_overgang_id_sequence;
drop sequence gebeurtenis_id_sequence;
drop sequence gebeurtenis_data_id_sequence;
drop sequence lo3_autorisatie_id_sequence;
drop sequence lo3_vospg_instructie_id_sequence;
drop sequence afnemer_id_sequence;

vacuum full;
