Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2536 Minimaal één hoofdpersoon moet een ingezetene zijn

Scenario: Registratie verblijfsrecht bij een Niet-ingezetene ingediend door de rol 'Bijhoudingsorgaan College'
          LT: WZVB01C50T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-WZVB/WZVB01C50T20-001.xls

When voer een bijhouding uit WZVB01C50T20a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

When voer een bijhouding uit WZVB01C50T20b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/WZVB/expected/WZVB01C50T20.xml voor expressie //brp:bhg_vbaRegistreerVerblijfsrecht_R
