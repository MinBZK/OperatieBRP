Meta:
@status                 Klaar
@regels                 R2493
@usecase                UCS-BY.0.VA

Narrative: R2493 Waarschuwing indien er een feitdatum op de persoonslijst staat die recenter dan of gelijk is aan de Peildatum, DAG en DEG van de Administratieve handeling.

Scenario:   R2493 Geboortedatum van gerelateerde is recenter dan de peildatum DAG en DEG van de AH
            LT: VZIG01C80T700

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C80T700-001.xls
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C80T700-002.xls

When voer een bijhouding uit VZIG01C80T700a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 291179721 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 582130633 wel als PARTNER betrokken bij een HUWELIJK



Given de database is aangepast met: update kern.his_persgeboorte set datgeboorte='20160102' where pers in (select id from kern.pers where voornamen='Elizabeth');




When voer een bijhouding uit VZIG01C80T700b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C80T700.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

