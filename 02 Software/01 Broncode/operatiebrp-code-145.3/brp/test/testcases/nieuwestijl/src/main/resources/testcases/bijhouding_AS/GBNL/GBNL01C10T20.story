Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1586 Burgerservicenummer mag niet reeds voorkomen in de BRP

Scenario: Kind Ingeschrevene met nadere bijhaard ongelijk aan F en bestaande Ingeschrevene met nadere bijhaard is F
          LT: GBNL01C10T20

Given alle personen zijn verwijderd

!-- De ouders
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C10T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C10T20-002.xls

!-- Een persoon met bijhaard F met een BSN dat wordt gebruikt voor het kind
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C10T20-003.xls

When voer een bijhouding uit GBNL01C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C10T20.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
