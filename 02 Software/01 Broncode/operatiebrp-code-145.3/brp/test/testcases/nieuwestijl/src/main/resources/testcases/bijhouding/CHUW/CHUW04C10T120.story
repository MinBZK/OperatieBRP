Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Verwerking van ontrelateren relaties

Scenario:   1. DB init scenario om uitgangssituatie te zetten
            preconditie

Given alle personen zijn verwijderd

Scenario:   2. Ontrelateren bij Ontbinding huwelijk in Nederland
            LT: CHUW04C10T120

Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW04C10T120-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW04C10T120-002.xls

!-- Voltrekking huwelijk
When voer een bijhouding uit CHUW04C10T120a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 866171721 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 799335113 wel als PARTNER betrokken bij een HUWELIJK

Given de database is aangepast met: delete from autaut.his_bijhouderfiatuitz where id=99999
And de database is aangepast met: delete from autaut.bijhouderfiatuitz where id=99999
And de database is aangepast met: insert into autaut.bijhouderfiatuitz (id,bijhouder, datingang, dateinde, bijhouderbijhvoorstel, srtdoc, srtadmhnd, indblok) values (99999, (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 3') and rol=2), null , null , (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2), null, null, null)
And de database is aangepast met: insert into autaut.his_bijhouderfiatuitz (id, bijhouderfiatuitz, tsreg, tsverval, datingang, dateinde, bijhouderbijhvoorstel, srtdoc, srtadmhnd, indblok) values(99999, 99999, now() at time zone 'UTC', null, 19990101, null, (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2), null, null, null)

Scenario:   4. Corrigeer Huwelijk
            postconditie

Given pas laatste relatie van soort 1 aan tussen persoon 866171721 en persoon 799335113 met relatie id 30010002 en betrokkenheid id 30010002

!-- De niet-vervallen rij gereed maken om als voorkomensleutel te gebruiken
Given de database is aangepast met: update kern.his_relatie
                                    set    id=9998
				    where  relatie=30010002
				    and    tsverval is null

When voer een bijhouding uit CHUW04C10T120b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer administratieve handeling relateren
Then in kern heeft select count(1) from kern.admhnd where srt=132 de volgende gegevens:
| veld  | waarde |
| count | 1      |

Scenario:   3. DB reset scenario om de aangepaste data weer terug te zetten
            postconditie

Given de database is aangepast met: delete from autaut.his_bijhouderfiatuitz where id=99999
And de database is aangepast met: delete from autaut.bijhouderfiatuitz where id=99999
