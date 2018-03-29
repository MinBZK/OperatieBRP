Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Verwerking Correctie geregistreerd partnerschap

Scenario:   Nederlandse gegevens corrigeren met maximaal aantal velden voor aanvang Nederland
            LT: CGPS04C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CGPS/CGPS04C10T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CGPS/CGPS04C10T10-002.xls

When voer een bijhouding uit CGPS04C10T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CGPS/expected/CGPS04C10T10a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Given pas laatste relatie van soort 2 aan tussen persoon 326018281 en persoon 268177673 met relatie id 30010001 en betrokkenheid id 30010001
And de database is aangepast met: update kern.his_relatie
                                  set    id = 9999
				  where  id = (
				                  select hr.id
						  from   kern.his_relatie hr
						  join   kern.relatie r
						  on     r.id = hr.relatie
						  where  r.srt = 2
						  and    hr.dataanv = 20160510
					      )

Then is in de database de persoon met bsn 326018281 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 268177673 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then in kern heeft select statuslev from kern.admhnd where partij = 27012 and tslev is null de volgende gegevens:
| veld                      | waarde |
| statuslev                 | 1      |

Then in kern heeft select toelichtingontlening from kern.admhnd where toelichtingontlening is not null de volgende gegevens:
| veld                      | waarde                |
| toelichtingontlening      | test toelichting      |

When voer een bijhouding uit CGPS04C10T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CGPS/expected/CGPS04C10T10b.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R

!-- Controleer verantwoordingsgegevens van de ActieBron
Then in kern heeft select    count(1),
                             sdoc.naam
                   from      kern.actiebron ab
                   join      kern.actie ainh
		   on        ainh.id = ab.actie
                   left join kern.srtactie sainh
		   on        ainh.srt = sainh.id
                   left join kern.doc doc
		   on        doc.id = ab.doc
                   left join kern.srtdoc sdoc
		   on        sdoc.id = doc.srt
                   where     sainh.naam in ('Correctieverval relatie','Correctieregistratie relatie')
		   group by  sdoc.naam de volgende gegevens:
| veld   | waarde                            |
| count  | 2                                 |
| naam   | Akte van partnerschapsregistratie |

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
| veld                      | waarde                                    |
| actieinhoud               | Correctieverval relatie                   |
| actieverval               | NULL                                      |
| voornamen                 | Libby                                     |
| admhndnaam                | Correctie geregistreerd partnerschap      |
| sorteervolgorde           | 1                                         |
----
| actieinhoud               | Correctieverval relatie                   |
| actieverval               | NULL                                      |
| voornamen                 | Piet                                      |
| admhndnaam                | Correctie geregistreerd partnerschap      |
| sorteervolgorde           | 1                                         |

!-- Controleer kern.relatie
Then in kern heeft select srt,
                          dataanv,
                         (select naam as gemeente from kern.gem where id=gemaanv),
                         (select naam as land from kern.landgebied where id=landgebiedaanv)
                   from   kern.relatie
                   where  id=30010001 de volgende gegevens:
| veld     | waarde         |
| srt      | 2              |
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
			  hr.wplnaamaanv,
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
| wplnaamaanv       | Huizen                         |
| land              | Nederland                      |
----
| tsreg             | gevuld                                                   |
| soortActie        | Registratie aanvang geregistreerd partnerschap           |
| tsverval          | tsreg van Correctieregistratie relatie                   |
| actieVervalNaam   | Correctieverval relatie                                  |
| nadereaandverval  | NULL                                                     |
| dataanv           | 20160510                                                 |
| gemeente          | Gemeente BRP 1                                           |
| wplnaamaanv       | NULL                                                     |
| land              | Nederland                                                |

!-- Controleer kern.betr
Then in kern heeft select rol from kern.betr where relatie=30010001 de volgende gegevens:
| veld | waarde |
| rol  | 3      |
| rol  | 3      |
