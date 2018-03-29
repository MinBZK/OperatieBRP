Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R1375 Locatieomschrijving mag uit niet meer dan 35 karakters bestaan

Scenario: Locatieomschrijving gevuld met 36 karakters
          LT: VZIG02C270T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG02C270T10-001.xls

When voer een bijhouding uit VZIG02C270T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C270T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 2      |

Then lees persoon met anummer 7504603937 uit database en vergelijk met expected VZIG02C270T10-persoon1.xml
