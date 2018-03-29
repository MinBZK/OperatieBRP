Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: Nevenactie Registratie verstrekkingsbeperking

Scenario:   Verwerking Volledige verstrekkingsbeperking
            LT: VZBG04C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBG/VZBG04C20T10-001.xls

When voer een bijhouding uit VZBG04C20T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBG/expected/VZBG04C20T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 2      |

!-- Controleer betrokken personen zijn gemarkeerd als bijgehouden
Then in kern heeft select count(1)
                   from kern.his_persafgeleidadministrati hpaf
                   join kern.actie ainh on ainh.id = hpaf.actieinh
                   left join kern.actie av on av.id = hpaf.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.pers p on hpaf.pers = p.id
                   left join kern.admhnd a on hpaf.admhnd = a.id
                   left join kern.srtadmhnd sa on a.srt = sa.id
                   where p.bsn ='122004851' de volgende gegevens:
| veld                      | waarde |
| count                     | 2      |

!-- Controleer of de his_persbijhouding niet is bijgewerkt
Then in kern heeft select count(1)
                   from kern.his_persbijhouding hpaf
                   join kern.actie ainh on ainh.id = hpaf.actieinh
                   left join kern.actie av on av.id = hpaf.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.pers p on hpaf.pers = p.id
                   where sainh.naam <> 'Conversie GBA' de volgende gegevens:
| veld                      | waarde |
| count                     | 0      |

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
                   where p.bsn ='122004851'
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

!-- Controleer of de volledige verstrekkingsbeperking geregistreerd is
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

!-- Controleer of er geen registratie van de specifieke verstrekkingsbeperking heeft plaatsgevonden
Then in kern heeft select count(1)
                   from kern.persverstrbeperking pv
                   left join kern.his_persverstrbeperking hpv on hpv.persverstrbeperking = pv.id
                   left join kern.actie ainh on ainh.id = hpv.actieinh
                   left join kern.actie av on av.id = hpv.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.partij p on pv.partij = p.id de volgende gegevens:
| veld                            | waarde                                        |
| count                           | 0                                             |

!-- Controleer verantwoordingsgegevens van de ActieBron
Then in kern heeft select count(1),ab.doc
                   from kern.actiebron ab join kern.actie ainh on ainh.id = ab.actie left join kern.srtactie sainh on ainh.srt = sainh.id
                   where sainh.naam in ('Registratie verstrekkingsbeperking') group by ab.doc de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |