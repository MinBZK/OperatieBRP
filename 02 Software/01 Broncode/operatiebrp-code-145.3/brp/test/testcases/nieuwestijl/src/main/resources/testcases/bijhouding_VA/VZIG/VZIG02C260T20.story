Meta:
@status                 Klaar
@regels                 R1927
@usecase                UCS-BY.0.VA

Narrative: Persoon wiens adres het betreft moet bevoegd zijn als aangever ingeschrevene is

Scenario:   R1927 Aangever adreshouding is geen ingeschrevene wel ouder dan 16 jaar en staat niet onder curatele.
            LT: VZIG02C260T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG02C260T20-001.xls

Given voer enkele update uit: update kern.pers set datgeboorte=to_number(to_char(current_date - interval '16 years','YYYYMMDD'),'9999999999.99') where voornamen='Libby';

When voer een bijhouding uit VZIG02C260T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C260T20.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 2      |