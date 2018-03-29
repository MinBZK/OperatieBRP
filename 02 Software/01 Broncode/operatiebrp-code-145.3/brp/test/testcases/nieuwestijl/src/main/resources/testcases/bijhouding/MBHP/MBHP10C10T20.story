Meta:
@status                 Onderhanden
@usecase                UCS-BY.1.MP

Narrative: Scenario 10 AH Verhuizing Intergemeentelijk

Scenario:   Verhuizing intergemeentelijk (BRP-BRP) en persoon is overleden
            LT: MBHP10C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP10C10T20-001.xls

When voer een bijhouding uit MBHP10C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBHP/expected/MBHP10C10T20.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT gem FROM kern.his_persadres WHERE tsverval is NULL and dateindegel is NULL de volgende gegevens:
| veld                      | waarde |
| gem                       | 7012   |

Then controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen
