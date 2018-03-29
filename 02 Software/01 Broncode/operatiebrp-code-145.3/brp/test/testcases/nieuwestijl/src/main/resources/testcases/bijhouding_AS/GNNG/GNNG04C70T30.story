Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland met erkenning na geboortedatum verwerking

Scenario:   Nevenactie Registratie geslachtsnaam voornaam waarbij voornamen vervallen d.m.v. Indicatie Namenreeks J
            LT: GNNG04C70T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C70T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C70T30-002.xls

When voer een bijhouding uit GNNG04C70T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer persoon samengesteldenaam indicatie namenreeks
Then in kern heeft select          sm.indnreeks
                   from            kern.his_perssamengesteldenaam sm
                   left outer join kern.actie a
		   on              a.id = sm.actieinh
                   left outer join kern.srtactie sa
		   on              sa.id = a.srt
                   left outer join kern.pers pe
		   on              pe.id = sm.pers
                   where           sa.naam = 'Registratie geslachtsnaam/voornaam'
		   and             pe.geslnaamstam = 'Jansens' de volgende gegevens:
| veld      | waarde |
| indnreeks | true   |

!-- Controleer dat de voornamen zijn vervallen
Then in kern heeft select count(*)
                   from   kern.his_persvoornaam hpv
                   where  hpv.naam like 'Voornaam%'
                   and    hpv.tsverval is not null
		   and    hpv.actieverval in (
                                                 select id
						 from   kern.actie
						 where  srt in (
						                   select id
								   from   kern.srtactie
                                                                   where  naam = 'Registratie geslachtsnaam/voornaam'
                                                               )
                                             ) de volgende gegevens:
| veld  | waarde   |
| count | 2        |

!-- Controleer dat de voornamen zijn vervallen a.d.h.v. aanpassing geldigheid
Then in kern heeft select count(*)
                   from   kern.his_persvoornaam hpv
                   where  hpv.naam like 'Voornaam%'
                   and    hpv.dateindegel = '20160102'
		   and    hpv.actieaanpgel in (
                                                  select id
						  from   kern.actie
						  where  srt in (
						                    select id
								    from   kern.srtactie
                                                                    where  naam = 'Registratie geslachtsnaam/voornaam'
                                                                )
                                              ) de volgende gegevens:
| veld  | waarde   |
| count | 2        |

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
                   where           sa.naam = 'Registratie geslachtsnaam/voornaam'
		   and             pe.geslnaamstam = 'Jansens' de volgende gegevens:
| veld                      | waarde   |
| volgnr                    | 1        |
| dataanvgel                | 20160102 |
| predicaat                 | NULL     |
| adellijketitel            | NULL     |
| voorvoegsel               | l        |
| scheidingsteken           | '        |
| stam                      | Jansens  |

!-- Controleer persoon naamgebruik. De voornaam moet nu leeg zijn.
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
                   where           sa.naam = 'Registratie geslachtsnaam/voornaam'
		   and             pe.geslnaamstam = 'Jansens' de volgende gegevens:
| veld                      | waarde                   |
| naamgebruik               | 1                        |
| voornamennaamgebruik      | NULL                     |
| voorvoegselnaamgebruik    | l                        |
| scheidingstekennaamgebruik| '                        |
| geslnaamstamnaamgebruik   | Jansens                  |
| predicaatnaamgebruik      | NULL                     |
| adellijketitelnaamgebruik | NULL                     |
| indnaamgebruikafgeleid    | true                     |
