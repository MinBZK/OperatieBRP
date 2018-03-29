Meta:
@status                 Klaar
@regels                 R1930
@usecase                UCS-BY.0.VA

Narrative: R1930 Persoon wiens adres het betreft moet meerderjarig kind zijn als aangever ouder is

Scenario:   R1930 Ouder1 heeft een beëindigd Ouderschap. Ouder2 heeft een niet-beëindigd Ouderschap
            LT: VZIG01C50T80

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C50T80-001.xls

And de database is aangepast met: update kern.his_persgeboorte set datgeboorte=to_number(to_char(current_date - interval '18 years','YYYYMMDD'),'9999999999.99') where pers in (select id from kern.pers where voornamen='James');

When voer een bijhouding uit VZIG01C50T80.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C50T80.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 2      |