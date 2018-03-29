Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2325 Registratie van een Nederlands adres mag alleen door de gemeente van het nieuwe adres worden geregistreerd

Scenario: Administratieve handeling.Partij ongelijk aan Persoon  Adres.Gemeente.Partij
          LT: VZIG01C60T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C60T10-001.xls

When voer een bijhouding uit VZIG01C60T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C60T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |
