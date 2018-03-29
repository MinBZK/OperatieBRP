Meta:
@status                 Klaar
@regels                 R2493
@usecase                UCS-BY.0.VA

Narrative: R2493 Waarschuwing indien er een feitdatum op de persoonslijst staat die recenter dan of gelijk is aan de Peildatum, DAG en DEG van de Administratieve handeling.

Scenario:   R2493 Materieel Persoon Bijhouding DEG
            LT: VZIG01C80T270

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C80T270-001.xls

When voer een bijhouding uit VZIG01C80T270a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C80T270a.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Given de database is aangepast met: update kern.his_persbijhouding set dateindegel='20160106' where pers in (select id from kern.pers where voornamen='James') and dateindegel='20160103';

When voer een bijhouding uit VZIG01C80T270b.xml namens partij 'Gemeente BRP 2'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C80T270b.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R
