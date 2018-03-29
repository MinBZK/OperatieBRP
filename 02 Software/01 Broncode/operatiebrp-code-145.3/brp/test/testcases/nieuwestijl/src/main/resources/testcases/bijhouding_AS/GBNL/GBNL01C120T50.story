Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1727 Geslachtsnaam Nederlands kind moet overeenkomen met die van eerder kind uit dezelfde relatie

Scenario: Ongelijke scheidingsteken en geslachtsnaamstam bij niet-ingezeten stiefsibling met pseudo-ouder
          LT: GBNL01C120T50

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C120T50-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C120T50-002.xls

!-- Geboorte stiefsibling met ingeschreven ouders met aanpassing tot opschorting 'E' om stiefsibling niet-ingezetene te maken
When voer een bijhouding uit GBNL01C120T50a.xml namens partij 'Gemeente BRP 1'
Given de database is aangepast met: update kern.his_persbijhouding set naderebijhaard = 3 where pers in (select id from kern.pers where voornamen = 'Siblingnaam')

Then heeft het antwoordbericht verwerking Geslaagd

!-- Geboorte kind met een ingeschreven ouwkig en pseudo-persoon nouwkig
When voer een bijhouding uit GBNL01C120T50b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C120T50.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R