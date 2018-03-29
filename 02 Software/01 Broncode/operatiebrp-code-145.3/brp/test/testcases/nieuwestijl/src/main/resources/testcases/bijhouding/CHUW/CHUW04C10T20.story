Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Verwerking Correctie Huwelijk

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: insert into kern.rechtsgrond
                                    (
                                        "code",
					"oms",
					"indleidttotstrijdigheid",
				        "dataanvgel"
				    )
				    select '111',
				           'Omschrijving rechtsgrond',
					    true,
					    to_number((to_char(now() - interval '1 day', 'YYYYMMDD')), '99999999')
				    where not exists
				    (
					select id
					from   kern.rechtsgrond
				        where  code='111'
				    )

Given maak bijhouding caches leeg

Scenario:   2. Correctie Huwelijk met Nadere aanduiding verval S en rechtsgrond
            LT: CHUW04C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW04C10T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW04C10T20-002.xls

When voer een bijhouding uit CHUW04C10T20a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CHUW/expected/CHUW04C10T20a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Given pas laatste relatie van soort 1 aan tussen persoon 435048521 en persoon 774869513 met relatie id 30010001 en betrokkenheid id 30010001
And de database is aangepast met: update kern.his_relatie set id = 9999 where id = (select hr.id from kern.his_relatie hr join kern.relatie r on r.id = hr.relatie where r.srt = 1 and hr.dataanv = 20160510)

Then is in de database de persoon met bsn 435048521 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 774869513 wel als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select statuslev from kern.admhnd where partij = 27012 and tslev is null de volgende gegevens:
| veld                      | waarde |
| statuslev                 | 1      |

Then in kern heeft select toelichtingontlening from kern.admhnd where toelichtingontlening is not null de volgende gegevens:
| veld                      | waarde                |
| toelichtingontlening      | test toelichting      |

When voer een bijhouding uit CHUW04C10T20b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CHUW/expected/CHUW04C10T20b.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R

!-- Controleer verantwoordingsgegevens van de ActieBron
Then in kern heeft select sd.naam,
                          rg.code
	           from   kern.actiebron ab
		   join   kern.doc d
		   on     ab.doc=d.id
		   join   kern.srtdoc sd
		   on     d.srt=sd.id
		   join   kern.rechtsgrond rg
		   on     rg.id=ab.rechtsgrond
		   where  actie in (
		                      select id
				      from   kern.actie
				      where  srt in (
				                       select id
						       from   kern.srtactie
						       where  naam='Correctieverval relatie'
						       or     naam='Correctieregistratie relatie'
						    )
				   )
 de volgende gegevens:
| veld        | waarde        |
| naam        | Huwelijksakte |
| code        | 111           |
----
| naam        | Huwelijksakte |
| code        | 111           |

!-- Controleer betrokken personen zijn gemarkeerd als bijgehouden
Then in kern heeft select    sainh.naam           as actieInhoud,
                             saav.naam            as actieVerval,
			     p.voornamen,
			     sa.naam              as AdmhndNaam,
			     hpaf.sorteervolgorde
                   from      kern.his_persafgeleidadministrati hpaf
                   join      kern.actie ainh
		   on        ainh.id = hpaf.actieinh
                   left join kern.actie av
		   on        av.id = hpaf.actieverval
                   left join kern.srtactie sainh
		   on        ainh.srt = sainh.id
                   left join kern.srtactie saav
		   on        av.srt = saav.id
                   left join kern.pers p
		   on        hpaf.pers = p.id
                   left join kern.admhnd a
		   on        hpaf.admhnd = a.id
                   left join kern.srtadmhnd sa
		   on        a.srt = sa.id
                   where     sainh.naam ='Correctieverval relatie'
		   or        sainh.naam ='Correctieregistratie relatie'
                   order by  p.voornamen de volgende gegevens:
| veld                      | waarde                  |
| actieinhoud               | Correctieverval relatie |
| actieverval               | NULL                    |
| voornamen                 | Libby                   |
| admhndnaam                | Correctie huwelijk      |
| sorteervolgorde           | 1                       |
----
| actieinhoud               | Correctieverval relatie |
| actieverval               | NULL                    |
| voornamen                 | Piet                    |
| admhndnaam                | Correctie huwelijk      |
| sorteervolgorde           | 1                       |

!-- Controleer kern.relatie
Then in kern heeft select srt,
                          dataanv,
                         (select naam as gemeente from kern.gem where id=gemaanv),
                         (select naam as land from kern.landgebied where id=landgebiedaanv)
                   from   kern.relatie
                   where  id=30010001 de volgende gegevens:
| veld     | waarde         |
| srt      | 1              |
| dataanv  | 20160511       |
| gemeente | Gemeente BRP 1 |
| land     | Nederland      |

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
						  where  relatie=30010001
					       )  then
			          'tsreg van Correctieregistratie relatie'
			  END as tsverval,
                          (
                             select sa.naam as actieVervalNaam
                             from   kern.actie    a
                             join   kern.srtactie sa
                             on     a.srt = sa.id
                             and    a.id  = hr.actieverval
                          ),
			  hr.nadereaandverval,
			  hr.dataanv,
			  (select g.naam as gemeente from kern.gem g where g.id=hr.gemaanv),
			  (select naam as land from kern.landgebied where id=hr.landgebiedaanv)
		   from   kern.his_relatie hr
		   join   kern.actie a
		   on     a.id=hr.actieinh
		   join   kern.srtactie sa
		   on     sa.id=a.srt
		   where  relatie=30010001
		   order by sa.naam de volgende gegevens:
| veld              | waarde                         |
| tsreg             | gevuld                         |
| soortActie        | Correctieregistratie relatie   |
| tsverval          | NULL                           |
| actieVervalNaam   | NULL                           |
| nadereaandverval  | NULL                           |
| dataanv           | 20160511                       |
| gemeente          | Gemeente BRP 1                 |
| land              | Nederland                      |
----
| tsreg             | gevuld                                 |
| soortActie        | Registratie aanvang huwelijk           |
| tsverval          | tsreg van Correctieregistratie relatie |
| actieVervalNaam   | Correctieverval relatie                |
| nadereaandverval  | S                                      |
| dataanv           | 20160510                               |
| gemeente          | Gemeente BRP 1                         |
| land              | Nederland                              |

!-- Controleer kern.betr
Then in kern heeft select rol from kern.betr where relatie=30010001 de volgende gegevens:
| veld | waarde |
| rol  | 3      |
| rol  | 3      |

Scenario: 3. DB reset
              postconditie

Given de database is aangepast met: delete from kern.actiebron where rechtsgrond in (select id from kern.rechtsgrond where code='111')
Given de database is aangepast met: delete from kern.rechtsgrond where code='111'

Given maak bijhouding caches leeg