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


Scenario: 1.    Zoek persoon op adres met zoekoptie leeg en een waarde die niet leeg is
                LT: R2266_LT05
                Zoek criteria waarde is niet leeg
                Verwacht Resultaat:
                - R2266 - Waarde moet leeg zijn bij zoekcriterium %Elementnaam%

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=1
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Adres.Postcode,Waarde=2000AA

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2266     | Waarde moet leeg zijn bij zoekcriterium %Elementnaam%     |


Scenario: 2.    Zoek persoon op adres met zoekoptie Exact en een waarde die leeg is
                LT: R2267_LT05
                Zoek criteria waarde is leeg, met optie Exact
                Verwacht Resultaat:
                - R2267 - Waarde moet gevuld zijn bij zoekcriterium %Elementnaam%

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=1
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2267     | Waarde moet gevuld zijn bij zoekcriterium %Elementnaam%   |


Scenario: 3.    Zoek persoon op adres, peilmoment mag niet in de toekomst liggen
                LT: R2295_LT05
                Zoek criteria met een peilmoment in de toekomst.
                Verwacht Resultaat:
                - R2295 - Peilmoment materieel mag niet in de toekomst liggen.

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=1
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2000AA
|peilmomentMaterieel|morgen

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                |
| R2295     | Peilmoment materieel mag niet in de toekomst liggen.   |


