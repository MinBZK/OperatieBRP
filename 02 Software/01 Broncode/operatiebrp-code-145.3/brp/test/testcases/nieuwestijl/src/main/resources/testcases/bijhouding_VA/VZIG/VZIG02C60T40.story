Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R1379 Gemeente in het adres moet geldig zijn op datum aanvang adreshouding

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: update kern.gem set dateindegel = 20170101 where naam = 'Gemeente BRP 1'
Given maak bijhouding caches leeg

Scenario: 2. Adres.Datum aanvang adreshouding voor Gemeente.dateindegelgel
            LT: VZIG02C60T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG02C60T30-001.xls

When voer een bijhouding uit VZIG02C60T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C60T40.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R


Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update kern.gem set dateindegel = null where naam = 'Gemeente BRP 1'
Given maak bijhouding caches leeg
