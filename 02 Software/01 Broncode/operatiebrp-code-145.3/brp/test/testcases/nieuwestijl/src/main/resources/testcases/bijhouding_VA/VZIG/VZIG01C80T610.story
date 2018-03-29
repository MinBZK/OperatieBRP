Meta:
@status                 Klaar
@regels                 R2493
@usecase                UCS-BY.0.VA

Narrative: R2493 Waarschuwing indien er een feitdatum op de persoonslijst staat die recenter dan of gelijk is aan de Peildatum, DAG en DEG van de Administratieve handeling.

Scenario: testen of Persoon - Nationaliteit.Migratie Datum einde bijhouding niet wordt gedaan
          LT: VZIG01C80T610

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C80T610-001.xls

And de database is aangepast met: update kern.his_persnation set migrdateindebijhouding='20160102' where persnation in (select id from kern.persnation where pers in (select id from kern.pers where voornamen = 'James'));
When voer een bijhouding uit VZIG01C80T610.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C80T610.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R
