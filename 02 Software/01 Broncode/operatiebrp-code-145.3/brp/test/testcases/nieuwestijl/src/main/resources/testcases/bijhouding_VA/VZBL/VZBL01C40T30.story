Meta:
@status                 Klaar
@regels                 R2370
@usecase                UCS-BY.0.VA

Narrative: R2370 Emigrant moet zelf ouder zijn als aangever meerderjarig kind is

Scenario:   R2370 Aangever is een kind en kind is jonger dan 18 en geen actueel of ontbonden huwelijk
            LT: VZBL01C40T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL01C40T30.xls

And de database is aangepast met: update kern.his_persgeboorte set datgeboorte=to_number(to_char(current_date - interval '17 years','YYYYMMDD'),'9999999999.99') where pers in (select id from kern.pers where voornamen='Jade');

When voer een bijhouding uit VZBL01C40T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL01C40T30.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R
