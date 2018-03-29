--
-- GBA-V lo3 database schema
--  Dit script bevat alleen de minimale indexen
--  Voor een volledig schema draai ook het script create_lo3_indexes.sql
--
-- IMPORTANT NOTICE!
--	Each change to the database schema requires an updated version number on delivery.
--	E.g. use the version of the formally delivered software!
--

create table spg_schema(
	version					varchar(40) NOT NULL );
insert into spg_schema values ('7.8.0');

create table SPG_mailbox(
       spg_mailbox_instantie                      integer not null,
       spg_mailbox_nummer                         varchar(7) not null
);
alter table SPG_mailbox add
    constraint SPG_mailbox_pl primary key (spg_mailbox_instantie);
create unique index SPG_mailbox_nr on SPG_mailbox (spg_mailbox_nummer);

insert into spg_mailbox(spg_mailbox_instantie, spg_mailbox_nummer) values (1, '3000200');
insert into spg_mailbox(spg_mailbox_instantie, spg_mailbox_nummer) values (2, '3000210');
insert into spg_mailbox(spg_mailbox_instantie, spg_mailbox_nummer) values (3, '3000220');
insert into spg_mailbox(spg_mailbox_instantie, spg_mailbox_nummer) values (4, '3000230');
insert into spg_mailbox(spg_mailbox_instantie, spg_mailbox_nummer) values (5, '3000250');
insert into spg_mailbox(spg_mailbox_instantie, spg_mailbox_nummer) values (6, '3000270');

create table lo3_adres(
          adres_id                                bigint not null,
          gemeente_code                           smallint not null,
          gemeente_deel                           varchar(24),
          straat_naam                             varchar(24),
          diak_straat_naam                        varchar(24),
          open_ruimte_naam                        varchar(80),
          diak_open_ruimte_naam                   varchar(80),
          huis_nr                                 integer,
          huis_letter                             character(1),
          huis_nr_toevoeging                      varchar(4),
          huis_nr_aand                            varchar(2),
          postcode                                varchar(6),
          woon_plaats_naam                        varchar(80),
          diak_woon_plaats_naam                   varchar(80),
          locatie_beschrijving                    varchar(35),
          diak_locatie_beschrijving               varchar(35),
          verblijf_plaats_ident_code              varchar(16),
          nummer_aand_ident_code                  varchar(16),
          creatie_dt                              timestamp not null default now());

alter table lo3_adres add
    constraint lo3_adres_pk primary key 
    	(adres_id);
    	
-- Index om unieke adres records af te dwingen
-- Gebruik TAB teken omdat deze niet in de GBA tekenset voor mag komen
--create unique index lo3_adres_idx_0 on lo3_adres(
--	gemeente_code,
--	coalesce(gemeente_deel, '	'),
--	coalesce(straat_naam, '	'),
--	coalesce(diak_straat_naam, '	'),
--	coalesce(open_ruimte_naam, '	'),
--	coalesce(diak_open_ruimte_naam, '	'),
--	coalesce(huis_nr, 666666),
--	coalesce(huis_letter, '	'),
--	coalesce(huis_nr_toevoeging, '	'),
--	coalesce(huis_nr_aand, '	'),
--	coalesce(postcode, '	'),
--	coalesce(woon_plaats_naam, '	'),
--	coalesce(diak_woon_plaats_naam, '	'),
--	coalesce(locatie_beschrijving, '	'),
--	coalesce(diak_locatie_beschrijving, '	'),
--	coalesce(verblijf_plaats_ident_code, '	'),
--	coalesce(nummer_aand_ident_code, '	')
--	);

-- Bij indexen rekening houden met LRD
-- Zoeken via postcode
-- Wordt ook gebruikt bij de initiele vulling van een kopie database
--create index lo3_adres_idx_1 on lo3_adres(
--	lower(postcode) text_pattern_ops,
--	huis_nr,
--	huis_letter,
--	huis_nr_toevoeging);

-- Draai dit script nadat met 
-- zoeken via gemeente
--create index lo3_adres_idx_2 on lo3_adres(
--    gemeente_code,
--    lower(straat_naam) text_pattern_ops,
--    huis_nr,
--    huis_letter,
--    huis_nr_toevoeging);
    
-- zoeken via straatnaam
--create index lo3_adres_idx_3 on lo3_adres(
--    lower(straat_naam) text_pattern_ops,
--    huis_nr,
--    huis_letter,
--    huis_nr_toevoeging);

-- zoeken via locatie_beschrijving
--create index lo3_adres_idx_4 on lo3_adres
--    (lower(locatie_beschrijving) text_pattern_ops);
    
	
create table lo3_adres_afnemer_ind(
          adres_id                                bigint not null,
          afnemer_code                            integer not null,
          creatie_dt                              timestamp not null default now());
alter table lo3_adres_afnemer_ind add
    constraint lo3_adres_afnemer_ind_pk primary key (adres_id,afnemer_code);

create unique index lo3_adres_afnemer_ind_idx_1 on lo3_adres_afnemer_ind(afnemer_code, adres_id);

create table lo3_afnemers_verstrekking_aut(
          autorisatie_id                          bigint not null,
          afnemer_code                            integer not null,
          creatie_dt                              timestamp not null default now());
alter table lo3_afnemers_verstrekking_aut add
    constraint lo3_afnemers_verstrekking_aut_pk primary key (autorisatie_id,afnemer_code);

create table lo3_akte_aand(
          akte_aand                               varchar(3) not null,
          akte_soort_oms                          varchar(80) not null,
          creatie_dt                              timestamp not null default now());
alter table lo3_akte_aand add
    constraint lo3_akte_aand_pk primary key (akte_aand);

create table afnemer(
		afnemer_id									bigint	not null,
		afnemer_code								integer	not null,
		oin									VARCHAR(20),
		geschatte_omvang							INTEGER NOT NULL DEFAULT 0,
		leverwijze									INTEGER NOT NULL DEFAULT 0,
		max_berichten								INTEGER NOT NULL DEFAULT 0,
		berichtsoort								INTEGER,
		selectiesoort								INTEGER NOT NULL DEFAULT 0,
		bestands_formaat							INTEGER,
		constraint afnemer_pk primary key (afnemer_id)
);

create unique index afnemer_idx_0 on afnemer (afnemer_code);
create unique index afnemer_idx_1 on afnemer (oin);

create table lo3_autorisatie(
          autorisatie_id                          bigint not null,
          afnemer_code                            integer not null,
          geheimhouding_ind                       smallint not null,
          verstrekkings_beperking                 smallint not null,
          afnemer_naam                            varchar(80),
          straat_naam                             varchar(24),
          huis_nr                                 integer,
          huis_letter                             character(1),
          huis_nr_toevoeging                      varchar(4),
          postcode                                varchar(6),
          gemeente_code                           smallint,
          conditionele_verstrekking               smallint,
          spontaan_medium                         character(1),
          selectie_soort                          smallint,
          bericht_aand                            smallint,
          eerste_selectie_datum                   integer,
          selectie_periode                        smallint,
          selectie_medium                         character(1),
          pl_plaatsings_bevoegdheid               smallint,
          adres_vraag_bevoegdheid                 smallint,
          ad_hoc_medium                           character(1),
          adres_medium                            character(1),
          tabel_regel_start_datum                 integer,
          tabel_regel_eind_datum                  integer,
          sleutel_rubrieken                       text,
          spontaan_rubrieken                      text,
          selectie_rubrieken                      text,
          ad_hoc_rubrieken                        text,
          adres_rubrieken                         text,
          afnemers_verstrekkingen                 text,
          creatie_dt                              timestamp not null default now());
alter table lo3_autorisatie add
    constraint lo3_autorisatie_pk primary key (autorisatie_id);

create unique index lo3_autorisatie_idx_0 on lo3_autorisatie (afnemer_code, tabel_regel_start_datum);

create table lo3_gemeente(
          gemeente_code                           smallint not null,
          gemeente_naam                           varchar(40) not null,
          nieuwe_gemeente_code                    smallint,
          tabel_regel_start_datum                 integer,
          tabel_regel_eind_datum                  integer,
          creatie_dt                              timestamp not null default now());
alter table lo3_gemeente add
    constraint lo3_gemeente_pk primary key (gemeente_code);

create table lo3_land(
          land_code                               smallint not null,
          land_naam                               varchar(42) not null,
          tabel_regel_start_datum                 integer,
          tabel_regel_eind_datum                  integer,
          creatie_dt                              timestamp not null default now());
alter table lo3_land add
    constraint lo3_land_pk primary key (land_code);

create table lo3_categorie(
          categorie_nr                            smallint not null,
          categorie_naam                          varchar(40) not null,
          categorie_oms                           varchar(80),
          creatie_dt                              timestamp not null default now());
alter table lo3_categorie add
    constraint lo3_categorie_pk primary key (categorie_nr);

create table lo3_categorie_groep(
          categorie_nr                            smallint not null,
          groep_nr                                smallint not null,
          creatie_dt                              timestamp not null default now());
alter table lo3_categorie_groep add
    constraint lo3_categorie_groep_pk primary key (categorie_nr,groep_nr);

create table lo3_element(
          element_nr                              smallint not null,
          element_naam                            varchar(40) not null,
          element_oms                             varchar(80),
          groep_nr                                smallint not null,
          creatie_dt                              timestamp not null default now());
alter table lo3_element add
    constraint lo3_rubriek_pk primary key (element_nr);

create table lo3_gba_deelnemer(
          deelnemer_code                          integer       not null,
          deelnemer_oms                           varchar(80)   not null,
          gba_netwerk_aand                        smallint,
          tabel_regel_start_datum                 integer,
          tabel_regel_eind_datum                  integer,
          creatie_dt                              timestamp not null default now());
alter table lo3_gba_deelnemer add
    constraint lo3_gba_deelnemer_pk primary key (deelnemer_code);

create table lo3_groep(
          groep_nr                                smallint not null,
          groep_naam                              varchar(40) not null,
          groep_oms                               varchar(80),
          creatie_dt                              timestamp not null default now());
alter table lo3_groep add
    constraint lo3_groep_pk primary key (groep_nr);

create table lo3_nationaliteit(
          nationaliteit_code                      smallint not null,
          nationaliteit_oms                       varchar(42) not null,
          tabel_regel_start_datum                 integer,
          tabel_regel_eind_datum                  integer,
          creatie_dt                              timestamp not null default now());
alter table lo3_nationaliteit add
    constraint lo3_nationaliteit_pk primary key (nationaliteit_code);

create table lo3_nl_nat_verkrijg_verlies_reden(
          nl_nat_verkrijg_verlies_reden           smallint not null,
          nl_nat_reden_oms                        varchar(80) not null,
          nl_nat_reden_soort                      varchar(2) not null,
          tabel_regel_start_datum                 integer,
          tabel_regel_eind_datum                  integer,
          creatie_dt                              timestamp not null default now());
alter table lo3_nl_nat_verkrijg_verlies_reden add
    constraint lo3_nl_nat_verkrijg_verlies_reden_pk primary key (nl_nat_verkrijg_verlies_reden);

create table lo3_nl_reis_doc_autoriteit(
          nl_reis_doc_autoriteit_code             varchar(6) not null,
          nl_reis_doc_autoriteit_oms              varchar(80) not null,
          tabel_regel_start_datum                 integer,
          tabel_regel_eind_datum                  integer,
          creatie_dt                              timestamp not null default now());
alter table lo3_nl_reis_doc_autoriteit add
    constraint lo3_nl_reis_doc_autoriteit_pk primary key (nl_reis_doc_autoriteit_code);

create table lo3_nl_reis_doc_soort(
          nl_reis_doc_soort                       varchar(2) not null,
          nl_reis_doc_soort_oms                   varchar(80) not null,
          tabel_regel_start_datum                 integer,
          tabel_regel_eind_datum                  integer,
          creatie_dt                              timestamp not null default now());
alter table lo3_nl_reis_doc_soort add
    constraint lo3_nl_reis_doc_soort_pk primary key (nl_reis_doc_soort);

create table lo3_pl(
          pl_id                                   bigint not null,
          pl_blokkering_start_datum               integer,
          bijhouding_opschort_datum               integer,
          bijhouding_opschort_reden               character(1),
          gba_eerste_inschrijving_datum           integer,
          pk_gemeente_code                        smallint,
          geheim_ind                              smallint,
          verificatie_datum                       integer,
          verificatie_oms                         varchar(50),
          versie_nr                               smallint,
          stempel_dt                              bigint,
          volledig_geconverteerd_pk               character(1),
          rni_deelnemer                           smallint,
          verdrag_oms                             varchar(50),
          europees_kiesrecht_aand                 smallint,
          europees_kiesrecht_datum                integer,
          europees_uitsluit_eind_datum            integer,
          kiesrecht_uitgesl_aand                  character(1),
          kiesrecht_uitgesl_eind_datum            integer,
          kiesrecht_doc_gemeente_code             smallint,
          kiesrecht_doc_datum                     integer,
          kiesrecht_doc_beschrijving              varchar(40),
          mutatie_activiteit_id                   bigint,
          creatie_dt                              timestamp not null default now(),
          mutatie_dt                              timestamp not null);
alter table lo3_pl add
    constraint lo3_pl_pk primary key (pl_id);

create table lo3_pl_afnemer_ind(
          pl_id                                   bigint not null,
          stapel_nr                               smallint not null,
          volg_nr                                 smallint not null,
          afnemer_code                            integer,
          geldigheid_start_datum                  integer);
alter table lo3_pl_afnemer_ind add
    constraint lo3_pl_afnemer_ind_pk primary key (pl_id,stapel_nr,volg_nr);

create index lo3_pl_afnemer_ind_idx_1 on lo3_pl_afnemer_ind(afnemer_code, geldigheid_start_datum);

create table lo3_pl_gezagsverhouding(
          pl_id                                   bigint not null,
          volg_nr                                 smallint not null,
          minderjarig_gezag_ind                   varchar(2),
          curatele_register_ind                   smallint,
          doc_gemeente_code                       smallint,
          doc_datum                               integer,
          doc_beschrijving                        varchar(40),
          onderzoek_gegevens_aand                 integer,
          onderzoek_start_datum                   integer,
          onderzoek_eind_datum                    integer,
          onjuist_ind                             character(1),
          geldigheid_start_datum                  integer,
          opneming_datum                          integer);
alter table lo3_pl_gezagsverhouding add
    constraint lo3_pl_gezagsverhouding_pk primary key (pl_id,volg_nr);

create table lo3_pl_nationaliteit(
          pl_id                                   bigint not null,
          stapel_nr                               smallint not null,
          volg_nr                                 smallint not null,
          nationaliteit_code                      smallint,
          nl_nat_verkrijg_reden                   smallint,
          nl_nat_verlies_reden                    smallint,
          bijzonder_nl_aand                       character(1),
          doc_gemeente_code                       smallint,
          doc_datum                               integer,
          doc_beschrijving                        varchar(40),
          onderzoek_gegevens_aand                 integer,
          onderzoek_start_datum                   integer,
          onderzoek_eind_datum                    integer,
          onjuist_ind                             character(1),
          geldigheid_start_datum                  integer,
          opneming_datum                          integer,
          rni_deelnemer                           smallint,
          verdrag_oms                             varchar(50));
alter table lo3_pl_nationaliteit add
    constraint lo3_pl_nationaliteit_pk primary key (pl_id,stapel_nr,volg_nr);

create table lo3_pl_overlijden(
          pl_id                                   bigint not null,
          volg_nr                                 smallint not null,
          overlijden_datum                        integer,
          overlijden_plaats                       varchar(40),
          overlijden_land_code                    smallint,
          akte_register_gemeente_code             smallint,
          akte_nr                                 varchar(7),
          doc_gemeente_code                       smallint,
          doc_datum                               integer,
          doc_beschrijving                        varchar(40),
          onderzoek_gegevens_aand                 integer,
          onderzoek_start_datum                   integer,
          onderzoek_eind_datum                    integer,
          onjuist_ind                             character(1),
          geldigheid_start_datum                  integer,
          opneming_datum                          integer,
          rni_deelnemer                           smallint,
          verdrag_oms                             varchar(50));
alter table lo3_pl_overlijden add
    constraint lo3_pl_overlijden_pk primary key (pl_id,volg_nr);

create table lo3_pl_persoon(
          pl_id                                   bigint not null,
          persoon_type                            character(1) not null,
          stapel_nr                               smallint not null,
          volg_nr                                 smallint not null,
          a_nr                                    bigint,
          burger_service_nr                       bigint,
          voor_naam                               varchar(200),
          diak_voor_naam                          varchar(200),
          titel_predikaat                         varchar(2),
          geslachts_naam_voorvoegsel              varchar(10),
          geslachts_naam                          varchar(200),
          diak_geslachts_naam                     varchar(200),
          geboorte_datum                          integer,
          geboorte_plaats                         varchar(40),
          geboorte_land_code                      smallint,
          geslachts_aand                          character(1),
          vorig_a_nr                              bigint,
          volgend_a_nr                            bigint,
          naam_gebruik_aand                       character(1),
          akte_register_gemeente_code             smallint,
          akte_nr                                 varchar(7),
          doc_gemeente_code                       smallint,
          doc_datum                               integer,
          doc_beschrijving                        varchar(40),
          onderzoek_gegevens_aand                 integer,
          onderzoek_start_datum                   integer,
          onderzoek_eind_datum                    integer,
          onjuist_ind                             character(1),
          geldigheid_start_datum                  integer,
          opneming_datum                          integer,
          rni_deelnemer                           smallint,
          verdrag_oms                             varchar(50),
          relatie_start_datum                     integer,
          relatie_start_plaats                    varchar(40),
          relatie_start_land_code                 smallint,
          relatie_eind_datum                      integer,
          relatie_eind_plaats                     varchar(40),
          relatie_eind_land_code                  smallint,
          relatie_eind_reden                      character(1),
          verbintenis_soort                       character(1),
          familie_betrek_start_datum              integer);
alter table lo3_pl_persoon add
    constraint lo3_pl_persoon_pk primary key (pl_id,persoon_type,stapel_nr,volg_nr);

--create unique index lo3_pl_persoon_idx_0 on lo3_pl_persoon(a_nr) where persoon_type = 'P' and stapel_nr = 0 and volg_nr = 0;
create index lo3_pl_persoon_idx_1 on lo3_pl_persoon(a_nr, persoon_type, stapel_nr, volg_nr);
create index lo3_pl_persoon_idx_2 on lo3_pl_persoon(burger_service_nr, persoon_type, stapel_nr, volg_nr);
--create index lo3_pl_persoon_idx_3 on lo3_pl_persoon(lower(geslachts_naam) text_pattern_ops, persoon_type, stapel_nr, volg_nr, lower(voor_naam) text_pattern_ops);
--create index lo3_pl_persoon_idx_4 on lo3_pl_persoon(geboorte_datum, lower(geslachts_naam) text_pattern_ops, persoon_type, stapel_nr, volg_nr, lower(voor_naam) text_pattern_ops);

-- Afgeleide tabel ter ondersteuning van de indexering van combinaties van persoons- en adresgegevens
--
-- Opm: Deze tabel is de tweede versie die de originele versie 'lo3_pl_paw_index' vervangt.
--      Er is gekozen voor een gewijzigde naam, opdat beide versies tijdens de upgrade konden coexisteren.
--	    De suffix _2 kan alleen worden weggehaald als ook de overige programmatuur is aangepast.
create table lo3_pl_paw_index_2(
          pl_id bigint not null,
          voor_naam varchar(200),
          diak_voor_naam varchar(200),
          geslachts_naam varchar(200) not null,
          diak_geslachts_naam varchar(200),
          titel_predikaat varchar(2),
		  geslachts_naam_voorvoegsel varchar(10),
          geboorte_datum integer,
          geboorte_plaats varchar(40),
          geboorte_land_code smallint,
          geslachts_aand char(1),
          naam_gebruik_aand char(1),
          inschrijving_gemeente_code smallint not null,
          gemeente_deel varchar(24),
          straat_naam varchar(24),
          diak_straat_naam varchar(24),
          open_ruimte_naam                        varchar(80),
          diak_open_ruimte_naam                   varchar(80),
          huis_nr integer,
          huis_letter char(1),
          huis_nr_toevoeging varchar(4),
          huis_nr_aand varchar(2),
          postcode varchar(6),
          woon_plaats_naam                        varchar(80),
          diak_woon_plaats_naam                   varchar(80),
          locatie_beschrijving varchar(35),
          diak_locatie_beschrijving varchar(35),
          verblijf_plaats_ident_code              varchar(16),
          nummer_aand_ident_code                  varchar(16));
alter table lo3_pl_paw_index_2 add
    constraint lo3_pl_paw_index_2_pk primary key (pl_id);

--create index lo3_pl_paw_index_2_idx_1 on lo3_pl_paw_index_2(lower(geslachts_naam) text_pattern_ops, inschrijving_gemeente_code, lower(straat_naam) text_pattern_ops);
--create index lo3_pl_paw_index_2_idx_2 on lo3_pl_paw_index_2(lower(postcode) text_pattern_ops, lower(geslachts_naam) text_pattern_ops);
--create index lo3_pl_paw_index_2_idx_3 on lo3_pl_paw_index_2(geboorte_datum, lower(postcode) text_pattern_ops);
--create index lo3_pl_paw_index_2_idx_4 on lo3_pl_paw_index_2(lower(straat_naam) text_pattern_ops, lower(geslachts_naam) text_pattern_ops);

create table lo3_pl_reis_doc(
          pl_id                                   bigint not null,
          stapel_nr                               smallint not null,
          nl_reis_doc_soort                       varchar(2),
          nl_reis_doc_nr                          varchar(9),
          nl_reis_doc_uitgifte_datum              integer,
          nl_reis_doc_autoriteit_code             varchar(6),
          nl_reis_doc_geldig_eind_datum           integer,
          nl_reis_doc_weg_datum                   integer,
          nl_reis_doc_weg_ind                     character(1),
          nl_reis_doc_houder_lengte               smallint,
          nl_reis_doc_signalering                 smallint,
          buitenland_reis_doc_aand                smallint,
          doc_gemeente_code                       smallint,
          doc_datum                               integer,
          doc_beschrijving                        varchar(40),
          onderzoek_gegevens_aand                 integer,
          onderzoek_start_datum                   integer,
          onderzoek_eind_datum                    integer,
          geldigheid_start_datum                  integer,
          opneming_datum                          integer);
alter table lo3_pl_reis_doc add
    constraint lo3_pl_reis_doc_pk primary key (pl_id,stapel_nr);

create table lo3_pl_verblijfplaats(
          pl_id                                   bigint not null,
          volg_nr                                 smallint not null,
          inschrijving_gemeente_code              smallint,
          adres_id                                bigint   references lo3_adres (adres_id),
          inschrijving_datum                      integer,
          adres_functie                           character(1),
          gemeente_deel                           varchar(24),
          adreshouding_start_datum                integer,
          vertrek_land_code                       smallint,
          vertrek_datum                           integer,
          vertrek_land_adres_1                    varchar(35),
          vertrek_land_adres_2                    varchar(35),
          vertrek_land_adres_3                    varchar(35),
          vestiging_land_code                     smallint,
          vestiging_datum                         integer,
          aangifte_adreshouding_oms               character(1),
          doc_ind                                 smallint,
          onderzoek_gegevens_aand                 integer,
          onderzoek_start_datum                   integer,
          onderzoek_eind_datum                    integer,
          onjuist_ind                             character(1),
          geldigheid_start_datum                  integer,
          opneming_datum                          integer,
          rni_deelnemer                           smallint,
          verdrag_oms                             varchar(50));
alter table lo3_pl_verblijfplaats add
    constraint lo3_pl_verblijfplaats_pk primary key (pl_id,volg_nr);

create index lo3_pl_verblijfplaats_idx_1 on lo3_pl_verblijfplaats(inschrijving_gemeente_code, volg_nr, geldigheid_start_datum);
create index lo3_pl_verblijfplaats_idx_2 on lo3_pl_verblijfplaats(adres_id, volg_nr, geldigheid_start_datum);

create table lo3_pl_verblijfstitel(
          pl_id                                   bigint not null,
          volg_nr                                 smallint not null,
          verblijfstitel_aand                     smallint,
          verblijfstitel_eind_datum               integer,
          verblijfstitel_start_datum              integer,
          onderzoek_gegevens_aand                 integer,
          onderzoek_start_datum                   integer,
          onderzoek_eind_datum                    integer,
          onjuist_ind                             character(1),
          geldigheid_start_datum                  integer,
          opneming_datum                          integer);
alter table lo3_pl_verblijfstitel add
    constraint lo3_pl_verblijfstitel_pk primary key (pl_id,volg_nr);

create table lo3_pl_serialized (
          pl_id                                   bigint not null,
          mutatie_dt                              timestamp not null default now(),
          data                                    text not null);
alter table lo3_pl_serialized add
    constraint lo3_pl_serialized_pk primary key (pl_id);

create index lo3_pl_serialized_idx_1 on lo3_pl_serialized(mutatie_dt);

create table lo3_relatie_eind_reden(
          relatie_eind_reden                      character(1) not null,
          relatie_eind_reden_oms                  varchar(80),
          tabel_regel_start_datum                 integer,
          tabel_regel_eind_datum                  integer,
          creatie_dt                              timestamp not null default now());
alter table lo3_relatie_eind_reden add
    constraint lo3_relatie_eind_reden_pk primary key (relatie_eind_reden);

create table lo3_rni_deelnemer(
          deelnemer_code                          smallint      not null,
          deelnemer_oms                           varchar(80)   not null,
          tabel_regel_start_datum                 integer,
          tabel_regel_eind_datum                  integer,
          creatie_dt                              timestamp not null default now());
alter table lo3_rni_deelnemer add
    constraint lo3_rni_deelnemer_pk primary key (deelnemer_code);

create table lo3_rubriek_aut(
          autorisatie_id                          bigint not null,
          rubriek_aut_type                        character(1) not null,
          rubriek_nr                              integer not null,
          creatie_dt                              timestamp not null default now());
alter table lo3_rubriek_aut add
    constraint lo3_rubriek_aut_pk primary key (autorisatie_id, rubriek_aut_type, rubriek_nr);

create table lo3_titel_predikaat(
          titel_predikaat                         varchar(2)  not null,
          titel_predikaat_oms                     varchar(10) not null,
          titel_predikaat_soort                   varchar(10) not null,
          creatie_dt                              timestamp not null default now());
alter table lo3_titel_predikaat add
    constraint lo3_titel_predikaat_pk primary key (titel_predikaat);

create table lo3_verblijfstitel_aand(
          verblijfstitel_aand                     smallint      not null,
          verblijfstitel_aand_oms                 varchar(80)   not null,
          tabel_regel_start_datum                 integer,
          tabel_regel_eind_datum                  integer,
          creatie_dt                              timestamp not null default now());
alter table lo3_verblijfstitel_aand add
    constraint verblijfstitel_aand_pk primary key (verblijfstitel_aand);

create table lo3_voorvoegsel(
          voorvoegsel                             varchar(10) not null,
          creatie_dt                              timestamp not null default now());
alter table lo3_voorvoegsel add
    constraint lo3_voorvoegsel_pk primary key (voorvoegsel);

create table lo3_voorwaarde_regel_aut(
          voorwaarde_type                         character(1)  not null,
          voorwaarde_regel                        varchar(4096) not null,
          autorisatie_id                          bigint not null,
          creatie_dt                              timestamp not null default now());
alter table lo3_voorwaarde_regel_aut add
    constraint lo3_voorwaarde_regel_aut_pk primary key (voorwaarde_type,autorisatie_id);

create table activiteit(
          activiteit_id                           bigint        not null,
          activiteit_type                         integer       not null,
          activiteit_subtype                      integer	not null,
          moeder_id                               bigint,
          toestand                                integer       not null,
          start_dt                                timestamp     not null default now(),
          laatste_actie_dt                        timestamp,
          uiterlijke_actie_dt                     timestamp     not null,
          pl_id                                   bigint,
          communicatie_partner                    varchar(40),
          nr_1                                    bigint,
          nr_2                                    bigint,
          nr_3                                    bigint,
          nr_4                                    bigint,
          nr_5                                    bigint,
          tekst_1                                 varchar(40),
          tekst_2                                 varchar(40),
          tekst_3                                 varchar(40),
          tekst_4                                 varchar(40),
          tekst_5                                 varchar(40),
          trace_level                             character(1),
          creatie_dt                              timestamp     not null default now());
alter table activiteit add
    constraint activiteit_pk primary key (activiteit_id);

create index activiteit_idx_1 on activiteit (pl_id);
create index activiteit_idx_2 on activiteit (start_dt, activiteit_type, toestand, activiteit_subtype);
create index activiteit_idx_3 on activiteit (moeder_id, toestand, activiteit_id);
create index activiteit_idx_4 on activiteit (activiteit_type, toestand, activiteit_subtype);
create index activiteit_idx_5 on activiteit (communicatie_partner, toestand, activiteit_type, activiteit_subtype);
--create index activiteit_idx_6 on activiteit (activiteit_type, toestand, activiteit_id, laatste_actie_dt) where activiteit_type = 100 and (toestand = 10000 or toestand = 10002);
--create index activiteit_idx_7 on activiteit (tekst_3) where activiteit_type = 112 and activiteit_subtype in (1750, 1751) and tekst_3 IS NOT null; -- index for msgid of lo3dk request in tekst_3
--create index activiteit_idx_8 on activiteit (uiterlijke_actie_dt, toestand, start_dt) where toestand > 9999; 
-- Please note that normally we would index column 'uiterlijke_actie_dt', but at the moment this functionality is not required.

create table gebeurtenis(
          gebeurtenis_id                          bigint        not null,
          gebeurtenis_type                        integer       not null,
          gebeurtenis_dt                          timestamp     not null default now(),
          gebeurtenis_oms                         text,
          activiteit_id                           bigint        not null references activiteit(activiteit_id),
          activiteit_nieuwe_toestand              integer,
          creatie_door                            varchar(40),
          creatie_dt                              timestamp     not null default now(),
          gebeurtenis_hash						  bigint);
alter table gebeurtenis add
    constraint gebeurtenis_pk primary key (gebeurtenis_id);

create index gebeurtenis_idx_1 on gebeurtenis (activiteit_id);
--create index gebeurtenis_idx_2 on gebeurtenis(gebeurtenis_type, gebeurtenis_hash, gebeurtenis_dt) where gebeurtenis_type in (1103, 1104, 1105, 1106, 1110, 1111, 1112, 1119);

create table gebeurtenis_data(
          gebeurtenis_data_id                     bigint        not null,
          gebeurtenis_data_type                   integer       not null,
          gebeurtenis_data                        text,
          gebeurtenis_nr_1                        bigint,
          gebeurtenis_nr_2                        bigint,
          gebeurtenis_id                          bigint        not null references gebeurtenis (gebeurtenis_id),
          creatie_dt                              timestamp     not null default now());
alter table gebeurtenis_data add
    constraint gebeurtenis_data_pk primary key (gebeurtenis_data_id);

create index gebeurtenis_data_idx_1 on gebeurtenis_data (gebeurtenis_id);

create table lo3_bericht(
          lo3_bericht_id                          bigint        not null,
          aanduiding_in_uit                       character(1)  not null,
          bericht_activiteit_id                   bigint	references activiteit (activiteit_id),
          medium                                  character(1)  not null,
          originator_or_recipient                 varchar(7),
          spg_mailbox_instantie                   integer,
          eref                                    varchar(12),
          bref                                    varchar(12),
          eref2                                   varchar(12),
          berichtcyclus_id                        bigint,
          tijdstip_verzending_ontvangst           timestamp,
          dispatch_sequence_number                integer,
          report_delivery_time                    timestamp,
          non_delivery_reason                     varchar(4),
          non_receipt_reason                      varchar(4),
          bericht_data                            text,
          kop_random_key                          integer,
          kop_berichtsoort_nummer                 varchar(4),
          kop_a_nummer                            bigint,
          kop_oud_a_nummer                        bigint,
          kop_herhaling                           character(1),
          kop_foutreden                           character(1),
          kop_datum_tijd                          bigint,
          creatie_dt                              timestamp     not null default now());
alter table lo3_bericht add
    constraint lo3_bericht_pk primary key (lo3_bericht_id);

create        index lo3_bericht_idx_1 on lo3_bericht (eref);
create        index lo3_bericht_idx_2 on lo3_bericht (bref);
create        index lo3_bericht_idx_3 on lo3_bericht (bericht_activiteit_id);
create        index lo3_bericht_idx_4 on lo3_bericht (dispatch_sequence_number);
create        index lo3_bericht_idx_5 on lo3_bericht (berichtcyclus_id);

create table lookup_codering(
          lookup_codering_id                      integer       not null,
          codering_naam                           varchar(40)   unique not null,
          code_type                               character(1)  not null,
          idc_vaste_codering                      character(1)  not null,
          creatie_dt                              timestamp     not null default now());

alter table lookup_codering add
    constraint lookup_codering_pk primary key (lookup_codering_id);

create table lookup_codewaarde(
          lookup_codering_id                      integer       not null
                                                                references lookup_codering(lookup_codering_id),
          referentie_waarde_num                   integer,
          referentie_waarde_alfa                  varchar(40),
          lookup_afkorting                        varchar(20),
          lookup_omschrijving                     varchar(40)   not null,
          omschrijving_volledig                   varchar(200),
          creatie_dt                              timestamp     not null default now(),
          mutatie_dt                              timestamp     not null default now());

alter table lookup_codewaarde add
    constraint lookup_codewaarde_pk primary key (lookup_codering_id, lookup_omschrijving);

create unique index lookup_codewaarde_idx_1 on lookup_codewaarde (lookup_codering_id, referentie_waarde_num);
create unique index lookup_codewaarde_idx_2 on lookup_codewaarde (lookup_codering_id, referentie_waarde_alfa);

create table toestand_overgang (
          toestand_overgang_id                    integer       not null,
          activiteit_type                         integer       not null,
          activiteit_subtype                      integer,
          toestand_huidig                         integer,
          gebeurtenis_type                        integer       not null,
          toestand_nieuw                          integer       not null,
          creatie_dt                              timestamp     not null default now());

alter table toestand_overgang add
    constraint toestand_overgang_pk primary key (toestand_overgang_id);

create unique index toestand_overgang_idx_1 on toestand_overgang
        (activiteit_type,activiteit_subtype,toestand_huidig,gebeurtenis_type,toestand_nieuw);

create table lo3_mailbox (
          lo3_mailbox_nummer                      varchar(7),
          spg_mailbox_instantie                   integer,
          soort_instantie                         character(1)	not null,
          -- code_instantie is in de praktijk een gemeentecode of afnemersindicatie
          code_instantie                          integer,
          indicatie_mailbox_actief                character(1),
          brp_overgangs_datum                     integer,
          blokkade_start_dt                       timestamp,
          blokkade_eind_dt                        timestamp,
          mutatie_dt                              timestamp,
          blokkade_bericht_nrs                    varchar(400),
          deblokkade_toestand                     integer,
          creatie_dt                              timestamp     not null default now());

alter table lo3_mailbox add
    constraint lo3_mailbox_pk primary key (lo3_mailbox_nummer);

create table lo3_vospg_instructie(
          vospg_instructie_id                     bigint NOT NULL,
          spg_mailbox_instantie                   integer      not null references SPG_mailbox (spg_mailbox_instantie),
          soort_instructie                        varchar(40),
          dag_van_de_week                         smallint,
          tijd                                    time,
          datum_tijd                              timestamp,
          tijdstip_laatste_actie                  timestamp,
          creatie_dt                              timestamp     not null default now());

alter table lo3_vospg_instructie add
    constraint lo3_vospg_instructie_pk primary key (vospg_instructie_id);

create table miteller (
 		  groep_id 								  varchar(8) not null,
		  soort_teller 							  integer not null,
		  aantal 								  integer not null,
		  datum_telling 						  date not null,
		  periode 							      smallint,
		  periode_aanduiding					  varchar(5) not null);

alter table miteller add
    constraint teller_pk primary key (soort_teller,groep_id,datum_telling,periode);

create table miteller_marker (
		  marker_id 							  varchar(30) PRIMARY KEY,
 		  marker  								  bigint NOT NULL
);

create table selectie_instelling(
          selectie_instelling_id                  bigint not null,
          autorisatie_id                          bigint not null references lo3_autorisatie (autorisatie_id),
          geschatte_omvang                        integer not null, 
          leverwijze                              integer not null,
          max_berichten                           integer not null,
          berichtsoort                            integer,
          selectiesoort                           integer not null,
          bestands_formaat                        integer, 
          selectie_datum                          date not null,
          berekende_selectie_datum                date not null,
          selectie_activiteit_id                  bigint not null references activiteit (activiteit_id), 
          voorwaarde_regel                        varchar(4096) not null,
          rubrieken                               varchar(4096) not null,
          creatie_dt                              timestamp not null default now(),
          constraint selectie_instelling_pk primary key (selectie_instelling_id)); 

create table selectie_enumeratie(
          selectie_instelling_id                  bigint not null references selectie_instelling (selectie_instelling_id),
          enumeratie                              text,
          constraint selectie_enumeratie_pk primary key (selectie_instelling_id));
                  
create table monitor (
          monitor_id                              bigint not null,
          node                                    varchar(20) not null,
          component                               varchar(20) not null,
          ernst                                   integer not null,
          melding                                 text,
          creatie_dt                              timestamp not null default now(),
          constraint monitor_pk primary key(monitor_id));

create table herindeling (
          herindeling_id                          bigint not null references activiteit (activiteit_id),
          nieuwe_gemeente_code                    varchar(4) not null check (length(nieuwe_gemeente_code) = 4),
          oude_gemeente_codes                     varchar(400),
          herindeling_datum                       integer not null check (herindeling_datum between verstrekking_start_datum and verstrekking_eind_datum),
          volg_nr                                 integer not null check (volg_nr between 1 and 10),
          verstrekking_start_datum                integer not null check (verstrekking_start_datum >= 20121101),
          verstrekking_eind_datum                 integer not null check (verstrekking_eind_datum >= verstrekking_start_datum),
          afnemers_am_verstrekkingen              text,
          afnemers_niet_verstrekkingen            text,
          toelichting                             text,
          creatie_dt                              timestamp not null default now());
    
alter table herindeling add
    constraint herindeling_pk primary key (herindeling_id);
    
create unique index herindeling_idx_0 on herindeling(nieuwe_gemeente_code, herindeling_datum);

create table anonimiseer(
          id                    bigint not null,
          old_pl                text null,
          new_pl                text not null);
          
alter table anonimiseer add
    constraint anonimiseer_pk primary key (id);

create table tmv_dossier(
          dossier_nummer                          bigint        not null references activiteit (activiteit_id),
          voorgaand_dossier_act_id                bigint,
          vervolg_dossier_act_id                  bigint,
          aanleg_dt                               integer,
          wijziging_dt                            integer,
          verwachte_datum_afhandeling             integer,
          resultaat_onderzoek                     smallint,
          toelichting_gemeente                    text,
          reactie_termijn                         integer,
          creatie_dt                              timestamp     not null default now());
          
alter table tmv_dossier add
    constraint tmv_dossier_pk primary key (dossier_nummer);
    
create        index tmv_dossier_idx_1 on tmv_dossier (dossier_nummer);
create        index tmv_dossier_idx_2 on tmv_dossier (wijziging_dt);

create table tmv_dossier_afnemer_melding(
          dossier_afnemer_melding_id              bigint        not null,
          dossier_nummer                          bigint        not null references tmv_dossier (dossier_nummer),
          melding_nr                              varchar(12),
          afnemers_indicatie                      integer,
          melder_id                               varchar(16),
          melding_volgnr                          integer,
          melding_datum                           integer,
          toelichting                             text,
          creatie_dt                              timestamp     not null default now());
          
alter table tmv_dossier_afnemer_melding add
    constraint dossier_afnemer_melding_pk primary key (dossier_afnemer_melding_id);

create index tmv_dossier_afnemer_melding_idx_1 on tmv_dossier_afnemer_melding (dossier_afnemer_melding_id);
create index tmv_dossier_afnemer_melding_idx_2 on tmv_dossier_afnemer_melding (dossier_nummer);

create table tmv_rubriek_waarde(
          rubriek_waarde_id                       bigint        not null,
          dossier_afnemer_melding_id              bigint        not null references tmv_dossier_afnemer_melding (dossier_afnemer_melding_id),
          rubriek                                 integer       not null,
          gbav_waarde                             varchar(200),
          voorgestelde_waarde                     varchar(200),
          creatie_dt                              timestamp     not null default now());
          
alter table tmv_rubriek_waarde add
    constraint tmv_rubriek_waarde_pk primary key (rubriek_waarde_id);
    
create index tmv_rubriek_waarde_idx_1 on tmv_rubriek_waarde (rubriek_waarde_id);
create index tmv_rubriek_waarde_idx_2 on tmv_rubriek_waarde (dossier_afnemer_melding_id);

create table tmv_annotatie (
          uuid                                    varchar(36) not null,
          melding_id                              bigint      references activiteit(activiteit_id),
          oin                                     varchar(20) not null,
          type                                    varchar(40) not null,
          xml                                     text        not null,
          uri                                     varchar(200),
          verwijzing                              varchar(36),
          geannoteerd_op                          timestamp   not null,
          creatie_dt                              timestamp   not null default now(),
          check ((type <> 'Wijzigingsverzoek' and verwijzing is not null) or
                 (type = 'Wijzigingsverzoek' and melding_id is not null and verwijzing is null)));
                 
alter table tmv_annotatie 
    add constraint tmv_annotatie_pk primary key (uuid);
    
create index tmv_annotatie_idx_1 on tmv_annotatie (oin, geannoteerd_op);


-- sequences
create sequence lo3_pl_id_sequence start with 1 increment by 1;
create sequence lo3_bericht_id_sequence start with 1 increment by 1;
create sequence adres_id_sequence start with 1 increment by 1;
create sequence activiteit_id_sequence start with 1 increment by 1;
create sequence toestand_overgang_id_sequence start with 1 increment by 1;
create sequence gebeurtenis_id_sequence start with 1 increment by 1;
create sequence gebeurtenis_data_id_sequence start with 1 increment by 1;
create sequence lo3_autorisatie_id_sequence start with 1 increment by 1;
create sequence lo3_vospg_instructie_id_sequence start with 1 increment by 1;
create sequence afnemer_id_sequence start with 1 increment by 1;
create sequence selectie_instelling_id_sequence start with 1 increment by 1;
create sequence monitor_id_sequence start with 1 increment by 1;
create sequence anonimiseer_id_sequence start with 1 increment by 1;
create sequence tmv_rubriek_waarde_id_sequence start with 1 increment by 1;
create sequence tmv_dossier_afnemer_melding_id_sequence start with 1 increment by 1;