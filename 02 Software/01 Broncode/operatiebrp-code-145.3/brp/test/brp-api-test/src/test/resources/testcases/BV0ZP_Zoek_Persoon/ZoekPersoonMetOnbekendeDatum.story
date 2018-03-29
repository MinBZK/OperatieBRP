Meta:
@status             Klaar
@usecase            BV.0.ZP
@regels             R2308

Narrative:
Als BRP wil ik dat afnemers kunnen zoeken naar personen met (gedeeltelijk) onbekende datum(s)
waarbij de (gedeeltelijk) onbekende datum volgens de stelsel standaard.

Geraakte diensten:
Zoek Persoon
Zoek Persoon op adres

Scenario: 1.    Zoek persoon expliciet op gedeeltelijk onbekende datum (maand & dag onbekend)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=1998-00-00
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2.    Zoek persoon expliciet op gedeeltelijk onbekende datum (maand & dag onbekend)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=1998
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3.    Zoek persoon expliciet op gedeeltelijk onbekende datum (maand & dag onbekend)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=1998-00
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 4.    Zoek persoon expliciet op gedeeltelijk onbekende datum (dag onbekend YYYY-MM-00)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/ElisaBeth_Onbekende_GeboorteDag_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Geboorte.Datum,Waarde=1966-08-00
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 5.    Zoek persoon expliciet op gedeeltelijk onbekende datum (dag onbekend - YYYY-MM)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:MaakBericht/ElisaBeth_Onbekende_GeboorteDag_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Geboorte.Datum,Waarde=1966-08
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Elisa

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 6.    Zoek persoon expliciet op geheel onbekende datum (0000-00-00)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C10T110_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Verblijfsrecht.DatumAanvang,Waarde=0000-00-00
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Karel

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 7.    Zoek persoon expliciet op geheel onbekende datum (0000-00)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C10T110_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Verblijfsrecht.DatumAanvang,Waarde=0000-00
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Karel

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 8.    Zoek persoon expliciet op geheel onbekende datum (0000)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C10T110_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Verblijfsrecht.DatumAanvang,Waarde=0000
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Karel

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 9.    Zoek persoon op adres expliciet op geheel onbekende datum (0000)
                LT: R2308_LT04
                Verwacht resultaat:
                - Verwerking geslaagd

                NB: Extra test om te valideren dat (gedeeltelijk) onbekende datums als input geaccepteerd worden bij de dienst 'Zoek persoon op adres'

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
!-- Persoon met onbekende datum aanvang adreshouding
Given persoonsbeelden uit specials:specials/Anne_met_Historie_geheel_onb_dat_xls
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.IdentificatiecodeNummeraanduiding,Waarde=0626200010016003
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=0000

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 10.   Zoek persoon op adres met ongeldige onbekende datum (onbekende datum met dag gevuld)
                LT: R2308_LT05
                Verwacht resultaat:
                - Verwerking Foutief; De opgegeven waarde komt niet overeen met het datatype van het opgegeven element.


Given leveringsautorisatie uit autorisatie/Zoek_Persoon
!-- Persoon met onbekende datum aanvang adreshouding
Given persoonsbeelden uit specials:specials/Anne_met_Historie_geheel_onb_dat_xls
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.IdentificatiecodeNummeraanduiding,Waarde=0626200010016003
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.DatumAanvangAdreshouding,Waarde=0000-00-01

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                           |
| R2308 | De opgegeven waarde komt niet overeen met het datatype van het opgegeven element. |

Scenario: 11.   Zoek persoon expliciet op ongeldige geheel onbekende datum (obekende datum met wild cards)
                LT: R2308_LT05
                Verwacht resultaat:
                - Verwerking geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C10T110_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Verblijfsrecht.DatumAanvang,Waarde=0000-**-**
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Voornamen,Waarde=Karel

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                           |
| R2308 | De opgegeven waarde komt niet overeen met het datatype van het opgegeven element. |