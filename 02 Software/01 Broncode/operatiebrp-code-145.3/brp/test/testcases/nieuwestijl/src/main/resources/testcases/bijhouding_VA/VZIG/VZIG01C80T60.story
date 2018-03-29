Meta:
@status                 Klaar
@regels                 R2493
@usecase                UCS-BY.0.VA

Narrative: R2493 Waarschuwing indien er een feitdatum op de persoonslijst staat die recenter dan of gelijk is aan de Peildatum, DAG en DEG van de Administratieve handeling.

Scenario:   R2493 Datum ingang familierechtelijke betrekking op de PL is recenter of gelijk aan de peildatum, DAG en DEG van de AH
            LT: VZIG01C80T60

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C80T60-001.xls

When voer een bijhouding uit VZIG01C80T60.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C80T60.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R
