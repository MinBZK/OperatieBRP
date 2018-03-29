Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: Ongedaan maken huwelijk, hoofdactie verval huwelijk

Scenario:   1. DB init scenario om uitgangssituatie te zetten
            preconditie

Given alle personen zijn verwijderd

Scenario:   2. Ongedaanmaking huwelijk triggert ontrelateren
            LT: ONHW04C10T100

Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW04C10T70-001.xls
Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW04C10T70-002.xls

When voer een bijhouding uit ONHW04C10T70a.xml namens partij 'Gemeente BRP 1'

Given pas laatste relatie van soort 1 aan tussen persoon 663844745 en persoon 944441993 met relatie id 30010001 en betrokkenheid id 30010001

Then is in de database de persoon met bsn 663844745 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 944441993 wel als PARTNER betrokken bij een HUWELIJK

Given de database is aangepast met: delete from autaut.his_bijhouderfiatuitz where id =99999
And de database is aangepast met: delete from autaut.bijhouderfiatuitz where id =99999
And de database is aangepast met: insert into autaut.bijhouderfiatuitz (id,bijhouder, datingang, dateinde, bijhouderbijhvoorstel, srtdoc, srtadmhnd, indblok) values (99999, (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 3') and rol=2), null , null , (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2), null, null, null)
And de database is aangepast met: insert into autaut.his_bijhouderfiatuitz (id, bijhouderfiatuitz, tsreg, tsverval, datingang, dateinde, bijhouderbijhvoorstel, srtdoc, srtadmhnd, indblok) values(99999, 99999, now() at time zone 'UTC', null, 19990101, null, (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2), null, null, null)

Given pas laatste relatie van soort 1 aan tussen persoon 663844745 en persoon 944441993 met relatie id 50000002 en betrokkenheid id 50000002

When voer een bijhouding uit ONHW04C10T100c.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer aanwezigheid AH Ontrelateren
Then in kern heeft select sad.naam from kern.admhnd ad
                   join kern.srtadmhnd sad on sad.id = ad.srt
                   where sad.naam = 'Ontrelateren' de volgende gegevens:
| veld   | waarde                                  |
| naam   | Ontrelateren                            |


Scenario:   3. DB reset scenario om de aangepaste data weer terug te zetten
            postconditie

Given de database is aangepast met: delete from autaut.his_bijhouderfiatuitz where id =99999
And de database is aangepast met: delete from autaut.bijhouderfiatuitz where id =99999