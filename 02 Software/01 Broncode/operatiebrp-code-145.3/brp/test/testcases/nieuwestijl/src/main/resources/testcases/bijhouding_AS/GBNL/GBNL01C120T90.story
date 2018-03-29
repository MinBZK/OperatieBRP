Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1727 Geslachtsnaam Nederlands kind moet overeenkomen met die van eerder kind uit dezelfde relatie

Scenario: NL kind met ongelijke scheidingsteken t.o.v. NI sibling met pseudo-ouder
          LT: GBNL01C120T90

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C120T90-001.xls

!-- Geboorte toekomstige sibling
When voer een bijhouding uit GBNL01C120T90a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd
Given de database is aangepast met: update kern.his_persbijhouding set naderebijhaard = 3 where pers in (select id from kern.pers where voornamen = 'Siblingnaam')

!-- Geboorte kind
When voer een bijhouding uit GBNL01C120T90b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C120T90.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R