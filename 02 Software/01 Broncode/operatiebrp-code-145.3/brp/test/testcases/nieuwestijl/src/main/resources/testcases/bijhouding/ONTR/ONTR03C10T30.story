Meta:
@status                 Klaar
@usecase                UCS-BY.1.ON


Narrative: Verwerking van ontrelateren familierechtelijke betrekkingen

Scenario: 1. DB init scenario om uitgangssituatie te zetten
            preconditie

Given alle personen zijn verwijderd

Scenario: 2. Ontrelateren bij Ontbinding huwelijk icm naamswijziging en kind, opa en oma wonen in een andere gemeente
             LT: ONTR03C10T30

!-- Initieel vullen van oma en opa en papa
Given enkel initiele vulling uit bestand /LO3PL-ONTR/ONTR03C10T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-ONTR/ONTR03C10T30-002.xls
Given enkel initiele vulling uit bestand /LO3PL-ONTR/ONTR03C10T30-003.xls

!-- voer GBNL uit voor de geboorte van mama en symmetrisch FRB te maken met oma en opa
Given maak bijhouding caches leeg
Given de database is aangepast met: update kern.gem set dataanvgel = '19800101' where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.partij set datingang = '19800101' where naam = 'Gemeente BRP 1'
When voer een bijhouding uit ONTR03C10T30a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- reset voor Gemeente BRP 1
Scenario: 2. DB reset voor Gemeente BRP 1
          postconditie
Given de database is aangepast met: update kern.gem set dataanvgel = '20160101' where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.partij set datingang = '20160101' where naam = 'Gemeente BRP 1'
Given maak bijhouding caches leeg

!-- voer VHNL uit om symmetrisch huwelijk te maken
When voer een bijhouding uit ONTR03C10T30b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- voer GBNL uit om symmetrische FRB te maken tussen papa mama en kind
When voer een bijhouding uit ONTR03C10T30c.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- voer een VZIG uit voor Kind
When voer een bijhouding uit ONTR03C10T30d.xml namens partij 'Gemeente BRP 2'
Then heeft het antwoordbericht verwerking Geslaagd

!-- voer een VZIG uit voor Oma
When voer een bijhouding uit ONTR03C10T30e.xml namens partij 'Gemeente BRP 2'
Then heeft het antwoordbericht verwerking Geslaagd

!-- zet autofiat uizondering voor Gemeente BRP 2 zodat ontrelateren wordt getriggerd bij volgende bijhouding (ontbinding)
Given de database is aangepast met: delete from autaut.his_bijhouderfiatuitz where id =99999
And de database is aangepast met: delete from autaut.bijhouderfiatuitz where id =99999
And de database is aangepast met: insert into autaut.bijhouderfiatuitz (id,bijhouder, datingang, dateinde, bijhouderbijhvoorstel, srtdoc, srtadmhnd, indblok) values (99999, (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 2') and rol=2), null , null , (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2), null, null, null)
And de database is aangepast met: insert into autaut.his_bijhouderfiatuitz (id, bijhouderfiatuitz, tsreg, tsverval, datingang, dateinde, bijhouderbijhvoorstel, srtdoc, srtadmhnd, indblok) values(99999, 99999, now() at time zone 'UTC', null, 19990101, null, (select id from kern.partijrol where partij = (select id from kern.partij where naam = 'Gemeente BRP 1') and rol=2), null, null, null)

!-- doe ontbinding huwelijk met naamswijziging
Then heeft $RELATIE_ID$ de waarde van de volgende query: select partnerBetr.relatie from kern.pers partner join kern.betr partnerBetr on partnerBetr.pers = partner.id where partner.bsn = '289056585' and partnerBetr.rol=3
When voer een bijhouding uit ONTR03C10T30f.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   3. DB reset scenario om de aangepaste data weer terug te zetten
            postconditie

Given de database is aangepast met: delete from autaut.his_bijhouderfiatuitz where id =99999
And de database is aangepast met: delete from autaut.bijhouderfiatuitz where id =99999

!-- Controleer kopie-voorkomen gemaakt van de ActieBron
Then in kern heeft select count(1),sd.naam, ab.doc
                   from kern.actiebron ab join kern.actie ainh on ainh.id = ab.actie
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.doc d on ab.doc = d.id
                   left join kern.srtdoc sd on d.srt = sd.id
                   where sainh.naam in ('Registratie geborene', 'Registratie aanvang huwelijk', 'Ontrelateren') group by sd.naam, ab.doc
                   order by sd.naam de volgende gegevens:
| veld                      | waarde                |
| count                     | 2                     |
| naam                      | Geboorteakte          |
----
| count                     | 2                     |
| naam                      | Geboorteakte          |
----
| count                     | 1                     |
| naam                      | Huwelijksakte         |

!-- Controleer aantal aangemaakte Pseudo-personen door ontrelateren van huwelijk en FRB
Then in kern heeft select count(1)
                    from kern.pers p
                    where p.srt='2'
                    and p.bsn in ('606745129', '441777065', '709810313', '289056585', '101345458') de volgende gegevens:
| veld                      | waarde                |
| count                     | 6                     |

!-- Controleer aangemaakte Psuedo-persoon van FRB vanuit kind (ingeschrevene)
Then in kern heeft select p.bsn, p.anr, hpsam.indafgeleid, hpsam.indnreeks, hpsam.predicaat, hpsam.voornamen, hpsam.adellijketitel, hpsam.voorvoegsel, hpsam.scheidingsteken, hpsam.geslnaamstam,
                   hpges.geslachtsaand, hpgeb.datgeboorte, hpgeb.gemgeboorte, hpgeb.wplnaamgeboorte, hpgeb.blplaatsgeboorte, hpgeb.blregiogeboorte, hpgeb.omslocgeboorte, hpgeb.landgebiedgeboorte
                   from kern.betr b
                   left outer join kern.his_betr hb on hb.betr = b.id
                   left outer join kern.relatie r on r.id = b.relatie
                   left outer join kern.his_relatie hr on r.id = hr.relatie
                   left outer join kern.pers p on p.id = b.pers
                   left outer join kern.actie av on av.id = hr.actieverval
                   left outer join kern.actie ainh on ainh.id = hr.actieinh
                   left outer join kern.srtactie sainh on ainh.srt = sainh.id
                   left outer join kern.srtactie saav on av.srt = saav.id
                   left outer join kern.his_persids hpid on p.id = hpid.pers
                   left outer join kern.his_perssamengesteldenaam hpsam on hpsam.pers = hpid.pers
                   left outer join kern.his_persgeslachtsaand hpges on hpsam.pers = hpges.pers
                   left outer join kern.his_persgeboorte hpgeb on hpsam.pers = hpgeb.pers
                   where hr.relatie in (select relatie from kern.betr where pers in (select id from kern.pers where bsn='101345458' and srt='1'))
                   and hb.tsverval is null
                   and hpsam.tsverval is null
                   and hpsam.dateindegel is null
                   and p.srt ='2' order by p.bsn de volgende gegevens:
| veld                      | waarde     |
| bsn                       | 289056585  |
| anr                       | 2980641298 |
| indafgeleid               | false      |
| indnreeks                 | false      |
| predicaat                 | NULL       |
| voornamen                 | Mama       |
| adellijketitel            | NULL       |
| voorvoegsel               | NULL       |
| scheidingsteken           | NULL       |
| geslnaamstam              | Vader      |
| geslachtsaand             | 2          |
| datgeboorte               | 19941231   |
| gemgeboorte               | 7012       |
| wplnaamgeboorte           | Rotterdam  |
| blplaatsgeboorte          | NULL       |
| blregiogeboorte           | NULL       |
| omslocgeboorte            | NULL       |
| landgebiedgeboorte        | 229        |
----
| bsn                       | 709810313  |
| anr                       | 4545982738 |
| indafgeleid               | false      |
| indnreeks                 | false      |
| predicaat                 | NULL       |
| voornamen                 | Papa       |
| adellijketitel            | NULL       |
| voorvoegsel               | NULL       |
| scheidingsteken           | NULL       |
| geslnaamstam              | Vader      |
| geslachtsaand             | 1          |
| datgeboorte               | 19660821   |
| gemgeboorte               | 17         |
| wplnaamgeboorte           | NULL       |
| blplaatsgeboorte          | NULL       |
| blregiogeboorte           | NULL       |
| omslocgeboorte            | NULL       |
| landgebiedgeboorte        | 229        |


!-- Controleer aangemaakte Pseudo-persoon van FRB vanuit kind (pseudo)
Then in kern heeft select p.bsn, p.anr, hpsam.indafgeleid, hpsam.indnreeks, hpsam.predicaat, hpsam.voornamen, hpsam.adellijketitel, hpsam.voorvoegsel, hpsam.scheidingsteken, hpsam.geslnaamstam,
                   hpges.geslachtsaand, hpgeb.datgeboorte, hpgeb.gemgeboorte, hpgeb.wplnaamgeboorte, hpgeb.blplaatsgeboorte, hpgeb.blregiogeboorte, hpgeb.omslocgeboorte, hpgeb.landgebiedgeboorte
                   from kern.betr b
                   left outer join kern.his_betr hb on hb.betr = b.id
                   left outer join kern.relatie r on r.id = b.relatie
                   left outer join kern.his_relatie hr on r.id = hr.relatie
                   left outer join kern.pers p on p.id = b.pers
                   left outer join kern.actie av on av.id = hr.actieverval
                   left outer join kern.actie ainh on ainh.id = hr.actieinh
                   left outer join kern.srtactie sainh on ainh.srt = sainh.id
                   left outer join kern.srtactie saav on av.srt = saav.id
                   left outer join kern.his_persids hpid on p.id = hpid.pers
                   left outer join kern.his_perssamengesteldenaam hpsam on hpsam.pers = hpid.pers
                   left outer join kern.his_persgeslachtsaand hpges on hpsam.pers = hpges.pers
                   left outer join kern.his_persgeboorte hpgeb on hpsam.pers = hpgeb.pers
                   where hr.relatie in (select relatie from kern.betr where pers in (select id from kern.pers where bsn='101345458' and srt='2'))
                   and hb.tsverval is null
                   and hpsam.tsverval is null
                   and hpsam.dateindegel is null
                   and p.srt ='2' order by p.bsn de volgende gegevens:
| veld                      | waarde            |
| bsn                       | 101345458         |
| anr                       | 6824672018        |
| indafgeleid               | false             |
| indnreeks                 | false             |
| predicaat                 | NULL              |
| voornamen                 | Kind              |
| adellijketitel            | NULL              |
| voorvoegsel               | NULL              |
| scheidingsteken           | NULL              |
| geslnaamstam              | Vader             |
| geslachtsaand             | NULL              |
| datgeboorte               | 20160201          |
| gemgeboorte               | 7012              |
| wplnaamgeboorte           | Rotterdam         |
| blplaatsgeboorte          | NULL              |
| blregiogeboorte           | NULL              |
| omslocgeboorte            | NULL              |
| landgebiedgeboorte        | 229               |
----
| bsn                       | 101345458         |
| anr                       | 6824672018        |
| indafgeleid               | false             |
| indnreeks                 | false             |
| predicaat                 | NULL              |
| voornamen                 | Kind              |
| adellijketitel            | NULL              |
| voorvoegsel               | NULL              |
| scheidingsteken           | NULL              |
| geslnaamstam              | Vader             |
| geslachtsaand             | NULL              |
| datgeboorte               | 20160201          |
| gemgeboorte               | 7012              |
| wplnaamgeboorte           | Rotterdam         |
| blplaatsgeboorte          | NULL              |
| blregiogeboorte           | NULL              |
| omslocgeboorte            | NULL              |
| landgebiedgeboorte        | 229               |

!-- Controleer totaal aantal nieuwe betrokkenheden
Then in kern heeft select count(1)
                   from kern.his_betr hr join kern.actie ainh on ainh.id = hr.actieinh
                   left join kern.actie av on av.id = hr.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   where sainh.naam ='Ontrelateren' and av.srt is NULL de volgende gegevens:
| veld                      | waarde |
| count                     | 9      |

!-- Controleer aantal vervallen betrokkenheden tav ontrelateren FRB
Then in kern heeft select count(1)
                   from kern.betr b
                   left join kern.his_betr hb on hb.betr = b.id
                   join kern.relatie r on r.id = b.relatie
                   join kern.his_relatie hr on r.id = hr.relatie
                   left outer join kern.pers p1 on p1.id = b.pers and p1.srt = 1
                   left outer join kern.pers p2 on p2.id = b.pers and p2.srt = 2
                   left join kern.actie av on av.id = hr.actieverval
                   left join kern.actie ainh on ainh.id = hr.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   where hr.relatie in (select relatie from kern.betr where pers in (select id from kern.pers where bsn in ('101345458','289056585') and srt='1'))
                   and hb.tsverval is not null de volgende gegevens:
| veld                      | waarde |
| count                     | 3      |

!-- Controleer aantal vervallen betrokkenheden tav ontrelateren huwelijk
Then in kern heeft select count(1)
                   from kern.betr b
                   left join kern.his_betr hb on hb.betr = b.id
                   join kern.relatie r on r.id = b.relatie
                   join kern.his_relatie hr on r.id = hr.relatie
                   left outer join kern.pers p1 on p1.id = b.pers and p1.srt = 1
                   left outer join kern.pers p2 on p2.id = b.pers and p2.srt = 2
                   left join kern.actie av on av.id = hr.actieverval
                   left join kern.actie ainh on ainh.id = hr.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   where hr.relatie in (select relatie from kern.betr where pers in (select id from kern.pers where bsn='791975241' and srt='1') and rol='3')
                   and hb.tsverval is not null de volgende gegevens:
| veld                      | waarde |
| count                     | 0      |

!-- Controleer relatie (FRB) vanuit kind (ingeschrevene)
Then in kern heeft select count(p1.id) ingeschrevenen, count(p2.id) pseudos, p1.bsn as bsn1, p2.bsn as bsn2
                   from kern.betr b
                   left join kern.his_betr hb on hb.betr = b.id
                   join kern.relatie r on r.id = b.relatie
                   join kern.his_relatie hr on r.id = hr.relatie
                   left outer join kern.pers p1 on p1.id = b.pers and p1.srt = 1
                   left outer join kern.pers p2 on p2.id = b.pers and p2.srt = 2
                   left join kern.actie av on av.id = hr.actieverval
                   left join kern.actie ainh on ainh.id = hr.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   where hr.relatie in (select relatie from kern.betr where pers in (select id from kern.pers where bsn='101345458' and srt='1')and rol ='1')
                   and hb.tsverval is null
                   group by p1.bsn, p2.bsn
                   order by ingeschrevenen, bsn1, bsn2  de volgende gegevens:
| veld                      | waarde      |
| ingeschrevenen            | 0           |
| pseudos                   | 1           |
| bsn1                      | NULL        |
| bsn2                      | 289056585   |
----
| ingeschrevenen            | 0           |
| pseudos                   | 1           |
| bsn1                      | NULL        |
| bsn2                      | 709810313   |
----
| ingeschrevenen            | 1           |
| pseudos                   | 0           |
| bsn1                      | 101345458   |
| bsn2                      | NULL        |

!-- Controleer relatie (FRB) vanuit Mama als kind (ingeschrevene)
Then in kern heeft select count(p1.id) ingeschrevenen, count(p2.id) pseudos, p1.bsn as bsn1, p2.bsn as bsn2
                   from kern.betr b
                   left join kern.his_betr hb on hb.betr = b.id
                   join kern.relatie r on r.id = b.relatie
                   join kern.his_relatie hr on r.id = hr.relatie
                   left outer join kern.pers p1 on p1.id = b.pers and p1.srt = 1
                   left outer join kern.pers p2 on p2.id = b.pers and p2.srt = 2
                   left join kern.actie av on av.id = hr.actieverval
                   left join kern.actie ainh on ainh.id = hr.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   where hr.relatie in (select relatie from kern.betr where pers in (select id from kern.pers where bsn='289056585' and srt='1') and rol='1')
                   and hb.tsverval is null
                   group by p1.bsn, p2.bsn
                   order by ingeschrevenen, bsn1, bsn2  de volgende gegevens:
| veld                      | waarde      |
| ingeschrevenen            | 0           |
| pseudos                   | 1           |
| bsn1                      | NULL        |
| bsn2                      | 606745129   |
----
| ingeschrevenen            | 1           |
| pseudos                   | 0           |
| bsn1                      | 289056585   |
| bsn2                      | NULL        |
----
| ingeschrevenen            | 1           |
| pseudos                   | 0           |
| bsn1                      | 441777065   |
| bsn2                      | NULL        |

!-- Controleer relatie (FRB) vanuit kind (pseudo)
Then in kern heeft select count(p1.id) ingeschrevenen, count(p2.id) pseudos, p1.bsn as bsn1, p2.bsn as bsn2
                   from kern.betr b
                   left join kern.his_betr hb on hb.betr = b.id
                   join kern.relatie r on r.id = b.relatie
                   join kern.his_relatie hr on r.id = hr.relatie
                   left outer join kern.pers p1 on p1.id = b.pers and p1.srt = 1
                   left outer join kern.pers p2 on p2.id = b.pers and p2.srt = 2
                   left join kern.actie av on av.id = hr.actieverval
                   left join kern.actie ainh on ainh.id = hr.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   where hr.relatie in (select relatie from kern.betr where pers in (select id from kern.pers where bsn='101345458' and srt='2')and rol ='1')
                   and hb.tsverval is null
                   group by p1.bsn, p2.bsn
                   order by ingeschrevenen, bsn1, bsn2 de volgende gegevens:
| veld                      | waarde      |
| ingeschrevenen            | 0           |
| pseudos                   | 2           |
| bsn1                      | NULL        |
| bsn2                      | 101345458   |
----
| ingeschrevenen            | 1           |
| pseudos                   | 0           |
| bsn1                      | 289056585   |
| bsn2                      | NULL        |
----
| ingeschrevenen            | 1           |
| pseudos                   | 0           |
| bsn1                      | 709810313   |
| bsn2                      | NULL        |

!-- Controleer relatie (FRB) vanuit Mama als kind (pseudo)
Then in kern heeft select count(p1.id) ingeschrevenen, count(p2.id) pseudos, p1.bsn as bsn1, p2.bsn as bsn2
                   from kern.betr b
                   left join kern.his_betr hb on hb.betr = b.id
                   join kern.relatie r on r.id = b.relatie
                   join kern.his_relatie hr on r.id = hr.relatie
                   left outer join kern.pers p1 on p1.id = b.pers and p1.srt = 1
                   left outer join kern.pers p2 on p2.id = b.pers and p2.srt = 2
                   left join kern.actie av on av.id = hr.actieverval
                   left join kern.actie ainh on ainh.id = hr.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   where hr.relatie in (select relatie from kern.betr where pers in (select id from kern.pers where bsn='289056585' and srt='2')and rol ='1')
                   and hb.tsverval is null
                   group by p1.bsn, p2.bsn
                   order by ingeschrevenen, bsn1, bsn2 de volgende gegevens:
| veld                      | waarde      |
| ingeschrevenen            | 0           |
| pseudos                   | 1           |
| bsn1                      | NULL        |
| bsn2                      | 289056585   |
----
| ingeschrevenen            | 1           |
| pseudos                   | 0           |
| bsn1                      | 606745129   |
| bsn2                      | NULL        |

!-- Controleer betrokken personen zijn gemarkeerd als bijgehouden door ontrelateren (FRB en huwelijk) en geslachtsnaam wijziging
Then in kern heeft select sainh.naam as actieInhoud, p.bsn, sa.naam as AdmhndNaam, hpaf.sorteervolgorde, CASE WHEN hpaf.tsverval IS NOT NULL THEN 'gevuld'
                   END as vervallen, saav.naam as actieVerval
                   from kern.his_persafgeleidadministrati hpaf
                   join kern.actie ainh on ainh.id = hpaf.actieinh
                   left join kern.actie av on av.id = hpaf.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.pers p on hpaf.pers = p.id
                   left join kern.admhnd a on hpaf.admhnd = a.id
                   left join kern.srtadmhnd sa on a.srt = sa.id
                   where sa.naam not in ('GBA - Initiële vulling', 'Voltrekking huwelijk in Nederland', 'Geboorte in Nederland')
                   order by p.bsn, sa.naam, saav.naam de volgende gegevens:
| veld                      | waarde                                        |
| actieinhoud               | Ontrelateren                                  |
| bsn                       | 101345458                                     |
| admhndnaam                | Ontrelateren                                  |
| sorteervolgorde           | 1                                             |
| vervallen                 | NULL                                          |
| actieverval               | NULL                                          |
----
| actieinhoud               | Registratie adres                             |
| bsn                       | 101345458                                     |
| admhndnaam                | Verhuizing intergemeentelijk                  |
| sorteervolgorde           | 1                                             |
| vervallen                 | gevuld                                        |
| actieverval               | Ontrelateren                                  |
----
| actieinhoud               | Registratie einde huwelijk                    |
| bsn                       | 289056585                                     |
| admhndnaam                | Ontbinding huwelijk in Nederland              |
| sorteervolgorde           | 1                                             |
| vervallen                 | NULL                                          |
| actieverval               | NULL                                          |
----
| actieinhoud               | Ontrelateren                                  |
| bsn                       | 289056585                                     |
| admhndnaam                | Ontrelateren                                  |
| sorteervolgorde           | 1                                             |
| vervallen                 | gevuld                                        |
| actieverval               | Ontrelateren                                  |
----
| actieinhoud               | Ontrelateren                                  |
| bsn                       | 289056585                                     |
| admhndnaam                | Ontrelateren                                  |
| sorteervolgorde           | 1                                             |
| vervallen                 | gevuld                                        |
| actieverval               | Registratie einde huwelijk                    |
----
| actieinhoud               | Registratie einde huwelijk                    |
| bsn                       | 441777065                                     |
| admhndnaam                | Ontbinding huwelijk in Nederland              |
| sorteervolgorde           | 1                                             |
| vervallen                 | NULL                                          |
| actieverval               | NULL                                          |
----
| actieinhoud               | Ontrelateren                                  |
| bsn                       | 606745129                                     |
| admhndnaam                | Ontrelateren                                  |
| sorteervolgorde           | 1                                             |
| vervallen                 | NULL                                          |
| actieverval               | NULL                                          |
----
| actieinhoud               | Registratie adres                             |
| bsn                       | 606745129                                     |
| admhndnaam                | Verhuizing intergemeentelijk                  |
| sorteervolgorde           | 1                                             |
| vervallen                 | gevuld                                        |
| actieverval               | Ontrelateren                                  |
----
| actieinhoud               | Registratie einde huwelijk                    |
| bsn                       | 709810313                                     |
| admhndnaam                | Ontbinding huwelijk in Nederland              |
| sorteervolgorde           | 1                                             |
| vervallen                 | NULL                                          |
| actieverval               | NULL                                          |
----
| actieinhoud               | Ontrelateren                                  |
| bsn                       | 709810313                                     |
| admhndnaam                | Ontrelateren                                  |
| sorteervolgorde           | 1                                             |
| vervallen                 | gevuld                                        |
| actieverval               | Registratie einde huwelijk                    |

!-- Controleer aangemaakte DAG's van Pseudo-persoon ouder
Then in kern heeft select p.bsn, hpid.dataanvgel AS DAG_identieficatienummers, hpsam.dataanvgel AS DAG_samengesteldenaam, hpges.dataanvgel AS DAG_geslachtsaand
                   from kern.betr b
                   left outer join kern.his_betr hb on hb.betr = b.id
                   left outer join kern.relatie r on r.id = b.relatie
                   left outer join kern.his_relatie hr on r.id = hr.relatie
                   left outer join kern.pers p on p.id = b.pers
                   left outer join kern.actie av on av.id = hr.actieverval
                   left outer join kern.actie ainh on ainh.id = hr.actieinh
                   left outer join kern.srtactie sainh on ainh.srt = sainh.id
                   left outer join kern.srtactie saav on av.srt = saav.id
                   left outer join kern.his_persids hpid on p.id = hpid.pers
                   left outer join kern.his_perssamengesteldenaam hpsam on hpsam.pers = hpid.pers
                   left outer join kern.his_persgeslachtsaand hpges on hpsam.pers = hpges.pers
                   left outer join kern.his_persgeboorte hpgeb on hpsam.pers = hpgeb.pers
                   where hr.relatie in (select relatie from kern.betr where pers in (select id from kern.pers where bsn='289056585' and srt='1'))
                   and hb.tsverval is null
                   and hpsam.tsverval is null
                   and hpsam.dateindegel is null
                   and p.bsn is not null
                   and p.srt ='2' order by p.bsn de volgende gegevens:
| veld                         | waarde      |
| bsn                          | 101345458   |
| DAG_identieficatienummers    | 20160201    |
| DAG_samengesteldenaam        | 20160201    |
| DAG_geslachtsaand            | NULL        |
----
| bsn                          | 289056585   |
| DAG_identieficatienummers    | 20160201    |
| DAG_samengesteldenaam        | 20160201    |
| DAG_geslachtsaand            | 20160201    |
----
| bsn                          | 606745129   |
| DAG_identieficatienummers    | 19941231    |
| DAG_samengesteldenaam        | 19941231    |
| DAG_geslachtsaand            | 19941231    |
----
| bsn                          | 709810313   |
| DAG_identieficatienummers    | 20160201    |
| DAG_samengesteldenaam        | 20160201    |
| DAG_geslachtsaand            | 20160201    |

!-- Controleer aangemaakte DAG's van Psuedo-persoon kind
Then in kern heeft select p.bsn, hpid.dataanvgel AS DAG_identieficatienummers, hpsam.dataanvgel AS DAG_samengesteldenaam, hpges.dataanvgel AS DAG_geslachtsaand
                   from kern.betr b
                   left outer join kern.his_betr hb on hb.betr = b.id
                   left outer join kern.relatie r on r.id = b.relatie
                   left outer join kern.his_relatie hr on r.id = hr.relatie
                   left outer join kern.pers p on p.id = b.pers
                   left outer join kern.actie av on av.id = hr.actieverval
                   left outer join kern.actie ainh on ainh.id = hr.actieinh
                   left outer join kern.srtactie sainh on ainh.srt = sainh.id
                   left outer join kern.srtactie saav on av.srt = saav.id
                   left outer join kern.his_persids hpid on p.id = hpid.pers
                   left outer join kern.his_perssamengesteldenaam hpsam on hpsam.pers = hpid.pers
                   left outer join kern.his_persgeslachtsaand hpges on hpsam.pers = hpges.pers
                   left outer join kern.his_persgeboorte hpgeb on hpsam.pers = hpgeb.pers
                   where hr.relatie in (select relatie from kern.betr where pers in (select id from kern.pers where bsn='101345458' and srt='2'))
                   and hb.tsverval is null
                   and hpsam.tsverval is null
                   and hpsam.dateindegel is null
                   and p.srt ='2' de volgende gegevens:
| veld                         | waarde      |
| bsn                          | 101345458   |
| DAG_identieficatienummers    | 20160201    |
| DAG_samengesteldenaam        | 20160201    |
| DAG_geslachtsaand            | NULL        |
----
| bsn                          | 101345458   |
| DAG_identieficatienummers    | 20160201    |
| DAG_samengesteldenaam        | 20160201    |
| DAG_geslachtsaand            | NULL        |

!-- Controleer geslachtsnaamwijziging niet is bijgehouden voor Pseudo op geslachtsnaamcomponent
Then in kern heeft select count(1) from kern.his_persgeslnaamcomp hpg
                   left outer join kern.persgeslnaamcomp pg on hpg.persgeslnaamcomp = pg.id
                   left outer join kern.pers p on p.id = pg.pers
                   where p.srt ='2' de volgende gegevens:
| veld                      | waarde |
| count                     | 0      |
