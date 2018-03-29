Meta:
@status                 Onderhanden
@usecase                UCS-BY.1.ON


Narrative: Verwerking van ontrelateren relaties

Scenario:   Na ontrelateren nieuw symetrisch huwelijk
            LT: ONTR02C10T100

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-ONTR/ONTR02C10T100-001.xls
Given enkel initiele vulling uit bestand /LO3PL-ONTR/ONTR02C10T100-002.xls
Given enkel initiele vulling uit bestand /LO3PL-ONTR/ONTR02C10T100-003.xls

When voer een bijhouding uit ONTR02C10T100a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONTR/expected/ONTR02C10T100a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Given pas laatste relatie van soort 1 aan tussen persoon 735658249 en persoon 797072329 met relatie id 30010001 en betrokkenheid id 30010001

Then is in de database de persoon met bsn 735658249 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 797072329 wel als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit ONTR02C10T100b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONTR/expected/ONTR02C10T100b.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

When voer een bijhouding uit ONTR02C10T100c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONTR/expected/ONTR02C10T100c.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

!-- Controleer nieuwe betrokkenheden
Then in kern heeft select count(1)
                   from kern.his_betr hr
                   join kern.actie ainh on ainh.id = hr.actieinh
                   LEFT join kern.actie av on av.id = hr.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   where sainh.naam ='Ontrelateren' and av.srt is NULL de volgende gegevens:
| veld                      | waarde |
| count                     | 4      |

!-- Controleer vervallen betrokkenheden
Then in kern heeft select count(1)
                   from kern.his_betr hr
                   join kern.actie ainh on ainh.id = hr.actieinh
                   LEFT join kern.actie av on av.id = hr.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   where sainh.naam ='Registratie aanvang huwelijk'
                   and saav.naam='Ontrelateren' de volgende gegevens:
| veld                      | waarde |
| count                     | 2      |

!-- Controleer kopie-voorkomen gemaakt van de ActieBron
Then in kern heeft select count(1),ab.doc
from kern.actiebron ab join kern.actie ainh on ainh.id = ab.actie left join kern.srtactie sainh on ainh.srt = sainh.id
where sainh.naam in ('Registratie aanvang huwelijk', 'Ontrelateren') group by ab.doc de volgende gegevens:
| veld                      | waarde |
| count                     | 2      |

!-- Juiste partners gekoppeld aan nieuwe relatie (1) aangemaakt door ontrelateren
Then in kern heeft select hr.actieinh, hr.relatie, count(p1.id) ingeschrevenen, count(p2.id) pseudos, p1.bsn as bsn1, p2.bsn as bsn2
                   from kern.betr b
                   join kern.relatie r on r.id = b.relatie
                   join kern.his_relatie hr on r.id = hr.relatie
                   left outer join kern.pers p1 on p1.id = b.pers and p1.srt = 1
                   left outer join kern.pers p2 on p2.id = b.pers and p2.srt = 2
                   left join kern.actie av on av.id = hr.actieverval
                   left join kern.actie ainh on ainh.id = hr.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   where sainh.naam ='Ontrelateren'
                   and hr.tsverval is null
                   group by hr.actieinh, hr.relatie, p1.bsn, p2.bsn
                   order by ingeschrevenen de volgende gegevens:
| veld                      | waarde     |
| pseudos                   | 1          |
| bsn2                      | 735658249  |
----
| ingeschrevenen            | 1          |
| bsn1                      | 797072329  |

!-- Juiste partners gekoppeld aan nieuwe relatie (2) aangemaakt door ontrelateren
Then in kern heeft select hr.actieinh, hr.relatie, count(p1.id) ingeschrevenen, count(p2.id) pseudos, p1.bsn as bsn1, p2.bsn as bsn2
                   from kern.betr b
                   join kern.relatie r on r.id = b.relatie
                   join kern.his_relatie hr on r.id = hr.relatie
                   left outer join kern.pers p1 on p1.id = b.pers and p1.srt = 1
                   left outer join kern.pers p2 on p2.id = b.pers and p2.srt = 2
                   left join kern.actie av on av.id = hr.actieverval
                   left join kern.actie ainh on ainh.id = hr.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   where sainh.naam ='Ontrelateren'
                   and hr.tsverval is not null
                   group by hr.actieinh, hr.relatie, p1.bsn, p2.bsn
                   order by ingeschrevenen de volgende gegevens:
| veld                      | waarde     |
| pseudos                   | 1          |
| bsn2                      | 797072329  |
----
| ingeschrevenen            | 1          |
| bsn1                      | 735658249  |

!-- Controleer aangemaakte personen (pseudo 1)
Then in kern heeft select p.srt, hpid.bsn, hpid.anr, hpsam.indafgeleid, hpsam.indnreeks, hpsam.predicaat, hpsam.voornamen, hpsam.adellijketitel, hpsam.voorvoegsel, hpsam.scheidingsteken, hpsam.geslnaamstam, hpges.geslachtsaand, hpgeb.datgeboorte, hpgeb.gemgeboorte, hpgeb.wplnaamgeboorte, hpgeb.blplaatsgeboorte, hpgeb.blregiogeboorte, hpgeb.omslocgeboorte, hpgeb.landgebiedgeboorte
                   from kern.his_persids hpid
                   left join kern.pers p on p.id = hpid.pers
                   left join kern.his_perssamengesteldenaam hpsam on hpsam.pers = hpid.pers
                   left join kern.actie ainh on ainh.id = hpid.actieinh
                   left join kern.actie av on av.id = hpid.actieverval
                   left join kern.his_persgeslachtsaand hpges on hpsam.pers = hpges.pers
                   left join kern.his_persgeboorte hpgeb on hpsam.pers = hpgeb.pers
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   where sainh.naam ='Conversie GBA'
                   and hpid.bsn ='735658249'
                   order by p.srt de volgende gegevens:
| veld                      | waarde     |
| srt                       | 1          |
| bsn                       | 735658249  |
| anr                       | 3796950817 |
| indafgeleid               | true       |
| indnreeks                 | false      |
| predicaat                 | NULL       |
| voornamen                 | Libby      |
| adellijketitel            | NULL       |
| voorvoegsel               | NULL       |
| scheidingsteken           | NULL       |
| geslnaamstam              | Thatcher   |
| geslachtsaand             | 2          |
| datgeboorte               | 19660821   |
| gemgeboorte               | 17         |
| wplnaamgeboorte           | NULL       |
| blplaatsgeboorte          | NULL       |
| blregiogeboorte           | NULL       |
| omslocgeboorte            | NULL       |
| landgebiedgeboorte        | 229        |
----
| srt                       | 2          |
| bsn                       | 735658249  |
| anr                       | 3796950817 |
| indafgeleid               | false      |
| indnreeks                 | false      |
| predicaat                 | NULL       |
| voornamen                 | Libby      |
| adellijketitel            | NULL       |
| voorvoegsel               | NULL       |
| scheidingsteken           | NULL       |
| geslnaamstam              | Thatcher   |
| geslachtsaand             | 2          |
| datgeboorte               | 19660821   |
| gemgeboorte               | 17         |
| wplnaamgeboorte           | NULL       |
| blplaatsgeboorte          | NULL       |
| blregiogeboorte           | NULL       |
| omslocgeboorte            | NULL       |
| landgebiedgeboorte        | 229        |

!-- Controleer aangemaakte personen (pseudo 2)
Then in kern heeft select p.srt, hpid.bsn, hpid.anr, hpsam.indafgeleid, hpsam.indnreeks, hpsam.predicaat, hpsam.voornamen, hpsam.adellijketitel, hpsam.voorvoegsel, hpsam.scheidingsteken, hpsam.geslnaamstam, hpges.geslachtsaand, hpgeb.datgeboorte, hpgeb.gemgeboorte, hpgeb.wplnaamgeboorte, hpgeb.blplaatsgeboorte, hpgeb.blregiogeboorte, hpgeb.omslocgeboorte, hpgeb.landgebiedgeboorte
                   from kern.his_persids hpid
                   left join kern.pers p on p.id = hpid.pers
                   left join kern.his_perssamengesteldenaam hpsam on hpsam.pers = hpid.pers
                   left join kern.actie ainh on ainh.id = hpid.actieinh
                   left join kern.actie av on av.id = hpid.actieverval
                   left join kern.his_persgeslachtsaand hpges on hpsam.pers = hpges.pers
                   left join kern.his_persgeboorte hpgeb on hpsam.pers = hpgeb.pers
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   where sainh.naam ='Conversie GBA'
                   and hpid.bsn ='797072329'
                   order by p.srt de volgende gegevens:
| veld                      | waarde     |
| srt                       | 1          |
| bsn                       | 797072329  |
| anr                       | 7842052385 |
| indafgeleid               | true       |
| indnreeks                 | false      |
| predicaat                 | NULL       |
| voornamen                 | Piet       |
| adellijketitel            | NULL       |
| voorvoegsel               | NULL       |
| scheidingsteken           | NULL       |
| geslnaamstam              | Jansen     |
| geslachtsaand             | 2          |
| datgeboorte               | 19600821   |
| gemgeboorte               | 17         |
| wplnaamgeboorte           | NULL       |
| blplaatsgeboorte          | NULL       |
| blregiogeboorte           | NULL       |
| omslocgeboorte            | NULL       |
| landgebiedgeboorte        | 229        |
----
| srt                       | 2          |
| bsn                       | 797072329  |
| anr                       | 7842052385 |
| indafgeleid               | false      |
| indnreeks                 | false      |
| predicaat                 | NULL       |
| voornamen                 | Piet       |
| adellijketitel            | NULL       |
| voorvoegsel               | NULL       |
| scheidingsteken           | NULL       |
| geslnaamstam              | Jansen     |
| geslachtsaand             | 2          |
| datgeboorte               | 19600821   |
| gemgeboorte               | 17         |
| wplnaamgeboorte           | NULL       |
| blplaatsgeboorte          | NULL       |
| blregiogeboorte           | NULL       |
| omslocgeboorte            | NULL       |
| landgebiedgeboorte        | 229        |

!-- Controleer betrokken personen zijn gemarkeerd als bijgehouden
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, p.bsn, sa.naam as AdmhndNaam, hpaf.sorteervolgorde
                   from kern.his_persafgeleidadministrati hpaf
                   join kern.actie ainh on ainh.id = hpaf.actieinh
                   left join kern.actie av on av.id = hpaf.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.pers p on hpaf.pers = p.id
                   left join kern.admhnd a on hpaf.admhnd = a.id
                   left join kern.srtadmhnd sa on a.srt = sa.id
                   where sainh.naam ='Ontrelateren'
                   order by p.bsn de volgende gegevens:
| veld                      | waarde                                        |
| actieinhoud               | Ontrelateren                                  |
| actieverval               | Registratie einde huwelijk                    |
| bsn                       | 735658249                                     |
| admhndnaam                | Ontrelateren                                  |
| sorteervolgorde           | 1                                             |
----
| actieinhoud               | Ontrelateren                                  |
| actieverval               | NULL                                          |
| bsn                       | 797072329                                     |
| admhndnaam                | Ontrelateren                                  |
| sorteervolgorde           | 1                                             |

!-- Controleer de 2 nieuwe relaties, de vervallen symmetrische relatie en de daadwerkelijke beeindingen van relatie
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, hr.dataanv, hr.gemaanv, hr.dateinde, hr.gemeinde
                   from kern.his_relatie hr
                   join kern.actie ainh on ainh.id = hr.actieinh
                   left join kern.actie av on av.id = hr.actieverval
                   left join kern.relatie r on r.id = hr.relatie
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.srtrelatie sr on r.srt = sr.id
                   where sainh.naam in ('Registratie einde huwelijk', 'Ontrelateren', 'Registratie aanvang huwelijk') AND sr.naam='Huwelijk'
                   order by actieinhoud, actieverval de volgende gegevens:
| veld                      | waarde                                        |
| actieinhoud               | Ontrelateren                                  |
| actieverval               | Registratie einde huwelijk                    |
| dataanv                   | 20160510                                      |
| gemaanv                   | 7012                                          |
| dateinde                  | NULL                                          |
| gemeinde                  | NULL                                          |
----
| actieinhoud               | Ontrelateren                                  |
| actieverval               | NULL                                          |
| dataanv                   | 20160510                                      |
| gemaanv                   | 7012                                          |
| dateinde                  | NULL                                          |
| gemeinde                  | NULL                                          |
----
| actieinhoud               | Registratie aanvang huwelijk                  |
| actieverval               | Ontrelateren                                  |
| dataanv                   | 20160510                                      |
| gemaanv                   | 7012                                          |
| dateinde                  | NULL                                          |
| gemeinde                  | NULL                                          |
----
| actieinhoud               | Registratie aanvang huwelijk                  |
| actieverval               | NULL                                          |
| dataanv                   | 20160710                                      |
| gemaanv                   | 7012                                          |
| dateinde                  | NULL                                          |
| gemeinde                  | NULL                                          |
----
| actieinhoud               | Registratie einde huwelijk                    |
| actieverval               | NULL                                          |
| dataanv                   | 20160510                                      |
| gemaanv                   | 7012                                          |
| dateinde                  | 20160601                                      |
| gemeinde                  | 7012                                          |

Then is in de database de persoon met bsn i:735658249 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn i:797072329 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn i:476933481 wel als PARTNER betrokken bij een HUWELIJK