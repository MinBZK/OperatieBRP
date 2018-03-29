Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland: Registratie geborene met erkenning op geboortedatum

Scenario:   Registratie geborene in Nederland met OUWKIG(I) en NOUWKIG(I) (Met Bijz.VerblijfsR. Positie) waarbij Registergemeente en gemeente van bijhoudingspartij van OUWKIG/NOUWKIG verschillend zijn
            LT: GNOG04C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNOG/GNOG04C10T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNOG/GNOG04C10T10-002.xls

When voer een bijhouding uit GNOG04C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer verantwoordingsgegevens van de ActieBron
Then in kern heeft select count(1),ab.doc
                   from kern.actiebron ab join kern.actie ainh on ainh.id = ab.actie left join kern.srtactie sainh on ainh.srt = sainh.id
                   where sainh.naam in ('Registratie geborene') group by ab.doc de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

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
                   where sainh.naam ='Registratie geborene'
                   order by p.voornamen de volgende gegevens:
| veld                      | waarde                                               |
| actieinhoud               | Registratie geborene                                 |
| actieverval               | NULL                                                 |
| voornamen                 | Hendrik Jan                                          |
| admhndnaam                | Geboorte in Nederland met erkenning op geboortedatum |
| sorteervolgorde           | 1                                                    |

!-- Controleer soort relatie en dat groep his_relatie leeg blijft
Then in kern heeft select sainh.naam as actieInhoud, hrel.actieverval, srel.naam, hrel.dataanv
                   from kern.his_relatie hrel
                   join kern.actie ainh on ainh.id = hrel.actieinh
                   left outer join kern.srtactie sainh on ainh.srt = sainh.id
                   left outer join kern.relatie rel on rel.id = hrel.relatie
                   left outer join kern.srtrelatie srel on srel.id = rel.srt
                   where sainh.naam = 'Registratie geborene' de volgende gegevens:
| veld                      | waarde                                        |
| actieinhoud               | Registratie geborene                          |
| actieverval               | NULL                                          |
| naam                      | Familierechtelijke betrekking                 |
| dataanv                   | NULL                                          |

!-- Controleer soort Rol (Kind) en soort persoon (Kind)
Then in kern heeft select sb.naam as soortrol, sp.naam as srtpersoon from kern.betr b
                   left outer join kern.pers p on b.pers = p.id
                   left outer join kern.srtbetr sb on sb.id = b.rol
                   left outer join kern.srtpers sp on sp.id = p.srt
                    where p.voornamen = 'Hendrik Jan' de volgende gegevens:
| veld                      | waarde                                        |
| soortrol                  | Kind                                          |
| srtpersoon                | Ingeschrevene                                 |

!-- Controleer his_persinschr (Kind)
Then in kern heeft select pi.datinschr, pi.versienr, sa.naam as actieInhoud, sad.naam as AdmhndNaam, p.voornamen
                                      from kern.his_persinschr pi
                                      left outer join kern.actie a on a.id = pi.actieinh
                                      left outer join kern.srtactie sa on sa.id = a.srt
                                      left outer join kern.admhnd ad on ad.id = a.admhnd
                                      left outer join kern.srtadmhnd sad on sad.id = ad.srt
                                      left outer join kern.pers p on p.id = pi.pers
                                      where sa.naam ='Registratie geborene' and p.voornamen = 'Hendrik Jan' de volgende gegevens:
| veld                      | waarde                                               |
| datinschr                 | 20161231                                             |
| versienr                  | 1                                                    |
| actieinhoud               | Registratie geborene                                 |
| admhndnaam                | Geboorte in Nederland met erkenning op geboortedatum |
| voornamen                 | Hendrik Jan                                          |

!-- Controleer his_persbijhouding
Then in kern heeft select bh.naam as nmbijh, nb.naam as nmnbijh, p.naam as nmpartij, pb.dataanvgel, sa.naam as actieInhoud
                   from kern.his_persbijhouding pb
                   left outer join kern.naderebijhaard nb on nb.id = pb.naderebijhaard
                   left outer join kern.bijhaard bh on bh.id = pb.bijhaard
                   left outer join kern.partij p on p.id = pb.bijhpartij
                   left outer join kern.actie a on a.id = pb.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   left outer join kern.pers pe on pe.id = pb.pers
                   where sa.naam ='Registratie geborene' and pe.voornamen = 'Hendrik Jan' de volgende gegevens:
| veld                      | waarde                                        |
| nmbijh                    | Ingezetene                                    |
| nmnbijh                   | Actueel                                       |
| nmpartij                  | Gemeente BRP 1                                |
| dataanvgel                | 20161231                                      |
| actieinhoud               | Registratie geborene                          |

!-- Controleer geslachtsaanduiding en persgeboorte
Then in kern heeft select pg.dataanvgel as DAGgslaand, lg.naam as land, pb.datgeboorte, g.code, pb.wplnaamgeboorte, gs.naam as geslacht
                                      from kern.his_persgeslachtsaand pg
                                      left outer join kern.pers p on p.id = pg.pers
                                      left outer join kern.geslachtsaand gs on gs.id = pg.geslachtsaand
                                      left outer join kern.his_persgeboorte pb on p.id = pb.pers
                                      left outer join kern.landgebied lg on lg.id = pb.landgebiedgeboorte
                                      left outer join kern.gem g on g.id = pb.gemgeboorte
                                      left outer join kern.actie a on a.id = pb.actieinh
                                      left outer join kern.srtactie sa on sa.id = a.srt
                                      left outer join kern.pers pe on pe.id = pb.pers
                                      where sa.naam ='Registratie geborene' and pe.voornamen = 'Hendrik Jan' de volgende gegevens:
| veld                      | waarde                                        |
| daggslaand                | 20161231                                      |
| land                      | Nederland                                     |
| datgeboorte               | 20161231                                      |
| code                      | 7112                                          |
| wplnaamgeboorte           | Rotterdam                                     |
| geslacht                  | Man                                           |

!-- Controleer voornamen
Then in kern heeft select vn.naam, vn.volgnr, hvn.dataanvgel
                   from kern.persvoornaam vn
                   left outer join kern.his_persvoornaam hvn on hvn.persvoornaam = vn.id
                   left outer join kern.actie a on a.id = hvn.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   left outer join kern.pers pe on pe.id = vn.pers
                   where sa.naam ='Registratie geborene' and pe.voornamen = 'Hendrik Jan' de volgende gegevens:
| veld                      | waarde                                        |
| naam                      | Hendrik                                       |
| volgnr                    | 1                                             |
| dataanvgel                | 20161231                                      |
----
| naam                      | Jan                                           |
| volgnr                    | 2                                             |
| dataanvgel                | 20161231                                      |

!-- Controleer persgeslachtsnaamcomponent
Then in kern heeft select pg.volgnr, hpg.dataanvgel, hpg.predicaat, hpg.adellijketitel, hpg.voorvoegsel, hpg.scheidingsteken, hpg.stam
                   from kern.persgeslnaamcomp pg
                   left outer join kern.his_persgeslnaamcomp hpg on hpg.persgeslnaamcomp = pg.id
                   left outer join kern.actie a on a.id = hpg.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   left outer join kern.pers pe on pe.id = pg.pers
                   where sa.naam ='Registratie geborene' and pe.voornamen = 'Hendrik Jan' de volgende gegevens:
| veld                      | waarde                                        |
| volgnr                    | 1                                             |
| dataanvgel                | 20161231                                      |
| predicaat                 | NULL                                          |
| adellijketitel            | 1                                             |
| voorvoegsel               | l                                             |
| scheidingsteken           | '                                             |
| stam                      | Vader                                         |

!-- Controleer persoon samengesteldenaam
Then in kern heeft select sm.voornamen, sm.voorvoegsel, sm.scheidingsteken, sm.geslnaamstam, sm.predicaat, sm.adellijketitel, sm.indafgeleid, sm.dataanvgel, sm.indnreeks
                   from kern.his_perssamengesteldenaam sm
                   left outer join kern.actie a on a.id = sm.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   left outer join kern.pers pe on pe.id = sm.pers
                   where sa.naam ='Registratie geborene' and pe.voornamen = 'Hendrik Jan' de volgende gegevens:
| veld                      | waarde                                        |
| voornamen                 | Hendrik Jan                                   |
| voorvoegsel               | l                                             |
| scheidingsteken           | '                                             |
| geslnaamstam              | Vader                                         |
| predicaat                 | NULL                                          |
| adellijketitel            | 1                                             |
| indafgeleid               | true                                          |
| dataanvgel                | 20161231                                      |
| indnreeks                 | false                                         |

!-- Controleer persoon naamgebruik
Then in kern heeft select ng.naamgebruik, ng.voornamennaamgebruik, ng.voorvoegselnaamgebruik, ng.scheidingstekennaamgebruik, ng.geslnaamstamnaamgebruik,
                   ng.predicaatnaamgebruik, ng.adellijketitelnaamgebruik, ng.indnaamgebruikafgeleid
                   from kern.his_persnaamgebruik ng
                   left outer join kern.actie a on a.id = ng.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   left outer join kern.pers pe on pe.id = ng.pers
                   where sa.naam ='Registratie geborene' and pe.voornamen = 'Hendrik Jan' de volgende gegevens:
| veld                      | waarde                                        |
| naamgebruik               | 1                                             |
| voornamennaamgebruik      | Hendrik Jan                                   |
| voorvoegselnaamgebruik    | l                                             |
| scheidingstekennaamgebruik| '                                             |
| geslnaamstamnaamgebruik   | Vader                                         |
| predicaatnaamgebruik      | NULL                                          |
| adellijketitelnaamgebruik | 1                                             |
| indnaamgebruikafgeleid    | true                                          |

!-- Controleer bijzonder verblijfsrechtelijke positie
Then in kern heeft select si.naam, pi.waarde
                   from kern.srtindicatie si
                   left outer join kern.persindicatie pi on pi.srt = si.id
                   left outer join kern.his_persindicatie hpi on hpi.persindicatie = pi.id
                   left outer join kern.actie a on a.id = hpi.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   left outer join kern.pers pe on pe.id = pi.pers
                   where sa.naam ='Registratie geborene' and pe.voornamen = 'Hendrik Jan' de volgende gegevens:
| veld                      | waarde                                        |
| naam                      | Bijzondere verblijfsrechtelijke positie?      |
| waarde                    | true                                          |


!-- Controleer persoon adres
Then in kern heeft select a.dataanvgel, srt.naam as srt, w.naam, a.aangadresh, a.dataanvadresh, a.identcodeadresseerbaarobject, a.identcodenraand, gem.naam as gem,
                                      a.nor, a.afgekortenor, a.huisnr, a.postcode, a.wplnaam, lg.naam as landgebied
                                      from kern.his_persadres a
                                      join kern.rdnwijzverblijf w on w.id = a.rdnwijz
                                      join kern.actie ac on ac.id = a.actieinh
                                      join kern.srtactie sa on sa.id = ac.srt
                                      join kern.landgebied lg on lg.id = a.landgebied
                                      join kern.gem gem on gem.id = a.gem
                                      join kern.srtadres srt on srt.id = a.srt
                                      where sa.naam ='Registratie geborene' de volgende gegevens:
| veld                        | waarde                                        |
| dataanvgel                  | 20161231                                      |
| srt                         | Woonadres                                     |
| naam                        | Ambtshalve                                    |
| aangadresh                  | NULL                                          |
| dataanvadresh               | 20161231                                      |
| identcodeadresseerbaarobject| 0626010010016001                              |
| identcodenraand             | 0626200010016001                              |
| gem                         | Gemeente BRP 1                                |
| nor                         | Baron Schimmelpenninck van der Oyelaan        |
| afgekortenor                | S vd Oyeln                                    |
| huisnr                      | 16                                            |
| postcode                    | 2252EB                                        |
| wplnaam                     | Voorschoten                                   |
| landgebied                  | Nederland                                     |

!-- Controleer betrokkenheid OUWKIG
Then in kern heeft select sb.naam as soortrol, sp.naam as srtpersoon, p.voornamen
                   from kern.betr b
                   left outer join kern.pers p on b.pers = p.id
                   left outer join kern.srtbetr sb on sb.id = b.rol
                   left outer join kern.srtpers sp on sp.id = p.srt
                   left outer join kern.relatie re on re.id = b.relatie
                   left outer join kern.his_relatie hre on hre.relatie = re.id
                   left outer join kern.actie a on a.id = hre.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   where sa.naam ='Registratie geborene' and p.voornamen = 'Mama' de volgende gegevens:
| veld                      | waarde                                        |
| soortrol                  | Ouder                                         |
| srtpersoon                | Ingeschrevene                                 |
| voornamen                 | Mama                                          |

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
                   where sa.naam ='Registratie geborene' and p.voornamen = 'Papa' de volgende gegevens:
| veld                      | waarde                                        |
| soortrol                  | Ouder                                         |
| srtpersoon                | Ingeschrevene                                 |
| voornamen                 | Papa                                          |

!-- Controleer datum aanvang geldigheid ouderschap OUWKIG en NOUWKIG
Then in kern heeft select oud.dataanvgel, sa.naam, p.voornamen, oud.indouderuitwiekindisgeboren
                   from kern.his_ouderouderschap oud
                   left outer join kern.betr b on b.id = oud.betr
                   left outer join kern.pers p on p.id = b.pers
                   left outer join kern.actie a on a.id = oud.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   where sa.naam ='Registratie geborene'
                   order by p.voornamen de volgende gegevens:
| veld                          | waarde                                        |
| dataanvgel                    | 20161231                                      |
| naam                          | Registratie geborene                          |
| voornamen                     | Mama                                          |
| indouderuitwiekindisgeboren   | true                                          |
----
| dataanvgel                    | 20161231                                      |
| naam                          | Registratie geborene                          |
| voornamen                     | Papa                                          |
| indouderuitwiekindisgeboren   | false                                         |

Then staat er 1 notificatiebericht voor bijhouders op de queue

