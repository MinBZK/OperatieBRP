Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R1901 Voor een persoon met een Nederlandse nationaliteit mag geen verblijfsrecht geregistreerd zijn

Scenario: R1901 Voor een persoon met een Nederlandse nationaliteit beeindigd voor de peildatum wordt een registratie verblijfsrecht verricht
          LT: WZVB01C10T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-WZVB/WZVB01C10T30-001.xls

When voer een bijhouding uit WZVB01C10T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/WZVB/expected/WZVB01C10T30.xml voor expressie //brp:bhg_vbaRegistreerVerblijfsrecht_R

