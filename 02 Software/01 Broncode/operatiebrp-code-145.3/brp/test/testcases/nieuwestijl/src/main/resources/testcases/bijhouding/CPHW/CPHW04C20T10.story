Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Verwerking Correctie partnergegevens huwelijk correctie Geboorte Nederland.

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

Scenario:   2. Correctie partnergegevens huwelijk correctieverval en correctieregistratie Geboorte
            LT: CPHW04C20T10

Given alle personen zijn verwijderd

!-- Vulling van de partners
Given enkel initiele vulling uit bestand /LO3PL-CPHW/CPHW04C20T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CPHW/CPHW04C20T10-002.xls

Then heeft $PERSOON_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen='Libby'
Then heeft $PARTNER_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen='Piet'

!-- Voltrekking huwelijk
When voer een bijhouding uit CPHW04C20T10a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 731198281 en persoon 330095353 met relatie id 30010002 en betrokkenheid id 30010001
Given pas laatste relatie van soort 1 aan tussen persoon 330095353 en persoon 731198281 met relatie id 30010001 en betrokkenheid id 30010002
And de database is aangepast met: update kern.his_relatie set id = 9999 where id = (select hr.id from kern.his_relatie hr join kern.relatie r on r.id = hr.relatie where r.srt = 1 and hr.dataanv = 20160329)

!-- Voorkomensleutel voor geboorte
And de database is aangepast met: update kern.his_persgeboorte set id = 9998 where pers in (select id from kern.pers where voornamen='Piet')

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Libby' and r.srt=1
Then heeft $BETROKKENHEID_PERS_ID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Libby' and r.srt=1
Then heeft $BETROKKENHEID_PARTNER_ID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Piet' and r.srt=1

Then is in de database de persoon met bsn 731198281 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 330095353 wel als PARTNER betrokken bij een HUWELIJK

!-- Wijzigen geboortegegevens van de ingeschreven partner
When voer een bijhouding uit CPHW04C20T10b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CPHW/expected/CPHW04C20T10.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R

!-- Controleer dat er ontrelateerd is
Then in kern heeft select sa.naam  as soortActie,
                          sah.naam as soortAH
                   from   kern.actie    a
                   join   kern.srtactie sa
                   on     a.srt=sa.id
                   join   kern.admhnd ah
                   on     a.admhnd=ah.id
                   join   kern.srtadmhnd sah
                   on     ah.srt=sah.id
                   where  sa.naam <> 'Conversie GBA'
		   order  by soortActie de volgende gegevens:
| veld       | waarde                                     |
| soortActie | Correctieregistratie geboorte gerelateerde |
| soortAH    | Correctie partnergegevens huwelijk         |
----
| soortActie | Correctieverval geboorte gerelateerde      |
| soortAH    | Correctie partnergegevens huwelijk         |
----
| soortActie | Ontrelateren                               |
| soortAH    | Ontrelateren                               |
----
| soortActie | Registratie aanvang huwelijk               |
| soortAH    | Voltrekking huwelijk in Nederland          |

!-- Controleer dat de pseudo-partner op de PL van de ingeschreven hoofdpersoon in kern.his_persgeboorte de nieuwe geboortegegevens bezit
Then in kern heeft select datgeboorte,
                          (select naam from kern.gem g where g.id=pg.gemgeboorte) as gemgeboorte,
			   wplnaamgeboorte,
			  (select naam from kern.landgebied l where l.id=pg.landgebiedgeboorte) as landgebiedgeboorte
                   from   kern.his_persgeboorte pg where pers in
		          (
			      select id
			      from   kern.pers
			      where  voornamen = 'Piet'
			      and    srt       = 2
		          )
                   and pers in
                   (
                       select pers from kern.betr b where relatie in
                       (
                           select r.id
			   from   kern.relatie r
			   join   kern.betr    b
			   on     r.id=b.relatie
			   and    b.pers in
                           (
                               select id
			       from   kern.pers
			       where  voornamen = 'Libby'
			       and    srt       = 1
                           )
                       )
                   )
                   order by datgeboorte de volgende gegevens:
| veld               | waarde     |
| datgeboorte        | 19600821   |
| gemgeboorte        | Groningen  |
| wplnaamgeboorte    | NULL       |
| landgebiedgeboorte | Nederland  |
----
| datgeboorte        | 19770101   |
| gemgeboorte        | Appingedam |
| wplnaamgeboorte    | Eibergen   |
| landgebiedgeboorte | Nederland  |

!-- In het bijhoudingsresultaatbericht is de melding dat de IST-gegevens zijn verwijderd (R2465) al gecontroleerd.
!-- Controleer in de database of deze gegevens ook daadwerkelijk zijn verwijderd uit ist.stapelrelatie, ist.stapel en ist.voorkomen.
Then in kern heeft select count(*)
                   from   ist.stapelrelatie
	           where  relatie in
                   (
                      select relatie
		      from   kern.betr
		      where  pers in
		      (
		          select id
			  from   kern.pers
			  where  voornamen = 'Piet'
			  and    srt       = 2
		      )
                   ) de volgende gegevens:
| veld  | waarde |
| count | 0      |

Then in kern heeft select count(*)
                   from   ist.stapel
		   where  pers in
		   (
		      select id
		      from   kern.pers
		      where  voornamen = 'Piet'
		      and    srt       = 2
		   ) de volgende gegevens:
| veld  | waarde |
| count | 0      |

Then in kern heeft select count(*)
                   from   ist.stapelvoorkomen
		   where  voornamen in
		   (
		      select voornamen
		      from   kern.pers
		      where  voornamen = 'Piet'
		      and    srt       = 2
		   ) de volgende gegevens:
| veld  | waarde |
| count | 0      |

Scenario: 3. DB reset
             postconditie

Given de database is aangepast met: delete from kern.actiebron where rechtsgrond in (select id from kern.rechtsgrond where code='111')
Given de database is aangepast met: delete from kern.rechtsgrond where code='111'

Given maak bijhouding caches leeg