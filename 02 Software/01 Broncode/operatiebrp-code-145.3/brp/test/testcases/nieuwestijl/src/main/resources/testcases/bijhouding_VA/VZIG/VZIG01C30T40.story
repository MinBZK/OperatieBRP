Meta:
@status                 Klaar
@regels                 R1929
@usecase                UCS-BY.0.VA

Narrative: R1929 Persoon wiens adres het betreft moet zelf ouder zijn als aangever meerderjarig kind is

Scenario:   R1929 De adreshouder is een ouder van een meerderjarig kind. Leeftijd lager dan 18 en ontbonden huwelijk aanwezig
            LT: VZIG01C30T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C30T40-001.xls
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C30T40-002.xls

And de database is aangepast met: update kern.his_persgeboorte set datgeboorte=to_number(to_char(current_date - interval '17 years','YYYYMMDD'),'9999999999.99') where pers in (select id from kern.pers where voornamen='Marianne');

And de database is aangepast met: update kern.betr set pers=(select id from kern.pers where voornamen='Marianne') where pers in (select id from kern.pers where voornamen='Elizabeth' and betr.pers=pers.id)

When voer een bijhouding uit VZIG01C30T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C30T40.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld  | waarde |
| count | 3      |
