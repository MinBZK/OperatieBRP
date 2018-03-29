Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: Verhuizing intergemeentelijk met hoofdactie Registratie adres

Scenario:   Verhuizing van BRP gemeente naar andere BRP gemeente
            LT: VZIG04C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG04C10T10-001.xls

When voer een bijhouding uit VZIG04C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG04C10T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

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
                   where p.bsn ='286116297'
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
| gem                             | 7021                                          |
| nor                             | Baron Schimmelpenninck van der Oyelaan        |
| afgekortenor                    | S vd Oyeln                                    |
| gemdeel                         | Aardam                                        |
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
| gem                             | 7021                                          |
| nor                             | Baron Schimmelpenninck van der Oyelaan        |
| afgekortenor                    | S vd Oyeln                                    |
| gemdeel                         | Aardam                                        |
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


Then in kern heeft select indag from kern.persadres de volgende gegevens:
| veld                      | waarde |
| indag                     | true   |

Then lees persoon met anummer 6214529825 uit database en vergelijk met expected VZIG04C10T10-persoon1.xml

Then staat er 1 notificatiebericht voor bijhouders op de queue
