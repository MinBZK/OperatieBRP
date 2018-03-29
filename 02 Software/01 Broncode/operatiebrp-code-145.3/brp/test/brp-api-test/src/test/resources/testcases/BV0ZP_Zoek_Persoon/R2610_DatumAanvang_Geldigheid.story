Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2610

Narrative:

Voor alle zoekcriteria in een zoekvraag moet gelden dat:

Zoekcriterium.Element mag niet het attribuut 'Datum aanvang geldigheid' van een groep zijn.

Scenario: 1.    Zoeken op DatumAanvangGeldigheid van een groep (Hoofdpersoon Persoon.Identificatienummers.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario: 2.    Zoeken op DatumAanvangGeldigheid van een groep (Hoofdpersoon Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario: 3.    Zoeken op DatumAanvangGeldigheid van een groep (Hoofdpersoon Persoon.SamengesteldeNaam.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario: 4.    Zoeken op DatumAanvangGeldigheid van een groep (Hoofdpersoon Persoon.Bijhouding.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Bijhouding.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario: 5.    Zoeken op DatumAanvangGeldigheid van een groep (Hoofdpersoon Persoon.Migratie.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Migratie.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario: 6.    Zoeken op DatumAanvangGeldigheid van een groep (Hoofdpersoon Persoon.Geslachtsnaamcomponent.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Geslachtsnaamcomponent.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario: 7.    Zoeken op DatumAanvangGeldigheid van een groep (Hoofdpersoon Persoon.Nationaliteit.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Nationaliteit.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario: 8.    Zoeken op DatumAanvangGeldigheid van een groep (Hoofdpersoon Persoon.Voornaam.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Voornaam.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  9.   Zoeken op DatumAanvangGeldigheid van een groep (Hoofdpersoon Persoon.Adres.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  10.  Zoeken op DatumAanvangGeldigheid van een groep (Hoofdpersoon Persoon.Nummerverwijzing.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Nummerverwijzing.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  11.  Zoeken op DatumAanvangGeldigheid van een groep (Gerelateerde GerelateerdeOuder.OuderlijkGezag.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=GerelateerdeOuder.OuderlijkGezag.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  12.  Zoeken op DatumAanvangGeldigheid van een groep (Gerelateerde GerelateerdeOuder.Persoon.Identificatienummers.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=GerelateerdeOuder.Persoon.Identificatienummers.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  13.  Zoeken op DatumAanvangGeldigheid van een groep (Gerelateerde GerelateerdeOuder.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=GerelateerdeOuder.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  14.  Zoeken op DatumAanvangGeldigheid van een groep (Gerelateerde GerelateerdeOuder.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=GerelateerdeOuder.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  15.  Zoeken op DatumAanvangGeldigheid van een groep (Gerelateerde GerelateerdeKind.Persoon.Identificatienummers.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=GerelateerdeKind.Persoon.Identificatienummers.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  16.  Zoeken op DatumAanvangGeldigheid van een groep (Gerelateerde GerelateerdeKind.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=GerelateerdeKind.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  17. Zoeken op DatumAanvangGeldigheid van een groep (Gerelateerde GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  18.  Zoeken op DatumAanvangGeldigheid van een groep (Gerelateerde GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  19.  Zoeken op DatumAanvangGeldigheid van een groep (Gerelateerde GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  20.  Zoeken op DatumAanvangGeldigheid van een groep (GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  21.  Zoeken op DatumAanvangGeldigheid van een groep (GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  22.  Zoeken op DatumAanvangGeldigheid van een groep (GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  23.  Zoeken op DatumAanvangGeldigheid van een groep (GerelateerdeOuder.Ouderschap.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=GerelateerdeOuder.Ouderschap.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  24.  Zoeken op DatumAanvangGeldigheid van een groep (Indicatie Persoon.Indicatie.DerdeHeeftGezag.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Indicatie.DerdeHeeftGezag.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  25.  Zoeken op DatumAanvangGeldigheid van een groep (Indicatie Persoon.Indicatie.OnderCuratele.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Indicatie.OnderCuratele.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  26.  Zoeken op DatumAanvangGeldigheid van een groep (Indicatie Persoon.Indicatie.Staatloos.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Indicatie.Staatloos.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  27.  Zoeken op DatumAanvangGeldigheid van een groep (Indicatie Persoon.Indicatie.VastgesteldNietNederlander.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Indicatie.VastgesteldNietNederlander.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  28.  Zoeken op DatumAanvangGeldigheid van een groep (Indicatie Persoon.Indicatie.BehandeldAlsNederlander.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Indicatie.VastgesteldNietNederlander.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  29.  Zoeken op DatumAanvangGeldigheid van een groep (Persoon.Ouder.Ouderschap.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Ouder.Ouderschap.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |

Scenario:  30.  Zoeken op DatumAanvangGeldigheid van een groep (Persoon.Ouder.Ouderschap.DatumAanvangGeldigheid)
                LT: R2610_LT01
                Verwacht Resultaat:
                - Foutief
                  R2610
                  Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Ouder.Ouderschap.DatumAanvangGeldigheid

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                 |
| R2610     | Zoeken op datum aanvang geldigheid van een groep is niet toegestaan.    |