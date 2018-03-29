Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon


Narrative:
De inhoud van Attribuut in Bericht \ Zoekcriteria.Zoekcriterium dient een Element.Element naam van een Element te zijn waarvoor geldt dat:
Element.Autorisatie gelijk aan 'Optioneel', 'Verplicht', 'Aanbevolen' of 'Bijhoudingsgegevens'
EN
Element.Identificatie database schema is gelijk aan "Kern"

Scenario: 1.    Zoeken op DatumAanvangGeldigheid van een groep (Ouder.OuderlijkGezag.DatumAanvangGeldigheid), met Element autorisatie is LEEG
                LT: R2542_LT05
                Verwacht Resultaat:
                - Foutief
                  R2542
                  Het als zoekcriterium opgegeven element is niet opvraagbaar.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Ouder.OuderlijkGezag.DatumAanvangGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief

Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                       |
| R2542     | Het als zoekcriterium opgegeven element is niet opvraagbaar.  |

Scenario: 2.    Zoeken op Element autorisatie is via groepsautorisatie, database schema is kern
                LT: R2542_LT06
                Verwacht Resultaat:
                - Foutief
                  R2542
                  Het als zoekcriterium opgegeven element is niet opvraagbaar.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.DatumEindeGeldigheid,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief

Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                       |
| R2542     | Het als zoekcriterium opgegeven element is niet opvraagbaar.  |

Scenario: 3.    Zoeken op Element autorisatie is niet verstrekken, database schema is kern
                LT: R2542_LT07
                Verwacht Resultaat:
                - Foutief
                  R2542
                  Het als zoekcriterium opgegeven element is niet opvraagbaar.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=FamilierechtelijkeBetrekking.DatumAanvang,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief

Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                       |
| R2542     | Het als zoekcriterium opgegeven element is niet opvraagbaar.  |

Scenario: 4.    Zoeken op Element autorisatie is structuur, database schema is kern
                LT: R2542_LT08
                Verwacht Resultaat:
                - Foutief
                  R2542
                  Het als zoekcriterium opgegeven element is niet opvraagbaar.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=GerelateerdeOuder.OuderlijkGezag.VoorkomenSleutel,Waarde=8

Then heeft het antwoordbericht verwerking Foutief

Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                       |
| R2542     | Het als zoekcriterium opgegeven element is niet opvraagbaar.  |

Scenario: 5.    Zoeken op Element autorisatie is uitzondering, database schema is kern
                LT: R2542_LT09
                Verwacht Resultaat:
                - Foutief
                  R2542
                  Het als zoekcriterium opgegeven element is niet opvraagbaar.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=GerelateerdeGeregistreerdePartner.Persoon.SoortCode,Waarde=8

Then heeft het antwoordbericht verwerking Foutief

Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                       |
| R2542     | Het als zoekcriterium opgegeven element is niet opvraagbaar.  |

Scenario: 6.1   Zoeken op Element autorisatie is optioneel, database schema is autaut
                LT: R2542_LT10
                Verwacht Resultaat:
                - Foutief
                  R2542
                  Het als zoekcriterium opgegeven element is niet opvraagbaar.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Afnemerindicatie.DatumAanvangMaterielePeriode,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Foutief

Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                       |
| R2542     | Het als zoekcriterium opgegeven element is niet opvraagbaar.  |

Scenario: 6.2   Zoeken op Afnemerindicatie, autaut schema
                LT: R2542_LT10
                Verwacht Resultaat:
                - Foutief
                  R2542
                  Het als zoekcriterium opgegeven element is niet opvraagbaar.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Afnemerindicatie.DatumAanvangMaterielePeriode

Then heeft het antwoordbericht verwerking Foutief

Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                       |
| R2542     | Het als zoekcriterium opgegeven element is niet opvraagbaar.  |

Scenario: 7.    Zoeken op Element autorisatie is LEEG, database schema is ber
                LT: R2542_LT11
                Verwacht Resultaat:
                - Foutief
                  R2542
                  Het als zoekcriterium opgegeven element is niet opvraagbaar.

!-- Er zijn geen attributen met database schema Ber, met autorisatie Optioneel, Verplicht, Aanbevolen of Bijhoudingsgegeven.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Bijhoudingsplan.BijhoudingsvoorstelPartijCode

Then heeft het antwoordbericht verwerking Foutief

Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                       |
| R2542     | Het als zoekcriterium opgegeven element is niet opvraagbaar.  |
