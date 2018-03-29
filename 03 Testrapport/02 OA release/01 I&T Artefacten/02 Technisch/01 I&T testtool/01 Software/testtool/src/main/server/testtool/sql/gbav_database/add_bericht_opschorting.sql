-- SQL voor toevoegen van berichten aan GBA-V db

INSERT INTO lo3_bericht (lo3_bericht_id, bericht_activiteit_id, aanduiding_in_uit, medium, originator_or_recipient, bericht_data) select case when max(lo3_bericht_id) is null then 1 else max(lo3_bericht_id)+1 end, case when max(bericht_activiteit_id) is null then 1 else max(lo3_bericht_id)+1 end, 'I', 'N', '{originator_or_recipient}', '{bericht}' FROM lo3_bericht;

INSERT INTO lo3_pl (pl_id, mutatie_dt, mutatie_activiteit_id, bijhouding_opschort_reden, bijhouding_opschort_datum) select case when max(pl.pl_id) is null then 1 else max(pl.pl_id)+1 end, '{mutatie_dt}', case when max(bericht.bericht_activiteit_id) is null then 1 else max(bericht.bericht_activiteit_id) end, '{reden_opschorting}', '{datum_opschorting}' from lo3_pl pl, lo3_bericht bericht;

INSERT INTO lo3_pl_persoon (pl_id, persoon_type, stapel_nr, volg_nr, a_nr, burger_service_nr) SELECT max(pl_id), 'P', 0, 0, '{anr}', '{bsn}' FROM lo3_pl;

