Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Ongedaan maken huwelijk, hoofdactie verval huwelijk

Scenario:   Ongedaan maken van een huwelijk dat eerder een GP was
            LT: ONHW04C10T50

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW04C10T50-001.xls
Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW04C10T50-002.xls

When voer een bijhouding uit ONHW04C10T50a.xml namens partij 'Gemeente BRP 1'

Given pas laatste relatie van soort 2 aan tussen persoon 234816089 en persoon 558275977 met relatie id 50000001 en betrokkenheid id 50000001
Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 234816089 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 558275977 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

When voer een bijhouding uit ONHW04C10T50b.xml namens partij 'Gemeente BRP 1'

Given pas laatste relatie van soort 1 aan tussen persoon 234816089 en persoon 558275977 met relatie id 50000002 en betrokkenheid id 50000002

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 234816089 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 558275977 wel als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit ONHW04C10T50c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 234816089 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 558275977 niet als PARTNER betrokken bij een HUWELIJK


!-- Controleer verantwoordingsgegevens van de ActieBron
Then in kern heeft select count(1), sdoc.naam
                   from kern.actiebron ab
                   join kern.actie ainh on ainh.id = ab.actie
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.doc doc on doc.id = ab.doc
                   left join kern.srtdoc sdoc on sdoc.id = doc.srt
                   where sainh.naam in ('Verval huwelijk') group by sdoc.naam de volgende gegevens:
| veld   | waarde                                  |
| count  | 1                                       |
| naam   | Nederlandse rechterlijke uitspraak      |

!-- Controleer betrokken personen zijn gemarkeerd als bijgehouden
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, p.voornamen, sa.naam as AdmhndNaam, hpaf.sorteervolgorde
                   from kern.his_persafgeleidadministrati hpaf
                   join kern.actie ainh on ainh.id = hpaf.actieinh
                   left join kern.actie av on av.id = hpaf.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.pers p on hpaf.pers = p.id
                   left join kern.admhnd a on hpaf.admhnd = a.id
                   left join kern.srtadmhnd sa on a.srt = sa.id
                   where sainh.naam ='Verval huwelijk'
                   order by p.voornamen de volgende gegevens:
| veld                      | waarde                                        |
| actieinhoud               | Verval huwelijk                               |
| actieverval               | NULL                                          |
| voornamen                 | Libby                                         |
| admhndnaam                | Ongedaanmaking huwelijk                       |
| sorteervolgorde           | 1                                             |
----
| actieinhoud               | Verval huwelijk                               |
| actieverval               | NULL                                          |
| voornamen                 | Piet                                          |
| admhndnaam                | Ongedaanmaking huwelijk                       |
| sorteervolgorde           | 1                                             |

!-- Controleer kern.relatie
Then in kern heeft select r.srt, r.gemaanv, r.indag, sainh.naam as actieinhoud, saav.naam as actieVerval
                   from kern.relatie r
                   join kern.his_relatie hr on hr.relatie = r.id
                   join kern.actie ainh on ainh.id = hr.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.actie av on av.id = hr.actieverval
                   left join kern.srtactie saav on av.srt = saav.id
                   where saav.naam = 'Verval huwelijk' de volgende gegevens:
| veld              | waarde                         |
| srt               | 1                              |
| gemaanv           | NULL                           |
| indag             | false                          |
| actieinhoud       | Registratie einde geregistreerd partnerschap   |
| actieverval       | Verval huwelijk                |

!-- Controleer kern.his_relatie
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, sa.naam as AdmhndNaam,
                   CASE WHEN tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval, hr.gemaanv, hr.nadereaandverval
                   from kern.his_relatie hr
                   join kern.actie ainh on ainh.id = hr.actieinh
                   left join kern.actie av on av.id = hr.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.admhnd a on av.admhnd = a.id
                   left join kern.srtadmhnd sa on a.srt = sa.id
                   where saav.naam = 'Verval huwelijk' or saav.naam = 'Registratie einde huwelijk' de volgende gegevens:
| veld              | waarde                         |
| actieinhoud       | Registratie einde geregistreerd partnerschap   |
| actieverval       | Verval huwelijk     |
| admhndnaam        | Ongedaanmaking huwelijk|
| tsverval          | Gevuld                         |
| gemaanv           | 7012                           |
| nadereaandverval  | NULL                           |


