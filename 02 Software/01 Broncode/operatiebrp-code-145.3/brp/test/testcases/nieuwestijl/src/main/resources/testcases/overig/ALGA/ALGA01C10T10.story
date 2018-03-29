Meta:
@status                 Klaar

Narrative: A-laag afleiding test

Scenario:   Materiele en formele historie
            LT: ALGA01C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-ALGA/ALGA01C10T10-001.xls

Then in kern heeft select geslachtsaand, indaggeslachtsaand, vorigeanr,
indagnrverwijzing, datoverlijden, indagoverlijden, voornamen, anr, bsn, indagids,
datgeboorte, indaggeboorte
from kern.pers
where voornamen = 'Libby' or voornamen = 'Piet' or voornamen = 'Margareth' or voornamen = 'Willy' order by voornamen
de volgende gegevens:
| veld                      | waarde |
| geslachtsaand             | 1      |
| indaggeslachtsaand        | true   |
| vorigeanr                 | NULL   |
| indagnrverwijzing         | false  |
| datoverlijden             | NULL   |
| indagoverlijden           | false  |
| voornamen                 | Libby  |
| anr                       | 4375639570|
| bsn                       | 807541321 |
| indagids                  | true      |
| datgeboorte               | 19660821  |
| indaggeboorte             | true   |
----
| geslachtsaand             | 2      |
| indaggeslachtsaand        | true   |
| vorigeanr                 | NULL   |
| indagnrverwijzing         | false  |
| datoverlijden             | NULL   |
| indagoverlijden           | false  |
| voornamen                 | Margareth |
| anr                       | NULL|
| bsn                       | NULL|
| indagids                  | false     |
| datgeboorte               | 19420831  |
| indaggeboorte             | true   |
----
| geslachtsaand             | 1      |
| indaggeslachtsaand        | true   |
| vorigeanr                 | NULL   |
| indagnrverwijzing         | false  |
| datoverlijden             | NULL   |
| indagoverlijden           | false  |
| voornamen                 | Piet   |
| anr                       | 4826245409|
| bsn                       | 487545801 |
| indagids                  | true      |
| datgeboorte               | 19650217  |
| indaggeboorte             | true   |
----
| geslachtsaand             | 1      |
| indaggeslachtsaand        | true   |
| vorigeanr                 | NULL   |
| indagnrverwijzing         | false  |
| datoverlijden             | NULL   |
| indagoverlijden           | false  |
| voornamen                 | Willy   |
| anr                       | 8039316257|
| bsn                       | 535750985 |
| indagids                  | true      |
| datgeboorte               | NULL      |
| indaggeboorte             | false   |

Then in kern heeft select dataanv, dateinde, indag
             from kern.relatie where srt = '1'
de volgende gegevens:
| veld                      | waarde |
| dataanv                   | 19920808      |
| dateinde                  | 19950808      |
| indag                     | true          |




