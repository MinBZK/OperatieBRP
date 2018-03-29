Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Ongedaan maken huwelijk, hoofdactie verval huwelijk

Scenario:   Ongedaan maken van een huwelijk BRP (I) en GBA (P), IST gegevens
            LT: ONHW04C10T60

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW04C10T60-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 286696137 en persoon 304325545 met relatie id 50000001 en betrokkenheid id 50000001
When voer een bijhouding uit ONHW04C10T60a.xml namens partij 'Gemeente BRP 1'


Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 286696137 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 304325545 niet als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit ONHW04C10T60b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 286696137 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 304325545 niet als PARTNER betrokken bij een HUWELIJK




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
| voornamen                 | Anne                                         |
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
| actieinhoud       | Registratie einde huwelijk     |
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
| actieinhoud       | Conversie GBA                  |
| actieverval       | Registratie einde huwelijk     |
| admhndnaam        | Ontbinding huwelijk in Nederland|
| tsverval          | Gevuld                         |
| gemaanv           | 7012                           |
| nadereaandverval  | NULL                           |
----
| actieinhoud       | Registratie einde huwelijk     |
| actieverval       | Verval huwelijk                |
| admhndnaam        | Ongedaanmaking huwelijk        |
| tsverval          | Gevuld                         |
| gemaanv           | 7012                           |
| nadereaandverval  | NULL                           |

!-- Controleer kern.betr
Then in kern heeft select b.indag, sainh.naam as actieinhoud, saav.naam as actieVerval, sr.naam as rol, p.voornamen
                   from kern.betr b
                   join kern.his_betr hb on hb.betr = b.id
                   join kern.actie ainh on ainh.id = hb.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.actie av on av.id = hb.actieverval
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.srtbetr sr on sr.id = b.rol
                   left join kern.pers p on p.id = b.pers
                   where saav.naam = 'Verval huwelijk' order by p.voornamen de volgende gegevens:
| veld              | waarde                         |
| indag             | false                          |
| actieinhoud       | Conversie GBA   |
| actieverval       | Verval huwelijk                |
| rol               | Partner                        |
| voornamen         | Anne                          |
----
| indag             | false                          |
| actieinhoud       | Conversie GBA   |
| actieverval       | Verval huwelijk                |
| rol               | Partner                        |
| voornamen         | NULL                           |

!-- Controleer kern.his_betr
Then in kern heeft select sainh.naam as actieinhoud, saav.naam as actieVerval, hb.nadereaandverval, CASE WHEN tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval
                   from kern.his_betr hb
                   join kern.actie ainh on ainh.id = hb.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.actie av on av.id = hb.actieverval
                   left join kern.srtactie saav on av.srt = saav.id
                   where saav.naam = 'Verval huwelijk' de volgende gegevens:
| veld              | waarde                         |
| actieinhoud       | Conversie GBA   |
| actieverval       | Verval huwelijk                |
| nadereaandverval  | NULL                           |
| tsverval          | Gevuld                         |
----
| actieinhoud       | Conversie GBA   |
| actieverval       | Verval huwelijk                |
| nadereaandverval  | NULL                           |
| tsverval          | Gevuld                         |

!-- Controleer kern.his_persids
Then in kern heeft select p.voornamen, id.bsn, sainh.naam as actieinhoud, saav.naam as actieVerval, id.nadereaandverval, CASE WHEN tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval
                   from kern.his_persids id
                   left join kern.pers p on p.id = id.pers
                   left join kern.actie ainh on ainh.id = id.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.actie av on av.id = id.actieverval
                   left join kern.srtactie saav on av.srt = saav.id
                   order by p.voornamen de volgende gegevens:
| veld              | waarde                         |
| voornamen         | Anne                          |
| bsn               | 286696137                      |
| actieinhoud       | Conversie GBA                  |
| actieverval       | NULL                           |
| nadereaandverval  | NULL                           |
| tsverval          | Leeg                           |
----
| voornamen         | NULL                           |
| bsn               | 304325545                      |
| actieinhoud       | Conversie GBA                  |
| actieverval       | Verval huwelijk                           |
| nadereaandverval  | NULL                           |
| tsverval          | Gevuld                           |

!-- Controleer kern.his_persgeboorte
Then in kern heeft select p.voornamen, id.datgeboorte, sainh.naam as actieinhoud, saav.naam as actieVerval, id.nadereaandverval, CASE WHEN tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval
                                      from kern.his_persgeboorte id
                                      left join kern.pers p on p.id = id.pers
                                      left join kern.actie ainh on ainh.id = id.actieinh
                                      left join kern.srtactie sainh on ainh.srt = sainh.id
                                      left join kern.actie av on av.id = id.actieverval
                                      left join kern.srtactie saav on av.srt = saav.id
                                      where saav.naam = 'Verval huwelijk' or p.voornamen = 'Anne'
                                      order by p.voornamen de volgende gegevens:
| veld              | waarde                         |
| voornamen         | Anne                          |
| datgeboorte       | 19760401                       |
| actieinhoud       | Conversie GBA                  |
| actieverval       | NULL                           |
| nadereaandverval  | NULL                           |
| tsverval          | Leeg                           |
----
| voornamen         | NULL                           |
| datgeboorte       | 19750505                       |
| actieinhoud       | Conversie GBA                  |
| actieverval       | Verval huwelijk                           |
| nadereaandverval  | NULL                           |
| tsverval          | Gevuld                           |

!-- Controleer kern.his_persgeslachtsaand
Then in kern heeft select p.voornamen, id.geslachtsaand, sainh.naam as actieinhoud, saav.naam as actieVerval, id.nadereaandverval, CASE WHEN tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval
                   from kern.his_persgeslachtsaand id
                   left join kern.pers p on p.id = id.pers
                   left join kern.actie ainh on ainh.id = id.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.actie av on av.id = id.actieverval
                   left join kern.srtactie saav on av.srt = saav.id
                   where saav.naam = 'Verval huwelijk' or p.voornamen = 'Anne'
                   order by p.voornamen de volgende gegevens:
| veld              | waarde                         |
| voornamen         | Anne                          |
| geslachtsaand     | 2                              |
| actieinhoud       | Conversie GBA                  |
| actieverval       | NULL                           |
| nadereaandverval  | NULL                           |
| tsverval          | Leeg                           |
----
| voornamen         | NULL                           |
| geslachtsaand     | 1                              |
| actieinhoud       | Conversie GBA                  |
| actieverval       | Verval huwelijk                           |
| nadereaandverval  | NULL                           |
| tsverval          | Gevuld                           |

!-- Controleer kern.his_perssamengesteldenaam
Then in kern heeft select p.voornamen, id.geslnaamstam, sainh.naam as actieinhoud, saav.naam as actieVerval, id.nadereaandverval, CASE WHEN tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval
                   from kern.his_perssamengesteldenaam id
                   left join kern.pers p on p.id = id.pers
                   left join kern.actie ainh on ainh.id = id.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.actie av on av.id = id.actieverval
                   left join kern.srtactie saav on av.srt = saav.id
                   where saav.naam = 'Verval huwelijk' or p.voornamen = 'Anne'
                   order by p.voornamen de volgende gegevens:
| veld              | waarde                         |
| voornamen         | Anne                           |
| geslnaamstam      | Bakker                       |
| actieinhoud       | Conversie GBA                  |
| actieverval       | NULL                           |
| nadereaandverval  | NULL                           |
| tsverval          | Leeg                           |
----
| voornamen         | NULL                           |
| geslnaamstam      | Pietersen                         |
| actieinhoud       | Conversie GBA                  |
| actieverval       | Verval huwelijk                           |
| nadereaandverval  | NULL                           |
| tsverval          | Gevuld                           |


!-- Controleer ist.stapelvoorkomen
Then in kern heeft select count(*) from ist.stapelvoorkomen de volgende gegevens:
| veld              | waarde                         |
| count             | 2                              |