Meta:
@status                 Klaar
@usecase                UCS-BY.1.AB

Narrative: Archiveer bericht

Scenario: Ingaand bericht die niet voldoet aan de XSD, wordt niet gearchiveerd
          LT: ARBR04C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG-001.xls

When voer een bijhouding uit ARBR04C10T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/ARBR/expected/ARBR04C10T10.txt

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

Then in ber heeft SELECT COUNT(id) FROM ber.ber de volgende gegevens:
| veld                      | waarde |
| count                     | 0      |

Then in ber heeft SELECT COUNT(ber) FROM ber.berpers de volgende gegevens:
| veld                      | waarde |
| count                     | 0      |
