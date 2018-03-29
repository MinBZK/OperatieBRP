Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1725 Waarschuwing als OUWKIG een briefadres heeft

Scenario: OUWKIG heeft een briefadres op DatumAanvangGeldigheid  
          LT: GBNL01C100T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C100T10-001.xls

When voer een bijhouding uit GBNL01C100T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C100T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
