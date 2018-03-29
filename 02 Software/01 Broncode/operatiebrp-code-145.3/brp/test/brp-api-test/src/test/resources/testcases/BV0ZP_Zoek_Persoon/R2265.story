Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2265

Narrative:
De inhoud van Attribuut in Bericht \ Zoekcriteria.Zoekcriterium dient een Element.Element naam van een Element met:
Element is Geldig voorkomen stamgegeven op peilmoment (R1284) op 'Systeemdatum' (R2016)
EN
Element.Soort gelijk aan "Attribuut"
EN
Element.Autorisatie gelijk aan "Optioneel", "Verplicht", "Aanbevolen", "Uitzondering" of "Bijhoudingsgegevens"

Scenario:   1. Zoekcriteria elementnaam bevat een geldige waarde welke gelijk is aan Elementsoort attribuut (3) en Element.autorisatie = Optioneel (3).
            LT: R2265_LT01
            Verwacht resultaat:
            - Zoek persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Vanaf klein,ElementNaam=Persoon.Overlijden.BuitenlandsePlaats,Waarde=Woerd

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   2. Zoekcriteria elementnaam bevat een geldige waarde welke gelijk is aan Elementsoort attribuut (3) en Element.autorisatie = verplicht (5).
            LT: R2265_LT02
            Verwacht resultaat:
            - Zoek persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Bijhouding.NadereBijhoudingsaardCode,Waarde=A

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   3. Zoekcriteria elementnaam bevat een geldige waarde welke gelijk is aan Elementsoort attribuut (3) en Element.autorisatie = Aanbevolen (6).
            LT: R2265_LT03
            Verwacht resultaat:
            - Zoek persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=823306185

Then heeft het antwoordbericht verwerking Geslaagd


Scenario:   4.1 Zoekcriteria elementnaam bevat een geldige waarde welke gelijk is aan Elementsoort attribuut (3) en Element.autorisatie = Aanbevolen (6).
            LT: R2265_LT04
            Zoekcriteria elementnaam is niet opvraagbaar want soort elementautorisatie is UITZONDERING.
            Verwacht resultaat:
            - Zoek persoon geslaagd
            - R2542: Het als zoekcriterium opgegeven element is niet opvraagbaar

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SoortCode,Waarde=I

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                        |
| R2542    | Het als zoekcriterium opgegeven element is niet opvraagbaar    |

Scenario:   4.2 Zoekcriteria elementnaam bevat een geldige waarde welke gelijk is aan Elementsoort attribuut (3) en Element.autorisatie = Aanbevolen (6).
            LT: R2265_LT04
            Zoekcriteria elementnaam is niet opvraagbaar want soort elementautorisatie is UITZONDERING.
            Verwacht resultaat:
            - R2542: Het als zoekcriterium opgegeven element is niet opvraagbaar

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Voornaam.Volgnummer,Waarde=1

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING
| R2542    | Het als zoekcriterium opgegeven element is niet opvraagbaar    |

Scenario:   5. Zoekcriteria elementnaam bevat een geldige waarde welke gelijk is aan Elementsoort attribuut (3) en Element.autorisatie = Aanbevolen (6).
            LT: R2265_LT05
            Zoekcriteria elementnaam is verantwoordings- of onderzoek attribuut.
            Verwacht resultaat:
            - R2389: Zoeken op verantwoording of onderzoek is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Actie.DatumAanvangGeldigheid,Waarde=2015-01-01

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                      |
| R2389     | Zoeken op verantwoording of onderzoek is niet toegestaan.    |


Scenario:   6. Zoekcriteria elementnaam bevat een geldige waarde welke gelijk is aan Elementsoort attribuut (3) en Element.autorisatie = Aanbevolen (6).
            LT: R2265_LT06
            Zoekcriteria elementnaam is niet opvraagbaar want soort autorisatie is 'Via Groepsautorisatie'.
            Verwacht resultaat:
            - R2542: Het als zoekcriterium opgegeven element is niet opvraagbaar

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.TijdstipRegistratie,Waarde=20150101

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                         |
| R2542     | Het als zoekcriterium opgegeven element is niet opvraagbaar     |

Scenario:   7. Zoekcriteria elementnaam bevat een geldige waarde welke gelijk is aan Elementsoort attribuut (3) en Element.autorisatie = Aanbevolen (6).
            LT: R2265_LT07
            Zoekcriteria elementnaam is niet opvraagbaar want soort element autorisatie is 'Niet Verstrekken'.
            Verwacht resultaat:
            - R2542: Het als zoekcriterium opgegeven element is niet opvraagbaar

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.AfgeleidAdministratief.Sorteervolgorde,Waarde=1

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                       |
| R2542     | Het als zoekcriterium opgegeven element is niet opvraagbaar   |

Scenario:   8. Zoekcriteria elementnaam bevat een geldige waarde welke gelijk is aan Elementsoort attribuut (3) en Element.autorisatie = Aanbevolen (6).
            LT: R2265_LT08
            Zoekcriteria elementnaam is niet opvraagbaar want soort element autorisatie is 'Structuur'
            Verwacht resultaat:
             - R2542: Het als zoekcriterium opgegeven element is niet opvraagbaar

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.ObjectSleutel,Waarde=23

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                       |
| R2542     | Het als zoekcriterium opgegeven element is niet opvraagbaar   |


Scenario:   9.  Zoeken op objecttype. Element = Geldig, Element.soort = Objecttype (1), Element.Autorisatie = Leeg
                LT: R2265_LT09
                Verwacht resultaat:
                - R2265: Ongeldig waarde voor attribuut in zoekcriterium: %attribuut%

Given leveringsautorisatie uit autorisatie/Zoek_Persoon

Given alle personen zijn verwijderd
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Voornaam,Waarde=Anne

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                          |
| R2265     | Ongeldig waarde voor attribuut in zoekcriterium: %attribuut%     |

Scenario:   10. Zoeken op groep. Element = Geldig, Element.soort = Groep (2), Element.Autorisatie = Leeg
                LT: R2265_LT10
                Verwacht resultaat:
                - R2265: Ongeldig waarde voor attribuut in zoekcriterium: %attribuut%

Given leveringsautorisatie uit autorisatie/Zoek_Persoon

Given alle personen zijn verwijderd
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Geslachtsaanduiding,Waarde=V

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                          |
| R2265     | Ongeldig waarde voor attribuut in zoekcriterium: %attribuut%     |


Scenario:   11.  Zoeken op objecttype. Element = Geldig, Element.Soort = 3, Element.Autorisatie = LEEG
                LT: R2265_LT13
                 Zoekcriteria elementnaam is niet opvraagbaar want niet bestaand
                Verwacht resultaat:
                - R2542: Het als zoekcriterium opgegeven element is niet opvraagbaar

Given leveringsautorisatie uit autorisatie/Zoek_Persoon

Given alle personen zijn verwijderd
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Element.ObjecttypeNaam,Waarde=Onderzoek

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                          |
| R2542    |  Het als zoekcriterium opgegeven element is niet opvraagbaar     |

Scenario:   12.  Zoeken op supertype. Element = Geldig, Element.Soort = 3, Element.Autorisatie = LEEG
                LT: R2265_LT13
                 Zoekcriteria elementnaam is niet opvraagbaar want niet bestaand
                Verwacht resultaat:
                - R2542: Het als zoekcriterium opgegeven element is niet opvraagbaar

Given leveringsautorisatie uit autorisatie/Zoek_Persoon

Given alle personen zijn verwijderd
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Relatie.DatumAanvang,Waarde=20100101

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                          |
| R2542    |  Het als zoekcriterium opgegeven element is niet opvraagbaar     |


Scenario:   13.  Zoeken op onderzoek omschrijving
                LT:
                Verwacht resultaat:
                - R2389: Zoeken op verantwoording of onderzoek is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon

Given alle personen zijn verwijderd
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Onderzoek.Omschrijving,Waarde=Conversie GBA: 081120

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                      |
| R2389     | Zoeken op verantwoording of onderzoek is niet toegestaan.    |


Scenario:   14.  Zoeken op Persoon Afnemerindicatie PartijCode
                LT:
                  Zoekcriteria elementnaam is niet opvraagbaar want niet uit Kern schema
                Verwacht resultaat:
                 - R2542: Het als zoekcriterium opgegeven element is niet opvraagbaar

Given leveringsautorisatie uit autorisatie/Zoek_Persoon

Given alle personen zijn verwijderd
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Afnemerindicatie.PartijCode,Waarde=03440

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                          |
| R2542    |  Het als zoekcriterium opgegeven element is niet opvraagbaar     |


