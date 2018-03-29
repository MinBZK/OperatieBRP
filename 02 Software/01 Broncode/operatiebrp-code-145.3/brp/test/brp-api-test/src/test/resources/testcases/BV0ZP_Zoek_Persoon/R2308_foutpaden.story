Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2308

Narrative:
Voor elke Zoekcriterium in een zoekvraag geldt dat de gebruikte waarde moet corresponderen met het datatype van betreffende element:
Als Zoekcriterium.Element van het type Datum is, dan dient Zoekcriterium.Waarde een geldige Datum (deels) onbekend (R1273) te bevatten.
Als Zoekcriterium.Element van het type Numeriek is, dan dient Zoekcriterium.Waarde een geldige numerieke waarde te bevatten.

Scenario: 1.    zoekcriterium.elementNaam Datatype <> zoekcriterium.elementNaam Datatype
                LT: R2308_LT02
                Verwacht resultaat:
                - Foutief: De opgegeven waarde komt niet overeen met het datatype van het opgegeven element.
                Uitwerking:
                - Huisnummer bevat in het verzoek een STRING waarde, dit komt niet overeen met GETAL

!-- NB: Test aangevukd met addiotioneel falend testcriteria om te valideren dat
!-- Als er meerdere zoek criteria niet voldoen, er ook meerdere meldingen terug komen

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jansen
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=aaa
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2252EBR

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                           |
| R2308 | De opgegeven waarde komt niet overeen met het datatype van het opgegeven element. |
| R2311 | De opgegeven waarde is te lang voor het opgegeven element in het zoekcriterium    |

Scenario: 2.    zoekcriterium.elementNaam Datatype <> zoekcriterium.elementNaam Datatype
                LT: R2308_LT02
                Verwacht resultaat:
                - Foutief: De opgegeven waarde komt niet overeen met het datatype van het opgegeven element.
                Uitwerking:
                - Deels onbekende datum wordt gevuld met sterretjes ipv. nullen


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jansen
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Geboorte.Datum,Waarde=196008**

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                           |
| R2308 | De opgegeven waarde komt niet overeen met het datatype van het opgegeven element. |



Scenario: 3.    zoekcriterium.elementNaam.adelijke titel <> adelijke titelcode
                LT: R2308_LT02
                Extra test mbt stamgegevens
                De opgegeven waarde komt niet overeen met een waarde uit de stamgegevenstabel
                Verwacht resultaat:
                - Foutief: De opgegeven waarde komt niet overeen met het datatype van het opgegeven element.

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jansen
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.AdellijkeTitelCode,Waarde=X

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                           |
| R2308 | De opgegeven waarde komt niet overeen met het datatype van het opgegeven element. |

Scenario: 4.    Zoekcriterium is 01-13-2015, GEEN Schrikkeljaar, Dienst Zoek Persoon
                LT: R2308_LT06
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Geboorte.Datum,Waarde=20151301


Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R2308     | De opgegeven waarde komt niet overeen met het datatype van het opgegeven element.