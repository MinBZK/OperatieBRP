Meta:
@status             Klaar
@usecase            LV.0.MB
@sleutelwoorden     Maak BRP bericht
@regels             R1976

Narrative:
Het te leveren resultaat bevat alleen Objecten die, binnen hun berichtdeel, enig geautoriseerd Attribuut kunnen bevatten.

De attribuut-autorisatie staat beschreven in R1974 - Alleen attributen leveren waarvoor autorisatie bestaat.

Toelichting: De attribuut-autorisatie van een Dienstbundel wordt beschreven door de voorkomens van Dienstbundel \ Groep \ Attribuut.
Het Object neemt een bepaalde plek in binnen het bericht, waarbij een XSD beschrijft welke inhoud (een hiërarchische structuur van andere Objecten,
Groepen en Attributen) er binnen dat Object in het bericht aanwezig mogen zijn.
Als er binnen die hiërarchische structuur geen enkel Attribuut te vinden is waarvoor autorisatie bestaat,
dan kunnen we daaruit afleiden dat er ook geen autorisatie voor dit Object bestaat op deze plek in het bericht.
We filteren dit Object (en de hele structuur er onder) dan weg uit het bericht.

Noot 1: Of de betreffende attributen daadwerkelijk voorkomen in het bewuste bericht is dus niet relevant.
Als bijvoorbeeld een afnemer geautoriseerd is voor attribuut "GerelateerdeOuder.OuderlijkGezag?" dan filteren we Object GerelateerdeOuder niet weg,
zelfs als de groep "Ouderlijk Gezag" niet daadwerkelijk voorkomt in het uiteindelijke bericht.

Noot 2: Deze hiërarchie voor deze afleiding verloopt dus via de structuur van het bericht!
Bijvoorbeeld: in een VolledigBericht bevat de betrokkenheid Kind van de hoofdpersoon de objecten FamilierechtelijkeBetrekking, Ouder en Persoon
('GerelateerdeOuder.Persoon'). Als de afnemer geautoriseerd is voor het Geboorteland van de ouder,
dan wordt het object betrokkenheid Kind op grond daarvan niet verwijderd uit het bericht.
De betrokkenheid Kind van zijn eigen kinderen bevatten binnen het bericht alleen Persoon ('GerelateerdKind').
Als de afnemer ook geautoriseerd is voor het BSN van het kind, dan wordt om die redenen de betrokkenheid Kind niet weggefilterd.
De afleiding is voor betrokkenheid Kind dus op verschillende plekken in de berichtstructuur verschillend qua uitwerking.

Scenario:   1. Indien een object over een of meerdere geautoriseerde attributen beschikt, die lager in de hierarchie zitten, dan mag dit object NIET gefiltert worden.
               LT: R1976_LT01
               Verwacht resultaat: Object geautoriseerd en leveren in bericht

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Abo_met_1_attribuut_voor_GerelateerdeOuder.Persoon.Identificatienummers

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Abo met 1 attribuut voor GerelateerdeOuder.Persoon.Identificatienummers'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo met 1 attribuut voor GerelateerdeOuder.Persoon.Identificatienummers is ontvangen en wordt bekeken

!-- BSN 1 is hoofdpersoon Jan, BSN 2 is vader Danny Jansen, BSN 3 is moeder Rita Ora
Then hebben attributen in voorkomens de volgende waardes:
| groep                | nummer | attribuut           | verwachteWaarde |
| identificatienummers | 1      | burgerservicenummer | 606417801       |
| identificatienummers | 2      | burgerservicenummer | 463095145       |
| identificatienummers | 3      | burgerservicenummer | 823306185       |

Then heeft het bericht 3 groepen 'persoon'

Then is het synchronisatiebericht gelijk aan expecteds/R1976_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   2.1 Indien een object NIET over een of meerdere geautoriseerde attributen beschikt, die lager in de hierarchie zitten, dan mag dit object WEL gefiltert worden.
                LT: R1976_LT02, R1976_LT03
                Verwacht resultaat:
                - Object (ouder) NIET geautoriseerd en uit bericht verwijdert
                - Betrokkenheid Kind wel gevuld onder de hoofdpersoon, ondanks dat zijn ouders worden weggefilterd.
                - Betrokkenheid partner NIET gevuld onder de hoofdpersoon, want niet in bericht en GEEN autorisatie


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Abo_met_1_attribuut_voor_GerelateerdeOuder.OuderlijkGezag

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Abo met 1 attribuut voor GerelateerdeOuder.OuderlijkGezag'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo met 1 attribuut voor GerelateerdeOuder.OuderlijkGezag is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep                | nummer | attribuut           | verwachteWaarde |
| identificatienummers | 1      | burgerservicenummer | 606417801       |

!-- R1976_LT02 Familierechtelijke betrekking wordt NIET geleverd voor betrokkenheden partner onder de hoofdpersoon,
!-- omdat er geen partner is en geen autorisatie voor bestaat
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep     | nummer | attribuut | aanwezig |
| partner   | 1      | persoon   | nee      |

!-- R1976_LT03 Familierechtelijke betrekking wordt geleverd voor betrokkenheden ouders onder de hoofdpersoon,
!-- ondanks dat de ouders weggefilterd worden
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep | nummer | attribuut | aanwezig |
| ouder | 1      | persoon   | nee      |
| ouder | 2      | persoon   | nee      |


Then is het synchronisatiebericht gelijk aan expecteds/R1976_expected_scenario_2.xml voor expressie //brp:lvg_synVerwerkPersoon