-- Clean database.
DROP TABLE IF EXISTS activiteit;
DROP TABLE IF EXISTS afnemer;
DROP TABLE IF EXISTS gebeurtenis;
DROP TABLE IF EXISTS gebeurtenis_data;
DROP TABLE IF EXISTS lo3_adres;
DROP TABLE IF EXISTS lo3_adres_afnemer_ind;
DROP TABLE IF EXISTS lo3_afnemers_verstrekking_aut;
DROP TABLE IF EXISTS lo3_akte_aand;
DROP TABLE IF EXISTS lo3_autorisatie;
DROP TABLE IF EXISTS lo3_bericht;
DROP TABLE IF EXISTS lo3_categorie;
DROP TABLE IF EXISTS lo3_categorie_groep;
DROP TABLE IF EXISTS lo3_element;
DROP TABLE IF EXISTS lo3_gba_deelnemer;
DROP TABLE IF EXISTS lo3_gemeente;
DROP TABLE IF EXISTS lo3_groep;
DROP TABLE IF EXISTS lo3_land;
DROP TABLE IF EXISTS lo3_mailbox;
DROP TABLE IF EXISTS lo3_nationaliteit;
DROP TABLE IF EXISTS lo3_nl_nat_verkrijg_verlies_reden;
DROP TABLE IF EXISTS lo3_nl_reis_doc_autoriteit;
DROP TABLE IF EXISTS lo3_nl_reis_doc_soort;
DROP TABLE IF EXISTS lo3_pl;
DROP TABLE IF EXISTS lo3_pl_afnemer_ind;
DROP TABLE IF EXISTS lo3_pl_gezagsverhouding;
DROP TABLE IF EXISTS lo3_pl_nationaliteit;
DROP TABLE IF EXISTS lo3_pl_overlijden;
DROP TABLE IF EXISTS lo3_pl_paw_index_2;
DROP TABLE IF EXISTS lo3_pl_persoon;
DROP TABLE IF EXISTS lo3_pl_reis_doc;
DROP TABLE IF EXISTS lo3_pl_serialized;
DROP TABLE IF EXISTS lo3_pl_verblijfplaats;
DROP TABLE IF EXISTS lo3_pl_verblijfstitel;
DROP TABLE IF EXISTS lo3_relatie_eind_reden;
DROP TABLE IF EXISTS lo3_rni_deelnemer;
DROP TABLE IF EXISTS lo3_rubriek_aut;
DROP TABLE IF EXISTS lo3_titel_predikaat;
DROP TABLE IF EXISTS lo3_verblijfstitel_aand;
DROP TABLE IF EXISTS lo3_voorvoegsel;
DROP TABLE IF EXISTS lo3_voorwaarde_regel_aut;
DROP TABLE IF EXISTS lo3_vospg_instructie;
DROP TABLE IF EXISTS lookup_codering;
DROP TABLE IF EXISTS lookup_codewaarde;
DROP TABLE IF EXISTS miteller;
DROP TABLE IF EXISTS miteller_marker;
DROP TABLE IF EXISTS proefSynchronisatieBericht;
DROP TABLE IF EXISTS spg_mailbox;
DROP TABLE IF EXISTS spg_schema;
DROP TABLE IF EXISTS toestand_overgang;

drop sequence if exists lo3_pl_id_sequence;
drop sequence if exists lo3_bericht_id_sequence;
drop sequence if exists adres_id_sequence;
drop sequence if exists activiteit_id_sequence;
drop sequence if exists toestand_overgang_id_sequence;
drop sequence if exists gebeurtenis_id_sequence;
drop sequence if exists gebeurtenis_data_id_sequence;
drop sequence if exists lo3_autorisatie_id_sequence;
drop sequence if exists lo3_vospg_instructie_id_sequence;
drop sequence if exists afnemer_id_sequence;
-- Einde cleanen database.

CREATE TABLE activiteit (
    activiteit_id INTEGER NOT NULL,
    activiteit_type INTEGER NOT NULL,
    activiteit_subtype INTEGER NOT NULL,
    moeder_id INTEGER,
    toestand INTEGER NOT NULL,
    start_dt TIMESTAMP NOT NULL,
    laatste_actie_dt TIMESTAMP,
    uiterlijke_actie_dt TIMESTAMP NOT NULL,
    pl_id INTEGER,
    communicatie_partner VARCHAR(40),
    nr_1 INTEGER,
    nr_2 INTEGER,
    nr_3 INTEGER,
    nr_4 INTEGER,
    nr_5 INTEGER,
    tekst_1 VARCHAR(40),
    tekst_2 VARCHAR(40),
    tekst_3 VARCHAR(40),
    tekst_4 VARCHAR(40),
    tekst_5 VARCHAR(40),
    trace_level CHAR(1),
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (activiteit_id)
);
CREATE TABLE afnemer (
    afnemer_id INTEGER NOT NULL,
    afnemer_code INTEGER NOT NULL,
    start_datum INTEGER NOT NULL,
    eind_datum INTEGER,
    geschatte_omvang INTEGER NOT NULL,
    leverwijze INTEGER NOT NULL,
    max_berichten INTEGER NOT NULL,
    berichtsoort INTEGER,
    selectiesoort INTEGER NOT NULL,
    bestands_formaat INTEGER,
    PRIMARY KEY (afnemer_id)
);
CREATE TABLE gebeurtenis (
    gebeurtenis_id INTEGER NOT NULL,
    gebeurtenis_type INTEGER NOT NULL,
    gebeurtenis_dt TIMESTAMP NOT NULL,
    gebeurtenis_oms VARCHAR(1000),
    activiteit_id INTEGER NOT NULL,
    activiteit_nieuwe_toestand INTEGER,
    creatie_door VARCHAR(40),
    creatie_dt TIMESTAMP NOT NULL,
    gebeurtenis_hash INTEGER,
    PRIMARY KEY (gebeurtenis_id)
);
CREATE TABLE gebeurtenis_data (
    gebeurtenis_data_id INTEGER NOT NULL,
    gebeurtenis_data_type INTEGER NOT NULL,
    gebeurtenis_data VARCHAR(20000),
    gebeurtenis_nr_1 INTEGER,
    gebeurtenis_nr_2 INTEGER,
    gebeurtenis_id INTEGER NOT NULL,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (gebeurtenis_data_id)
);
CREATE TABLE lo3_adres (
    adres_id INTEGER NOT NULL,
    gemeente_code INTEGER NOT NULL,
    gemeente_deel VARCHAR(24),
    straat_naam VARCHAR(24),
    diak_straat_naam VARCHAR(24),
    open_ruimte_naam VARCHAR(80),
    diak_open_ruimte_naam VARCHAR(80),
    huis_nr INTEGER,
    huis_letter CHAR(1),
    huis_nr_toevoeging VARCHAR(4),
    huis_nr_aand VARCHAR(2),
    postcode VARCHAR(6),
    woon_plaats_naam VARCHAR(80),
    diak_woon_plaats_naam VARCHAR(80),
    locatie_beschrijving VARCHAR(35),
    diak_locatie_beschrijving VARCHAR(35),
    verblijf_plaats_ident_code VARCHAR(16),
    nummer_aand_ident_code VARCHAR(16),
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (adres_id)
);
CREATE TABLE lo3_adres_afnemer_ind (
    adres_id INTEGER NOT NULL,
    afnemer_code INTEGER NOT NULL,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (adres_id,afnemer_code)
);
CREATE TABLE lo3_afnemers_verstrekking_aut (
    autorisatie_id INTEGER NOT NULL,
    afnemer_code INTEGER NOT NULL,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (autorisatie_id,afnemer_code)
);
CREATE TABLE lo3_akte_aand (
    akte_aand VARCHAR(3) NOT NULL,
    akte_soort_oms VARCHAR(80) NOT NULL,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (akte_aand)
);
CREATE TABLE lo3_autorisatie (
    autorisatie_id INTEGER NOT NULL,
    afnemer_code INTEGER NOT NULL,
    geheimhouding_ind INTEGER NOT NULL,
    verstrekkings_beperking INTEGER NOT NULL,
    afnemer_naam VARCHAR(80),
    straat_naam VARCHAR(24),
    huis_nr INTEGER,
    huis_letter CHAR(1),
    huis_nr_toevoeging VARCHAR(4),
    postcode VARCHAR(6),
    gemeente_code INTEGER,
    conditionele_verstrekking INTEGER,
    spontaan_medium CHAR(1),
    selectie_soort INTEGER,
    bericht_aand INTEGER,
    eerste_selectie_datum INTEGER,
    selectie_periode INTEGER,
    selectie_medium CHAR(1),
    pl_plaatsings_bevoegdheid INTEGER,
    adres_vraag_bevoegdheid INTEGER,
    ad_hoc_medium CHAR(1),
    adres_medium CHAR(1),
    tabel_regel_start_datum INTEGER,
    tabel_regel_eind_datum INTEGER,
    sleutel_rubrieken VARCHAR(1000),
    spontaan_rubrieken VARCHAR(1000),
    selectie_rubrieken VARCHAR(1000),
    ad_hoc_rubrieken VARCHAR(1000),
    adres_rubrieken VARCHAR(1000),
    afnemers_verstrekkingen VARCHAR(1000),
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (autorisatie_id)
);
CREATE TABLE lo3_bericht (
    lo3_bericht_id INTEGER NOT NULL,
    aanduiding_in_uit CHAR(1) NOT NULL,
    bericht_activiteit_id INTEGER,
    medium CHAR(1) NOT NULL,
    originator_or_recipient VARCHAR(7),
    spg_mailbox_instantie INTEGER,
    eref VARCHAR(12),
    bref VARCHAR(12),
    eref2 VARCHAR(12),
    berichtcyclus_id INTEGER,
    tijdstip_verzending_ontvangst TIMESTAMP,
    dispatch_sequence_number INTEGER,
    report_delivery_time TIMESTAMP,
    non_delivery_reason VARCHAR(4),
    non_receipt_reason VARCHAR(4),
    bericht_data VARCHAR(20000),
    kop_random_key INTEGER,
    kop_berichtsoort_nummer VARCHAR(4),
    kop_a_nummer INTEGER,
    kop_oud_a_nummer INTEGER,
    kop_herhaling CHAR(1),
    kop_foutreden CHAR(1),
    kop_datum_tijd INTEGER,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (lo3_bericht_id)
);
CREATE TABLE lo3_categorie (
    categorie_nr INTEGER NOT NULL,
    categorie_naam VARCHAR(40) NOT NULL,
    categorie_oms VARCHAR(80),
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (categorie_nr)
);
CREATE TABLE lo3_categorie_groep (
    categorie_nr INTEGER NOT NULL,
    groep_nr INTEGER NOT NULL,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (categorie_nr,groep_nr)
);
CREATE TABLE lo3_element (
    element_nr INTEGER NOT NULL,
    element_naam VARCHAR(40) NOT NULL,
    element_oms VARCHAR(80),
    groep_nr INTEGER NOT NULL,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (element_nr)
);
CREATE TABLE lo3_gba_deelnemer (
    deelnemer_code INTEGER NOT NULL,
    deelnemer_oms VARCHAR(80) NOT NULL,
    gba_netwerk_aand INTEGER,
    tabel_regel_start_datum INTEGER,
    tabel_regel_eind_datum INTEGER,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (deelnemer_code)
);
CREATE TABLE lo3_gemeente (
    gemeente_code INTEGER NOT NULL,
    gemeente_naam VARCHAR(40) NOT NULL,
    nieuwe_gemeente_code INTEGER,
    tabel_regel_start_datum INTEGER,
    tabel_regel_eind_datum INTEGER,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (gemeente_code)
);
CREATE TABLE lo3_groep (
    groep_nr INTEGER NOT NULL,
    groep_naam VARCHAR(40) NOT NULL,
    groep_oms VARCHAR(80),
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (groep_nr)
);
CREATE TABLE lo3_land (
    land_code INTEGER NOT NULL,
    land_naam VARCHAR(42) NOT NULL,
    tabel_regel_start_datum INTEGER,
    tabel_regel_eind_datum INTEGER,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (land_code)
);
CREATE TABLE lo3_mailbox (
    lo3_mailbox_nummer VARCHAR(7) NOT NULL,
    spg_mailbox_instantie INTEGER,
    soort_instantie CHAR(1) NOT NULL,
    code_instantie INTEGER,
    indicatie_mailbox_actief CHAR(1),
    brp_overgangs_datum INTEGER,
    blokkade_start_dt TIMESTAMP,
    blokkade_eind_dt TIMESTAMP,
    creatie_dt TIMESTAMP NOT NULL,
    mutatie_dt TIMESTAMP,
    PRIMARY KEY (lo3_mailbox_nummer)
);
CREATE TABLE lo3_nationaliteit (
    nationaliteit_code INTEGER NOT NULL,
    nationaliteit_oms VARCHAR(42) NOT NULL,
    tabel_regel_start_datum INTEGER,
    tabel_regel_eind_datum INTEGER,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (nationaliteit_code)
);
CREATE TABLE lo3_nl_nat_verkrijg_verlies_reden (
    nl_nat_verkrijg_verlies_reden INTEGER NOT NULL,
    nl_nat_reden_oms VARCHAR(80) NOT NULL,
    nl_nat_reden_soort VARCHAR(2) NOT NULL,
    tabel_regel_start_datum INTEGER,
    tabel_regel_eind_datum INTEGER,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (nl_nat_verkrijg_verlies_reden)
);
CREATE TABLE lo3_nl_reis_doc_autoriteit (
    nl_reis_doc_autoriteit_code VARCHAR(6) NOT NULL,
    nl_reis_doc_autoriteit_oms VARCHAR(80) NOT NULL,
    tabel_regel_start_datum INTEGER,
    tabel_regel_eind_datum INTEGER,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (nl_reis_doc_autoriteit_code)
);
CREATE TABLE lo3_nl_reis_doc_soort (
    nl_reis_doc_soort VARCHAR(2) NOT NULL,
    nl_reis_doc_soort_oms VARCHAR(80) NOT NULL,
    tabel_regel_start_datum INTEGER,
    tabel_regel_eind_datum INTEGER,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (nl_reis_doc_soort)
);
CREATE TABLE lo3_pl (
    pl_id INTEGER NOT NULL,
    pl_blokkering_start_datum INTEGER,
    bijhouding_opschort_datum INTEGER,
    bijhouding_opschort_reden CHAR(1),
    gba_eerste_inschrijving_datum INTEGER,
    pk_gemeente_code INTEGER,
    geheim_ind INTEGER,
    verificatie_datum INTEGER,
    verificatie_oms VARCHAR(50),
    versie_nr INTEGER,
    stempel_dt BIGINT,
    volledig_geconverteerd_pk CHAR(1),
    rni_deelnemer INTEGER,
    verdrag_oms VARCHAR(50),
    europees_kiesrecht_aand INTEGER,
    europees_kiesrecht_datum INTEGER,
    europees_uitsluit_eind_datum INTEGER,
    kiesrecht_uitgesl_aand CHAR(1),
    kiesrecht_uitgesl_eind_datum INTEGER,
    kiesrecht_doc_gemeente_code INTEGER,
    kiesrecht_doc_datum INTEGER,
    kiesrecht_doc_beschrijving VARCHAR(40),
    mutatie_activiteit_id INTEGER,
    creatie_dt TIMESTAMP NOT NULL,
    mutatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (pl_id)
);
CREATE TABLE lo3_pl_afnemer_ind (
    pl_id INTEGER NOT NULL,
    stapel_nr INTEGER NOT NULL,
    volg_nr INTEGER NOT NULL,
    afnemer_code INTEGER,
    geldigheid_start_datum INTEGER,
    PRIMARY KEY (pl_id,stapel_nr,volg_nr)
);
CREATE TABLE lo3_pl_gezagsverhouding (
    pl_id INTEGER NOT NULL,
    volg_nr INTEGER NOT NULL,
    minderjarig_gezag_ind VARCHAR(2),
    curatele_register_ind INTEGER,
    doc_gemeente_code INTEGER,
    doc_datum INTEGER,
    doc_beschrijving VARCHAR(40),
    onderzoek_gegevens_aand INTEGER,
    onderzoek_start_datum INTEGER,
    onderzoek_eind_datum INTEGER,
    onjuist_ind CHAR(1),
    geldigheid_start_datum INTEGER,
    opneming_datum INTEGER,
    PRIMARY KEY (pl_id,volg_nr)
);
CREATE TABLE lo3_pl_nationaliteit (
    pl_id INTEGER NOT NULL,
    stapel_nr INTEGER NOT NULL,
    volg_nr INTEGER NOT NULL,
    nationaliteit_code INTEGER,
    nl_nat_verkrijg_reden INTEGER,
    nl_nat_verlies_reden INTEGER,
    bijzonder_nl_aand CHAR(1),
    doc_gemeente_code INTEGER,
    doc_datum INTEGER,
    doc_beschrijving VARCHAR(40),
    onderzoek_gegevens_aand INTEGER,
    onderzoek_start_datum INTEGER,
    onderzoek_eind_datum INTEGER,
    onjuist_ind CHAR(1),
    geldigheid_start_datum INTEGER,
    opneming_datum INTEGER,
    rni_deelnemer INTEGER,
    verdrag_oms VARCHAR(50),
    PRIMARY KEY (pl_id,stapel_nr,volg_nr)
);
CREATE TABLE lo3_pl_overlijden (
    pl_id INTEGER NOT NULL,
    volg_nr INTEGER NOT NULL,
    overlijden_datum INTEGER,
    overlijden_plaats VARCHAR(40),
    overlijden_land_code INTEGER,
    akte_register_gemeente_code INTEGER,
    akte_nr VARCHAR(7),
    doc_gemeente_code INTEGER,
    doc_datum INTEGER,
    doc_beschrijving VARCHAR(40),
    onderzoek_gegevens_aand INTEGER,
    onderzoek_start_datum INTEGER,
    onderzoek_eind_datum INTEGER,
    onjuist_ind CHAR(1),
    geldigheid_start_datum INTEGER,
    opneming_datum INTEGER,
    rni_deelnemer INTEGER,
    verdrag_oms VARCHAR(50),
    PRIMARY KEY (pl_id,volg_nr)
);
CREATE TABLE lo3_pl_paw_index_2 (
    pl_id INTEGER NOT NULL,
    voor_naam VARCHAR(200),
    diak_voor_naam VARCHAR(200),
    geslachts_naam VARCHAR(200) NOT NULL,
    diak_geslachts_naam VARCHAR(200),
    titel_predikaat VARCHAR(2),
    geslachts_naam_voorvoegsel VARCHAR(10),
    geboorte_datum INTEGER,
    geboorte_plaats VARCHAR(40),
    geboorte_land_code INTEGER,
    geslachts_aand CHAR(1),
    naam_gebruik_aand CHAR(1),
    inschrijving_gemeente_code INTEGER NOT NULL,
    gemeente_deel VARCHAR(24),
    straat_naam VARCHAR(24),
    diak_straat_naam VARCHAR(24),
    open_ruimte_naam VARCHAR(80),
    diak_open_ruimte_naam VARCHAR(80),
    huis_nr INTEGER,
    huis_letter CHAR(1),
    huis_nr_toevoeging VARCHAR(4),
    huis_nr_aand VARCHAR(2),
    postcode VARCHAR(6),
    woon_plaats_naam VARCHAR(80),
    diak_woon_plaats_naam VARCHAR(80),
    locatie_beschrijving VARCHAR(35),
    diak_locatie_beschrijving VARCHAR(35),
    verblijf_plaats_ident_code VARCHAR(16),
    nummer_aand_ident_code VARCHAR(16),
    PRIMARY KEY (pl_id)
);
CREATE TABLE lo3_pl_persoon (
    pl_id INTEGER NOT NULL,
    persoon_type CHAR(1) NOT NULL,
    stapel_nr INTEGER NOT NULL,
    volg_nr INTEGER NOT NULL,
    a_nr INTEGER,
    burger_service_nr INTEGER,
    voor_naam VARCHAR(200),
    diak_voor_naam VARCHAR(200),
    titel_predikaat VARCHAR(2),
    geslachts_naam_voorvoegsel VARCHAR(10),
    geslachts_naam VARCHAR(200),
    diak_geslachts_naam VARCHAR(200),
    geboorte_datum INTEGER,
    geboorte_plaats VARCHAR(40),
    geboorte_land_code INTEGER,
    geslachts_aand CHAR(1),
    vorig_a_nr INTEGER,
    volgend_a_nr INTEGER,
    naam_gebruik_aand CHAR(1),
    akte_register_gemeente_code INTEGER,
    akte_nr VARCHAR(7),
    doc_gemeente_code INTEGER,
    doc_datum INTEGER,
    doc_beschrijving VARCHAR(40),
    onderzoek_gegevens_aand INTEGER,
    onderzoek_start_datum INTEGER,
    onderzoek_eind_datum INTEGER,
    onjuist_ind CHAR(1),
    geldigheid_start_datum INTEGER,
    opneming_datum INTEGER,
    rni_deelnemer INTEGER,
    verdrag_oms VARCHAR(50),
    relatie_start_datum INTEGER,
    relatie_start_plaats VARCHAR(40),
    relatie_start_land_code INTEGER,
    relatie_eind_datum INTEGER,
    relatie_eind_plaats VARCHAR(40),
    relatie_eind_land_code INTEGER,
    relatie_eind_reden CHAR(1),
    verbintenis_soort CHAR(1),
    familie_betrek_start_datum INTEGER,
    PRIMARY KEY (pl_id,persoon_type,stapel_nr,volg_nr)
);
CREATE TABLE lo3_pl_reis_doc (
    pl_id INTEGER NOT NULL,
    stapel_nr INTEGER NOT NULL,
    nl_reis_doc_soort VARCHAR(2),
    nl_reis_doc_nr VARCHAR(9),
    nl_reis_doc_uitgifte_datum INTEGER,
    nl_reis_doc_autoriteit_code VARCHAR(6),
    nl_reis_doc_geldig_eind_datum INTEGER,
    nl_reis_doc_weg_datum INTEGER,
    nl_reis_doc_weg_ind CHAR(1),
    nl_reis_doc_houder_lengte INTEGER,
    nl_reis_doc_signalering INTEGER,
    buitenland_reis_doc_aand INTEGER,
    doc_gemeente_code INTEGER,
    doc_datum INTEGER,
    doc_beschrijving VARCHAR(40),
    onderzoek_gegevens_aand INTEGER,
    onderzoek_start_datum INTEGER,
    onderzoek_eind_datum INTEGER,
    geldigheid_start_datum INTEGER,
    opneming_datum INTEGER,
    PRIMARY KEY (pl_id,stapel_nr)
);
CREATE TABLE lo3_pl_serialized (
    pl_id INTEGER NOT NULL,
    mutatie_dt TIMESTAMP NOT NULL,
    "data" VARCHAR(20000) NOT NULL,
    PRIMARY KEY (pl_id)
);
CREATE TABLE lo3_pl_verblijfplaats (
    pl_id INTEGER NOT NULL,
    volg_nr INTEGER NOT NULL,
    inschrijving_gemeente_code INTEGER,
    adres_id INTEGER,
    inschrijving_datum INTEGER,
    adres_functie CHAR(1),
    gemeente_deel VARCHAR(24),
    adreshouding_start_datum INTEGER,
    vertrek_land_code INTEGER,
    vertrek_datum INTEGER,
    vertrek_land_adres_1 VARCHAR(35),
    vertrek_land_adres_2 VARCHAR(35),
    vertrek_land_adres_3 VARCHAR(35),
    vestiging_land_code INTEGER,
    vestiging_datum INTEGER,
    aangifte_adreshouding_oms CHAR(1),
    doc_ind INTEGER,
    onderzoek_gegevens_aand INTEGER,
    onderzoek_start_datum INTEGER,
    onderzoek_eind_datum INTEGER,
    onjuist_ind CHAR(1),
    geldigheid_start_datum INTEGER,
    opneming_datum INTEGER,
    rni_deelnemer INTEGER,
    verdrag_oms VARCHAR(50),
    PRIMARY KEY (pl_id,volg_nr)
);
CREATE TABLE lo3_pl_verblijfstitel (
    pl_id INTEGER NOT NULL,
    volg_nr INTEGER NOT NULL,
    verblijfstitel_aand INTEGER,
    verblijfstitel_eind_datum INTEGER,
    verblijfstitel_start_datum INTEGER,
    onderzoek_gegevens_aand INTEGER,
    onderzoek_start_datum INTEGER,
    onderzoek_eind_datum INTEGER,
    onjuist_ind CHAR(1),
    geldigheid_start_datum INTEGER,
    opneming_datum INTEGER,
    PRIMARY KEY (pl_id,volg_nr)
);
CREATE TABLE lo3_relatie_eind_reden (
    relatie_eind_reden CHAR(1) NOT NULL,
    relatie_eind_reden_oms VARCHAR(80),
    tabel_regel_start_datum INTEGER,
    tabel_regel_eind_datum INTEGER,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (relatie_eind_reden)
);
CREATE TABLE lo3_rni_deelnemer (
    deelnemer_code INTEGER NOT NULL,
    deelnemer_oms VARCHAR(80) NOT NULL,
    tabel_regel_start_datum INTEGER,
    tabel_regel_eind_datum INTEGER,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (deelnemer_code)
);
CREATE TABLE lo3_rubriek_aut (
    autorisatie_id INTEGER NOT NULL,
    rubriek_aut_type CHAR(1) NOT NULL,
    rubriek_nr INTEGER NOT NULL,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (autorisatie_id,rubriek_aut_type,rubriek_nr)
);
CREATE TABLE lo3_titel_predikaat (
    titel_predikaat VARCHAR(2) NOT NULL,
    titel_predikaat_oms VARCHAR(10) NOT NULL,
    titel_predikaat_soort VARCHAR(10) NOT NULL,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (titel_predikaat)
);
CREATE TABLE lo3_verblijfstitel_aand (
    verblijfstitel_aand INTEGER NOT NULL,
    verblijfstitel_aand_oms VARCHAR(80) NOT NULL,
    tabel_regel_start_datum INTEGER,
    tabel_regel_eind_datum INTEGER,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (verblijfstitel_aand)
);
CREATE TABLE lo3_voorvoegsel (
    voorvoegsel VARCHAR(10) NOT NULL,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (voorvoegsel)
);
CREATE TABLE lo3_voorwaarde_regel_aut (
    voorwaarde_type CHAR(1) NOT NULL,
    voorwaarde_regel VARCHAR(4096) NOT NULL,
    autorisatie_id INTEGER NOT NULL,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (voorwaarde_type,autorisatie_id)
);
CREATE TABLE lo3_vospg_instructie (
    vospg_instructie_id INTEGER NOT NULL,
    spg_mailbox_instantie INTEGER NOT NULL,
    soort_instructie VARCHAR(40),
    dag_van_de_week INTEGER,
    tijd TIME,
    datum_tijd TIMESTAMP,
    tijdstip_laatste_actie TIMESTAMP,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (vospg_instructie_id)
);
CREATE TABLE lookup_codering (
    lookup_codering_id INTEGER NOT NULL,
    codering_naam VARCHAR(40) NOT NULL,
    code_type CHAR(1) NOT NULL,
    idc_vaste_codering CHAR(1) NOT NULL,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (lookup_codering_id)
);
CREATE TABLE lookup_codewaarde (
    lookup_codering_id INTEGER NOT NULL,
    referentie_waarde_num INTEGER,
    referentie_waarde_alfa VARCHAR(40),
    lookup_afkorting VARCHAR(20),
    lookup_omschrijving VARCHAR(40) NOT NULL,
    omschrijving_volledig VARCHAR(200),
    creatie_dt TIMESTAMP NOT NULL,
    mutatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (lookup_codering_id,lookup_omschrijving)
);
CREATE TABLE miteller (
    groep_id VARCHAR(8) NOT NULL,
    soort_teller INTEGER NOT NULL,
    aantal INTEGER NOT NULL,
    datum_telling DATE NOT NULL,
    periode INTEGER NOT NULL,
    periode_aanduiding VARCHAR(5) NOT NULL,
    PRIMARY KEY (soort_teller,groep_id,datum_telling,periode)
);
CREATE TABLE miteller_marker (
    marker_id VARCHAR(30) NOT NULL,
    marker INTEGER NOT NULL,
    PRIMARY KEY (marker_id)
);
--CREATE TABLE proefSynchronisatieBericht (
--    bericht_id,
--    afzender,
--    bericht_datum,
--    bericht,
--   ms_sequence_number
--) ;
CREATE TABLE spg_mailbox (
    spg_mailbox_instantie INTEGER NOT NULL,
    spg_mailbox_nummer VARCHAR(7) NOT NULL,
    PRIMARY KEY (spg_mailbox_instantie)
);
CREATE TABLE spg_schema (
    "version" VARCHAR(40) NOT NULL
);
CREATE TABLE toestand_overgang (
    toestand_overgang_id INTEGER NOT NULL,
    activiteit_type INTEGER NOT NULL,
    activiteit_subtype INTEGER,
    toestand_huidig INTEGER,
    gebeurtenis_type INTEGER NOT NULL,
    toestand_nieuw INTEGER NOT NULL,
    creatie_dt TIMESTAMP NOT NULL,
    PRIMARY KEY (toestand_overgang_id)
);

ALTER TABLE gebeurtenis
    ADD FOREIGN KEY (activiteit_id) 
    REFERENCES activiteit (activiteit_id);


ALTER TABLE gebeurtenis_data
    ADD FOREIGN KEY (gebeurtenis_id) 
    REFERENCES gebeurtenis (gebeurtenis_id);


ALTER TABLE lo3_bericht
    ADD FOREIGN KEY (bericht_activiteit_id) 
    REFERENCES activiteit (activiteit_id);


ALTER TABLE lo3_pl_verblijfplaats
    ADD FOREIGN KEY (adres_id) 
    REFERENCES lo3_adres (adres_id);


ALTER TABLE lo3_vospg_instructie
    ADD FOREIGN KEY (spg_mailbox_instantie) 
    REFERENCES spg_mailbox (spg_mailbox_instantie);


ALTER TABLE lookup_codewaarde
    ADD FOREIGN KEY (lookup_codering_id) 
    REFERENCES lookup_codering (lookup_codering_id);


create sequence lo3_pl_id_sequence start 1 increment by 1;
create sequence lo3_bericht_id_sequence start 1 increment by 1;
create sequence adres_id_sequence start 1 increment by 1;
create sequence activiteit_id_sequence start 1 increment by 1;
create sequence toestand_overgang_id_sequence start 1 increment by 1;
create sequence gebeurtenis_id_sequence start 1 increment by 1;
create sequence gebeurtenis_data_id_sequence start 1 increment by 1;
create sequence lo3_autorisatie_id_sequence start 1 increment by 1;
create sequence lo3_vospg_instructie_id_sequence start 1 increment by 1;
create sequence afnemer_id_sequence start 1 increment by 1;

INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (14, 102, 1305, null, 9021, '2013-01-22 14:46:04.522', '2013-01-22 14:46:04.529', '2013-01-22 14:46:04.522', null, '0599010', null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 14:46:04.522813');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (12, 100, 1111, 14, 8000, '2013-01-22 14:46:04.411', '2013-01-22 14:46:04.539', '2013-01-27 14:46:04.41', null, '0599010', null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 14:46:04.426194');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (13, 105, 1700, null, 8070, '2013-01-22 14:46:04.466', '2013-01-22 14:46:04.56', '2013-01-22 22:46:04.466', null, null, null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 14:46:04.471076');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (15, 101, 1202, 14, 5010, '2013-01-22 14:46:04.527', '2013-01-22 14:46:04.527', '2013-01-30 14:46:04.529', null, '0599010', null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 14:46:04.45746');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (19, 101, 1204, 18, 5010, '2013-01-22 15:21:07.615', '2013-01-22 15:21:07.615', '2013-01-30 15:21:07.617', null, '0599', null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 15:21:07.616132');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (16, 100, 1111, 18, 8000, '2013-01-22 15:21:06.622', '2013-01-22 15:21:07.632', '2013-01-27 15:21:06.622', null, '0599', null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 15:21:07.253199');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (18, 102, 1305, null, 9021, '2013-01-22 15:21:07.586', '2013-01-22 15:21:07.638', '2013-01-22 15:21:07.586', null, '0599', null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 15:21:07.586883');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (17, 105, 1700, null, 8070, '2013-01-22 15:21:07.287', '2013-01-22 15:21:07.661', '2013-01-22 23:21:07.287', null, null, null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 15:21:07.291544');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (23, 101, 1204, 22, 5010, '2013-01-22 15:32:59.523', '2013-01-22 15:32:59.523', '2013-01-30 15:32:59.525', null, '0599', null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 15:32:59.523853');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (20, 100, 1111, 22, 8000, '2013-01-22 15:32:58.543', '2013-01-22 15:32:59.535', '2013-01-27 15:32:58.542', null, '0599', null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 15:32:59.162942');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (22, 102, 1305, null, 9021, '2013-01-22 15:32:59.495', '2013-01-22 15:32:59.541', '2013-01-22 15:32:59.495', null, '0599', null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 15:32:59.495965');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (21, 105, 1700, null, 8070, '2013-01-22 15:32:59.197', '2013-01-22 15:32:59.56', '2013-01-22 23:32:59.197', null, null, null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 15:32:59.201371');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (27, 101, 1204, 26, 5010, '2013-01-22 15:52:39.169', '2013-01-22 15:52:39.169', '2013-01-30 15:52:39.171', null, '0599', null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 15:52:39.169772');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (24, 100, 1111, 26, 8000, '2013-01-22 15:52:37.944', '2013-01-22 15:52:39.181', '2013-01-27 15:52:37.943', null, '0599', null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 15:52:38.565561');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (26, 102, 1305, null, 9021, '2013-01-22 15:52:39.135', '2013-01-22 15:52:39.189', '2013-01-22 15:52:39.135', null, '0599', null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 15:52:39.136077');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (25, 105, 1700, null, 8070, '2013-01-22 15:52:38.823', '2013-01-22 15:52:39.209', '2013-01-22 23:52:38.823', null, null, null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 15:52:38.828382');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (31, 101, 1204, 30, 5010, '2013-01-22 15:55:47.756', '2013-01-22 15:55:47.756', '2013-01-30 15:55:47.758', null, '0599', null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 15:55:47.757288');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (28, 100, 1111, 30, 8000, '2013-01-22 15:55:46.758', '2013-01-22 15:55:47.769', '2013-01-27 15:55:46.758', null, '0599', null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 15:55:47.393161');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (30, 102, 1305, null, 9021, '2013-01-22 15:55:47.731', '2013-01-22 15:55:47.775', '2013-01-22 15:55:47.731', null, '0599', null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 15:55:47.73234');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (29, 105, 1700, null, 8070, '2013-01-22 15:55:47.427', '2013-01-22 15:55:47.795', '2013-01-22 23:55:47.427', null, null, null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 15:55:47.432182');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (32, 100, 1111, 34, 8000, '2013-01-22 16:19:28.607', '2013-01-22 16:19:29.746', '2013-01-27 16:19:28.606', 6, '0599', null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 16:19:29.233227');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (34, 102, 1305, null, 8022, '2013-01-22 16:19:29.568', '2013-01-22 16:19:29.76', '2013-01-22 16:19:29.568', null, '0599', null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 16:19:29.568651');
INSERT INTO activiteit(activiteit_id, activiteit_type, activiteit_subtype, moeder_id, toestand, start_dt, laatste_actie_dt, uiterlijke_actie_dt, pl_id, communicatie_partner, nr_1, nr_2, nr_3, nr_4, nr_5, tekst_1, tekst_2, tekst_3, tekst_4, tekst_5, trace_level, creatie_dt) VALUES (33, 105, 1700, null, 8070, '2013-01-22 16:19:29.269', '2013-01-22 16:19:29.787', '2013-01-23 00:19:29.269', null, null, null, null, null, null, null, null, null, null, null, null, null, '2013-01-22 16:19:29.273636');
INSERT INTO lo3_adres(adres_id, gemeente_code, gemeente_deel, straat_naam, diak_straat_naam, open_ruimte_naam, diak_open_ruimte_naam, huis_nr, huis_letter, huis_nr_toevoeging, huis_nr_aand, postcode, woon_plaats_naam, diak_woon_plaats_naam, locatie_beschrijving, diak_locatie_beschrijving, verblijf_plaats_ident_code, nummer_aand_ident_code, creatie_dt) VALUES (3, 599, '1901', 'Viooltjeslaan', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2013-01-09 17:51:26.572835');
INSERT INTO spg_mailbox(spg_mailbox_instantie, spg_mailbox_nummer) VALUES (1, '3000200');
INSERT INTO spg_mailbox(spg_mailbox_instantie, spg_mailbox_nummer) VALUES (2, '3000210');
INSERT INTO spg_mailbox(spg_mailbox_instantie, spg_mailbox_nummer) VALUES (3, '3000220');
INSERT INTO spg_mailbox(spg_mailbox_instantie, spg_mailbox_nummer) VALUES (4, '3000230');
INSERT INTO spg_mailbox(spg_mailbox_instantie, spg_mailbox_nummer) VALUES (5, '3000250');
INSERT INTO spg_mailbox(spg_mailbox_instantie, spg_mailbox_nummer) VALUES (6, '3000270');
INSERT INTO lo3_bericht(lo3_bericht_id, aanduiding_in_uit, bericht_activiteit_id, medium, originator_or_recipient, spg_mailbox_instantie, eref, bref, eref2, berichtcyclus_id, tijdstip_verzending_ontvangst, dispatch_sequence_number, report_delivery_time, non_delivery_reason, non_receipt_reason, bericht_data, kop_random_key, kop_berichtsoort_nummer, kop_a_nummer, kop_oud_a_nummer, kop_herhaling, kop_foutreden, kop_datum_tijd, creatie_dt) VALUES (15, 'I', 32, 'N', '0599010', 1, null, 'test', null, null, '2013-01-22 16:19:28.599', 1234, null, null, null, '00000000Lg01201301221619285991010101025000000000000765011970110010101010102501200096947984590210013Peter Richard0220002JH0230007van den0240006Heuvel03100081990011003200040599033000460300410001M6110001E8110004059981200071 A1284851000819900110861000819900112021800110010101010129101200095859039060210006Hennie0240012Meerdervoort03100081949020203200041900033000460300410001V6210008199001108110004059981200071 A1284851000819900110861000819900112031870110010101010132701200094647322190210005Harry0230007van den0240006Heuvel03100081951010103200041901033000460300410001M6210008199001108110004059981200071 A128485100081990011086100081990011207058681000819900110701000108010004000180200171990011000000000008118091000405990920008199001101010001W102000419011030008199001101110013Viooltjeslaan7210001I851000819900110861000819900112', 0, 'Lg01', 1010101025, 0, null, null, null, '2013-01-22 16:19:29.245');
INSERT INTO lo3_pl(pl_id, pl_blokkering_start_datum, bijhouding_opschort_datum, bijhouding_opschort_reden, gba_eerste_inschrijving_datum, pk_gemeente_code, geheim_ind, verificatie_datum, verificatie_oms, versie_nr, stempel_dt, volledig_geconverteerd_pk, rni_deelnemer, verdrag_oms, europees_kiesrecht_aand, europees_kiesrecht_datum, europees_uitsluit_eind_datum, kiesrecht_uitgesl_aand, kiesrecht_uitgesl_eind_datum, kiesrecht_doc_gemeente_code, kiesrecht_doc_datum, kiesrecht_doc_beschrijving, mutatie_activiteit_id, creatie_dt, mutatie_dt) VALUES (6, null, null, null, 19900110, null, 0, null, null, 1, 19900110000000000, null, null, null, null, null, null, null, null, null, null, null, 32, '2013-01-09 17:51:26.709122', '2013-01-22 16:19:28.599');
INSERT INTO lo3_pl_persoon(pl_id, persoon_type, stapel_nr, volg_nr, a_nr, burger_service_nr, voor_naam, diak_voor_naam, titel_predikaat, geslachts_naam_voorvoegsel, geslachts_naam, diak_geslachts_naam, geboorte_datum, geboorte_plaats, geboorte_land_code, geslachts_aand, vorig_a_nr, volgend_a_nr, naam_gebruik_aand, akte_register_gemeente_code, akte_nr, doc_gemeente_code, doc_datum, doc_beschrijving, onderzoek_gegevens_aand, onderzoek_start_datum, onderzoek_eind_datum, onjuist_ind, geldigheid_start_datum, opneming_datum, rni_deelnemer, verdrag_oms, relatie_start_datum, relatie_start_plaats, relatie_start_land_code, relatie_eind_datum, relatie_eind_plaats, relatie_eind_land_code, relatie_eind_reden, verbintenis_soort, familie_betrek_start_datum) VALUES (6, 'P', 0, 0, 1010101025, 694798459, 'Peter Richard', null, 'JH', 'van den', 'Heuvel', null, 19900110, '0599', 6030, 'M', null, null, 'E', 599, '1 A1284', null, null, null, null, null, null, null, 19900110, 19900112, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO lo3_pl_persoon(pl_id, persoon_type, stapel_nr, volg_nr, a_nr, burger_service_nr, voor_naam, diak_voor_naam, titel_predikaat, geslachts_naam_voorvoegsel, geslachts_naam, diak_geslachts_naam, geboorte_datum, geboorte_plaats, geboorte_land_code, geslachts_aand, vorig_a_nr, volgend_a_nr, naam_gebruik_aand, akte_register_gemeente_code, akte_nr, doc_gemeente_code, doc_datum, doc_beschrijving, onderzoek_gegevens_aand, onderzoek_start_datum, onderzoek_eind_datum, onjuist_ind, geldigheid_start_datum, opneming_datum, rni_deelnemer, verdrag_oms, relatie_start_datum, relatie_start_plaats, relatie_start_land_code, relatie_eind_datum, relatie_eind_plaats, relatie_eind_land_code, relatie_eind_reden, verbintenis_soort, familie_betrek_start_datum) VALUES (6, '1', 0, 0, 1010101291, 585903906, 'Hennie', null, null, null, 'Meerdervoort', null, 19490202, '1900', 6030, 'V', null, null, null, 599, '1 A1284', null, null, null, null, null, null, null, 19900110, 19900112, null, null, null, null, null, null, null, null, null, null, 19900110);
INSERT INTO lo3_pl_persoon(pl_id, persoon_type, stapel_nr, volg_nr, a_nr, burger_service_nr, voor_naam, diak_voor_naam, titel_predikaat, geslachts_naam_voorvoegsel, geslachts_naam, diak_geslachts_naam, geboorte_datum, geboorte_plaats, geboorte_land_code, geslachts_aand, vorig_a_nr, volgend_a_nr, naam_gebruik_aand, akte_register_gemeente_code, akte_nr, doc_gemeente_code, doc_datum, doc_beschrijving, onderzoek_gegevens_aand, onderzoek_start_datum, onderzoek_eind_datum, onjuist_ind, geldigheid_start_datum, opneming_datum, rni_deelnemer, verdrag_oms, relatie_start_datum, relatie_start_plaats, relatie_start_land_code, relatie_eind_datum, relatie_eind_plaats, relatie_eind_land_code, relatie_eind_reden, verbintenis_soort, familie_betrek_start_datum) VALUES (6, '2', 0, 0, 1010101327, 464732219, 'Harry', null, null, 'van den', 'Heuvel', null, 19510101, '1901', 6030, 'M', null, null, null, 599, '1 A1284', null, null, null, null, null, null, null, 19900110, 19900112, null, null, null, null, null, null, null, null, null, null, 19900110);
INSERT INTO lo3_pl_verblijfplaats(pl_id, volg_nr, inschrijving_gemeente_code, adres_id, inschrijving_datum, adres_functie, gemeente_deel, adreshouding_start_datum, vertrek_land_code, vertrek_datum, vertrek_land_adres_1, vertrek_land_adres_2, vertrek_land_adres_3, vestiging_land_code, vestiging_datum, aangifte_adreshouding_oms, doc_ind, onderzoek_gegevens_aand, onderzoek_start_datum, onderzoek_eind_datum, onjuist_ind, geldigheid_start_datum, opneming_datum, rni_deelnemer, verdrag_oms) VALUES (6, 0, 599, 3, 19900110, 'W', '1901', 19900110, null, null, null, null, null, null, null, 'I', null, null, null, null, null, 19900110, 19900112, null, null);
--INSERT INTO proefSynchronisatieBericht(bericht_id,afzender,bericht_datum, bericht, ms_sequence_number) VALUES (1010, 599, '2013-01-22 16:19:28.599', '00000000Lg01201301221619285991010101025000000000000765011970110010101010102501200096947984590210013Peter Richard0220002JH0230007van den0240006Heuvel03100081990011003200040599033000460300410001M6110001E8110004059981200071 A1284851000819900110861000819900112021800110010101010129101200095859039060210006Hennie0240012Meerdervoort03100081949020203200041900033000460300410001V6210008199001108110004059981200071 A1284851000819900110861000819900112031870110010101010132701200094647322190210005Harry0230007van den0240006Heuvel03100081951010103200041901033000460300410001M6210008199001108110004059981200071 A128485100081990011086100081990011207058681000819900110701000108010004000180200171990011000000000008118091000405990920008199001101010001W102000419011030008199001101110013Viooltjeslaan7210001I851000819900110861000819900112', 1234);
