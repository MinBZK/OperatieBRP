Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland met erkenning na geboortedatum verwerking

Scenario:   Nevenactie Registratie ouder met ingeschreven NOUWKIG
            LT: GNNG04C60T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C60T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C60T10-002.xls

When voer een bijhouding uit GNNG04C60T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer verantwoordingsgegevens van de ActieBron die door registratieOuder wordt gebruikt
Then in kern heeft select    count(1),ab.doc
                   from      kern.actiebron ab
		   join      kern.actie ainh
		   on        ainh.id = ab.actie
		   left join kern.srtactie sainh
		   on        ainh.srt = sainh.id
                   where     sainh.naam in ('Registratie ouder')
		   group     by ab.doc de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

!-- Controleer dat de NOUWKIG is gemarkeerd als bijgehouden
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
                   where     sainh.naam ='Registratie geborene'
		   and       p.voornamen = 'Henk'
                   order by  p.voornamen de volgende gegevens:
| veld                      | waarde                                               |
| actieinhoud               | Registratie geborene                                 |
| actieverval               | NULL                                                 |
| voornamen                 | Henk                                                 |
| admhndnaam                | Geboorte in Nederland met erkenning na geboortedatum |
| sorteervolgorde           | 1                                                    |

!-- Controleer soort relatie en dat groep his_relatie leeg blijft
Then in kern heeft select          sainh.naam as actieInhoud,
                                   hrel.actieverval,
			           srel.naam,
			           hrel.dataanv
                   from            kern.his_relatie hrel
                   join            kern.actie ainh
		   on              ainh.id = hrel.actieinh
                   left outer join kern.srtactie sainh
		   on              ainh.srt = sainh.id
                   left outer join kern.relatie rel
		   on              rel.id = hrel.relatie
                   left outer join kern.srtrelatie srel
		   on              srel.id = rel.srt
                   where           sainh.naam = 'Registratie geborene' de volgende gegevens:
| veld                      | waarde                        |
| actieinhoud               | Registratie geborene          |
| actieverval               | NULL                          |
| naam                      | Familierechtelijke betrekking |
| dataanv                   | NULL                          |

!-- Controleer soort Rol (NOUWKIG) en soort persoon (NOUWKIG)
!-- Henk is het kind van Margareth via zijn PL en de NOUWKIG van Jan via de bijhouding
Then in kern heeft select          sb.naam as soortrol,
                                   sp.naam as srtpersoon
                   from kern.betr b
                   left outer join kern.pers p
		   on              b.pers = p.id
                   left outer join kern.srtbetr sb
		   on              sb.id = b.rol
                   left outer join kern.srtpers sp
		   on              sp.id = p.srt
                   where           p.voornamen = 'Henk'
		   and             sb.naam <> 'Kind' de volgende gegevens:
| veld                      | waarde        |
| soortrol                  | Ouder         |
| srtpersoon                | Ingeschrevene |

!-- Controleer his_persinschr (NOUWKIG)
Then in kern heeft select          pi.datinschr,
                                   sa.naam  as actieInhoud,
			           sad.naam as AdmhndNaam,
			           p.voornamen
                   from            kern.his_persinschr pi
                   left outer join kern.actie a
		   on              a.id = pi.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   left outer join kern.admhnd ad on ad.id = a.admhnd
                   left outer join kern.srtadmhnd sad on sad.id = ad.srt
                   left outer join kern.pers p on p.id = pi.pers
                   where           p.voornamen = 'Henk' de volgende gegevens:
| veld                      | waarde                 |
| datinschr                 | 19940930               |
| actieinhoud               | Conversie GBA          |
| admhndnaam                | GBA - Initiële vulling |
| voornamen                 | Henk                   |

!-- Controleer his_persbijhouding
Then in kern heeft select          bh.naam        as nmbijh,
                                   nb.naam        as nmnbijh,
			           p.naam         as nmpartij,
			           pb.dataanvgel,
			           sa.naam        as actieInhoud
                   from            kern.his_persbijhouding pb
                   left outer join kern.naderebijhaard nb
		   on              nb.id = pb.naderebijhaard
                   left outer join kern.bijhaard bh
		   on              bh.id = pb.bijhaard
                   left outer join kern.partij p
		   on              p.id = pb.bijhpartij
                   left outer join kern.actie a
		   on              a.id = pb.actieinh
                   left outer join kern.srtactie sa
		   on              sa.id = a.srt
                   left outer join kern.pers pe
		   on              pe.id = pb.pers
		   where           pe.voornamen = 'Henk' de volgende gegevens:
| veld                      | waarde         |
| nmbijh                    | Ingezetene     |
| nmnbijh                   | Actueel        |
| nmpartij                  | Gemeente BRP 1 |
| dataanvgel                | 19980808       |
| actieinhoud               | Conversie GBA  |

!-- Controleer geslachtsaanduiding en persgeboorte
Then in kern heeft select          pg.dataanvgel       as DAGgslaand,
                                   lg.naam             as land,
			           pb.datgeboorte,
			           g.code,
			           pb.wplnaamgeboorte,
			           gs.naam             as geslacht
                   from            kern.his_persgeslachtsaand pg
                   left outer join kern.pers p
		   on              p.id = pg.pers
                   left outer join kern.geslachtsaand gs
		   on              gs.id = pg.geslachtsaand
                   left outer join kern.his_persgeboorte pb
		   on              p.id = pb.pers
                   left outer join kern.landgebied lg
		   on              lg.id = pb.landgebiedgeboorte
                   left outer join kern.gem g
		   on              g.id = pb.gemgeboorte
                   left outer join kern.actie a
		   on              a.id = pb.actieinh
                   left outer join kern.srtactie sa
		   on              sa.id = a.srt
                   left outer join kern.pers pe
		   on              pe.id = pb.pers
                   where           sa.naam ='Conversie GBA'
		   and             pe.voornamen = 'Henk' de volgende gegevens:
| veld                      | waarde    |
| daggslaand                | 19920808  |
| land                      | Nederland |
| datgeboorte               | 19660821  |
| code                      | 0014      |
| wplnaamgeboorte           | NULL      |
| geslacht                  | Man       |

!-- Controleer voornamen
Then in kern heeft select          vn.naam,
                                   vn.volgnr,
		                   hvn.dataanvgel
                   from            kern.persvoornaam vn
                   left outer join kern.his_persvoornaam hvn
		   on              hvn.persvoornaam = vn.id
                   left outer join kern.actie a
		   on              a.id = hvn.actieinh
                   left outer join kern.srtactie sa
		   on              sa.id = a.srt
                   left outer join kern.pers pe
		   on              pe.id = vn.pers
                   where sa.naam = 'Conversie GBA'
		   and pe.voornamen = 'Henk' de volgende gegevens:
| veld                      | waarde   |
| naam                      | Henk     |
| volgnr                    | 1        |
| dataanvgel                | 19920808 |

!-- Controleer persgeslachtsnaamcomponent
Then in kern heeft select          pg.volgnr,
                                   hpg.dataanvgel,
			           hpg.predicaat,
			           hpg.adellijketitel,
			           hpg.voorvoegsel,
			           hpg.scheidingsteken,
			           hpg.stam
                   from            kern.persgeslnaamcomp pg
                   left outer join kern.his_persgeslnaamcomp hpg
		   on              hpg.persgeslnaamcomp = pg.id
                   left outer join kern.actie a
		   on              a.id = hpg.actieinh
                   left outer join kern.srtactie sa
		   on              sa.id = a.srt
                   left outer join kern.pers pe
		   on              pe.id = pg.pers
                   where           sa.naam = 'Conversie GBA'
		   and             pe.voornamen = 'Henk' de volgende gegevens:
| veld                      | waarde   |
| volgnr                    | 1        |
| dataanvgel                | 19920808 |
| predicaat                 | NULL     |
| adellijketitel            | 1        |
| voorvoegsel               | l        |
| scheidingsteken           | '        |
| stam                      | Jansen   |

!-- Controleer persoon samengesteldenaam
Then in kern heeft select          sm.voornamen,
                                   sm.voorvoegsel,
			           sm.scheidingsteken,
			           sm.geslnaamstam,
			           sm.predicaat,
			           sm.adellijketitel,
			           sm.indafgeleid,
			           sm.dataanvgel,
			           sm.indnreeks
                   from            kern.his_perssamengesteldenaam sm
                   left outer join kern.actie a
		   on              a.id = sm.actieinh
                   left outer join kern.srtactie sa
		   on              sa.id = a.srt
                   left outer join kern.pers pe
		   on              pe.id = sm.pers
                   where           sa.naam = 'Conversie GBA'
		   and             pe.voornamen = 'Henk' de volgende gegevens:
| veld                      | waarde   |
| voornamen                 | Henk     |
| voorvoegsel               | l        |
| scheidingsteken           | '        |
| geslnaamstam              | Jansen   |
| predicaat                 | NULL     |
| adellijketitel            | 1        |
| indafgeleid               | true     |
| dataanvgel                | 19920808 |
| indnreeks                 | false    |

!-- Controleer persoon naamgebruik
Then in kern heeft select          ng.naamgebruik,
                                   ng.voornamennaamgebruik,
			           ng.voorvoegselnaamgebruik,
		         	   ng.scheidingstekennaamgebruik,
	         		   ng.geslnaamstamnaamgebruik,
                                   ng.predicaatnaamgebruik,
			           ng.adellijketitelnaamgebruik,
			           ng.indnaamgebruikafgeleid
                   from            kern.his_persnaamgebruik ng
                   left outer join kern.actie a
		   on              a.id = ng.actieinh
                   left outer join kern.srtactie sa
		   on              sa.id = a.srt
                   left outer join kern.pers pe
		   on              pe.id = ng.pers
                   where           sa.naam = 'Conversie GBA'
		   and             pe.voornamen = 'Henk' de volgende gegevens:
| veld                      | waarde |
| naamgebruik               | 1      |
| voornamennaamgebruik      | Henk   |
| voorvoegselnaamgebruik    | l      |
| scheidingstekennaamgebruik| '      |
| geslnaamstamnaamgebruik   | Jansen |
| predicaatnaamgebruik      | NULL   |
| adellijketitelnaamgebruik | 1      |
| indnaamgebruikafgeleid    | true   |

!-- Controleer persoon adres
Then in kern heeft select a.dataanvgel,
                          srt.naam                        as srt,
			  w.naam,
			  a.aangadresh,
			  a.dataanvadresh,
			  a.identcodeadresseerbaarobject,
			  a.identcodenraand, 
                          gem.naam                        as gem,
                          a.nor,
			  a.afgekortenor,
			  a.huisnr,
			  a.postcode,
			  a.wplnaam,
			  lg.naam                         as landgebied
                   from   kern.his_persadres a
                   join   kern.rdnwijzverblijf w
		   on     w.id = a.rdnwijz
                   join   kern.actie ac
		   on     ac.id = a.actieinh
                   join   kern.srtactie sa
		   on     sa.id = ac.srt
                   join   kern.landgebied lg
		   on     lg.id = a.landgebied
                   join   kern.gem gem
		   on     gem.id = a.gem
                   join   kern.srtadres srt
		   on     srt.id = a.srt
                   where  sa.naam ='Conversie GBA'
		   and    persadres in (
		                           select id
					   from   kern.persadres
					   where  pers in (
					                      select id
							      from   kern.pers
							      where  voornamen='Henk'
							  )
				       ) de volgende gegevens:
| veld                        | waarde                                        |
| dataanvgel                  | 20110316                                      |
| srt                         | Woonadres                                     |
| naam                        | Technische wijzigingen i.v.m. BAG             |
| aangadresh                  | NULL                                          |
| dataanvadresh               | 20170101                                      |
| identcodeadresseerbaarobject| 0626010010016001                              |
| identcodenraand             | 0626200010016001                              |
| gem                         | Gemeente BRP 1                                |
| nor                         | Baron Schimmelpenninck van der Oyelaan        |
| afgekortenor                | S vd Oyeln                                    |
| huisnr                      | 16                                            |
| postcode                    | 2252EB                                        |
| wplnaam                     | Voorschoten                                   |
| landgebied                  | Nederland                                     |

!-- Controleer betrokkenheid NOUWKIG
Then in kern heeft select          sb.naam as soortrol,
                                   sp.naam as srtpersoon,
			           p.voornamen
                   from            kern.betr b
                   left outer join kern.pers p
		   on              b.pers = p.id
                   left outer join kern.srtbetr sb
		   on              sb.id = b.rol
                   left outer join kern.srtpers sp
		   on              sp.id = p.srt
                   left outer join kern.relatie re
		   on              re.id = b.relatie
                   left outer join kern.his_relatie hre
		   on              hre.relatie = re.id
                   left outer join kern.actie a
		   on              a.id = hre.actieinh
                   left outer join kern.srtactie sa
		   on              sa.id = a.srt
                   where           sa.naam = 'Registratie geborene'
		   and             p.voornamen = 'Henk' de volgende gegevens:
| veld                      | waarde                                        |
| soortrol                  | Ouder                                         |
| srtpersoon                | Ingeschrevene                                 |
| voornamen                 | Henk                                          |

!-- Controleer datum aanvang geldigheid ouderschap OUWKIG en NOUWKIG
Then in kern heeft select          oud.dataanvgel,
                                   sa.naam,
			           p.voornamen,
				   oud.indouderuitwiekindisgeboren
                   from            kern.his_ouderouderschap oud
                   left outer join kern.betr b
		   on              b.id = oud.betr
                   left outer join kern.pers p
		   on              p.id = b.pers
                   left outer join kern.actie a
		   on              a.id = oud.actieinh
                   left outer join kern.srtactie sa
		   on              sa.id = a.srt
                   where sa.naam = 'Registratie geborene' or
		         sa.naam = 'Registratie ouder'
                   order by p.voornamen de volgende gegevens:
| veld                        | waarde               |
| dataanvgel                  | 20160102             |
| naam                        | Registratie ouder    |
| voornamen                   | Henk                 |
| indouderuitwiekindisgeboren | false                |
----
| dataanvgel                  | 20160101             |
| naam                        | Registratie geborene |
| voornamen                   | Marie                |
| indouderuitwiekindisgeboren | true                 |