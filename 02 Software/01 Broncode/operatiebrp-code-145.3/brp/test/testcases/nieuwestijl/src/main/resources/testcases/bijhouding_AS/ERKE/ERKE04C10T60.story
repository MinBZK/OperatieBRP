Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Erkenning: Hoofdactie Registratie ouder

Scenario:   Erkenning waarbij de ouder of erkenner een pseudo-persoon is
            LT: ERKE04C10T60

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE04C10T60-001.xls

When voer een bijhouding uit ERKE04C10T60a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('237394121') and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('237394121') and r.srt=3

When voer een bijhouding uit ERKE04C10T60b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer verantwoordingsgegevens van de ActieBron
Then in kern heeft select count(1),ab.doc
                   from kern.actiebron ab join kern.actie ainh on ainh.id = ab.actie left join kern.srtactie sainh on ainh.srt = sainh.id
                   where sainh.naam in ('Registratie ouder') group by ab.doc de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

!-- Controleer betrokkenheid NOUWKIG
Then in kern heeft select sb.naam as soortrol, sp.naam as srtpersoon, p.voornamen
                   from kern.betr b
                   left outer join kern.pers p on b.pers = p.id
                   left outer join kern.srtbetr sb on sb.id = b.rol
                   left outer join kern.srtpers sp on sp.id = p.srt
                   left outer join kern.relatie re on re.id = b.relatie
                   left outer join kern.his_relatie hre on hre.relatie = re.id
                   left outer join kern.actie a on a.id = hre.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   where b.relatie in (select relatie from kern.betr where pers in (select id from kern.pers where bsn='237394121'))
                   order by p.voornamen de volgende gegevens:
| veld                      | waarde                                        |
| soortrol                  | Ouder                                         |
| srtpersoon                | Pseudo-persoon                                |
| voornamen                 | Henk                                          |
----
| soortrol                  | Kind                                          |
| srtpersoon                | Ingeschrevene                                 |
| voornamen                 | Jan                                           |
----
| soortrol                  | Ouder                                         |
| srtpersoon                | Ingeschrevene                                 |
| voornamen                 | Marie                                         |

!-- Controleer datum aanvang geldigheid ouderschap OUWKIG en NOUWKIG
Then in kern heeft select oud.dataanvgel, sa.naam, p.voornamen, oud.indouderuitwiekindisgeboren
                   from kern.his_ouderouderschap oud
                   left outer join kern.betr b on b.id = oud.betr
                   left outer join kern.pers p on p.id = b.pers
                   left outer join kern.actie a on a.id = oud.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   where sa.naam ='Registratie geborene'
                   or sa.naam ='Registratie ouder'
                   order by p.voornamen de volgende gegevens:
| veld                          | waarde                                        |
| dataanvgel                    | 20160102                                      |
| naam                          | Registratie ouder                             |
| voornamen                     | Henk                                          |
| indouderuitwiekindisgeboren   | NULL                                          |
----
| dataanvgel                    | 20160101                                      |
| naam                          | Registratie geborene                          |
| voornamen                     | Marie                                         |
| indouderuitwiekindisgeboren   | true                                          |

!-- Controleer registratie van identificatienummers van pseudo-persoon NOUWKIG
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, hpid.bsn, hpid.anr, hpid.dataanvgel, hpid.dateindegel
                   from kern.his_persids hpid
                   left join kern.pers p on p.id = hpid.pers
                   left join kern.actie ainh on ainh.id = hpid.actieinh
                   left join kern.actie av on av.id = hpid.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   where p.voornamen ='Henk' de volgende gegevens:
| veld         | waarde            |
| actieinhoud  | Registratie ouder |
| actieverval  | NULL              |
| bsn          | 378985577         |
| anr          | 8913738529        |
| dataanvgel   | 20160102          |
| dateindegel  | NULL              |

!-- Controleer geslachtsaanduiding en persgeboorte
Then in kern heeft select sa.naam as soortActie, pg.dataanvgel as DAGgslaand, lg.naam as land, pb.datgeboorte, g.code, pb.wplnaamgeboorte,
			       gs.naam as geslacht
                   from kern.his_persgeslachtsaand pg
                   left outer join kern.pers p on p.id = pg.pers
                   left outer join kern.geslachtsaand gs on gs.id = pg.geslachtsaand
                   left outer join kern.his_persgeboorte pb on p.id = pb.pers
                   left outer join kern.landgebied lg on lg.id = pb.landgebiedgeboorte
                   left outer join kern.gem g on g.id = pb.gemgeboorte
                   left outer join kern.actie a on a.id = pb.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   left outer join kern.pers pe on pe.id = pb.pers
		           where pe.voornamen = 'Henk' de volgende gegevens:
| veld                      | waarde            |
| soortActie                | Registratie ouder |
| daggslaand                | 20160102          |
| land                      | Nederland         |
| datgeboorte               | 19660821          |
| code                      | 0014              |
| wplnaamgeboorte           | Rotterdam         |
| geslacht                  | Man               |

!-- Controleer persoon samengesteldenaam
Then in kern heeft select sm.voornamen, sm.voorvoegsel, sm.scheidingsteken, sm.geslnaamstam, sm.predicaat, sm.adellijketitel,
			       sm.indafgeleid, sm.dataanvgel, sm.indnreeks
                   from kern.his_perssamengesteldenaam sm
                   left outer join kern.actie a on a.id = sm.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   left outer join kern.pers pe on pe.id = sm.pers
                   where sa.naam = 'Registratie ouder'
		           and pe.voornamen = 'Henk' de volgende gegevens:
| veld                      | waarde   |
| voornamen                 | Henk     |
| voorvoegsel               | l        |
| scheidingsteken           | '        |
| geslnaamstam              | Jansen   |
| predicaat                 | NULL     |
| adellijketitel            | 1        |
| indafgeleid               | false    |
| dataanvgel                | 20160102 |
| indnreeks                 | false    |

