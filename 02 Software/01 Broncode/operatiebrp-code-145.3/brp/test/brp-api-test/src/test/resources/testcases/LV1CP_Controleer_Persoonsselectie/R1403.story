Meta:
@status             Klaar
@usecase            LV.1.CPI
@regels             R1403
@sleutelwoorden     Controleer Persoonsselectie


Narrative:
In het Bericht moet het opgegeven Identiteitsnummer (R2191) minstens één te leveren persoon identificeren binnen de
Totale populatiebeperking (R2059)
(rekening houdend met R1538 - Alleen persoonslijsten van ingeschreven personen kunnen geleverd worden. en
R1539 - Alleen niet-vervallen personen kunnen geleverd worden).


Scenario:   1.  GeefDetailsPersoon service identificatienummer BSN evalueert niet naar een uniek persoon binnen de populatiebeperking,
                LT: R1403_LT02
                omdat er geen BSN gevonden wordt binnen de leveringsautorisatie.
                Verwacht resultaat: Foutmelding: Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.

Given alle personen zijn verwijderd
Given leveringsautorisatie uit autorisatie/GeefDetailsPersoon_PopulatieBeperking

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'geefDetailsPersoonPopulatieBeperking'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|159247913

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                                |
| R1403 | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie. |


Scenario: 2.    Geef details persoon op basis van ANR, er wordt 1 persoon gevonden binnen de pop.bep.
                LT:R1403_LT03
                Verwacht resultaat:
                - Persoon wordt geleverd

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|anr|5398948626

Then heeft het antwoordbericht verwerking Geslaagd
Then is er voor xpath //brp:identificatienummers[brp:administratienummer = '5398948626'] een node aanwezig in het antwoord bericht

Scenario: 3.    Geef details persoon op basis van ANR, er wordt GEEN persoon gevonden binnen de pop.bep.
                LT:R1403_LT04
                Verwacht resultaat:
                - Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|anr|2829195937

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                                |
| R1403 | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie. |

Scenario: 4.    PlaatsAfnemerIndicatie service identificatienummer BSN evalueert niet naar een uniek persoon,
                LT: R1403_LT07
                omdat er geen BSN gevonden wordt binnen de leveringsautorisatie.
                Verwacht resultaat: Foutmelding: Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.

Given leveringsautorisatie uit autorisatie/R1403_PopulatieBeperking
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'R1403PersoonPopulatieBeperking'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|111111110

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                                |
| R1403 | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie. |


Scenario: 5.    Verwijderen afnemerindicatie service identificatienummer BSN evalueert niet naar een uniek persoon binnen de populatiebeperking,
                LT: R1403_LT08
                omdat de persoon niet binnen de totale populatiebeperking valt.
                Verwacht resultaat: Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie.

Given leveringsautorisatie uit autorisatie/R1403_PopulatieBeperking
!-- Verhuizen van Piet zodat deze buiten de Pop bep van de toegang levsautorisatie voor Haarlem valt
Given persoonsbeelden uit specials:specials/ElisaBeth_Haarlem_Beverwijk_xls
!-- R1403_LT02 Elisabeth valt buiten de totale populatie beperking van de autorisatie voor gemeente Haarlem
Given verzoek verwijder afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'R1403PersoonPopulatieBeperking'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|270433417

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                                               |
| R1401 | Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie.  |

Scenario: 6.    Synchroniseer persoon op basis van doelbinding expressie populatiebeperking evalueert op NULL
                LT:  R1403_LT09
                Verwacht resultaat:  populatie beperking evalueert op NULL wat weer evalueert naar ONWAAR
                -Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Pop_bep_geboortedatum_Syn
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Pop bep geboortedatum Syn'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE   | MELDING                                                                                       |
| R1403  | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie.        |

Then is er geen synchronisatiebericht gevonden