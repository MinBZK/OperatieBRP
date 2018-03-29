Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1586 Burgerservicenummer mag niet reeds voorkomen in de BRP

Scenario: Kind Ingeschrevene met nadere bijhaard ongelijk aan F en bestaande niet-Ingeschrevene met nadere bijhaard ongelijk aan F
          LT: GBNL01C10T30

Given alle personen zijn verwijderd

!-- De ouders
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C10T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C10T30-002.xls

When voer een bijhouding uit GBNL01C10T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C10T30.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
