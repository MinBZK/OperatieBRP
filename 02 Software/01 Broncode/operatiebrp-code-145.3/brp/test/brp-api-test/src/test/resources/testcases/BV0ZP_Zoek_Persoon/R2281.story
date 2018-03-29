Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2281

Narrative:
Voor elke Zoekcriterium in een zoekvraag geldt dat de gebruikte optie mogelijk moet zijn bij het betreffende element:

 Zoekcriterium.Optie mag alleen de waarde "Klein" hebben als:

   Zoekcriterium.Element van het type "alfanumeriek" is.

Zoekcriterium.Optie mag alleen de waarde "Vanaf klein" of "Vanaf exact" hebben als:
   Zoekcriterium.Element van het type "alfanumeriek" is.
        OF
     Zoekcriterium.Element van het type "datum (evt. deels onbekend)" is.

Dus bijvoorbeeld:

    Opties "Vanaf klein" en "Vanaf exact" zijn toegestaan op Persoon.Geboorte.Datum (datum)
    Optie "Klein" is niet toegestaan op Persoon.Geboorte.Datum (datum)
    Opties "Klein", "Vanaf klein" en "Vanaf exact" zijn toegestaan bij Persoon.Adres.postcode (alfanumeriek, 6 posities)
    Opties "Klein", "Vanaf klein" en "Vanaf exact" zijn niet toegestaan bij Persoon.Adres.huisnummer (numeriek)
    Opties "Klein", "Vanaf klein" en "Vanaf exact" zijn toegestaan op Persoon.Adres.naamOpenbareRuimte (alfanumeriek, maximaal 80 posities)

Scenario:   1.  Zoekcriterium Optie = Vanaf klein, Zoekcriterium element = STRING
                LT: R2281_LT01
                Doel: Vaststellen dat de zoek dienst correcte resultaten geeft met
                      zoekcriterium optie Vanaf klein
                      en een element van het type alfanummeriek
                Verwacht resultaat:
                - Zoek persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Adres.NaamOpenbareRuimte,Waarde=Baron Schimmelpenninck van der Oyelaan
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jan

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 1 groepen 'persoon'

Scenario:   2.  Zoekcriterium Optie = Klein, Zoekcriterium element = STRING
                LT: R2281_LT02
                Doel: Vaststellen dat de zoek dienstcorrecte resultaten geeft met
                      zoekcriterium optie Klein en
                      een element van het type alfanummeriek
                Verwacht resultaat:
                - Zoek persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Klein,ElementNaam=Persoon.Adres.NaamOpenbareRuimte,Waarde=Baron Schimmelpenninck van der Oyelaan
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jan

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 1 groepen 'persoon'

Scenario:   3.  Zoekcriterium Optie = Vanaf klein, Zoekcriterium element = geboortedatum (deels onbekend)
                LT: R2281_LT03, R2293_LT04
                Doel: Vaststellen dat de zoek dienstcorrecte resultaten geeft met
                      zoekcriterium optie Vanaf klein en
                      een element van het type datum
                Verwacht resultaat:
                - Zoek persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Geboorte.Datum,Waarde=1960
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jan

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 1 groepen 'persoon'

Scenario:   4.  Zoekcriterium Optie = Klein, Zoekcriterium element <> STRING
                LT: R2281_LT04
                Doel: Vaststellen dat de zoek dienst een correcte melding geeft met
                zoekcriterium optie Klein en een element van het type <> alfanummeriek
                Verwacht resultaat:
                - Zoek persoon foutief
                - Melding: 	Optie "Vanaf" is alleen toegestaan bij tekst- en datumvelden. Optie "Klein" alleen bij tekstvelden.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Klein,ElementNaam=Persoon.Geboorte.Datum,Waarde=2015-01-01

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                             |
| R2281     | Optie "Vanaf" is alleen toegestaan bij tekst- en datumvelden. Optie "Klein" alleen bij tekstvelden. |

Scenario:   5.  Zoekcriterium Optie = Vanaf exact, Zoekcriterium element = Alfanummeriek
                LT: R2281_LT05
                Doel: Vaststellen dat de zoek dienst een correcte resultaat geeft met
                zoekcriterium optie Vanaf exact en een element van het type alfanummeriek
                Verwacht resultaat:
                - Zoek persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2252
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jan

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   6.  Zoekcriterium Optie = Vanaf klein, Zoekcriterium element <> alfanummeriek
                LT: R2281_LT06
                Doel: Vaststellen dat de zoek dienst een correcte melding geeft met
                zoekcriterium optie Vanaf klein en een element ongelijk het type alfanummeriek of datum
                Verwacht resultaat:
                - Zoek persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Adres.Huisnummer,Waarde=1

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                             |
| R2281     | Optie "Vanaf" is alleen toegestaan bij tekst- en datumvelden. Optie "Klein" alleen bij tekstvelden. |

Scenario:   7.  Zoekcriterium Optie = Vanaf exact, Zoekcriterium element <> alfanummeriek of datum
                LT: R2281_LT07
                Doel: Vaststellen dat de Vaststellen dat de zoek dienst een
                correcte melding geeft als
                zoekcriterium optie is Vanaf exact en het element is niet van het type datum of alfanummeriek
                Verwacht resultaat:
                - Zoek persoon foutief
                - Melding: 	Optie "Vanaf" is alleen toegestaan bij tekst- en datumvelden. Optie "Klein" alleen bij tekstvelden.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf exact,ElementNaam=Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Waarde,Waarde=N

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                             |
| R2281     | Optie "Vanaf" is alleen toegestaan bij tekst- en datumvelden. Optie "Klein" alleen bij tekstvelden. |

Scenario:   8.  Zoekcriterium Optie = Vanaf exact, Zoekcriterium element = datum
                LT: R2281_LT08, R2293_LT04
                Doel: Vaststellen dat de zoek dienst correcte resultaten
                geeft als zoekcriterium optie is Vanaf exact
                en het element is van het type datum
                Verwacht resultaat:
                - Zoek persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf exact,ElementNaam=Persoon.Geboorte.Datum,Waarde=2015-01-01

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 1 groepen 'persoon'


Scenario:   8.1 Zoekcriterium Optie = Vanaf klein, Zoekcriterium element = geboortedatum (deels onbekend 0)
                LT: R2281_LT03, R2293_LT04
                Verwacht resultaat:
                - Zoek persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Geboorte.Datum,Waarde=0
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jan

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 1 groepen 'persoon'

!-- Extra test variatie scenario's rondom gedeeltelijk onbekende datums

Scenario:   8.2 Zoekcriterium Optie = Vanaf klein, Zoekcriterium element = geboortedatum (deels onbekend 1960)
                LT: R2281_LT03, R2293_LT04
                Verwacht resultaat:
                - Zoek persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Geboorte.Datum,Waarde=1960
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jan

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 1 groepen 'persoon'

Scenario:   8.3 Zoekcriterium Optie = Vanaf klein, Zoekcriterium element = geboortedatum (deels onbekend 1960-00)
                LT: R2281_LT03, R2293_LT04
                Verwacht resultaat:
                - Zoek persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Geboorte.Datum,Waarde=1960-00
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jan

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 1 groepen 'persoon'


Scenario:   8.4 Zoekcriterium Optie = Vanaf klein, Zoekcriterium element = geboortedatum (deels onbekend 1960-01)
                LT: R2281_LT03, R2293_LT04
                Verwacht resultaat:
                - Zoek persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Geboorte.Datum,Waarde=1960-01
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jan

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 1 groepen 'persoon'


Scenario:   8.5 Zoekcriterium Optie = Vanaf klein, Zoekcriterium element = geboortedatum (deels onbekend 1960-01-00)
                LT: R2281_LT03, R2293_LT04
                Verwacht resultaat:
                - Zoek persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Geboorte.Datum,Waarde=1960-01-00
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jan

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 1 groepen 'persoon'


Scenario:   8.6 Zoekcriterium Optie = Vanaf klein, Zoekcriterium element = geboortedatum (deels onbekend 19601)
                LT: R2281_LT03, R2293_LT04
                Verwacht resultaat:
                - Zoek persoon foutief

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Geboorte.Datum,Waarde=19601
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jan

Then heeft het antwoordbericht verwerking Foutief


Scenario:   8.7 Zoekcriterium Optie = Vanaf klein, Zoekcriterium element = geboortedatum (deels onbekend 1960-01-111)
                LT: R2281_LT03, R2293_LT04
                Verwacht resultaat:
                - Zoek persoon foutief

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Geboorte.Datum,Waarde=1960-01-111
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jan

Then heeft het antwoordbericht verwerking Foutief


Scenario:   8.8 Zoekcriterium Optie = Vanaf klein, Zoekcriterium element = geboortedatum (deels onbekend 196-01-11)
                LT: R2281_LT03, R2293_LT04
                Verwacht resultaat:
                - Zoek persoon foutief

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Geboorte.Datum,Waarde=196-01-11
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jan

Then heeft het antwoordbericht verwerking Foutief
