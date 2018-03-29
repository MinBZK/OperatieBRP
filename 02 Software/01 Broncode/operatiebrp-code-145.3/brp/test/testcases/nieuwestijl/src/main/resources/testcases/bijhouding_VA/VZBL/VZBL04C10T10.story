Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: Registratie verhuizing buitenland

Scenario:   Verwerking hoofdactie Registratie migratie. Aangever is Gezaghouder
            LT: VZBL04C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL04C10T10.xls

And de database is aangepast met: update kern.his_persgeboorte set datgeboorte=to_number(to_char(current_date - interval '15 years','YYYYMMDD'),'9999999999.99') where pers in (select id from kern.pers where voornamen='Anne');

When voer een bijhouding uit VZBL04C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL04C10T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 2      |

Then in kern heeft select indag, gem from kern.persadres de volgende gegevens:
| veld                      | waarde |
| indag                     | false  |
| gem                       | NULL   |

!-- Controleer betrokken personen zijn gemarkeerd als bijgehouden
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, p.bsn, sa.naam as AdmhndNaam, hpaf.sorteervolgorde
                   from kern.his_persafgeleidadministrati hpaf
                   join kern.actie ainh on ainh.id = hpaf.actieinh
                   left join kern.actie av on av.id = hpaf.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.pers p on hpaf.pers = p.id
                   left join kern.admhnd a on hpaf.admhnd = a.id
                   left join kern.srtadmhnd sa on a.srt = sa.id
                   where sainh.naam ='Registratie migratie'
                   order by p.bsn de volgende gegevens:
| veld                      | waarde                                        |
| actieinhoud               | Registratie migratie                          |
| actieverval               | NULL                                          |
| bsn                       | 186791033                                     |
| admhndnaam                | Verhuizing naar buitenland                    |
| sorteervolgorde           | 1                                             |

!-- Controleer Persoon.Migratie
Then in kern heeft select sm.naam as soortMigratie,
                          pm.dataanvgel as dataanvgel,
                          rwv.naam as redenWijzigenMigratie,
                          aa.naam as Aangever,
                          lg.naam as Land,
                          bladresregel1migratie,
                          bladresregel2migratie,
                          bladresregel3migratie
                          from kern.his_persmigratie pm
                          join kern.rdnwijzverblijf rwv on pm.rdnwijzmigratie=rwv.id
                          join kern.aang aa on pm.aangmigratie=aa.id
                          join kern.srtmigratie sm on pm.srtmigratie=sm.id
                          join kern.landgebied lg on pm.landgebiedmigratie=lg.id
			  where landgebiedmigratie in (select id from kern.landgebied where naam='Aruba')
 de volgende gegevens:
| veld                      | waarde                             |
| soortMigratie             | Emigratie                          |
| dataanvgel                | 20170101                           |
| redenWijzigenMigratie     | Aangifte door persoon              |
| Aangever                  | Gezaghouder                        |
| Land                      | Aruba                              |
| bladresregel1migratie     | adres xxxxxxxxxxxxxxxxxxxxxxxxxxxx |
| bladresregel2migratie     | adres xxxxxxxxxxxxxxxxxxxxxxxxxxxx |
| bladresregel3migratie     | adres xxxxxxxxxxxxxxxxxxxxxxxxxxxx |

Then in kern heeft select sm.naam as soortMigratie,
                          pm.dataanvgel as dataanvgel,
			  pm.dateindegel as dateindegel,
                          rwv.naam as redenWijzigenMigratie,
                          aa.naam as Aangever,
                          lg.naam as Land,
                          bladresregel1migratie
                          from kern.his_persmigratie pm
                          join kern.rdnwijzverblijf rwv on pm.rdnwijzmigratie=rwv.id
                          join kern.aang aa on pm.aangmigratie=aa.id
                          join kern.srtmigratie sm on pm.srtmigratie=sm.id
                          join kern.landgebied lg on pm.landgebiedmigratie=lg.id
                          where pm.dateindegel is not null or pm.tsverval is not null
			  order by pm.dateindegel
de volgende gegevens:
| veld                      | waarde                             |
| soortMigratie             | Emigratie                          |
| dataanvgel                | 20101030                           |
| dateindegel               | 20170101                           |
| redenWijzigenMigratie     | Aangifte door persoon              |
| Aangever                  | Ingeschrevene                      |
| Land                      | Canada                             |
| bladresregel1migratie     | 59th Street                        |
----
| soortMigratie             | Emigratie                          |
| dataanvgel                | 20101030                           |
| dateindegel               | NULL                               |
| redenWijzigenMigratie     | Aangifte door persoon              |
| Aangever                  | Ingeschrevene                      |
| Land                      | Canada                             |
| bladresregel1migratie     | 59th Street                        |

!-- Controleer Persoon.Deelname EU verkiezingen
Then in kern heeft select count(*)
                   from   kern.his_persdeelneuverkiezingen
		   where  tsverval in (select tsreg from kern.his_persmigratie)
		   and    actieverval in (select actieinh from kern.his_persmigratie)
                   and    pers in (select id from kern.pers where voornamen='Anne')
de volgende gegevens:
| veld  | waarde |
| count | 1      |

!-- Controleer Persoon.Bijhouding
Then in kern heeft select count(*)
                   from   kern.his_persbijhouding
                   where  bijhaard in (select id from kern.bijhaard where code='N')
                   and    naderebijhaard in (select id from kern.naderebijhaard where code='E')
                   and    bijhpartij in (select id from kern.partij where naam='Minister')
                   and    dataanvgel in (select dataanvgel from kern.his_persmigratie)
                   and    actieinh in (select actieinh from kern.his_persmigratie)
de volgende gegevens:
| veld  | waarde |
| count | 1      |

!-- Controleer Persoon Adres.Standaard
Then in kern heeft select count(*) from kern.his_persadres
                   where  dateindegel in (select dataanvgel from kern.his_persmigratie)
                   and    actieaanpgel in (select actieinh from kern.his_persmigratie)
de volgende gegevens:
| veld  | waarde |
| count | 1      |
