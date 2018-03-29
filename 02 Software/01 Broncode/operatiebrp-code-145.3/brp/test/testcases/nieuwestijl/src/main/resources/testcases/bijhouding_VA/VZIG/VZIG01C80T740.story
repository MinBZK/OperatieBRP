Meta:
@status                 Klaar
@regels                 R2493
@usecase                UCS-BY.0.VA

Narrative: R2493 Waarschuwing indien er een feitdatum op de persoonslijst staat die recenter dan of gelijk is aan de Peildatum, DAG en DEG van de Administratieve handeling.

Scenario:   Gerelateerde feitdatum gelijk aan peildatum
            LT: VZIG01C80T740

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C80T740-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 419497225 en persoon 538201289 met relatie id 70010001 en betrokkenheid id 70010001
And de database is aangepast met: update kern.his_persgeboorte set datgeboorte='20160102' where pers in (select id from kern.pers where voornamen='Jan');


When voer een bijhouding uit VZIG01C80T740.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C80T740.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R