Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland met erkenning na geboortedatum verwerking

Scenario:   Nevenactie Registratie geslachtsnaam voornaam met adellijke titel en 2 voornamen
            LT: GNNG04C70T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C70T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C70T10-002.xls

When voer een bijhouding uit GNNG04C70T10.xml namens partij 'Gemeente BRP 1'

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
		   and             pe.geslnaamstam = 'Jansen' de volgende gegevens:
| veld      | waarde |
| indnreeks | false  |

!-- Controleer de historie van samengesteldenaam
Then in kern heeft select case
                              when hps.tsreg is not null then
                              'gevuld'
                          else
                              'leeg'
                          end as tsreg,
                          case
                              when hps.tsverval is not null then
                              'gevuld'
                          else
                              'leeg'
                          end as tsverval,
                          hps.dataanvgel,
                          hps.dateindegel,
                          (
                              select ai.naam as actieInhoud
                              from kern.srtactie ai
                              where id in (
                                              select srt
                                              from   kern.actie
                                              where  id=hps.actieinh
                                          )
                          ),
                          (
                              select av.naam as actieVerval
                              from kern.srtactie av
                              where id in (
                                              select srt
                                              from   kern.actie
                                              where  id=hps.actieverval
                                          )
                          ),
                          (
                              select av.naam as actieAanpassingGeldigheid
                              from kern.srtactie av
                              where id in (
                                              select srt
                                              from   kern.actie
                                              where  id=hps.actieaanpgel
                                          )
                          ),
                          hps.voornamen,
                          hps.adellijketitel,
                          hps.voorvoegsel,
                          case
			      when hps.scheidingsteken=' ' then
			      'spatie'
			  else
			      hps.scheidingsteken
			  end as scheidingsteken,
                          hps.geslnaamstam
                          from kern.his_perssamengesteldenaam hps
                          where pers in (
                                            select id
                                            from   kern.pers
                                            where  voornamen='Kees Koos'
                                        ) de volgende gegevens:
| veld                      | waarde                             |
| tsreg                     | gevuld                             |
| tsverval                  | leeg                               |
| dataanvgel                | 20160101                           |
| dateindegel               | 20160102                           |
| actieinhoud               | Registratie geborene               |
| actieverval               | NULL                               |
| actieaanpassinggeldigheid | Registratie geslachtsnaam/voornaam |
| voornamen                 | NULL                               |
| adellijketitel            | 1                                  |
| voorvoegsel               | l                                  |
| scheidingsteken           | '                             |
| geslnaamstam              | Jansen                             |
----
| tsreg                     | gevuld                             |
| tsverval                  | gevuld                             |
| dataanvgel                | 20160101                           |
| dateindegel               | NULL                               |
| actieinhoud               | Registratie geborene               |
| actieverval               | Registratie geslachtsnaam/voornaam |
| actieaanpassinggeldigheid | NULL                               |
| voornamen                 | NULL                               |
| adellijketitel            | 1                                  |
| voorvoegsel               | l                                  |
| scheidingsteken           | '                            |
| geslnaamstam              | Jansen                             |
----
| tsreg                     | gevuld                             |
| tsverval                  | leeg                               |
| dataanvgel                | 20160102                           |
| dateindegel               | NULL                               |
| actieinhoud               | Registratie geslachtsnaam/voornaam |
| actieverval               | NULL                               |
| actieaanpassinggeldigheid | NULL                               |
| voornamen                 | Kees Koos                          |
| adellijketitel            | 1                                  |
| voorvoegsel               | l                                  |
| scheidingsteken           | '                             |
| geslnaamstam              | Jansen                             |

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
                   where sa.naam = 'Registratie geslachtsnaam/voornaam'
		   and pe.geslnaamstam = 'Jansen' de volgende gegevens:
| veld       | waarde   |
| naam       | Kees     |
| volgnr     | 1        |
| dataanvgel | 20160102 |
----
| naam       | Koos     |
| volgnr     | 2        |
| dataanvgel | 20160102 |

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
		   and             pe.geslnaamstam = 'Jansen' de volgende gegevens:
| veld                      | waarde   |
| volgnr                    | 1        |
| dataanvgel                | 20160102 |
| predicaat                 | NULL     |
| adellijketitel            | 1        |
| voorvoegsel               | l        |
| scheidingsteken           | '        |
| stam                      | Jansen  |

!-- Controleer de historie van geslachtsnaamcomponent
Then in kern heeft select case
                              when hpg.tsreg is not null then
                              'gevuld'
                          else
                              'leeg'
                          end as tsreg,
                          case
                              when hpg.tsverval is not null then
                              'gevuld'
                          else
                              'leeg'
                          end as tsverval,
                          hpg.dataanvgel,
                          hpg.dateindegel,
                          (
			      select ai.naam as actieInhoud
			      from   kern.srtactie ai
			      where  id in (
			                       select srt
					       from   kern.actie
					       where  id=hpg.actieinh
					   )
		          ),
                          (
			      select av.naam as actieVerval
			      from   kern.srtactie av
			      where  id in (
			                       select srt
					       from   kern.actie
					       where  id=hpg.actieverval
					   )
			  ),
                          (
			      select av.naam as actieAanpassingGeldigheid
			      from   kern.srtactie av
			      where  id in (
			                       select srt
					       from   kern.actie
					       where  id=hpg.actieaanpgel
					   )
			  ),
                          hpg.adellijketitel,
                          hpg.voorvoegsel,
                          case
			      when hpg.scheidingsteken=' ' then
			      'spatie'
			  else
			      hpg.scheidingsteken
			  end as scheidingsteken,
                          hpg.stam
                          from kern.his_persgeslnaamcomp hpg
                          where hpg.persgeslnaamcomp in (
			                                    select id
							    from   kern.persgeslnaamcomp
							    where  pers in (
							                       select id
									       from   kern.pers
									       where  voornamen='Kees Koos'
									   )
							) order by dataanvgel,dateindegel
                          de volgende gegevens:
| veld                      | waarde                             |
| tsreg                     | gevuld                             |
| tsverval                  | leeg                               |
| dataanvgel                | 20160101                           |
| dateindegel               | 20160102                           |
| actieinhoud               | Registratie geborene               |
| actieverval               | NULL                               |
| actieaanpassinggeldigheid | Registratie geslachtsnaam/voornaam |
| adellijketitel            | 1                                  |
| voorvoegsel               | l                                  |
| scheidingsteken           | '                             |
| stam                      | Jansen                             |
----
| tsreg                     | gevuld                             |
| tsverval                  | gevuld                             |
| dataanvgel                | 20160101                           |
| dateindegel               | NULL                               |
| actieinhoud               | Registratie geborene               |
| actieverval               | Registratie geslachtsnaam/voornaam |
| actieaanpassinggeldigheid | NULL                               |
| adellijketitel            | 1                                  |
| voorvoegsel               | l                                  |
| scheidingsteken           | '                            |
| stam                      | Jansen                             |
----
| tsreg                     | gevuld                             |
| tsverval                  | leeg                               |
| dataanvgel                | 20160102                           |
| dateindegel               | NULL                               |
| actieinhoud               | Registratie geslachtsnaam/voornaam |
| actieverval               | NULL                               |
| actieaanpassinggeldigheid | NULL                               |
| adellijketitel            | 1                                  |
| voorvoegsel               | l                                  |
| scheidingsteken           | '                                  |
| stam                      | Jansen                            |

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
                   where           sa.naam = 'Registratie geslachtsnaam/voornaam'
		   and             pe.geslnaamstam = 'Jansen' de volgende gegevens:
| veld                      | waarde    |
| naamgebruik               | 1         |
| voornamennaamgebruik      | Kees Koos |
| voorvoegselnaamgebruik    | l         |
| scheidingstekennaamgebruik| '         |
| geslnaamstamnaamgebruik   | Jansen   |
| predicaatnaamgebruik      | NULL      |
| adellijketitelnaamgebruik | 1         |
| indnaamgebruikafgeleid    | true      |
