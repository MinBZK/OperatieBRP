Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Verwerking Correctie Geregistreerd partnerschap

Scenario:   Gegevens einde relatie corrigeren einde buitenland
            LT: CGPS04C10T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CGPS/CGPS04C10T40-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CGPS/CGPS04C10T40-002.xls

!-- Aangaan Geregistreerd partnerschap in buitenland
When voer een bijhouding uit CGPS04C10T40a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CGPS/expected/CGPS04C10T40a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Given pas laatste relatie van soort 2 aan tussen persoon 236312601 en persoon 136888161 met relatie id 30010001 en betrokkenheid id 30010001
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

Then is in de database de persoon met bsn 236312601 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 136888161 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then in kern heeft select statuslev from kern.admhnd where partij = 27012 and tslev is null de volgende gegevens:
| veld                      | waarde |
| statuslev                 | 1      |

!-- Einde Geregistreerd partnerschap in buitenland
When voer een bijhouding uit CGPS04C10T40b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CGPS/expected/CGPS04C10T40b.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R
Then is in de database de persoon met bsn 236312601 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

!-- De niet-vervallen rij gereed maken om als voorkomensleutel te gebruiken
Given de database is aangepast met: update kern.his_relatie
                                    set    id=9998
				    where  relatie=30010001
				    and    id<>9999
				    and    tsverval is null

!-- Correctie registratie geregistreerd partnerschap
When voer een bijhouding uit CGPS04C10T40c.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CGPS/expected/CGPS04C10T40c.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R

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
| veld                      | waarde                               |
| actieinhoud               | Correctieverval relatie              |
| actieverval               | NULL                                 |
| voornamen                 | Libby                                |
| admhndnaam                | Correctie geregistreerd partnerschap |
| sorteervolgorde           | 1                                    |
----
| actieinhoud               | Correctieverval relatie              |
| actieverval               | NULL                                 |
| voornamen                 | Piet                                 |
| admhndnaam                | Correctie geregistreerd partnerschap |
| sorteervolgorde           | 1                                    |

!-- Controleer kern.relatie
Then in kern heeft select srt,
                          dataanv,
                          blregioaanv,
			 (select naam as landgebiedaanv from kern.landgebied where id=landgebiedaanv),
			 (select code as rdneinde from kern.rdneinderelatie where id=rdneinde),
			  dateinde,
                          blplaatseinde,
			  blregioeinde,
			  omsloceinde,
                         (select naam as landgebiedeinde from kern.landgebied where id=landgebiedeinde)
                   from   kern.relatie
                   where  id=30010001 de volgende gegevens:
| veld            | waarde                   |
| srt             | 2                        |
| dataanv         | 20160511                 |
| blregioaanv     | buitenlandseRegioAanvang |
| landgebiedaanv  | Canada                   |
| rdneinde        | S                        |
| dateinde        | 20161122                 |
| blplaatseinde   | buitenlandsePlaatsEinde  |
| blregioeinde    | buitenlandseRegioEinde   |
| omsloceinde     | omschrijvingLocatieEinde |
| landgebiedeinde | Frankrijk                |

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
						  and    rdneinde in (
						                        select id
									from   kern.rdneinderelatie
									where  code='A'
								     )
					       )  then
			          'tsreg van Registratie einde geregistreerd partnerschap'
			      WHEN tsverval in (
			                          select tsreg
						  from   kern.his_relatie
						  where  relatie=30010001
						  and    rdneinde in (
						                        select id
									from   kern.rdneinderelatie
									where  code='S'
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
			  hr.blplaatseinde,
			  hr.blregioeinde,
			  hr.omsloceinde,
			  (select naam as landgebiedeinde from kern.landgebied where id=hr.landgebiedeinde)
		   from   kern.his_relatie hr
		   join   kern.actie a
		   on     a.id=hr.actieinh
		   join   kern.srtactie sa
		   on     sa.id=a.srt
		   where  relatie=30010001
		   order by sa.naam de volgende gegevens:
| veld            | waarde                       |
| tsreg           | gevuld                       |
| soortActie      | Correctieregistratie relatie |
| tsverval        | leeg                         |
| actievervalnaam | NULL                         |
| rdneinde        | S                            |
| dateinde        | 20161122                     |
| blplaatseinde   | buitenlandsePlaatsEinde      |
| blregioeinde    | buitenlandseRegioEinde       |
| omsloceinde     | omschrijvingLocatieEinde     |
| landgebiedeinde | Frankrijk                    |
----
| tsreg           | gevuld                                                 |
| soortActie      | Registratie aanvang geregistreerd partnerschap         |
| tsverval        | tsreg van Registratie einde geregistreerd partnerschap |
| actievervalnaam | Registratie einde geregistreerd partnerschap           |
| rdneinde        | NULL                                                   |
| dateinde        | NULL                                                   |
| blplaatseinde   | NULL                                                   |
| blregioeinde    | NULL                                                   |
| omsloceinde     | NULL                                                   |
| landgebiedeinde | NULL                                                   |
----
| tsreg           | gevuld                                       |
| soortActie      | Registratie einde geregistreerd partnerschap |
| tsverval        | tsreg van Correctieregistratie relatie       |
| actievervalnaam | Correctieverval relatie                      |
| rdneinde        | A                                            |
| dateinde        | 20160511                                     |
| blplaatseinde   | Barcelona                                    |
| blregioeinde    | Catalonie                                    |
| omsloceinde     | NULL                                         |
| landgebiedeinde | Verzonnenland                                |

!-- Controleer kern.betr
Then in kern heeft select rol from kern.betr where relatie=30010001 de volgende gegevens:
| veld | waarde |
| rol  | 3      |
| rol  | 3      |
