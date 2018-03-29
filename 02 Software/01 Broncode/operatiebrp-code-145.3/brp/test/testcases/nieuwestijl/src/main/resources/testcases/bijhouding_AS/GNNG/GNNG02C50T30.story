Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2471 Voornaam mag alleen ingevuld worden indien de namenreeks indicator wijzigt naar Nee

Scenario:   Indicator namenreeks wijzigt van "Ja" naar "Ja" en voornaam ingevuld
            LT: GNNG02C50T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C50-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C50-002.xls

When voer een bijhouding uit GNNG02C50T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG02C50T30.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R