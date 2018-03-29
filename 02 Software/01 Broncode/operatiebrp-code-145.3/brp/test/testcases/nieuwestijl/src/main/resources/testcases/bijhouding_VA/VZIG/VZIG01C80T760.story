Meta:
@status                 Klaar
@regels                 R2493
@usecase                UCS-BY.0.VA

Narrative: R2493 Waarschuwing indien er een feitdatum op de persoonslijst staat die recenter dan of gelijk is aan de Peildatum, DAG en DEG van de Administratieve handeling.

Scenario:   hoofdactie en nevenactie. Feitdatum groter dan hoofdactie DAG. (voor beide hoofdpersonen)
            LT: VZIG01C80T760

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C80T760-001.xls
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C80T760-002.xls

Given de database is aangepast met: update kern.his_persreisdoc set datingangdoc='20160102' where persreisdoc in (select id from kern.persreisdoc where pers in (select id from kern.pers where voornamen = 'Evelien'));
Given de database is aangepast met: update kern.his_persreisdoc set datingangdoc='20160102' where persreisdoc in (select id from kern.persreisdoc where pers in (select id from kern.pers where voornamen = 'Ben'));

When voer een bijhouding uit VZIG01C80T760.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C80T760.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R
