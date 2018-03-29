Meta:
@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Geef Medebewoner van Persoon
@regels             R2377

Narrative:
Indien de verstrekking de Soort dienst Geef medebewoners van persoon betreft
dan moet als identificatiecriterium Persoonsidentificatie of identificatiecodeNummeraanduiding of adrescriteria worden gebruikt.
Dit betekent dat er in het verzoekbericht één identificatiecriterium aanwezig moet zijn;
Persoonsidentificatie (Identiteitsnummer (R2191)); deze bestaat uit Bericht.Burgerservicenummer OF Bericht.Administratienummer OF Bericht.Object sleutel
    OF
Bericht.Identificatiecode nummeraanduiding
    OF
minstens één van de volgende adresgegevens:
Bericht.Gemeente code OF
Bericht.Afgekorte naam openbare ruimte OF
Bericht.Huisnummer OF
Bericht.Huisletter OF
Bericht.Huisnummertoevoeging OF
Bericht.Locatie ten opzichte van adres OF
Bericht.Postcode OF
Bericht.Woonplaatsnaam.


Scenario: 01.   Geef medebewoners van persoon op identiteitsnummer administratienummer en Bericht.Identificatiecode nummeraanduiding
                LT: R2377_LT04
                Verwacht resultaat:
                - Foutief
                - R2377: Persoonsidentificatie of adresidentificatie moet zijn ingevuld.

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
| key                               | value               |
| leveringsautorisatieNaam          | 'Geef Medebewoners' |
| zendendePartijNaam                | 'Gemeente Utrecht'  |
| administratienummer               | '5398948626'        |
| identificatiecodeNummeraanduiding | '0626200010016001'  |

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                         |
| R2377 | Persoonsidentificatie of adresidentificatie moet zijn ingevuld. |


Scenario: 02.   Geef medebewoners van persoon op identiteitsnummer administratienummer en overig adresgegeven
                LT: R2377_LT05
                Verwacht resultaat:
                - Foutief
                - R2377: Persoonsidentificatie of adresidentificatie moet zijn ingevuld.

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
| key                      | value               |
| leveringsautorisatieNaam | 'Geef Medebewoners' |
| zendendePartijNaam       | 'Gemeente Utrecht'  |
| administratienummer      | '5398948626'        |
| postcode                 | '2252EB'            |

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                         |
| R2377 | Persoonsidentificatie of adresidentificatie moet zijn ingevuld. |

Scenario: 03.    Geef medebewoners van persoon op overige adresgegevens postcode en identificatiecode nummeraanduiding
                LT: R2377_LT06
                Verwacht resultaat:
                - Foutief
                - R2377: Persoonsidentificatie of adresidentificatie moet zijn ingevuld.

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_xls


Given verzoek geef medebewoners van persoon:
| key                               | value               |
| leveringsautorisatieNaam          | 'Geef Medebewoners' |
| zendendePartijNaam                | 'Gemeente Utrecht'  |
| identificatiecodeNummeraanduiding | '0626200010016001'  |
| postcode                          | '2252EB'            |

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                         |
| R2377 | Persoonsidentificatie of adresidentificatie moet zijn ingevuld. |

Scenario: 04.    Geef medebewoners van persoon zonder identificatie criteria
                LT: R2377_LT07
                Verwacht resultaat:
                - Foutief
                - R2377: Persoonsidentificatie of adresidentificatie moet zijn ingevuld.

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_xls


Given verzoek geef medebewoners van persoon:
| key                      | value               |
| leveringsautorisatieNaam | 'Geef Medebewoners' |
| zendendePartijNaam       | 'Gemeente Utrecht'  |

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                         |
| R2377 | Persoonsidentificatie of adresidentificatie moet zijn ingevuld. |

Scenario: 05.   Geef medebewoners van persoon op Identificatiecode adresseerbaar object
                LT: R2377_LT08
                Verwacht resultaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
| key                                   | value               |
| leveringsautorisatieNaam              | 'Geef Medebewoners' |
| zendendePartijNaam                    | 'Gemeente Utrecht'  |
| identificatiecodeAdresseerbaarObject  | '0626010010016001'  |

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 06.   Geef medebewoners van persoon op Identificatiecode adresseerbaar object
                LT: R2377_LT09
                Verwacht resultaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
| key                                   | value               |
| leveringsautorisatieNaam              | 'Geef Medebewoners' |
| zendendePartijNaam                    | 'Gemeente Utrecht'  |
| identificatiecodeAdresseerbaarObject  | '0626010010016001'  |
| postcode                              | '2252EB'            |

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                         |
| R2377 | Persoonsidentificatie of adresidentificatie moet zijn ingevuld. |