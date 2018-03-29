Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2311

Narrative:
Voor elke Zoekcriterium in een zoekvraag geldt dat de opgegeven waarde niet langer mag zijn dat wat bij het betreffende element is toegestaan:
(uitwerking met aanname dat we dit via een stamtabel configureerbaar maken):
Er dient een voorkomen van Zoekelement te zijn voor het opgegeven Element waarbij de opgegeven waarde niet meer tekens mag bevatten dan wat is toegestaan voor het betreffende element.
Bijvoorbeeld:
Een postcode mag maximaal 6 tekens bevatten
SamengesteldeNaam.Geslachtsnaamstam mag maximaal 200 tekens bevatten

Scenario: 1.    Zoek persoon met meer dan maximaal toegestaan aantal tekens, zoekoptie Exact
                LT: R2311_LT02
                Verwacht resultaat:
                - Foutmelding R2311 - De opgegeven waarde is te lang voor het opgegeven element in het zoekcriterium
                Uitwerking:
                - Zoek persoon op postcode met 7 tekens
                + Op achternaam omdat alleen op adresgegevens niet mag

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jansen
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2252EBR

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                        |
| R2311    | De opgegeven waarde is te lang voor het opgegeven element in het zoekcriterium |


Scenario: 2.    Zoek persoon met meer dan maximaal toegestaan aantal tekens, zoekoptie Vanaf
                LT: R2311_LT04
                Verwacht resultaat:
                - Foutmelding R2311 - De opgegeven waarde is te lang voor het opgegeven element in het zoekcriterium
                Uitwerking:
                - Zoek persoon op woonplaatsnaam met 81 tekens (80 toegestaan)
                + Op achternaam omdat alleen op adresgegevens niet mag

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jansen
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Woonplaatsnaam,Waarde=ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                        |
| R2311    | De opgegeven waarde is te lang voor het opgegeven element in het zoekcriterium |


Scenario: 3.    Zoek persoon met meer dan maximaal toegestaan aantal tekens, zoekoptie Klein
                LT: R2311_LT06
                Verwacht resultaat:
                - Foutmelding R2311 - De opgegeven waarde is te lang voor het opgegeven element in het zoekcriterium
                Uitwerking:
                - Zoek persoon op woonplaatsnaam met 81 tekens (80 toegestaan)
                + Op achternaam omdat alleen op adresgegevens niet mag

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jansen
|zoekcriteria|ZoekOptie=Klein,ElementNaam=Persoon.Adres.Woonplaatsnaam,Waarde=ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd


Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                        |
| R2311    | De opgegeven waarde is te lang voor het opgegeven element in het zoekcriterium |


Scenario: 4.    Zoek persoon met meer dan maximaal toegestaan aantal tekens, zoekoptie Leeg
                LT: R2311_LT07
                Verwacht resultaat:
                - Foutmelding R2311 - De opgegeven waarde is te lang voor het opgegeven element in het zoekcriterium
                Uitwerking:
                - Zoek persoon op postcode met 7 tekens
                + Op achternaam omdat alleen op adresgegevens niet mag

!-- Zoek optie leeg mag eigenlijk al geen tekens bevatten. Test om te zien welke regel eerst afgaat. Het blijkt dat R2311 eerst afgaat.
Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jansen
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Adres.Postcode,Waarde=2252EBR

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                        |
| R2311    | De opgegeven waarde is te lang voor het opgegeven element in het zoekcriterium |


Scenario: 5.    zoekcriterium.elementNaam.adelijke titel <> adelijke titelcode
                LT:
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
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.AdellijkeTitelCode,Waarde=XXX

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                        |
| R2311 | De opgegeven waarde is te lang voor het opgegeven element in het zoekcriterium |
