Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1745 Leeftijdsverschil OUWKIG en kind moet minimaal 14 en maximaal 50 jaar zijn

Scenario:   Leeftijdsverschil tussen OUWKIG en kind is groter dan 50 jaar
            LT: GBNL01C160T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C160T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C160T10-002.xls

When voer een bijhouding uit GBNL01C160T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C160T20.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R