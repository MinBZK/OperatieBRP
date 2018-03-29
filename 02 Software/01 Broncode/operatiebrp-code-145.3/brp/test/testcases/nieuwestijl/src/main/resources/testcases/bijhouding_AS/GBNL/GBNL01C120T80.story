Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1727 Geslachtsnaam Nederlands kind moet overeenkomen met die van eerder kind uit dezelfde relatie

Scenario: niet-NL kind met ongelijke geslachtsnaam t.o.v. NI stiefsibling met pseudo-NOUWKIG
          LT: GBNL01C120T80

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C120T80-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C120T80-002.xls

!-- Geboorte toekomstige sibling
When voer een bijhouding uit GBNL01C120T80a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd
Given de database is aangepast met: update kern.his_persbijhouding set naderebijhaard = 3 where pers in (select id from kern.pers where voornamen = 'Siblingnaam')

!-- Geboorte kind
When voer een bijhouding uit GBNL01C120T80b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C120T80.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R