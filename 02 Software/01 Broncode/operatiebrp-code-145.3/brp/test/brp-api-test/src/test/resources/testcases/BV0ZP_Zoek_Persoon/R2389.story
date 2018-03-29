Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2389

Narrative:
Voor alle zoekcriteria in een zoekvraag moet gelden dat:
Zoekcriterium.Element mag geen deel uitmaken van een 'Verantwoordingsgroep' (R1541) of een 'Onderzoeksgroep' (R1543).

Scenario: 1.    Zoek persoon op verantwoordingsgroep
                LT: R2389_LT01
                Verwacht resultaat:
                - Foutmelding R2389: 	Zoeken op verantwoording of onderzoek is niet toegestaan.

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

Scenario: 2.1   Zoek persoon op onderzoeksgroep
                LT: R2389_LT02
                Verwacht resultaat:
                - Foutmelding R2389: 	Zoeken op verantwoording of onderzoek is niet toegestaan.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=GegevenInOnderzoek.ObjectSleutelGegeven,Waarde=23

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                      |
| R2389     | Zoeken op verantwoording of onderzoek is niet toegestaan.    |

Scenario: 2.2   Zoek persoon op onderzoeksgroep
                LT: R2389_LT02
                Verwacht resultaat:
                - Foutmelding R2389: 	Zoeken op verantwoording of onderzoek is niet toegestaan.

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