Meta:
@status             Klaar
@usecase            LV.1.AL
@regels             R2239, R2242, R2243, R2244, R2245
@sleutelwoorden     Autorisatie Levering

Narrative:
Als BRP wil ik dat alleen geautoriseerde afnemers toegang hebben tot de BRP


Scenario:   1. R2239 Ongeldige Dienstbundel
               LT: R2239_LT04
               Verwacht Resultaat:
               Foutieve situatie aangetroffen; verwerking blokkeert
               Meldingstekst:	De dienstbundel is niet geldig.

Given leveringsautorisatie uit autorisatie/R2239/R2239
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'R2239 Geldigheid Dienstbundel'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                         |
| R2343 | De dienstbundel is niet geldig. |

Then is er een autorisatiefout gelogd met regelcode R2239

Scenario:   2.  R2242 Ongeldige Partij
                LT: R2242_LT05
                Verwacht resultaat:

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De partij is niet geldig.

Given leveringsautorisatie uit autorisatie/R2242_GeldigheidPartij/R2242.txt
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'R2242 Is partij geldig'
|zendendePartijNaam|'GemeenteDatumIngangDatumEindeInVerleden'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                  |
| R2343 | De partij is niet geldig |

Then is er een autorisatiefout gelogd met regelcode R2242


Scenario:   3.  R2243 Ongeldige Ondertekenaar
                LT: R2243_LT04
                Verwacht resultaat:
                - Foutmelding: R2243
                  De ondertekenaar is geen geldige partij.

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2243/oin_ongelijk_Ongeldig_dat_einde_gisteren
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|ondertekenaar|'DatumEindeGisterenPartij'
|transporteur|'Gemeente Alkmaar'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                   |
| R2343 | De ondertekenaar is geen geldige partij.  |

Then is er een autorisatiefout gelogd met regelcode R2243


Scenario:   4.  R2244 Ongeldige transporteur
                LT: R2244_LT04
                Verwacht resultaat:
                - Foutmelding: R2244
                De transporteur is geen geldige partij.


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2244/oin_ongelijk_Ongeldig_dat_einde_gisteren
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|ondertekenaar|'Gemeente Utrecht'
|transporteur|'DatumEindeGisterenPartij'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                     |
| R2343 | De transporteur is geen geldige partij.     |

Then is er een autorisatiefout gelogd met regelcode R2244

Scenario:   5.  R2245 Ongeldige PartijRol
                LT: R2245_LT04
                Verwacht resultaat:

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De combinatie partij en rol is niet geldig.

Given leveringsautorisatie uit autorisatie/R2245_GeldigheidPartijRol/2245.txt
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'R2245 Is partijRol geldig'
|zendendePartijNaam|'PartijRolDatumIngangGisterenDatumEindeVandaag'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                     |
| R2343 | De combinatie partij en rol is niet geldig. |

Then is er een autorisatiefout gelogd met regelcode R2245