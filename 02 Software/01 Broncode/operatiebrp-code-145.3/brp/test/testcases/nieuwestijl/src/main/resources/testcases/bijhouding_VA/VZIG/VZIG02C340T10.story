Meta:
@status                 Klaar
@regels                 R2317
@usecase                UCS-BY.0.VA

Narrative: R2317 Huisnummertoevoeging en locatie ten opzichte van adres mogen niet beide gevuld zijn

Scenario: Huisnummertoevoeging is ingevuld en locatie ten opzichte van adres is gevuld
          LT: VZIG02C340T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG-001.xls

When voer een bijhouding uit VZIG02C340T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C340T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |