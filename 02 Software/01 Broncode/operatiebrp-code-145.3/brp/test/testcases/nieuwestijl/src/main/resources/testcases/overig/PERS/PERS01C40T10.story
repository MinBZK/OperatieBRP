Meta:
@status                 Klaar
@usecase                UCS-BY.1.HG


Narrative: Persoonsbeelden GBA en BRP (ontbinding en ontrelateren)

Scenario:   1. DB init scenario om uitgangssituatie te zetten
            preconditie

Given alle personen zijn verwijderd

Scenario:   2. Ontrelateren bij Ontbinding huwelijk in Nederland
            LT: PERS01C40T10

Given enkel initiele vulling uit bestand /LO3PL-PERS/PERS01C40T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-PERS/PERS01C40T10-002.xls

When voer een bijhouding uit PERS01C40T10a.xml namens partij 'Gemeente BRP 1'

Given pas laatste relatie van soort 1 aan tussen persoon 663844745 en persoon 944441993 met relatie id 30010001 en betrokkenheid id 30010001

Then is in de database de persoon met bsn 663844745 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 944441993 wel als PARTNER betrokken bij een HUWELIJK

Given de database is aangepast met: delete from autaut.his_bijhouderfiatuitz where id =99999
And de database is aangepast met: delete from autaut.bijhouderfiatuitz where id =99999
And de database is aangepast met: insert into autaut.bijhouderfiatuitz (id,bijhouder, datingang, dateinde, bijhouderbijhvoorstel, srtdoc, srtadmhnd, indblok) values (99999, (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 3') and rol=2), null , null , (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2), null, null, null)
And de database is aangepast met: insert into autaut.his_bijhouderfiatuitz (id, bijhouderfiatuitz, tsreg, tsverval, datingang, dateinde, bijhouderbijhvoorstel, srtdoc, srtadmhnd, indblok) values(99999, 99999, now() at time zone 'UTC', null, 19990101, null, (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2), null, null, null)

When voer een bijhouding uit PERS01C40T10b.xml namens partij 'Gemeente BRP 1'

Scenario:   3. DB reset scenario om de aangepaste data weer terug te zetten
            postconditie

Given de database is aangepast met: delete from autaut.his_bijhouderfiatuitz where id =99999
And de database is aangepast met: delete from autaut.bijhouderfiatuitz where id =99999