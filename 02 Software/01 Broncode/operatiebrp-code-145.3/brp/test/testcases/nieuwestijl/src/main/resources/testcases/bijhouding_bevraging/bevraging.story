Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2253 De partij waarbij is gekozen voor een verstrekkingsbeperking moet geldig zijn op peildatum

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: update kern.partij set datingang = 20160102 where naam = 'Stichting Interkerkelijke Ledenadministratie'
Given maak bijhouding caches leeg

Scenario: 2. Peildatum ligt op de Partij.Datingang
          LT: VZIG02C360T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG02C360T20-001.xls

When voer een bevraging uit bevraging.xml namens partij 'Gemeente BRP 1'

When voer een bijhouding uit bevraging_bijhouding.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update kern.partij set datingang = 19941001 where naam = 'Stichting Interkerkelijke Ledenadministratie'
Given maak bijhouding caches leeg
