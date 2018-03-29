Meta:
@status             Klaar
@usecase            LV.1.CPI
@regels             R1539
@sleutelwoorden     Zoek Persoon
@versienummer       7

Narrative:
Het systeem levert alleen niet vervallen personen (eis: Persoon.Soort = "Ingeschrevene" (I)
en Persoon.Nadere bijhoudingsaard <> "Fout" (F) of "Onbekend" (?))

Vanuit het perspectief van een gebruiker (afnemer of bijhouder) bezien bestaan deze personen dus niet.
Ontsluiting is slechts mogelijk via de beheerder.

Scenario:   1.  Hoofdpersoon heeft nadere.bijhoudingsaard Overleden (O)
                LT: R1539_LT04
                Verwacht resultaat:
                - Plaats afnemerindicatie succesvol

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06aC10T20_xls

Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|201573337

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut                 | verwachteWaarde |
| identificatienummers 	| 1         | burgerservicenummer       | 201573337       |
| bijhouding           	| 1         | nadereBijhoudingsaardCode | O               |

Scenario:   2.  Hoofdpersoon heeft nadere.bijhoudingsaard Ministerieel Besluit (M)
                LT: R1539_LT06
                Verwacht resultaat:
                - Plaats afnemerindicatie succesvol

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_nadere_bijhoudingsaard_ministerieel_besluit_xls

Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut                 | verwachteWaarde |
| identificatienummers 	| 1         | burgerservicenummer       | 606417801       |
| bijhouding           	| 1         | nadereBijhoudingsaardCode | M               |

Scenario:   3.  Hoofdpersoon heeft nadere.bijhoudingsaard Fout (F)
                LT: R1539_LT07
                Verwacht resultaat:
                - R1403
                - Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.
                Uitwerking:
                - Dienst plaats afnemerindicatie

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_nadere_bijhoudingsaard_fout_xls

Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE   | MELDING                                                                                       |
| R1403  | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.        |

Then is er geen synchronisatiebericht gevonden

Scenario:   4.  Hoofdpersoon heeft nadere.bijhoudingsaard Fout (F)
                LT: R1539_LT09
                Verwacht resultaat:
                - R1403
                - Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.
                Uitwerking:
                - Dienst Synchroniseer Persoon

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Jan_nadere_bijhoudingsaard_fout_xls

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE   | MELDING                                                                                       |
| R1403  | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.        |

Then is er geen synchronisatiebericht gevonden


Scenario:   5.  Hoofdpersoon heeft nadere.bijhoudingsaard Fout (F)
                LT: R1539_LT10
                Verwacht resultaat:
                - R1403
                - Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.
                Uitwerking:
                - Dienst Geef details persoon

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Jan_nadere_bijhoudingsaard_fout_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE   | MELDING                                                                                       |
| R1403  | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.        |

Then is er geen synchronisatiebericht gevonden

Scenario:   6.  Hoofdpersoon heeft nadere.bijhoudingsaard Fout (F)
                LT: R1539_LT11
                Verwacht resultaat:
                - R1403
                - Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.
                Uitwerking:
                - Dienst Verwijder afnemerindicatie

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_nadere_bijhoudingsaard_fout_xls

Given verzoek verwijder afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE   | MELDING                                                                                       |
| R1403  | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.        |

Then is er geen synchronisatiebericht gevonden

Scenario: 7.    Plaatsing afnemerindicatie bij Persoon.naderebijhoudingsaard = Gewist (W)
                LT: R1539_LT15
                Verwacht Resultaat: Verwerking Foutief

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls

Given blob voor persoon 606417801 en attribuut Persoon.Bijhouding.NadereBijhoudingsaardCode is aangepast met waarde W

Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE   | MELDING                                                                                       |
| R1403  | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.        |


Scenario: 9.    Synchroniseer persoon Persoon.naderebijhoudingsaard = Gewist (W)
                LT: R1539_LT17
                Verwacht Resultaat: Verwerking Foutief

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Jan_xls

Given blob voor persoon 606417801 en attribuut Persoon.Bijhouding.NadereBijhoudingsaardCode is aangepast met waarde W

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE   | MELDING                                                                                       |
| R1403  | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.        |

Scenario: 10.   Geef details persoon Persoon.naderebijhoudingsaard = Gewist (W)
                LT: R1539_LT18
                Verwacht Resultaat: Verwerking Foutief

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls

Given blob voor persoon 606417801 en attribuut Persoon.Bijhouding.NadereBijhoudingsaardCode is aangepast met waarde W

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE   | MELDING                                                                                       |
| R1403  | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.        |

Scenario: 11.   Geef medebewoners Persoon.naderebijhoudingsaard = Gewist (W)
                LT: R1539_LT19
                Verwacht Resultaat: Verwerking Foutief

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls

Given blob voor persoon 606417801 en attribuut Persoon.Bijhouding.NadereBijhoudingsaardCode is aangepast met waarde W

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|burgerservicenummer|'606417801'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE   | MELDING                                                                                       |
| R1403  | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.        |

Scenario: 12.   Zoek persoon op adres Persoon.naderebijhoudingsaard = Gewist (W)
                LT: R1539_LT20
                Verwacht Resultaat: Verwerking Geslaagd, maar persoon niet gevonden

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls

Given blob voor persoon 606417801 en attribuut Persoon.Bijhouding.NadereBijhoudingsaardCode is aangepast met waarde W

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=1
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2000AA

Then heeft het antwoordbericht verwerking Geslaagd
