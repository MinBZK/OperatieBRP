Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: Hoofdactie Registratie adres in combinatie met AH wijziging gemeente infrastructureel

Scenario:   Registratie wijziging gemeente infrastructureel met een volledige verstrekkingsbeperking
            LT: WGIS04C10T50

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-WGIS/WGIS04C10T50-001.xls

When voer een bijhouding uit WGIS04C10T50a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

When voer een bijhouding uit WGIS04C10T50b.xml namens partij 'Gemeente BRP 2'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/WGIS/expected/WGIS04C10T50.xml voor expressie //brp:bhg_vbaActualiseerInfrastructureleWijziging_R

Then staat er 0 notificatiebericht voor bijhouders op de queue

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 3      |

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
                   where sainh.naam ='Registratie adres'
                   order by p.bsn de volgende gegevens:
| veld                      | waarde                                        |
| actieinhoud               | Registratie adres                             |
| actieverval               | NULL                                          |
| bsn                       | 336792025                                     |
| admhndnaam                | Wijziging gemeente infrastructureel           |
| sorteervolgorde           | 1                                             |

!-- Controleer of de his_persbijhouding is bijgewerkt
Then in kern heeft select sainh.naam as actieInh, saav.naam as actieVerval, hpaf.dataanvgel, hpaf.dateindegel, saaa.naam as actieAanp, hpaf.bijhpartij,
                                      hpaf.bijhaard, hpaf.naderebijhaard
                                      from kern.his_persbijhouding hpaf
                                      join kern.actie ainh on ainh.id = hpaf.actieinh
                                      left join kern.actie av on av.id = hpaf.actieverval
                                      left join kern.actie aa on aa.id = hpaf.actieaanpgel
                                      left join kern.srtactie sainh on ainh.srt = sainh.id
                                      left join kern.srtactie saav on av.srt = saav.id
                                      left join kern.srtactie saaa on aa.srt = saaa.id
                                      left join kern.pers p on hpaf.pers = p.id
                                      where sainh.naam ='Registratie adres'
                                      or saav.naam ='Registratie adres'
                                      or saaa.naam ='Registratie adres'
                                      order by hpaf.bijhpartij, hpaf.dateindegel de volgende gegevens:
| veld                      | waarde                   |
| actieInh                  | Conversie GBA            |
| actieVerval               | NULL                     |
| dataanvgel                | 19980622                 |
| dateindegel               | 20160102                 |
| actieAanp                 | Registratie adres        |
| bijhpartij                | 27012                    |
| bijhaard                  | 1                        |
| naderebijhaard            | 1                        |
----
| actieInh                  | Conversie GBA            |
| actieVerval               | Registratie adres        |
| dataanvgel                | 19980622                 |
| dateindegel               | NULL                     |
| actieAanp                 | NULL                     |
| bijhpartij                | 27012                    |
| bijhaard                  | 1                        |
| naderebijhaard            | 1                        |
----
| actieInh                  | Registratie adres        |
| actieVerval               | NULL                     |
| dataanvgel                | 20160102                 |
| dateindegel               | NULL                     |
| actieAanp                 | NULL                     |
| bijhpartij                | 27017                    |
| bijhaard                  | 1                        |
| naderebijhaard            | 1                        |

!-- Controleer adresregels in his_persadres
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, hpad.dataanvgel, hpad.dateindegel, saaa.naam as actieAanpas, hpad.srt, hpad.rdnwijz,
                   hpad.aangadresh, hpad.dataanvadresh, hpad.identcodeadresseerbaarobject, hpad.identcodenraand, hpad.gem, hpad.nor, hpad.afgekortenor, hpad.gemdeel, hpad.huisnr,
                   hpad.huisletter, hpad.huisnrtoevoeging, hpad.postcode, hpad.wplnaam, hpad.loctenopzichtevanadres, hpad.locoms, hpad.bladresregel1,
                   hpad.bladresregel2, hpad.bladresregel3, hpad.bladresregel4, hpad.bladresregel5, hpad.bladresregel6,
                   hpad.landgebied, hpad.indpersaangetroffenopadres
                   from kern.his_persadres hpad
                   left join kern.persadres pad on pad.id = hpad.persadres
                   left join kern.pers p on p.id = pad.pers
                   join kern.actie ainh on ainh.id = hpad.actieinh
                   left join kern.actie av on av.id = hpad.actieverval
                   left join kern.actie aa on aa.id = hpad.actieaanpgel
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.srtactie saaa on aa.srt = saaa.id
                   where p.bsn ='336792025'
                   order by hpad.dataanvgel, hpad.dateindegel de volgende gegevens:
| veld                            | waarde                                        |
| actieinhoud                     | Conversie GBA                                 |
| actieverval                     | NULL                                          |
| dataanvgel                      | 20110316                                      |
| dateindegel                     | 20160102                                      |
| actieAanpas                     | Registratie adres                             |
| srt                             | 1                                             |
| rdnwijz                         | 4                                             |
| aangadresh                      | NULL                                          |
| dataanvadresh                   | 19980622                                      |
| identcodeadresseerbaarobject    | 0626010010016001                              |
| identcodenraand                 | 0626200010016001                              |
| gem                             | 7012                                          |
| nor                             | Baron Schimmelpenninck van der Oyelaan        |
| afgekortenor                    | S vd Oyeln                                    |
| gemdeel                         | NULL                                          |
| huisnr                          | 16                                            |
| huisletter                      | NULL                                          |
| huisnrtoevoeging                | NULL                                          |
| postcode                        | 2252EB                                        |
| wplnaam                         | Voorschoten                                   |
| loctenopzichtevanadres          | NULL                                          |
| locoms                          | NULL                                          |
| bladresregel1                   | NULL                                          |
| bladresregel2                   | NULL                                          |
| bladresregel3                   | NULL                                          |
| bladresregel4                   | NULL                                          |
| bladresregel5                   | NULL                                          |
| bladresregel6                   | NULL                                          |
| landgebied                      | 229                                           |
| indpersaangetroffenopadres      | NULL                                          |
----
| actieinhoud                     | Conversie GBA                                 |
| actieverval                     | Registratie adres                             |
| dataanvgel                      | 20110316                                      |
| dateindegel                     | NULL                                          |
| actieAanpas                     | NULL                                          |
| srt                             | 1                                             |
| rdnwijz                         | 4                                             |
| aangadresh                      | NULL                                          |
| dataanvadresh                   | 19980622                                      |
| identcodeadresseerbaarobject    | 0626010010016001                              |
| identcodenraand                 | 0626200010016001                              |
| gem                             | 7012                                          |
| nor                             | Baron Schimmelpenninck van der Oyelaan        |
| afgekortenor                    | S vd Oyeln                                    |
| gemdeel                         | NULL                                          |
| huisnr                          | 16                                            |
| huisletter                      | NULL                                          |
| huisnrtoevoeging                | NULL                                          |
| postcode                        | 2252EB                                        |
| wplnaam                         | Voorschoten                                   |
| loctenopzichtevanadres          | NULL                                          |
| locoms                          | NULL                                          |
| bladresregel1                   | NULL                                          |
| bladresregel2                   | NULL                                          |
| bladresregel3                   | NULL                                          |
| bladresregel4                   | NULL                                          |
| bladresregel5                   | NULL                                          |
| bladresregel6                   | NULL                                          |
| landgebied                      | 229                                           |
| indpersaangetroffenopadres      | NULL                                          |
----
| actieinhoud                     | Registratie adres                             |
| actieverval                     | NULL                                          |
| dataanvgel                      | 20160102                                      |
| dateindegel                     | 20160103                                      |
| actieAanpas                     | Registratie adres                             |
| srt                             | 1                                             |
| rdnwijz                         | 2                                             |
| aangadresh                      | NULL                                          |
| dataanvadresh                   | 20160102                                      |
| identcodeadresseerbaarobject    | NULL                                          |
| identcodenraand                 | NULL                                          |
| gem                             | 7012                                          |
| nor                             | NULL                                          |
| afgekortenor                    | NULL                                          |
| gemdeel                         | NULL                                          |
| huisnr                          | NULL                                          |
| huisletter                      | NULL                                          |
| huisnrtoevoeging                | NULL                                          |
| postcode                        | NULL                                          |
| wplnaam                         | NULL                                          |
| loctenopzichtevanadres          | NULL                                          |
| locoms                          | in het bos                                    |
| bladresregel1                   | NULL                                          |
| bladresregel2                   | NULL                                          |
| bladresregel3                   | NULL                                          |
| bladresregel4                   | NULL                                          |
| bladresregel5                   | NULL                                          |
| bladresregel6                   | NULL                                          |
| landgebied                      | 229                                           |
| indpersaangetroffenopadres      | NULL                                          |
----
| actieinhoud                     | Registratie adres                             |
| actieverval                     | Registratie adres                             |
| dataanvgel                      | 20160102                                      |
| dateindegel                     | NULL                                          |
| actieAanpas                     | NULL                                          |
| srt                             | 1                                             |
| rdnwijz                         | 2                                             |
| aangadresh                      | NULL                                          |
| dataanvadresh                   | 20160102                                      |
| identcodeadresseerbaarobject    | NULL                                          |
| identcodenraand                 | NULL                                          |
| gem                             | 7012                                          |
| nor                             | NULL                                          |
| afgekortenor                    | NULL                                          |
| gemdeel                         | NULL                                          |
| huisnr                          | NULL                                          |
| huisletter                      | NULL                                          |
| huisnrtoevoeging                | NULL                                          |
| postcode                        | NULL                                          |
| wplnaam                         | NULL                                          |
| loctenopzichtevanadres          | NULL                                          |
| locoms                          | in het bos                                    |
| bladresregel1                   | NULL                                          |
| bladresregel2                   | NULL                                          |
| bladresregel3                   | NULL                                          |
| bladresregel4                   | NULL                                          |
| bladresregel5                   | NULL                                          |
| bladresregel6                   | NULL                                          |
| landgebied                      | 229                                           |
| indpersaangetroffenopadres      | NULL                                          |
----
| actieinhoud                     | Registratie adres                             |
| actieverval                     | NULL                                          |
| dataanvgel                      | 20160103                                      |
| dateindegel                     | NULL                                          |
| actieAanpas                     | NULL                                          |
| srt                             | 1                                             |
| rdnwijz                         | 5                                             |
| aangadresh                      | NULL                                          |
| dataanvadresh                   | 20160102                                      |
| identcodeadresseerbaarobject    | NULL                                          |
| identcodenraand                 | NULL                                          |
| gem                             | 7017                                          |
| nor                             | NULL                                          |
| afgekortenor                    | NULL                                          |
| gemdeel                         | NULL                                          |
| huisnr                          | NULL                                          |
| huisletter                      | NULL                                          |
| huisnrtoevoeging                | NULL                                          |
| postcode                        | NULL                                          |
| wplnaam                         | NULL                                          |
| loctenopzichtevanadres          | NULL                                          |
| locoms                          | Bleiswijk                                     |
| bladresregel1                   | NULL                                          |
| bladresregel2                   | NULL                                          |
| bladresregel3                   | NULL                                          |
| bladresregel4                   | NULL                                          |
| bladresregel5                   | NULL                                          |
| bladresregel6                   | NULL                                          |
| landgebied                      | 229                                           |
| indpersaangetroffenopadres      | NULL                                          |

!-- Controleer of de volledige verstrekkingsbeperking niet beeindigd is
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, hpi.waarde, pi.srt, hpi.dataanvgel, hpi.dateindegel, hpi.actieaanpgel
                   from kern.persindicatie pi
                   join kern.his_persindicatie hpi on hpi.persindicatie = pi.id
                   left join kern.actie ainh on ainh.id = hpi.actieinh
                   left join kern.actie av on av.id = hpi.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.pers p on pi.pers = p.id de volgende gegevens:
| veld                            | waarde                                        |
| actieinhoud                     | Registratie verstrekkingsbeperking            |
| actieverval                     | NULL                                          |
| waarde                          | true                                          |
| srt                             | 3                                             |
| dataanvgel                      | NULL                                          |
| dateindegel                     | NULL                                          |
| actieaanpgel                    | NULL                                          |