Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: Ongedaan maken huwelijk, hoofdactie verval huwelijk

Scenario:   1. DB init scenario om uitgangssituatie te zetten
            preconditie

Given alle personen zijn verwijderd

Scenario:   2. Ongedaan maken van een huwelijk (van de ontbonden relatie) dat ontrelateerd is
            LT: ONHW04C10T70

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

When voer een bijhouding uit ONHW04C10T70b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd



Then is in de database de persoon met bsn i:663844745 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn i:944441993 niet als PARTNER betrokken bij een HUWELIJK

Given pas laatste relatie van soort 1 aan tussen persoon 663844745 en persoon 944441993 met relatie id 50000002 en betrokkenheid id 50000002

When voer een bijhouding uit ONHW04C10T70c.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer vervallen relatie, betrokkenheden en personen
Then in kern heeft select r.srt as srtrel, r.gemaanv, r.indag as indagrel, sainh.naam as actieinhoud, saav.naam as actieVerval, p.voornamen, b.indag as indagbetr, p.srt as srtpers, p.indagids as indagpers
                                      from kern.relatie r
                                      left join kern.his_relatie hr on hr.relatie = r.id
                                      left join kern.betr b on r.id = b.relatie
                                      left join kern.pers p on p.id = b.pers
                                      left join kern.actie ainh on ainh.id = hr.actieinh
                                      left join kern.srtactie sainh on ainh.srt = sainh.id
                                      left join kern.actie av on av.id = hr.actieverval
                                      left join kern.srtactie saav on av.srt = saav.id
                                      where saav.naam = 'Verval huwelijk' order by p.srt de volgende gegevens:
| veld              | waarde                         |
| srtrel            | 1                              |
| gemaanv           | NULL                           |
| indagrel          | false                          |
| actieinhoud       | Registratie einde huwelijk     |
| actieverval       | Verval huwelijk                |
| voornamen         | Piet                           |
| indagbetr         | false                          |
| srtpers           | 1                              |
| indagpers         | true                           |
----
| srtrel            | 1                              |
| gemaanv           | NULL                           |
| indagrel          | false                          |
| actieinhoud       | Registratie einde huwelijk     |
| actieverval       | Verval huwelijk                |
| voornamen         | NULL                           |
| indagbetr         | false                          |
| srtpers           | 2                              |
| indagpers         | false                          |


Scenario:   3. DB reset scenario om de aangepaste data weer terug te zetten
            postconditie

Given de database is aangepast met: delete from autaut.his_bijhouderfiatuitz where id =99999
And de database is aangepast met: delete from autaut.bijhouderfiatuitz where id =99999