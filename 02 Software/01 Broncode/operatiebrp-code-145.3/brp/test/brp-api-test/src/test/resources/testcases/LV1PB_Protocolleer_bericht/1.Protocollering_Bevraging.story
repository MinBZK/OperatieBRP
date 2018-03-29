Meta:
@status             Klaar
@usecase            LV.1.PB, AL.1.VZ
@regels             R1613, R1616, R1617, R1618, R1619, R1620
@sleutelwoorden     Protocoleer bericht

Narrative:
Indien een verstrekking van persoonsgegevens plaatsvindt als directe reactie op een bevraging van een afnemer

DAN moeten de protocolleringsgegevens als volgt worden samengesteld:
Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID waarvoor verstrekt wordt
Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (volgt uit opgegeven de parameters van het verzoekbericht)
Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd systeem van ophalen gegevens(Persoon en Autorisatie) uit de database
Leveringsaantekening.Datum materieel selectie = R1616 - Afleiding Datum materieel selectie
Leveringsaantekening.Datum aanvang materiële periode resultaat = R1617 - Afleiding Datum aanvang materiele periode resultaat
Leveringsaantekening.Datum einde materiële periode resultaat = R1618 - Afleiding Datum einde materiele periode resultaat
Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = R1619 - Afleiding Datum/tijd aanvang formele periode resultaat
Leveringsaantekening.Datum/tijd einde formele periode resultaat = R1620 - Afleiding Datum/tijd einde formele periode resultaat
Leveringsaantekening.Administratieve handeling = 'leeg' (R1614)
Leveringsaantekening.Soort synchronisatie = 'leeg' (R1615)


Scenario:   1. verstrekking van persoonsgegevens vindt plaats als directe reactie op een bevraging van een afnemer
            LT: R1613_LT01, R1616_LT02, R1617_LT06, R1618_LT02, R1619_LT03, R1620_LT01
            Dienst: Geef Details Persoon (Historie vorm = 'Materieel')
            Verwacht resultaat:
            Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID waarvoor verstrekt wordt (R1613_LT01)
            Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (volgt uit opgegeven de parameters van het verzoekbericht) (R1613_LT01)
            Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd systeem van ophalen gegevens(Persoon en Autorisatie) uit de database (R1613_LT01)
            Leveringsaantekening.Datum materieel selectie = 'leeg' (R1616_LT02)
            Leveringsaantekening.Datum aanvang materiële periode resultaat = 'leeg' (R1617_LT06)
            Leveringsaantekening.Datum einde materiële periode resultaat = Peilmoment materieel resultaat + 1 dag (R1618_LT02)
            Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = Peilmoment formeel resultaat (R1619_LT03)
            Leveringsaantekening.Datum/tijd einde formele periode resultaat = Peilmoment formeel resultaat (R1620_LT01)
            Leveringsaantekening.Administratieve handeling = 'leeg' (NVT voor bevraging)
            Leveringsaantekening.Soort synchronisatie = 'leeg' (NVT voor bevraging)
            Leveringsaantekening.Scope patroon: vullen met het Elementpatroon van die scoping (R1613_LT01)

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|590984809|'2014-12-31 T23:59:00Z'

Given verzoek geef details persoon:
| key                          | value                                                                        |
| leveringsautorisatieNaam     | 'Geen pop.bep. levering op basis van afnemerindicatie'                       |
| zendendePartijNaam           | 'Gemeente Utrecht'                                                              |
| bsn                          | 590984809                                                                    |
| historievorm                 | Materieel                                                                    |
| peilmomentMaterieelResultaat | '2015-12-31'                                                                 |
| peilmomentFormeelResultaat   | '2015-12-31T23:59:00+01:00'                                                        |
| scopingElementen             | Persoon.Adres.Huisnummer,Persoon.Adres.Woonplaatsnaam,Persoon.Adres.Postcode |

Then heeft het antwoordbericht verwerking Geslaagd

!-- R1613_LT01: Geef details persoon met scoping, scoping vastgelegd in Leveringsaantekening.Scope
Then is het laatste bericht geprotocolleerd met de gegevens:
| veld                        | waarde                  |
| toeganglevsautorisatie      | 1                       |
| dienst                      | 5                       |
| tsklaarzettenlev            | .*                      |
| dataanvmaterieleperioderes  | null                    |
| dateindematerieleperioderes | 20160101                |
| tsaanvformeleperioderes     | 2015-12-31 23:59:00.0   |
| tseindeformeleperioderes    | 2015-12-31 23:59:00.0   |
| admhnd                      | null                    |
| srtsynchronisatie           | null                    |

Then zijn de laatst geprotocolleerde scopeelementen:
| element                      |
| Persoon.Adres.Huisnummer     |
| Persoon.Adres.Woonplaatsnaam |
| Persoon.Adres.Postcode       |

Then zijn de laatst geprotocolleerde personen:
| pers  | tslaatstewijzpers |
| 1     | .*                |

Scenario:   2. verstrekking van persoonsgegevens vindt plaats als directe reactie op een bevraging van een afnemer
            LT: R1613_LT06, R1616_LT02, R1617_LT06, R1618_LT02, R1619_LT04, R1620_LT02
            Dienst: Geef Details Persoon
            Historievorm = 'Materieel', peilmomentMaterieelResultaat = Datum X , peilmomentFormeelResultaat = leeg
            Verwacht resultaat:
            Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID waarvoor verstrekt wordt (R1613_LT06)
            Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (volgt uit opgegeven de parameters van het verzoekbericht) (R1613_LT06)
            Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd systeem van ophalen gegevens(Persoon en Autorisatie) uit de database (R1613_LT06)
            Leveringsaantekening.Datum materieel selectie = 'leeg' (R1616_LT02)
            Leveringsaantekening.Datum aanvang materiële periode resultaat = 'leeg' (R1617_LT06)
            Leveringsaantekening.Datum einde materiële periode resultaat = Peilmoment materieel resultaat + 1 dag (R1618_LT02)
            Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'systeemdatum' (R1619_LT04)
            Leveringsaantekening.Datum/tijd einde formele periode resultaat = datum / tijd klaarzetten bericht (R1620_LT02)
            Leveringsaantekening.Administratieve handeling = 'leeg' (NVT voor bevraging)
            Leveringsaantekening.Soort synchronisatie = 'leeg' (NVT voor bevraging)
            Leveringsaantekening.Scope patroon: 'leeg' (R1613_LT06)

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
Given verzoek geef details persoon:
| key                          | value                                                  |
| leveringsautorisatieNaam     | 'Geen pop.bep. levering op basis van afnemerindicatie' |
| zendendePartijNaam           | 'Gemeente Utrecht'                                        |
| bsn                          | 590984809                                              |
| historievorm                 | Materieel                                              |
| peilmomentMaterieelResultaat | '2015-12-31'                                           |

Then heeft het antwoordbericht verwerking Geslaagd

Then is het laatste bericht geprotocolleerd met de gegevens:
| veld                        | waarde   |
| toeganglevsautorisatie      | 1        |
| dienst                      | 5        |
| tsklaarzettenlev            | .*       |
| dataanvmaterieleperioderes  | null     |
| dateindematerieleperioderes | 20160101 |
| tsaanvformeleperioderes     | .*       |
| tseindeformeleperioderes    | .*       |
| admhnd                      | null     |
| srtsynchronisatie           | null     |

Then zijn de laatst geprotocolleerde personen:
| pers  | tslaatstewijzpers |
| 1     | .*                |

Scenario:   3. verstrekking van persoonsgegevens vindt plaats als directe reactie op een bevraging van een afnemer
            LT: R1613_LT06, R1616_LT02, R1617_LT07, R1618_LT01, R1619_LT04, R1620_LT02
            Dienst: Geef Details Persoon
            Historievorm = 'Materieel', peilmomentMaterieelResultaat = leeg , peilmomentFormeelResultaat = leeg
            Verwacht resultaat:
            Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID waarvoor verstrekt wordt (R1613_LT06)
            Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (volgt uit opgegeven de parameters van het verzoekbericht) (R1613_LT06)
            Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd systeem van ophalen gegevens(Persoon en Autorisatie) uit de database (R1613_LT06)
            Leveringsaantekening.Datum materieel selectie = 'leeg' (R1616_LT02)
            Leveringsaantekening.Datum aanvang materiële periode resultaat = 'leeg' (R1617_LT07)
            Leveringsaantekening.Datum einde materiële periode resultaat = 'systeemdatum + 1 dag' (R1618_LT01)
            Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'systeemdatum' (R1619_LT04)
            Leveringsaantekening.Datum/tijd einde formele periode resultaat = datum / tijd klaarzetten bericht (R1620_LT02)
            Leveringsaantekening.Administratieve handeling = 'leeg' (NVT voor bevraging)
            Leveringsaantekening.Soort synchronisatie = 'leeg' (NVT voor bevraging)
            Leveringsaantekening.Scope patroon: 'leeg' (R1613_LT06)

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
Given verzoek geef details persoon:
| key                      | value                                                  |
| leveringsautorisatieNaam | 'Geen pop.bep. levering op basis van afnemerindicatie' |
| zendendePartijNaam       | 'Gemeente Utrecht'                                        |
| bsn                      | 590984809                                              |
| historievorm             | Materieel                                              |

Then heeft het antwoordbericht verwerking Geslaagd

Then is het laatste bericht geprotocolleerd met de gegevens:
| veld                        | waarde |
| toeganglevsautorisatie      | 1      |
| dienst                      | 5      |
| tsklaarzettenlev            | .*     |
| dataanvmaterieleperioderes  | null   |
| dateindematerieleperioderes | morgen |
| tsaanvformeleperioderes     | .*     |
| tseindeformeleperioderes    | .*     |
| admhnd                      | null   |
| srtsynchronisatie           | null   |

Then zijn de laatst geprotocolleerde personen:
| pers  | tslaatstewijzpers |
| 1     | .*                |


Scenario:   4. verstrekking van persoonsgegevens vindt plaats als directe reactie op een bevraging van een afnemer
            LT: R1613_LT06, R1616_LT02, R1617_LT05, R1618_LT02, R1619_LT01, R1620_LT01
            Dienst: Geef Details Persoon
            Historie vorm = 'Geen', peilmomentMaterieelResultaat = datum X, peilmomentFormeelResultaat = Datum X
            Verwacht resultaat:
            Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID waarvoor verstrekt wordt (R1613_LT06)
            Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (volgt uit opgegeven de parameters van het verzoekbericht) (R1613_LT06)
            Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd systeem van ophalen gegevens(Persoon en Autorisatie) uit de database (R1613_LT06)
            Leveringsaantekening.Datum materieel selectie = 'leeg' (R1616_LT02)
            Leveringsaantekening.Datum aanvang materiële periode resultaat = Peilmoment materieel resultaat (R1617_LT05)
            Leveringsaantekening.Datum einde materiële periode resultaat = Peilmoment materieel resultaat + 1 dag (R1618_LT02)
            Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = Peilmoment formeel resultaat (R1619_LT01)
            Leveringsaantekening.Datum/tijd einde formele periode resultaat = Peilmoment formeel resultaat (R1620_LT01)
            Leveringsaantekening.Administratieve handeling = 'leeg' (NVT voor bevraging)
            Leveringsaantekening.Soort synchronisatie = 'leeg' (NVT voor bevraging)
            Leveringsaantekening.Scope patroon: 'leeg' (R1613_LT06)


Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|590984809|'2014-12-31 T23:59:00Z'

Given verzoek geef details persoon:
| key                          | value                                                  |
| leveringsautorisatieNaam     | 'Geen pop.bep. levering op basis van afnemerindicatie' |
| zendendePartijNaam           | 'Gemeente Utrecht'                                        |
| bsn                          | 590984809                                              |
| historievorm                 | Geen                                                   |
| peilmomentMaterieelResultaat | '2015-12-31'                                           |
| peilmomentFormeelResultaat   | '2015-12-31T23:59:00+01:00'                                  |

Then heeft het antwoordbericht verwerking Geslaagd
Then is het laatste bericht geprotocolleerd met de gegevens:
| veld                        | waarde                  |
| toeganglevsautorisatie      | 1                       |
| dienst                      | 5                       |
| tsklaarzettenlev            | .*                      |
| dataanvmaterieleperioderes  | 20151231                |
| dateindematerieleperioderes | 20160101                |
| tsaanvformeleperioderes     | 2015-12-31 23:59:00.0   |
| tseindeformeleperioderes    | 2015-12-31 23:59:00.0   |
| admhnd                      | null                    |
| srtsynchronisatie           | null                    |
!-- Controleer dat als er geen scoping in het request zit, deze ook niet in de protocollering aanwezig is
Then zijn er geen scopeelementen geprotocoleerd
Then zijn de laatst geprotocolleerde personen:
| pers  | tslaatstewijzpers |
| 1     | .*                |

Scenario:   5. verstrekking van persoonsgegevens vindt plaats als directe reactie op een bevraging van een afnemer
            LT: R1613_LT06, R1616_LT02, R1617_LT05, R1618_LT02, R1619_LT02, R1620_LT02
            Dienst: Geef Details Persoon
            Historie vorm = 'Geen', peilmomentMaterieelResultaat = datum X, peilmomentFormeelResultaat = 'leeg'
            Verwacht resultaat:
            Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID waarvoor verstrekt wordt (R1613_LT06)
            Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (volgt uit opgegeven de parameters van het verzoekbericht) (R1613_LT06)
            Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd systeem van ophalen gegevens(Persoon en Autorisatie) uit de database (R1613_LT06)
            Leveringsaantekening.Datum materieel selectie = 'leeg' (R1616_LT02)
            Leveringsaantekening.Datum aanvang materiële periode resultaat = Peilmoment materieel resultaat (R1617_LT05)
            Leveringsaantekening.Datum einde materiële periode resultaat = Peilmoment materieel resultaat + 1 dag (R1618_LT02)
            Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'systeemdatum' (R1619_LT02)
            Leveringsaantekening.Datum/tijd einde formele periode resultaat = datum/tijd van klaarzetten bericht (R1620_LT02)
            Leveringsaantekening.Administratieve handeling = 'leeg' (NVT voor bevraging)
            Leveringsaantekening.Soort synchronisatie = 'leeg' (NVT voor bevraging)
            Leveringsaantekening.Scope patroon: 'leeg' (R1613_LT06)

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls


Given verzoek geef details persoon:
| key                          | value                                                  |
| leveringsautorisatieNaam     | 'Geen pop.bep. levering op basis van afnemerindicatie' |
| zendendePartijNaam           | 'Gemeente Utrecht'                                        |
| bsn                          | 590984809                                              |
| historievorm                 | Geen                                                   |
| peilmomentMaterieelResultaat | '2015-12-31'                                           |


Then heeft het antwoordbericht verwerking Geslaagd
Then is het laatste bericht geprotocolleerd met de gegevens:
| veld                        | waarde   |
| toeganglevsautorisatie      | 1        |
| dienst                      | 5        |
| tsklaarzettenlev            | .*       |
| dataanvmaterieleperioderes  | 20151231 |
| dateindematerieleperioderes | 20160101 |
| tsaanvformeleperioderes     | .*       |
| tseindeformeleperioderes    | .*       |
| admhnd                      | null     |
| srtsynchronisatie           | null     |
Then zijn er geen scopeelementen geprotocoleerd
Then zijn de laatst geprotocolleerde personen:
| pers  | tslaatstewijzpers |
| 1     | .*                |

Scenario:   6. verstrekking van persoonsgegevens vindt plaats als directe reactie op een bevraging van een afnemer
            LT: R1613_LT06, R1616_LT02, R1617_LT09, R1618_LT01, R1619_LT02, R1620_LT02
            Dienst: Geef Details Persoon
            Historie vorm = 'Geen', peilmomentMaterieelResultaat = 'leeg', peilmomentFormeelResultaat = 'leeg'
            Verwacht resultaat:
            Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID waarvoor verstrekt wordt (R1613_LT06)
            Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (volgt uit opgegeven de parameters van het verzoekbericht) (R1613_LT06)
            Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd systeem van ophalen gegevens(Persoon en Autorisatie) uit de database (R1613_LT06)
            Leveringsaantekening.Datum materieel selectie = 'leeg' (R1616_LT02)
            Leveringsaantekening.Datum aanvang materiële periode resultaat = 'leeg' (R1617_LT09)
            Leveringsaantekening.Datum einde materiële periode resultaat = 'systeemdatum + 1 dag' (R1618_LT01)
            Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'systeemdatum' (R1619_LT02)
            Leveringsaantekening.Datum/tijd einde formele periode resultaat = datum/tijd van klaarzetten bericht (R1620_LT02)
            Leveringsaantekening.Administratieve handeling = 'leeg' (NVT voor bevraging)
            Leveringsaantekening.Soort synchronisatie = 'leeg' (NVT voor bevraging)
            Leveringsaantekening.Scope patroon: 'leeg' (R1613_LT06)

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls


Given verzoek geef details persoon:
| key                      | value                                                  |
| leveringsautorisatieNaam | 'Geen pop.bep. levering op basis van afnemerindicatie' |
| zendendePartijNaam       | 'Gemeente Utrecht'                                        |
| bsn                      | 590984809                                             |
| historievorm             | Geen                                                   |

Then heeft het antwoordbericht verwerking Geslaagd
Then is het laatste bericht geprotocolleerd met de gegevens:
| veld                        | waarde |
| toeganglevsautorisatie      | 1      |
| dienst                      | 5      |
| tsklaarzettenlev            | .*     |
| dataanvmaterieleperioderes  | vandaag|
| dateindematerieleperioderes | morgen |
| tsaanvformeleperioderes     | .*     |
| tseindeformeleperioderes    | .*     |
| admhnd                      | null   |
| srtsynchronisatie           | null   |
Then zijn er geen scopeelementen geprotocoleerd
Then zijn de laatst geprotocolleerde personen:
| pers  | tslaatstewijzpers |
| 1     | .*                |

Scenario:   7. verstrekking van persoonsgegevens vindt plaats als directe reactie op een bevraging van een afnemer
            LT: R1613_LT06, R1616_LT02, R1617_LT07, R1618_LT01, R1619_LT05, R1620_LT02
            Dienst: Geef Details Persoon
            Historie vorm = 'MaterieelFormeel', peilmomentMaterieelResultaat = 'leeg', peilmomentFormeelResultaat = 'leeg'
            Verwacht resultaat:
            Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID waarvoor verstrekt wordt (R1613_LT06)
            Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (volgt uit opgegeven de parameters van het verzoekbericht) (R1613_LT06)
            Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd systeem van ophalen gegevens(Persoon en Autorisatie) uit de database (R1613_LT06)
            Leveringsaantekening.Datum materieel selectie = 'leeg' (R1616_LT02)
            Leveringsaantekening.Datum aanvang materiële periode resultaat = 'leeg' (R1617_LT07)
            Leveringsaantekening.Datum einde materiële periode resultaat = 'systeemdatum + 1 dag' (R1618_LT01)
            Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'leeg' (R1619_LT05)
            Leveringsaantekening.Datum/tijd einde formele periode resultaat = datum/tijd van klaarzetten bericht (R1620_LT02)
            Leveringsaantekening.Administratieve handeling = 'leeg' (NVT voor bevraging)
            Leveringsaantekening.Soort synchronisatie = 'leeg' (NVT voor bevraging)
            Leveringsaantekening.Scope patroon: 'leeg' (R1613_LT06)

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
Given verzoek geef details persoon:
| key                      | value                                                  |
| leveringsautorisatieNaam | 'Geen pop.bep. levering op basis van afnemerindicatie' |
| zendendePartijNaam       | 'Gemeente Utrecht'                                        |
| bsn                      | 590984809                                              |
| historievorm             | MaterieelFormeel                                       |

Then heeft het antwoordbericht verwerking Geslaagd
Then is het laatste bericht geprotocolleerd met de gegevens:
| veld                        | waarde |
| toeganglevsautorisatie      | 1      |
| dienst                      | 5      |
| tsklaarzettenlev            | .*     |
| dataanvmaterieleperioderes  | null   |
| dateindematerieleperioderes | morgen |
| tsaanvformeleperioderes     | null   |
| tseindeformeleperioderes    | .*     |
| admhnd                      | null   |
| srtsynchronisatie           | null   |

Then zijn er geen scopeelementen geprotocoleerd
Then zijn de laatst geprotocolleerde personen:
| pers  | tslaatstewijzpers |
| 1     | .*                |

Scenario:   8. verstrekking van persoonsgegevens vindt plaats als directe reactie op een bevraging van een afnemer
            LT: R1613_LT06, R1616_LT02, R1617_LT06, R1618_LT02, R1619_LT05, R1620_LT01
            Dienst: Geef Details Persoon
            Historie vorm = 'MaterieelFormeel', peilmomentMaterieelResultaat = Datum X, peilmomentFormeelResultaat = Datum X
            Verwacht resultaat:
            Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID waarvoor verstrekt wordt (R1613_LT06)
            Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (volgt uit opgegeven de parameters van het verzoekbericht) (R1613_LT06)
            Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd systeem van ophalen gegevens(Persoon en Autorisatie) uit de database (R1613_LT06)
            Leveringsaantekening.Datum materieel selectie = 'leeg' (R1616_LT02)
            Leveringsaantekening.Datum aanvang materiële periode resultaat = 'leeg' (R1617_LT06)
            Leveringsaantekening.Datum einde materiële periode resultaat = Datum X + 1 dag (R1618_LT02)
            Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'leeg' (R1619_LT05)
            Leveringsaantekening.Datum/tijd einde formele periode resultaat = peilmomentFormeelResultaat (R1620_LT01)
            Leveringsaantekening.Administratieve handeling = 'leeg' (NVT voor bevraging)
            Leveringsaantekening.Soort synchronisatie = 'leeg' (NVT voor bevraging)
            Leveringsaantekening.Scope patroon: 'leeg' (R1613_LT06)


Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|590984809|'2014-12-31 T23:59:00Z'


Given verzoek geef details persoon:
| key                          | value                                                  |
| leveringsautorisatieNaam     | 'Geen pop.bep. levering op basis van afnemerindicatie' |
| zendendePartijNaam           | 'Gemeente Utrecht'                                        |
| bsn                          | 590984809                                              |
| historievorm                 | MaterieelFormeel                                       |
| peilmomentMaterieelResultaat | '2015-12-31'                                           |
| peilmomentFormeelResultaat   | '2015-12-31T23:59:00+01:00'                                  |


Then heeft het antwoordbericht verwerking Geslaagd
Then is het laatste bericht geprotocolleerd met de gegevens:
| veld                        | waarde                  |
| toeganglevsautorisatie      | 1                       |
| dienst                      | 5                       |
| tsklaarzettenlev            | .*                      |
| dataanvmaterieleperioderes  | null                    |
| dateindematerieleperioderes | 20160101                |
| tsaanvformeleperioderes     | null                    |
| tseindeformeleperioderes    | 2015-12-31 23:59:00.0   |
| admhnd                      | null                    |
| srtsynchronisatie           | null                    |
Then zijn er geen scopeelementen geprotocoleerd
Then zijn de laatst geprotocolleerde personen:
| pers  | tslaatstewijzpers |
| 1     | .*                |

Scenario: 9.    verstrekking van persoonsgegevens vindt plaats als directe reactie op een bevraging van een afnemer
                LT: R1613_LT03, R1616_LT02, R1617_LT09, R1618_LT03, R1619_LT06, R1620_LT03
                Dienst: Zoek Persoon
                Verwacht resultaat:
                Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID waarvoor verstrekt wordt (R1613_LT03)
                Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (volgt uit opgegeven de parameters van het verzoekbericht) (R1613_LT03)
                Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd systeem van ophalen gegevens(Persoon en Autorisatie) uit de database (R1613_LT03)
                Leveringsaantekening.Datum materieel selectie = 'leeg' (R1616_LT02)
                Leveringsaantekening.Datum aanvang materiële periode resultaat = 'leeg' (R1617_LT09)
                Leveringsaantekening.Datum einde materiële periode resultaat = 'leeg' (R1618_LT03)
                Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'leeg' (R1619_LT06)
                Leveringsaantekening.Datum/tijd einde formele periode resultaat = datum/tijd van klaarzetten bericht (R1620_LT03)
                Leveringsaantekening.Administratieve handeling = 'leeg' (NVT voor bevraging)
                Leveringsaantekening.Soort synchronisatie = 'leeg' (NVT voor bevraging)
                Leveringsaantekening.Scope patroon: 'leeg' (R1613_LT03)


Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek zoek persoon:
| key                      | value                                                                                         |
| leveringsautorisatieNaam | 'Zoek Persoon'                                                                                |
| zendendePartijNaam       | 'Gemeente Utrecht'                                                                               |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801 |

Then heeft het antwoordbericht verwerking Geslaagd
Then is het laatste bericht geprotocolleerd met de gegevens:
| veld                        | waarde |
| toeganglevsautorisatie      | 1      |
| dienst                      | 2      |
| tsklaarzettenlev            | .*     |
| dataanvmaterieleperioderes  | null   |
| dateindematerieleperioderes | null   |
| tsaanvformeleperioderes     | null   |
| tseindeformeleperioderes    | .*     |
| admhnd                      | null   |
| srtsynchronisatie           | null   |

Then zijn er geen scopeelementen geprotocoleerd
Then zijn de laatst geprotocolleerde personen:
| pers  | tslaatstewijzpers |
| 1     | .*                |

