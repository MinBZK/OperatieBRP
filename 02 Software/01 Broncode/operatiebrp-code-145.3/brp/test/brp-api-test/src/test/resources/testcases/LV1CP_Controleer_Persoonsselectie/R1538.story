Meta:
@status             Klaar
@usecase            LV.1.CPI
@regels             R1538
@sleutelwoorden     Zoek Persoon


Narrative:
Het systeem levert alleen persoonslijsten van ingeschreven personen (Persoon.Soort = "Ingeschrevene" (I)).

De overige personen ('pseudo personen') komen slechts voor als gerelateerde van een ingeschreven persoon en
worden alleen geleverd als onderdeel van de persoonslijst van een ingeschreven persoon.

Scenario:   1.  Hoofdpersoon is een Ingeschreve en gerelateerde is een pseudo-persoon.
                LT: R1538_LT02
                Verwacht resultaat:
                - hoofdpersoon gelevered, met pseudopersoon als gerelateerde
                Uitwerking:
                - Dienst Plaats afnemerindicatie
                - BSN hoofdpersoon Jan = 606417801
                - BSN Pseudo persoon vader Jan = 823306185

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
!-- R1538_LT02
Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| identificatienummers 	| 1         | burgerservicenummer   | 606417801       |
| identificatienummers 	| 3         | burgerservicenummer   | 823306185       |

Scenario:   2.  Plaats afnemerindicatie op pseudo-persoon vader Jan
                LT: R1538_LT03
                Verwacht resultaat:
                - Geen levering
                Uitwerking:
                - Dienst Plaats afnemerindicatie
                - BSN hoofdpersoon Jan = 606417801
                - BSN Pseudo persoon vader Jan = 823306185

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|823306185

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                                |
| R1403 | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie. |

Scenario:   3.  Synchroniseer persoon op pseudo-persoon vader Jan
                LT: R1538_LT04
                Verwacht resultaat:
                - Geen levering
                Uitwerking:
                - Synchroniseer persoon
                - BSN hoofdpersoon Jan = 606417801
                - BSN Pseudo persoon vader Jan = 823306185

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|823306185

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                                |
| R1403 | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie. |

Scenario:   4.  Geef details persoon op pseudo-persoon vader Jan
                LT: R1538_LT05
                Verwacht resultaat:
                - Geen levering
                Uitwerking:
                - Geef details persoon
                - BSN hoofdpersoon Jan = 606417801
                - BSN Pseudo persoon vader Jan = 823306185

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|823306185

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                                |
| R1403 | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie. |

