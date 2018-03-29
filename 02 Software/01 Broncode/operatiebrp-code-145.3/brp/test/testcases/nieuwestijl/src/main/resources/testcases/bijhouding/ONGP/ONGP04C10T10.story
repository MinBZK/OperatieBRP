Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Ongedaan maken geregistreerd partnerschap, hoofdactie verval geregistreerd partnerschap

Scenario:   Ongedaan maken van een geregistreerd partnerschap tussen BRP (I) en BRP (I), nav 'O', 1 bron, bijhoudingsgemeente persoon
            LT: ONGP04C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-ONGP/ONGP04C10T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-ONGP/ONGP04C10T10-002.xls

When voer een bijhouding uit ONGP04C10T10a.xml namens partij 'Gemeente BRP 1'

Given pas laatste relatie van soort 2 aan tussen persoon 460488521 en persoon 623057001 met relatie id 50000001 en betrokkenheid id 50000001
Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 460488521 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 623057001 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

When voer een bijhouding uit ONGP04C10T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 460488521 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 623057001 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

!-- Controleer verantwoordingsgegevens van de ActieBron
Then in kern heeft select count(1), sdoc.naam
                   from kern.actiebron ab
                   join kern.actie ainh on ainh.id = ab.actie
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.doc doc on doc.id = ab.doc
                   left join kern.srtdoc sdoc on sdoc.id = doc.srt
                   where sainh.naam in ('Verval geregistreerd partnerschap') group by sdoc.naam de volgende gegevens:
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
                   where sainh.naam ='Verval geregistreerd partnerschap'
                   order by p.voornamen de volgende gegevens:
| veld                      | waarde                                        |
| actieinhoud               | Verval geregistreerd partnerschap             |
| actieverval               | NULL                                          |
| voornamen                 | Libby                                         |
| admhndnaam                | Ongedaanmaking geregistreerd partnerschap     |
| sorteervolgorde           | 1                                             |
----
| actieinhoud               | Verval geregistreerd partnerschap             |
| actieverval               | NULL                                          |
| voornamen                 | Piet                                          |
| admhndnaam                | Ongedaanmaking geregistreerd partnerschap     |
| sorteervolgorde           | 1                                             |

!-- Controleer kern.relatie
Then in kern heeft select r.srt, r.gemaanv, r.indag, sainh.naam as actieinhoud, saav.naam as actieVerval
                   from kern.relatie r
                   join kern.his_relatie hr on hr.relatie = r.id
                   join kern.actie ainh on ainh.id = hr.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.actie av on av.id = hr.actieverval
                   left join kern.srtactie saav on av.srt = saav.id
                   where saav.naam = 'Verval geregistreerd partnerschap' de volgende gegevens:
| veld              | waarde                                           |
| srt               | 2                                                |
| gemaanv           | NULL                                             |
| indag             | false                                            |
| actieinhoud       | Registratie aanvang geregistreerd partnerschap   |
| actieverval       | Verval geregistreerd partnerschap                |

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
                   where saav.naam = 'Verval geregistreerd partnerschap' de volgende gegevens:
| veld              | waarde                                           |
| actieinhoud       | Registratie aanvang geregistreerd partnerschap   |
| actieverval       | Verval geregistreerd partnerschap                |
| admhndnaam        | Ongedaanmaking geregistreerd partnerschap        |
| tsverval          | Gevuld                                           |
| gemaanv           | 7012                                             |
| nadereaandverval  | O                                                |

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
                   where saav.naam = 'Verval geregistreerd partnerschap' order by p.voornamen de volgende gegevens:
| veld              | waarde                                           |
| indag             | false                                            |
| actieinhoud       | Registratie aanvang geregistreerd partnerschap   |
| actieverval       | Verval geregistreerd partnerschap                |
| rol               | Partner                                          |
| voornamen         | Libby                                            |
----
| indag             | false                                            |
| actieinhoud       | Registratie aanvang geregistreerd partnerschap   |
| actieverval       | Verval geregistreerd partnerschap                |
| rol               | Partner                                          |
| voornamen         | Piet                                             |

!-- Controleer kern.his_betr
Then in kern heeft select sainh.naam as actieinhoud, saav.naam as actieVerval, hb.nadereaandverval, CASE WHEN tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval
                   from kern.his_betr hb
                   join kern.actie ainh on ainh.id = hb.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.actie av on av.id = hb.actieverval
                   left join kern.srtactie saav on av.srt = saav.id
                   where saav.naam = 'Verval geregistreerd partnerschap' de volgende gegevens:
| veld              | waarde                                           |
| actieinhoud       | Registratie aanvang geregistreerd partnerschap   |
| actieverval       | Verval geregistreerd partnerschap                |
| nadereaandverval  | O                                                |
| tsverval          | Gevuld                                           |
----
| actieinhoud       | Registratie aanvang geregistreerd partnerschap   |
| actieverval       | Verval geregistreerd partnerschap                |
| nadereaandverval  | O                                                |
| tsverval          | Gevuld                                           |

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
| voornamen         | Libby                          |
| bsn               | 460488521                      |
| actieinhoud       | Conversie GBA                  |
| actieverval       | NULL                           |
| nadereaandverval  | NULL                           |
| tsverval          | Leeg                           |
----
| voornamen         | Piet                           |
| bsn               | 623057001                      |
| actieinhoud       | Conversie GBA                  |
| actieverval       | NULL                           |
| nadereaandverval  | NULL                           |
| tsverval          | Leeg                           |

!-- Controleer kern.his_persgeboorte
Then in kern heeft select p.voornamen, id.datgeboorte, sainh.naam as actieinhoud, saav.naam as actieVerval, id.nadereaandverval, CASE WHEN tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval
                   from kern.his_persgeboorte id
                   left join kern.pers p on p.id = id.pers
                   left join kern.actie ainh on ainh.id = id.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.actie av on av.id = id.actieverval
                   left join kern.srtactie saav on av.srt = saav.id
                   where p.voornamen = 'Piet' or p.voornamen = 'Libby'
                   order by p.voornamen de volgende gegevens:
| veld              | waarde                         |
| voornamen         | Libby                          |
| datgeboorte       | 19660821                       |
| actieinhoud       | Conversie GBA                  |
| actieverval       | NULL                           |
| nadereaandverval  | NULL                           |
| tsverval          | Leeg                           |
----
| voornamen         | Piet                           |
| datgeboorte       | 19600821                       |
| actieinhoud       | Conversie GBA                  |
| actieverval       | NULL                           |
| nadereaandverval  | NULL                           |
| tsverval          | Leeg                           |

!-- Controleer kern.his_persgeslachtsaand
Then in kern heeft select p.voornamen, id.geslachtsaand, sainh.naam as actieinhoud, saav.naam as actieVerval, id.nadereaandverval, CASE WHEN tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval
from kern.his_persgeslachtsaand id
left join kern.pers p on p.id = id.pers
left join kern.actie ainh on ainh.id = id.actieinh
left join kern.srtactie sainh on ainh.srt = sainh.id
left join kern.actie av on av.id = id.actieverval
left join kern.srtactie saav on av.srt = saav.id
where p.voornamen = 'Piet' or p.voornamen = 'Libby'
order by p.voornamen de volgende gegevens:
| veld              | waarde                         |
| voornamen         | Libby                          |
| geslachtsaand     | 2                              |
| actieinhoud       | Conversie GBA                  |
| actieverval       | NULL                           |
| nadereaandverval  | NULL                           |
| tsverval          | Leeg                           |
----
| voornamen         | Piet                           |
| geslachtsaand     | 1                              |
| actieinhoud       | Conversie GBA                  |
| actieverval       | NULL                           |
| nadereaandverval  | NULL                           |
| tsverval          | Leeg                           |

!-- Controleer kern.his_perssamengesteldenaam
Then in kern heeft select p.voornamen, id.geslnaamstam, sainh.naam as actieinhoud, saav.naam as actieVerval, id.nadereaandverval, CASE WHEN tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval
from kern.his_perssamengesteldenaam id
left join kern.pers p on p.id = id.pers
left join kern.actie ainh on ainh.id = id.actieinh
left join kern.srtactie sainh on ainh.srt = sainh.id
left join kern.actie av on av.id = id.actieverval
left join kern.srtactie saav on av.srt = saav.id
where p.voornamen = 'Piet' or p.voornamen = 'Libby'
order by p.voornamen de volgende gegevens:
| veld              | waarde                         |
| voornamen         | Libby                          |
| geslnaamstam      | Thatcher                       |
| actieinhoud       | Conversie GBA                  |
| actieverval       | NULL                           |
| nadereaandverval  | NULL                           |
| tsverval          | Leeg                           |
----
| voornamen         | Piet                           |
| geslnaamstam      | Jansen                         |
| actieinhoud       | Conversie GBA                  |
| actieverval       | NULL                           |
| nadereaandverval  | NULL                           |
| tsverval          | Leeg                           |

!-- Controleer kern.relatie tbv A-laag test
Then in kern heeft select indag, dataanv from kern.relatie where srt = '2' de volgende gegevens:
| veld              | waarde                         |
| indag             | false                          |
| dataanv           | NULL                           |