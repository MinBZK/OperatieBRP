Meta:
@status             Klaar
@usecase            BV.0.ZA
@sleutelwoorden     Zoek Persoon op adres


Narrative:
Bij het zoekcriterium straatnaam moet verplicht ook een gemeente of woonplaats opgegeven worden.

Indien de Bericht \ Zoekcriteria.Zoekcriterium één van de volgende elementen bevat:

    Element.Element naam = 'Naam openbare ruimte'
OF
    Element.Element naam = 'Afgekorte naam openbare ruimte'

dan moet ook één van de volgende elementen opgenomen zijn in de Bericht \ Zoekcriteria.Zoekcriterium :

   Element.Element naam = 'Gemeente'

 OF
    Element.Element naam = 'Woonplaatsnaam'

Waarvoor geldt dat


Bericht.Optie is ongelijk aan 'Leeg'

EN

Bericht.Optie is ongelijk aan 'Vanaf'

Scenario: 1.    Zoek persoon op adres met criteria Naam openbare ruimte en Woonplaatsnaam en optie Exact
                LT: R2375_LT01, R2054_LT07
                Verwacht Resultaat:
                - Verwerking Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.NaamOpenbareRuimte,Waarde=Bertram van oost laan
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Woonplaatsnaam,Waarde=Uithoorn

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2.    Zoek persoon op adres met criteria Naam openbare ruimte en Gemeente en optie Exact
                LT: R2375_LT02
                Verwacht Resultaat:
                - Verwerking Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.NaamOpenbareRuimte,Waarde=Bertram van oost laan
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.GemeenteCode,Waarde=0626

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3.    Zoek persoon op adres met criteria Afgekorte Naam openbare ruimte en Woonplaatsnaam en optie Exact
                LT: R2375_LT03
                Verwacht Resultaat:
                - Verwerking Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.AfgekorteNaamOpenbareRuimte,Waarde=Bertram
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Woonplaatsnaam,Waarde=Uithoorn

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 4.    Zoek persoon op adres met criteria Afgekorte Naam openbare ruimte en Woonplaatsnaam en optie Exact
                LT: R2375_LT04
                Verwacht Resultaat:
                - Verwerking Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.AfgekorteNaamOpenbareRuimte,Waarde=Bertram
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.GemeenteCode,Waarde=0626

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 5.    Zoek persoon op adres met criteria Afgekorte Naam openbare ruimte (optie Exact) en Woonplaatsnaam en optie Leeg
                LT: R2375_LT05
                Verwacht Resultaat:
                - Verwerking Foutief; Zoekcriteria Persoon.Adres.Woonplaatsnaam heeft optie leeg

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.AfgekorteNaamOpenbareRuimte,Waarde=Bertram
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Adres.Woonplaatsnaam
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2375    | De zoekvraag bevat onvoldoende adresgegevens.      |

Scenario: 6.    Zoek persoon op adres met criteria Naam openbare ruimte (optie Exact) en Woonplaatsnaam en optie Leeg
                LT: R2375_LT06
                Verwacht Resultaat:
                - Verwerking Foutief; Zoekcriteria Persoon.Adres.Woonplaatsnaam heeft optie leeg

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.NaamOpenbareRuimte,Waarde=Bertram van oost laan
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Adres.Woonplaatsnaam
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2375    | De zoekvraag bevat onvoldoende adresgegevens.      |

Scenario: 7.    Zoek persoon op adres met criteria Naam openbare ruimte (optie Exact) en GemeenteCode en optie Leeg
                LT: R2375_LT07
                Verwacht Resultaat:
                - Verwerking Foutief; Zoekcriteria Persoon.Adres.GemeenteCode heeft optie leeg

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.NaamOpenbareRuimte,Waarde=Bertram van oost laan
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Adres.GemeenteCode
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2375    | De zoekvraag bevat onvoldoende adresgegevens.      |

Scenario: 8.    Zoek persoon op adres met criteria Afgekorte Naam openbare ruimte (optie Exact) en GemeenteCode en optie Leeg
                LT: R2375_LT08
                Verwacht Resultaat:
                - Verwerking Foutief; Zoekcriteria Persoon.Adres.GemeenteCode heeft optie leeg

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.AfgekorteNaamOpenbareRuimte,Waarde=Bertram
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Adres.GemeenteCode
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2375    | De zoekvraag bevat onvoldoende adresgegevens.      |
