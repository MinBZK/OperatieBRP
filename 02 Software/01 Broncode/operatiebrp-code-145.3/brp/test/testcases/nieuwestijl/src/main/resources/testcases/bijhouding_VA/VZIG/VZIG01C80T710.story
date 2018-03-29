Meta:
@status                 Klaar
@regels                 R2493
@usecase                UCS-BY.0.VA

Narrative: R2493 Waarschuwing indien er een feitdatum op de persoonslijst staat die recenter dan of gelijk is aan de Peildatum, DAG en DEG van de Administratieve handeling.

Scenario:   R2493 Materieel feitdatum gelijk aan DAG op de persoonslijst is gelijk aan de DAG van de AH
            LT: VZIG01C80T710

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C80T710-001.xls

And de database is aangepast met: update kern.his_persgeslnaamcomp set dataanvgel='20160101' where persgeslnaamcomp in (select id from kern.persgeslnaamcomp where pers in (select id from kern.pers where voornamen='James'));

When voer een bijhouding uit VZIG01C80T710.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C80T710.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R
