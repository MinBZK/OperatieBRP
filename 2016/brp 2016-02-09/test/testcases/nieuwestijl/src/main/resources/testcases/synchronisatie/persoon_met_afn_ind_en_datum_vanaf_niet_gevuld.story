Meta:
@sprintnummer           65
@epic                   Synchronisatie Persoon
@auteur                 dihoe
@jiraIssue              TEAMBRP-2289
@status                 Klaar
@sleutelwoorden         synchronisatie

Narrative:
    Bug: Synchronisatie persoon op persoon met afnemerindicatie gaf nullpointer als datumAanvangMaterielePeriode niet gevuld is

Scenario: persoon met afnemerindicatie en datumAanvangMaterielePeriode niet gevuld, daarna wordt de persoon gesynchroniseerd

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand persoon_met_afn_ind_en_datum_vanaf_niet_gevuld_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is er 1 bericht geleverd
Given wacht 60 seconden


Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand persoon_met_afn_ind_en_datum_vanaf_niet_gevuld_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
