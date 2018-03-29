Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2417 Huisnummer mag alleen ingevuld zijn bij een BAG adres en een Niet-BAG adres met openbare ruimte

Scenario: niet-BAG adres met locatieomschrijving waarbij Huisnummer is gevuld
          LT: VZIG02C220T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG-001.xls

When voer een bijhouding uit VZIG02C220T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C220T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |