Meta:
@status                 Klaar
@usecase                UCS-BY.1.ON


Narrative: Triggers voor ontrelateren

Scenario:   1. DB init scenario om uitgangssituatie te zetten
            preconditie

Given alle personen zijn verwijderd

Scenario:   2. Opnieuw indienen icm Indiener is bijhoudingspartij
            LT: ONTR01C10T10

Given enkel initiele vulling uit bestand /LO3PL-ONTR/ONTR01C10T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-ONTR/ONTR01C10T10-002.xls

When voer een bijhouding uit ONTR01C10T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONTR/expected/ONTR01C10T10a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Given pas laatste relatie van soort 1 aan tussen persoon 642653641 en persoon 813753545 met relatie id 30010001 en betrokkenheid id 30010001

Then is in de database de persoon met bsn 642653641 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 813753545 wel als PARTNER betrokken bij een HUWELIJK

Given de database is aangepast met: delete from autaut.his_bijhouderfiatuitz where id =99999
And de database is aangepast met: delete from autaut.bijhouderfiatuitz where id =99999
And de database is aangepast met: insert into autaut.bijhouderfiatuitz (id,bijhouder, datingang, dateinde, bijhouderbijhvoorstel, srtdoc, srtadmhnd, indblok) values (99999, (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 3') and rol=2), null , null , (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2), null, null, null)
And de database is aangepast met: insert into autaut.his_bijhouderfiatuitz (id, bijhouderfiatuitz, tsreg, tsverval, datingang, dateinde, bijhouderbijhvoorstel, srtdoc, srtadmhnd, indblok) values(99999, 99999, now() at time zone 'UTC', null, 19990101, null, (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2), null, null, null)

When voer een bijhouding uit ONTR01C10T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONTR/expected/ONTR01C10T10b.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

!-- Controleer nieuwe betrokkenheden
Then in kern heeft select count(1)
                   from kern.his_betr hr join kern.actie ainh on ainh.id = hr.actieinh LEFT join kern.actie av on av.id = hr.actieverval left join kern.srtactie sainh on ainh.srt = sainh.id left join kern.srtactie saav on av.srt = saav.id
                   where sainh.naam ='Ontrelateren' and av.srt is NULL de volgende gegevens:
| veld                      | waarde |
| count                     | 4      |

Scenario:   3. DB reset scenario om de aangepaste data weer terug te zetten
            postconditie

Given de database is aangepast met: delete from autaut.his_bijhouderfiatuitz where id =99999
And de database is aangepast met: delete from autaut.bijhouderfiatuitz where id =99999