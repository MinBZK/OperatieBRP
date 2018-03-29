Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland met erkenning na geboortedatum verwerking

Scenario:   Nevenactie Registratie ouder met ingeschreven pseudo-persoon NOUWKIG en predicaat
            LT: GNNG04C60T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C60T30-001.xls

When voer een bijhouding uit GNNG04C60T30.xml namens partij 'Gemeente BRP 1'

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

!-- Controleer dat de NOUWKIG niet is gemarkeerd als bijgehouden omdat het een pseudo-persoon is
!-- Pseudo-personen komen niet in afgeleid administratief.
Then in kern heeft select    count(hpaf.sorteervolgorde)
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
		   and       p.voornamen = 'Henk' de volgende gegevens:
| veld  | waarde |
| count | 0      |

!-- Controleer soort relatie en groep his_relatie
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
| veld                      | waarde         |
| soortrol                  | Ouder          |
| srtpersoon                | Pseudo-persoon |

!-- Controleer in identificatienummers dat de datum aanvang gelijk is aan de actie datum aanvang geldigheid
Then in kern heeft select p.voornamen as pers,
                          (
			      select naam
			      from   kern.srtactie
			      where  id in (
			                       select srt
					       from   kern.actie
					       where  id=hpi.actieinh
					   )
			  ) as actieinh,
                          hpi.actieverval,
                          hpi.dataanvgel,
                          hpi.dateindegel,
                          hpi.actieaanpgel,
                          hpi.bsn,
                          hpi.anr
                   from   kern.his_persids hpi
                   join   kern.pers p
                   on     hpi.pers=p.id
                   where  voornamen='Henk' de volgende gegevens:
| veld         | waarde            |
| pers         | Henk              |
| actieinh     | Registratie ouder |
| actieverval  | NULL              |
| dataanvgel   | 20160102          |
| dateindegel  | NULL              |
| actieaanpgel | NULL              |
| bsn          | 463716681         |
| anr          | 4741527314        |

!-- Controleer geslachtsaanduiding en persgeboorte
Then in kern heeft select          sa.naam             as soortActie,
                                   pg.dataanvgel       as DAGgslaand,
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
		   where           pe.voornamen = 'Henk' de volgende gegevens:
| veld                      | waarde            |
| soortActie                | Registratie ouder |
| daggslaand                | 20160102          |
| land                      | Nederland         |
| datgeboorte               | 19660821          |
| code                      | 0014              |
| wplnaamgeboorte           | Rotterdam         |
| geslacht                  | Man               |

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
                   where           sa.naam = 'Registratie ouder'
		   and             pe.voornamen = 'Henk' de volgende gegevens:
| veld                      | waarde   |
| voornamen                 | Henk     |
| voorvoegsel               | l        |
| scheidingsteken           | '        |
| geslnaamstam              | Jansen   |
| predicaat                 | 1        |
| adellijketitel            | NULL     |
| indafgeleid               | false    |
| dataanvgel                | 20160102 |
| indnreeks                 | false    |

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
| veld                      | waarde         |
| soortrol                  | Ouder          |
| srtpersoon                | Pseudo-persoon |
| voornamen                 | Henk           |

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
