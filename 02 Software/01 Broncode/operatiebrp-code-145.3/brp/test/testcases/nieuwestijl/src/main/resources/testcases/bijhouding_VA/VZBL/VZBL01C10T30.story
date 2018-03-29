Meta:
@status                 Klaar
@regels                 R2367
@usecase                UCS-BY.0.VA

Narrative: R2367 Migrant moet bevoegd zijn als aangever ingeschrevene is

Scenario:   R2367 Doe een bijhouding waarbij de aangever een Hoofd instelling is en de persoon minderjarig
            LT: VZBL01C10T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL01C10T30.xls

And de database is aangepast met: update kern.his_persgeboorte set datgeboorte=to_number(to_char(current_date - interval '15 years','YYYYMMDD'),'9999999999.99') where pers in (select id from kern.pers where voornamen='Anne');

When voer een bijhouding uit VZBL01C10T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL01C10T30.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R
