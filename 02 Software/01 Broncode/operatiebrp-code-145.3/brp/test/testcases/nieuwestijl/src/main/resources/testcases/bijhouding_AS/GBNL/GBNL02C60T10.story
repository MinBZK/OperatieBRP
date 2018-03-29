Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1746 Leeftijdsverschil kinderen van zelfde OUWKIG moet minstens 9 maanden zijn

Scenario:   DatumGeboorte van een ander Kind van diezelfde Ouder verschilt 305 dagen
            LT: GBNL02C60T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C60-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C60-002.xls

When voer een bijhouding uit GBNL02C60T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

When voer een bijhouding uit GBNL02C60T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL02C60T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R