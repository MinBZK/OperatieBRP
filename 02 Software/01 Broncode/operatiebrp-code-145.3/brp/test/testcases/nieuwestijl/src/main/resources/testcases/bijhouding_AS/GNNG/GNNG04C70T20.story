Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland met erkenning na geboortedatum verwerking

Scenario:   Nevenactie Registratie geslachtsnaam voornaam met predicaat
            LT: GNNG04C70T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C70T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C70T20-002.xls

When voer een bijhouding uit GNNG04C70T20.xml namens partij 'Gemeente BRP 1'

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
| indnreeks | false  |

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
		   and pe.geslnaamstam = 'Jansens' de volgende gegevens:
| veld       | waarde   |
| naam       | Kees     |
| volgnr     | 1        |
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
		   and             pe.geslnaamstam = 'Jansens' de volgende gegevens:
| veld                      | waarde   |
| volgnr                    | 1        |
| dataanvgel                | 20160102 |
| predicaat                 | 1        |
| adellijketitel            | NULL     |
| voorvoegsel               | l        |
| scheidingsteken           | '        |
| stam                      | Jansens  |

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
		   and             pe.geslnaamstam = 'Jansens' de volgende gegevens:
| veld                      | waarde  |
| naamgebruik               | 1       |
| voornamennaamgebruik      | Kees    |
| voorvoegselnaamgebruik    | l       |
| scheidingstekennaamgebruik| '       |
| geslnaamstamnaamgebruik   | Jansens |
| predicaatnaamgebruik      | 1       |
| adellijketitelnaamgebruik | NULL    |
| indnaamgebruikafgeleid    | true    |
