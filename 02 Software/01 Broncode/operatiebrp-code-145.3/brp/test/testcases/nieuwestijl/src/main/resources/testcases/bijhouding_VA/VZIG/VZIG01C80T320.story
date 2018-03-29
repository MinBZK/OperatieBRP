Meta:
@status                 Klaar
@regels                 R2493
@usecase                UCS-BY.0.VA

Narrative: R2493 Waarschuwing indien er een feitdatum op de persoonslijst staat die recenter dan of gelijk is aan de Peildatum, DAG en DEG van de Administratieve handeling.

Scenario:   Materieel Persoon - Migratie DEG kern.his_persmigratie.dateindegel
            LT: VZIG01C80T320

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C80T320-001.xls

And de database is aangepast met: update kern.his_persmigratie set dateindegel='20160101' where pers in (select id from kern.pers where voornamen='James') and dateindegel is not null;

When voer een bijhouding uit VZIG01C80T320.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C80T320.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R
