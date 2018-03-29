Meta:
@status                 Klaar
@regels                 R2370
@usecase                UCS-BY.0.VA

Narrative: R2370 Emigrant moet zelf ouder zijn als aangever meerderjarig kind is

Scenario:   R2370 Aangever is een kind en kind is jonger dan 18 en heeft een huwelijk in de toekomst
            LT: VZBL01C40T70

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL01C40T70.xls

And de database is aangepast met: update kern.his_persgeboorte set datgeboorte=to_number(to_char(current_date - interval '17 years','YYYYMMDD'),'9999999999.99') where pers in (select id from kern.pers where voornamen='Jade');

!-- Ontkoppel het ontbonden huwelijk van de ouder en koppel die aan het kind. Gevolg: het kind heeft een ontbonden huwelijk.
And de database is aangepast met: update kern.betr set pers=(select id from kern.pers where voornamen='Jade') where rol=3 and pers in (select id from kern.pers where voornamen='Anne');

When voer een bijhouding uit VZBL01C40T70.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL01C40T70.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R
