Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland: Registratie geborene

Scenario:   Registratie geborene in Nederland waarbij adres van OUWKIG is gewijzigd na de geboorte maar de bijhouding van verhuizing is ervoor
            LT: GBNL04C10T150

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL04C10T60-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL04C10T60-002.xls

When voer een bijhouding uit GBNL04C10T150a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

When voer een bijhouding uit GBNL04C10T150b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

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



