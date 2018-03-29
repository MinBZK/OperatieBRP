Meta:
@status             Klaar
@usecase            BV.0.ZA
@sleutelwoorden     Zoek Persoon op adres:

Narrative:
Een zoekvraag dient tenminste één zoekcriterium te bevatten met een adresgegeven dat geografisch beperkend is.
Bij de dienst Zoek Persoon op Adres moet een geografisch beperkend adresgegeven worden gebruikt.
Dit betekent dat er in de zoekvraag tenminste één zoekcriterium aanwezig moet zijn waarbij geldt dat
Bericht \ Zoekcriteria.Zoekcriterium is gevuld met Element.Element naam = 'Postcode'
OF
Bericht \ Zoekcriteria.Zoekcriterium is gevuld met Element.Element naam = 'Gemeente'
OF
Bericht \ Zoekcriteria.Zoekcriterium is gevuld met Element.Element naam = 'Gemeentedeel'
OF
Bericht \ Zoekcriteria.Zoekcriterium is gevuld met Element.Element naam = 'Woonplaatsnaam'
OF
Bericht \ Zoekcriteria.Zoekcriterium is gevuld met Element.Element naam =
'Identificatiecode nummeraanduiding'
EN
Bericht.Optie is ongelijk aan 'Leeg'
EN
Bericht.Optie ongelijk is aan 'Vanaf'


Scenario: 1.    Zoek persoon op adres met criteria die niet voldoet
                LT: R2265_LT14
                Attribuut van zoekcriterium dient een element van de soort attribuut te zijn
                Zoek criteria Persoon.Adres.IndicatiePersoonAangetroffenOpAdres heeft autorisatie uitzondering hierop mag niet gezocht worden.

                Verwacht Resultaat:
                - R2542 - Het als zoekcriterium opgegeven element is niet opvraagbaar

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.IndicatiePersoonAangetroffenOpAdres,Waarde=1

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                         |
| R2542     | Het als zoekcriterium opgegeven element is niet opvraagbaar     |

Scenario: 2.    Zoek persoon op adres met criteria Woonplaatsnaam en optie Vanaf
                LT: R2281_LT06
                Verwacht Resultaat:
                - Verwerking Foutief; De zoekvraag bevat onvoldoende adresgegevens.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Adres.Huisnummer,Waarde=10

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                                                      |
| R2281 | Foutief: Optie "Klein" of "Vanaf" is alleen toegestaan bij alfanumerieke attributen met een variabele lengte |

Scenario:   3.  Dienstbundel heeft GEEN autorisatie op huisnummer. Zoekcriterium is huisnummer. Zoekcriterium 2 is postcode
                LT: R2290_LT04
                Verwacht resultaat:
                - Foutmelding: Er bestaat geen autorisatie voor het zoekcriterium

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_huisnummer_niet_geautoriseerd

Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Huisnummer niet geautoriseerd'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=33
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2000AA

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2290     | Er bestaat geen autorisatie voor het zoekcriterium        |

Scenario: 5.    zoekcriterium.elementNaam Datatype <> zoekcriterium.elementNaam Datatype
                LT: R2308_LT03
                Verwacht resultaat:
                - Foutief: De opgegeven waarde komt niet overeen met het datatype van het opgegeven element.
                Uitwerking:
                - Zoeken op huisnummer met een string ipv nr.


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=nummer1
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2000AA

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                           |
| R2308 | De opgegeven waarde komt niet overeen met het datatype van het opgegeven element. |


Scenario: 6.    zoekcriterium opgegeven waarde is te lange voor het opgegeven element
                LT: R2311_LT08
                Verwacht resultaat:
                - Foutief: De opgegeven waarde is te lang voor het opgegeven element in het zoekcriterium.
                Uitwerking:
                - Zoeken op postcode met een te lange waarde


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=1
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2000AAAA

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                         |
| R2311 | De opgegeven waarde is te lang voor het opgegeven element in het zoekcriterium. |
