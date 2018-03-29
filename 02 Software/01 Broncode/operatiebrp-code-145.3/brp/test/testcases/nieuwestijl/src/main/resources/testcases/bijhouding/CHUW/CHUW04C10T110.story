Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: Verwerking van ontrelateren relaties

Scenario:   1. DB init scenario om uitgangssituatie te zetten
            preconditie

Given alle personen zijn verwijderd

Scenario:   2. Ontrelateren bij Ontbinding huwelijk in Nederland
            LT: CHUW04C10T110

Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW04C10T110-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW04C10T110-002.xls

When voer een bijhouding uit CHUW04C10T110a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 791172041 en persoon 309644793 met relatie id 30010001 en betrokkenheid id 30010001

Then is in de database de persoon met bsn 791172041 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 309644793 wel als PARTNER betrokken bij een HUWELIJK

Given de database is aangepast met: delete from autaut.his_bijhouderfiatuitz where id =99999
And de database is aangepast met: delete from autaut.bijhouderfiatuitz where id =99999
And de database is aangepast met: insert into autaut.bijhouderfiatuitz (id,bijhouder, datingang, dateinde, bijhouderbijhvoorstel, srtdoc, srtadmhnd, indblok) values (99999, (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 3') and rol=2), null , null , (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2), null, null, null)
And de database is aangepast met: insert into autaut.his_bijhouderfiatuitz (id, bijhouderfiatuitz, tsreg, tsverval, datingang, dateinde, bijhouderbijhvoorstel, srtdoc, srtadmhnd, indblok) values(99999, 99999, now() at time zone 'UTC', null, 19990101, null, (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2), null, null, null)

When voer een bijhouding uit CHUW04C10T110b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn i:791172041 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn i:309644793 niet als PARTNER betrokken bij een HUWELIJK

Scenario:   3. DB reset scenario om de aangepaste data weer terug te zetten
            postconditie

Given de database is aangepast met: delete from autaut.his_bijhouderfiatuitz where id =99999
And de database is aangepast met: delete from autaut.bijhouderfiatuitz where id =99999

Scenario:   4. Corrigeer Huwelijk
            postconditie

Given pas laatste relatie van soort 1 aan tussen persoon 791172041 en persoon 309644793 met relatie id 30010002 en betrokkenheid id 30010002

!-- De niet-vervallen rij gereed maken om als voorkomensleutel te gebruiken
Given de database is aangepast met: update kern.his_relatie
                                    set    id=9998
				    where  relatie=30010002
				    and    tsverval is null

When voer een bijhouding uit CHUW04C10T110c.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer kern.relatie
Then in kern heeft select srt,
                          (select code from kern.rdneinderelatie where id=rdneinde),
			  dateinde,
                         (select naam as gemeinde from kern.gem where id=gemeinde),
                          wplnaameinde,
			  omsloceinde,
                         (select naam as landgebiedeinde from kern.landgebied where id=landgebiedeinde)
                   from   kern.relatie
                   where  id=30010002 de volgende gegevens:
| veld            | waarde                   |
| srt             | 1                        |
| code            | Z                        |
| dateinde        | 20161122                 |
| gemeinde        | Gemeente BRP 1           |
| wplnaameinde    | Hoogerheide              |
| omsloceinde     | omschrijvingLocatieEinde |
| landgebiedeinde | Nederland                |

!-- Controleer kern.his_relatie
Then in kern heeft select CASE
                              WHEN hr.tsreg IS NOT NULL THEN
			          'gevuld'
			  END as tsreg,
                          sa.naam as soortActie,
			  CASE
			      WHEN tsverval in (
			                          select tsreg
						  from   kern.his_relatie
						  where  relatie=30010002
						  and    rdneinde in (
						                        select id
									from   kern.rdneinderelatie
									where  code='S'
								     )
					       )  then
			          'tsreg van Registratie einde huwelijk'
			      WHEN tsverval in (
			                          select tsreg
						  from   kern.his_relatie
						  where  relatie=30010002
						  and    rdneinde in (
						                        select id
									from   kern.rdneinderelatie
									where  code='Z'
								     )
					       )  then
			          'tsreg van Correctieregistratie relatie'
			      WHEN tsverval is null then 'leeg'
			  END as tsverval,
                          (
                             select sa.naam as actieVervalNaam
                             from   kern.actie    a
                             join   kern.srtactie sa
                             on     a.srt = sa.id
                             and    a.id  = hr.actieverval
                          ),
			  (select code as rdneinde from kern.rdneinderelatie where id=hr.rdneinde),
			  hr.dateinde,
			  (select g.naam as gemeenteeinde from kern.gem g where g.id=hr.gemeinde),
			  hr.wplnaameinde,
			  hr.omsloceinde,
			  (select naam as landgebiedeinde from kern.landgebied where id=hr.landgebiedeinde)
		   from   kern.his_relatie hr
		   join   kern.actie a
		   on     a.id=hr.actieinh
		   join   kern.srtactie sa
		   on     sa.id=a.srt
		   where  relatie=30010002
		   order by sa.naam de volgende gegevens:
| veld            | waarde                       |
| tsreg           | gevuld                       |
| soortActie      | Correctieregistratie relatie |
| tsverval        | leeg                         |
| actievervalnaam | NULL                         |
| rdneinde        | Z                            |
| dateinde        | 20161122                     |
| gemeenteeinde   | Gemeente BRP 1               |
| wplnaameinde    | Hoogerheide                  |
| omsloceinde     | omschrijvingLocatieEinde     |
| landgebiedeinde | Nederland                    |
----
| tsreg           | gevuld                                 |
| soortActie      | Ontrelateren                           |
| tsverval        | tsreg van Registratie einde huwelijk   |
| actievervalnaam | Registratie einde huwelijk             |
| rdneinde        | NULL                                   |
| dateinde        | NULL                                   |
| gemeenteeinde   | NULL                                   |
| wplnaameinde    | NULL                                   |
| omsloceinde     | NULL                                   |
| landgebiedeinde | NULL                                   |
----
| tsreg           | gevuld                                 |
| soortActie      | Registratie einde huwelijk             |
| tsverval        | tsreg van Correctieregistratie relatie |
| actievervalnaam | Correctieverval relatie                |
| rdneinde        | S                                      |
| dateinde        | 20160601                               |
| gemeenteeinde   | Gemeente BRP 1                         |
| wplnaameinde    | Nieuwveen                              |
| omsloceinde     | NULL                                   |
| landgebiedeinde | Nederland                              |