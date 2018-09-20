--
-- Draai dit script nadat met 
-- zoeken via gemeente
create index lo3_adres_idx_2 on lo3_adres(
	gemeente_code,
	lower(straat_naam) text_pattern_ops,
	huis_nr,
	huis_letter,
	huis_nr_toevoeging);
	
-- zoeken via straatnaam
create index lo3_adres_idx_3 on lo3_adres(
	lower(straat_naam) text_pattern_ops,
	huis_nr,
	huis_letter,
	huis_nr_toevoeging);

-- zoeken via locatie_beschrijving
create index lo3_adres_idx_4 on lo3_adres
	(lower(locatie_beschrijving) text_pattern_ops);
	
create index lo3_pl_afnemer_ind_idx_1 on lo3_pl_afnemer_ind(afnemer_code, geldigheid_start_datum);

create unique index lo3_pl_persoon_idx_0 on lo3_pl_persoon(a_nr) where persoon_type = 'P' and stapel_nr = 0 and volg_nr = 0;
create index lo3_pl_persoon_idx_1 on lo3_pl_persoon(a_nr, persoon_type, stapel_nr, volg_nr);
create index lo3_pl_persoon_idx_2 on lo3_pl_persoon(burger_service_nr, persoon_type, stapel_nr, volg_nr);
create index lo3_pl_persoon_idx_3 on lo3_pl_persoon(lower(geslachts_naam) text_pattern_ops, persoon_type, stapel_nr, volg_nr, lower(voor_naam) text_pattern_ops);
create index lo3_pl_persoon_idx_4 on lo3_pl_persoon(geboorte_datum, lower(geslachts_naam) text_pattern_ops, persoon_type, stapel_nr, volg_nr, lower(voor_naam) text_pattern_ops);

create index lo3_pl_paw_index_2_idx_1 on lo3_pl_paw_index_2(lower(geslachts_naam) text_pattern_ops, inschrijving_gemeente_code, lower(straat_naam) text_pattern_ops);
create index lo3_pl_paw_index_2_idx_2 on lo3_pl_paw_index_2(lower(postcode) text_pattern_ops, lower(geslachts_naam) text_pattern_ops);
create index lo3_pl_paw_index_2_idx_3 on lo3_pl_paw_index_2(geboorte_datum, lower(postcode) text_pattern_ops);
create index lo3_pl_paw_index_2_idx_4 on lo3_pl_paw_index_2(lower(straat_naam) text_pattern_ops, lower(geslachts_naam) text_pattern_ops);

create index lo3_pl_verblijfplaats_idx_1 on lo3_pl_verblijfplaats(inschrijving_gemeente_code, volg_nr, geldigheid_start_datum);
create index lo3_pl_verblijfplaats_idx_2 on lo3_pl_verblijfplaats(adres_id, volg_nr, geldigheid_start_datum);

create index lo3_pl_serialized_idx_1 on lo3_pl_serialized(mutatie_dt);
