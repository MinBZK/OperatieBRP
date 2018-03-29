Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2253 De partij waarbij is gekozen voor een verstrekkingsbeperking moet geldig zijn op peildatum

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: update kern.partij set dateinde = to_number((to_char(now(), 'YYYYMMDD')),'99999999') where naam = 'Stichting Interkerkelijke Ledenadministratie'
Given maak bijhouding caches leeg

Scenario: 2. Peildatum ligt voor de Partij.Dateinde
          LT: VZIG02C360T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG02C360T30-001.xls

When voer een bijhouding uit VZIG02C360T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C360T30.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 2      |

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update kern.partij set dateinde = null where naam = 'Stichting Interkerkelijke Ledenadministratie'
Given maak bijhouding caches leeg
