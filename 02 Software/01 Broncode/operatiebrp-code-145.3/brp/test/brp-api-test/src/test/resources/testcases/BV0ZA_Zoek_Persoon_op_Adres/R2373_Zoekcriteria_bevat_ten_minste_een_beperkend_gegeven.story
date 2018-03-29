Meta:
@status             Klaar
@usecase            BV.0.ZA
@sleutelwoorden     Zoek Persoon op adres


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



Scenario: 1.    Zoek persoon op adres met criteria Persoon.Adres.IdentificatiecodeAdresseerbaarObject en optie Exact
                LT: R2373_LT01, R1262_LT23, R1264_LT19, R2054_LT07, R2055_LT06, R2056_LT19
                Verwacht Resultaat:
                - Verwerking Geslaagd.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.IdentificatiecodeAdresseerbaarObject,Waarde=12

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2.    Zoek persoon op adres met criteria GemeenteCode en optie Exact
                LT: R2373_LT02
                Verwacht Resultaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.GemeenteCode,Waarde=0626


Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3.    Zoek persoon op adres met criteria Woonplaatsnaam en optie:Exact
                LT: R2373_LT04
                Verwacht Resultaat:
                - Verwerking Foutief; De zoekvraag bevat onvoldoende adresgegevens.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Woonplaatsnaam,Waarde=Uithoorn

Scenario: 4.    Zoek persoon op adres met criteria Persoon.Adres.IdentificatiecodeNummeraanduiding en optie:Exact
                LT: R2373_LT05
                Verwacht Resultaat:
                - Verwerking Foutief; De zoekvraag bevat onvoldoende adresgegevens.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.IdentificatiecodeNummeraanduiding,Waarde=0626200010016001


Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 5.    Zoek persoon op adres met criteria Postcode en optie: Vanaf klein
                LT: R2373_LT06
                Verwacht Resultaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 6.    Zoek persoon op adres met criteria Postcode en optie: Vanaf exact
                LT: R2373_LT07
                Verwacht Resultaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 8.    Zoek persoon op adres met criteria Buitenlandseadresregel2 en optie: Vanaf klein
                LT: R2373_LT08
                Verwacht Resultaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Adres.BuitenlandsAdresRegel2,Waarde=1422RZ

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 9.    Zoek persoon op adres met criteria Buitenlandseadresregel2 en optie: Vanaf exact
                LT: R2373_LT09
                Verwacht Resultaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf exact,ElementNaam=Persoon.Adres.BuitenlandsAdresRegel2,Waarde=1422RZ

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 10.   Zoek persoon op adres met criteria Woonplaatsnaam en optie:Leeg
                LT: R2373_LT10
                Verwacht Resultaat:
                - Verwerking Foutief; De zoekvraag bevat onvoldoende adresgegevens.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Adres.Woonplaatsnaam

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2373    | De zoekvraag bevat onvoldoende adresgegevens.      |

Scenario: 11.   Zoek persoon op adres met criteria Woonplaatsnaam en optie: Vanaf Klein
                LT: R2373_LT11
                Verwacht Resultaat:
                - Verwerking Foutief; De zoekvraag bevat onvoldoende adresgegevens.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Adres.Woonplaatsnaam,Waarde=Uithoorn

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2373    | De zoekvraag bevat onvoldoende adresgegevens.      |

Scenario: 12.   Zoek persoon op adres met criteria Persoon.Adres.IdentificatiecodeAdresseerbaarObject en optie Vanaf Exact
                LT: R2373_LT12
                Verwacht Resultaat:
                - Verwerking Foutief; De zoekvraag bevat onvoldoende adresgegevens.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf exact,ElementNaam=Persoon.Adres.IdentificatiecodeAdresseerbaarObject,Waarde=0626010010016001

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2373    | De zoekvraag bevat onvoldoende adresgegevens.      |

Scenario: 13.   Zoek persoon op adres met criteria Postcode en optie: Leeg
                LT: R2373_LT13
                Verwacht Resultaat:
                Verwacht Resultaat:
                - Verwerking Foutief; De zoekvraag bevat onvoldoende adresgegevens.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Adres.Postcode

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2373    | De zoekvraag bevat onvoldoende adresgegevens.      |

Scenario: 14.   Zoek persoon op adres met criteria Buitenlandseadresregel2 en optie: Leeg
                LT: R2373_LT14
                Verwacht Resultaat:
                - Verwerking Foutief; De zoekvraag bevat onvoldoende adresgegevens.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres2_xls
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres3_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Leeg,ElementNaam=Persoon.Adres.BuitenlandsAdresRegel2

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2373    | De zoekvraag bevat onvoldoende adresgegevens.      |

Scenario: 15.   Zoek persoon op adres met criteria Persoon.Adres.IdentificatiecodeAdresseerbaarObject en optie Vanaf Exact
                LT: R2373_LT15
                Verwacht Resultaat:
                - Verwerking Foutief; De zoekvraag bevat onvoldoende adresgegevens.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:ZoekPersoonAdres/2374_Anne_Bakker_Adres1_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Klein,ElementNaam=Persoon.Adres.IdentificatiecodeAdresseerbaarObject,Waarde=0626010010016001

Then heeft het antwoordbericht verwerking Geslaagd