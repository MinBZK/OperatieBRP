Meta:
@status             Klaar
@usecase            BV.0.ZP
@regels             R2308, R1273

Narrative:

Voor elke Zoekcriterium in een zoekvraag geldt dat de gebruikte waarde moet corresponderen met het datatype van betreffende element:

Als Zoekcriterium.Element van het attribuuttype Datum is, dan moet Zoekcriterium.Waarde voldoen aan R1274 - Datum moet een geldige kalenderdatum zijn.
Als Zoekcriterium.Element van het attribuuttype Datum evt. (deels) onbekend is, dan moet Zoekcriterium.Waarde voldoen aan Datum (deels) onbekend (R1273).
Als Zoekcriterium.Element van het type Numeriek is, dan dient Zoekcriterium.Waarde een geldige numerieke waarde te bevatten.

Volgens de stelsel standaard worden de volgende waarden voor (gedeeltelijk) onbekende datums ondersteund:
Onbekende dag: YYYY-MM of YYYY-MM-00
Onbekende Maand: YYYY of YYYY-00 (onbekende maand en wel bekende dag is niet toegestaan)
Geheel onbekend : 0000 of 0000-00 of 0000-00-00

Ongeldige onbekende datums zijn bijv:
- YYYY-**
- YYYY-00-DD

Scenario: 1     Zoek persoon expliciet op gedeeltelijk onbekende datum (dag onbekend)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek zoek persoon:
| key                      | value                                                                                |
| leveringsautorisatieNaam | 'Zoek Persoon'                                                                       |
| zendendePartijNaam       | 'Gemeente Utrecht'                                                                   |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=1998-01-00 |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa         |

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2     Zoek persoon expliciet op gedeeltelijk onbekende datum (maand & dag onbekend)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek zoek persoon:
| key                      | value                                                                                |
| leveringsautorisatieNaam | 'Zoek Persoon'                                                                       |
| zendendePartijNaam       | 'Gemeente Utrecht'                                                                   |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=1998-00-00 |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa         |

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3     Zoek persoon expliciet op gedeeltelijk onbekende datum (maand & dag onbekend)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek zoek persoon:
| key                      | value                                                                             |
| leveringsautorisatieNaam | 'Zoek Persoon'                                                                    |
| zendendePartijNaam       | 'Gemeente Utrecht'                                                                |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=1998-00 |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa      |

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 4     Zoek persoon expliciet op geheel onbekende datum (jaar & maand & dag onbekend)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek zoek persoon:
| key                      | value                                                                                |
| leveringsautorisatieNaam | 'Zoek Persoon'                                                                       |
| zendendePartijNaam       | 'Gemeente Utrecht'                                                                   |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=0000-00-00 |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa         |

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 5     Zoek persoon expliciet op geheel onbekende datum Geslaagd (0000-00)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek zoek persoon:
| key                      | value                                                                             |
| leveringsautorisatieNaam | 'Zoek Persoon'                                                                    |
| zendendePartijNaam       | 'Gemeente Utrecht'                                                                |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=0000-00 |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa      |

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 6     Zoek persoon expliciet op geheel onbekende datum Geslaagd (0000)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek zoek persoon:
| key                      | value                                                                          |
| leveringsautorisatieNaam | 'Zoek Persoon'                                                                 |
| zendendePartijNaam       | 'Gemeente Utrecht'                                                             |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=0000 |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa   |

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 7    Zoek persoon expliciet op geheel onbekende datum Geslaagd (0)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek zoek persoon:
| key                      | value                                                                        |
| leveringsautorisatieNaam | 'Zoek Persoon'                                                               |
| zendendePartijNaam       | 'Gemeente Utrecht'                                                           |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=0  |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa |

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 8    Zoek persoon expliciet op gedeeltelijk onbekende datum (maand en dag onbekend) (1960)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek zoek persoon:
| key                      | value                                                                          |
| leveringsautorisatieNaam | 'Zoek Persoon'                                                                 |
| zendendePartijNaam       | 'Gemeente Utrecht'                                                             |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=1960 |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa   |

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 9    Zoek persoon expliciet op gedeeltelijk onbekende datum (dag onbekend) (1960-11)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek zoek persoon:
| key                      | value                                                                             |
| leveringsautorisatieNaam | 'Zoek Persoon'                                                                    |
| zendendePartijNaam       | 'Gemeente Utrecht'                                                                |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=1960-11 |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa      |

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 10   Zoek persoon expliciet op gedeeltelijk onbekende datum (196-11)
                LT: R2308_LT05
                Verwacht resultaat:
                - Verwerking Foutief; omdat jaartal niet voldoet aan patroon YYYY

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek zoek persoon:
| key                      | value                                                                            |
| leveringsautorisatieNaam | 'Zoek Persoon'                                                                   |
| zendendePartijNaam       | 'Gemeente Utrecht'                                                               |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=196-11 |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa     |

Then heeft het antwoordbericht verwerking Foutief

And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                           |
| R2308 | De opgegeven waarde komt niet overeen met het datatype van het opgegeven element. |

Scenario: 11   Zoek persoon expliciet op gedeeltelijk onbekende datum onder jaar 1000 GOED (0196-11-01)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek zoek persoon:
| key                      | value                                                                                |
| leveringsautorisatieNaam | 'Zoek Persoon'                                                                       |
| zendendePartijNaam       | 'Gemeente Utrecht'                                                                   |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=0196-11-01 |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa         |

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 12   Zoek persoon expliciet op gedeeltelijk onbekende datum enkel maand onbekend (1960-00-13)
                LT: R2308_LT05
                Verwacht resultaat:
                - Verwerking foutief

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek zoek persoon:
| key                      | value                                                                                |
| leveringsautorisatieNaam | 'Zoek Persoon'                                                                       |
| zendendePartijNaam       | 'Gemeente Utrecht'                                                                   |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=1960-00-13 |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa         |

Then heeft het antwoordbericht verwerking Foutief

And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                           |
| R2308 | De opgegeven waarde komt niet overeen met het datatype van het opgegeven element. |

Scenario: 13   Zoek persoon vanaf op gedeeltelijk onbekende datum met wildcards (1960-01-**)
                LT: R2308_LT05
                Verwacht resultaat:
                - Verwerking foutief

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek zoek persoon:
| key                      | value                                                                                |
| leveringsautorisatieNaam | 'Zoek Persoon'                                                                       |
| zendendePartijNaam       | 'Gemeente Utrecht'                                                                   |
| zoekcriteria             | ZoekOptie=Vanaf klein,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=1960-01-** |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa         |

Then heeft het antwoordbericht verwerking Foutief

And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                           |
| R2308 | De opgegeven waarde komt niet overeen met het datatype van het opgegeven element. |


Scenario: 14   Zoek persoon vanaf met gedeeltelijk onbekende datum (1960-00)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek zoek persoon:
| key                      | value                                                                             |
| leveringsautorisatieNaam | 'Zoek Persoon'                                                                    |
| zendendePartijNaam       | 'Gemeente Utrecht'                                                                |
| zoekcriteria             | ZoekOptie=Vanaf klein,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=1960-00 |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa      |

Then heeft het antwoordbericht verwerking Geslaagd
