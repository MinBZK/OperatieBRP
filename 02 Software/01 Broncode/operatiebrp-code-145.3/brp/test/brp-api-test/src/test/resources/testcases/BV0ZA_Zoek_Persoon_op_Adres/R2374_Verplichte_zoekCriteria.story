Meta:
@status             Klaar
@usecase            BV.0.ZA
@sleutelwoorden     Zoek Persoon op adres


Narrative:

Indien de Bericht \ Zoekcriteria.Zoekcriterium één van de volgende elementen bevat:

Element.Element naam = ('Huisnummer', 'Huisletter', 'Huisnummertoevoeging')

dan moet ook minstens één voorkomen van Bericht \ Zoekcriteria.Zoekcriterium aanwezig zijn met daarbij de volgende elementen:

( Zoekcriterium.Optie is ongelijk aan ('Leeg', 'Vanaf klein', 'Vanaf exact')

EN

Element.Element naam = ('Naam openbare ruimte', 'Afgekorte naam openbare ruimte') )

OF

( Zoekcriterium.Optie is ongelijk aan 'Leeg'

EN

Element.Element naam = 'Postcode')


Scenario: 1.    Zoek persoon op adres met criteria Huisnummer en Postcode en optie Exact
                LT: R2374_LT01
                Verwacht Resultaat:
                - Verwerking Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=157
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2.    Zoek persoon op adres met criteria Huisletter en Postcode en optie Exact
                LT: R2374_LT02
                Verwacht Resultaat:
                - Verwerking Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisletter,Waarde=A
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 3.    Zoek persoon op adres met criteria Huisnummertoevoeging en Postcode en optie:Exact
                LT: R2374_LT03
                Verwacht Resultaat:
                - Verwerking Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummertoevoeging,Waarde=AA
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 4.    Zoek persoon op adres met criteria Huisnummer en Woonplaatsnaam en optie:Exact
                LT: R2374_LT04
                Verwacht Resultaat:
                - Verwerking Foutief

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=157
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Woonplaatsnaam,Waarde=Uithoorn

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2374    | De zoekvraag bevat onvoldoende adresgegevens.      |

Scenario: 5.    Zoek persoon op adres met criteria Huisletter en Woonplaatsnaam en optie:Exact
                LT: R2374_LT05
                Verwacht Resultaat:
                - Verwerking Foutief

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisletter,Waarde=A
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Woonplaatsnaam,Waarde=Uithoorn

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2374    | De zoekvraag bevat onvoldoende adresgegevens.      |

Scenario: 6.    Zoek persoon op adres met criteria Huisnummertoevoeging en Woonplaatsnaam en optie:Exact
                LT: R2374_LT06
                Verwacht Resultaat:
                - Verwerking Foutief

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummertoevoeging,Waarde=AA
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Woonplaatsnaam,Waarde=Uithoorn

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2374    | De zoekvraag bevat onvoldoende adresgegevens.      |

Scenario: 7.    Zoek persoon op adres met criteria Huisnummer (optie exact) en postcode (optie leeg)
                LT: R2374_LT07
                Verwacht Resultaat:
                - Verwerking Foutief

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=157
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Adres.Postcode

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2374    | De zoekvraag bevat onvoldoende adresgegevens.      |


Scenario: 8.    Zoek persoon op Adres met meerdere criteria
                LT: R2374_LT08
                Verwacht Resultaat:
                - Verwerking Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=157
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisletter,Waarde=A
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummertoevoeging,Waarde=AA
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.AfgekorteNaamOpenbareRuimte,Waarde=Bertram
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Woonplaatsnaam,Waarde=Uithoorn

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 9.    Zoek persoon op adres met criteria Huisletter (optie Exact) en postcode (optie Leeg)
                LT: R2374_LT09
                Verwacht Resultaat:
                - Verwerking Foutief

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisletter,Waarde=A
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Adres.Postcode

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2374    | De zoekvraag bevat onvoldoende adresgegevens.      |

Scenario: 10.    Zoek persoon op adres met criteria Huisnummertoevoeging (optie Exact) en postcode (optie Leeg)
                LT: R2374_LT10
                Verwacht Resultaat:
                - Verwerking Foutief

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummertoevoeging,Waarde=AA
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Adres.Postcode

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2374    | De zoekvraag bevat onvoldoende adresgegevens.      |

Scenario: 11.    Zoek persoon op adres met criteria Huisnummertoevoeging (optie Exact) en postcode (optie vanaf)
                LT: R2374_LT11
                Verwacht Resultaat:
                - Verwerking Foutief

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=157
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Adres.AfgekorteNaamOpenbareRuimte,Waarde=Bertram
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Adres.Woonplaatsnaam,Waarde=Uithoorn

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2374    | De zoekvraag bevat onvoldoende adresgegevens.      |

Scenario: 12.a  Zoek persoon op adres met criteria Huisletter (optie Exact) en postcode (optie Vanaf)
                LT: R2374_LT12
                Verwacht Resultaat:
                - Verwerking Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisletter,Waarde=A
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Adres.Postcode,Waarde=1422

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 12.b  Zoek persoon op adres met criteria Huisletter (optie Exact) en postcode (optie Vanaf)
                LT: R2374_LT12
                Verwacht Resultaat:
                - Verwerking Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisletter,Waarde=A
|zoekcriteria|ZoekOptie=Vanaf exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 13.   Zoek persoon op adres met criteria Huisnummertoevoeging (optie Exact) en naam openbare ruimte
                LT: R2374_LT11
                Verwacht Resultaat:
                - Verwerking Foutief

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=157
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Adres.NaamOpenbareRuimte,Waarde=Bertram

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2374    | De zoekvraag bevat onvoldoende adresgegevens.      |

Scenario: 13a.   Zoek persoon op adres met criteria Huisnummertoevoeging (optie leeg) en naam openbare ruimte
                LT: R2374_LT11
                Verwacht Resultaat:
                - Verwerking Foutief

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=157
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Adres.NaamOpenbareRuimte

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2374    | De zoekvraag bevat onvoldoende adresgegevens.      |

Scenario: 14.   Zoek persoon op adres met criteria Huisnummertoevoeging (optie Exact) en naam openbare ruimte
                LT: R2374_LT11
                Verwacht Resultaat:
                - Verwerking Foutief

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=157
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Adres.AfgekorteNaamOpenbareRuimte,Waarde=Bertram

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2374    | De zoekvraag bevat onvoldoende adresgegevens.      |

Scenario: 14a.   Zoek persoon op adres met criteria Huisnummertoevoeging (optie leeg) en naam openbare ruimte
                LT: R2374_LT11
                Verwacht Resultaat:
                - Verwerking Foutief

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=157
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Adres.AfgekorteNaamOpenbareRuimte

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2374    | De zoekvraag bevat onvoldoende adresgegevens.      |