Meta:
@status                 Klaar
@regels                 R2368
@usecase                UCS-BY.0.VA

Narrative: R2368 Emigrant moet meerderjarig kind zijn als aangever ouder is

Scenario:   R2368 Emigrant is 18 jaar en heeft wel een ouder
            LT: VZBL01C20T70

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL01C20T70.xls

And de database is aangepast met: update kern.his_persgeboorte set datgeboorte=to_number(to_char(current_date - interval '18 years','YYYYMMDD'),'9999999999.99') where pers in (select id from kern.pers where voornamen='Anne');

When voer een bijhouding uit VZBL01C20T70.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL01C20T70.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R
