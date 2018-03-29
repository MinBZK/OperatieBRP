Meta:
@status             Klaar
@usecase            SA.0.PA, SA.0.VA

Narrative:
Met deze dienst is het voor de afnemer mogelijk om aan de hand van een identificerend kenmerk een persoon toe te voegen aan de doelgroep van de leveringsautorisatie.
De afnemer ontvangt direct (synchroon) een bevestiging van het plaatsen van de afnemerindicatie.
De afnemer ontvangt (asynchroon) een zogenaamd Volledig bericht voor de persoon.
Met deze gegevens kan de afnemer de persoon opnemen in de eigen administratie.


Scenario:   1. Plaats afnemerindicatie bij Persoon door afnemer Gemeente Standaard
            LT: R1266_LT03
            UC: SA.0.PA
            Verwacht resultaat: Afnemer indicatie geplaatst, vul bericht aan afnemer


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_1.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

!-- R1266_LT01 Controle op responsebericht
Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

When alle berichten zijn geleverd

Then is er een volledigbericht ontvangen voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie

Then is er voor persoon met bsn 422531881 en leveringautorisatie Geen pop.bep. levering op basis van afnemerindicatie en partij Gemeente Standaard en soortDienst PLAATSING_AFNEMERINDICATIE een afnemerindicatie geplaatst

Scenario:   2. Verwijder afnemerindicatie bij Persoon
            LT: R1266_LT03, R1270_LT11
            UC: SA.0.VA
            Verwacht resultaat: Afnemer indicatie bij persoon voor partij verwijderd, geen vul bericht aan afnemer

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_2.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

!-- R1266_LT03 Controle op responsebericht
Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

When alle berichten zijn geleverd
Then zijn er geen berichten ontvangen

!-- R1270_LT11
Then is er gearchiveerd met de volgende gegevens:
| veld         | waarde                                        |
| bsn          | 422531881                                     |

Scenario:   3. Opnieuw plaatsen afnemerindicatie bij Persoon
            UC: SA.0.PA
            Verwacht resultaat: Afnemer indicatie geplaatst, vul bericht aan afnemer

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_3.1.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

When alle berichten zijn geleverd

Then is er een volledigbericht ontvangen voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie

!-- Synchroniseer persoon om te valideren dat de afnemerindicatie weer een actueel voorkomen betreft
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_3.2.xml

Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht verwerking Geslaagd


Scenario:   4a. Plaats afnemerindicatie bij persoon door Partij A
                LT: R1408_LT01
               - Beide partijen hebben via dezelfde leveringsautorisatie een toegang
               Verwacht resultaat: Voor beide voorkomens van Persoon.Afnemerindicatie heeft 'Indicatie actueel en geldig' de waarde 'TRUE'

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_4a.xml
Then heeft het antwoordbericht verwerking Geslaagd

!-- Voor gemeente Standaard is een actueel en geldig voorkomen van persoon.afnemerindicatie
Then in autaut heeft select indag from autaut.persafnemerindicatie where pers=(select id from kern.pers where bsn='606417801') AND levsautorisatie=(select id from autaut.levsautorisatie where naam='levering op basis van afnemerindicatie') AND partij=32002 de volgende gegevens:
| veld  | waarde |
| indag | true   |

Scenario:   4b. Plaats afnemerindicatie bij persoon door Partij B
                LT: R1408_LT01

Given verzoek voor leveringsautorisatie 'levering op basis van afnemerindicatie' en partij 'Gemeente Standaard2'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_4b.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- Voor gemeente Standaard is een actueel en geldig voorkomen van persoon.afnemerindicatie
Then in autaut heeft select indag from autaut.persafnemerindicatie where pers=(select id from kern.pers where bsn='606417801') AND levsautorisatie=(select id from autaut.levsautorisatie where naam='levering op basis van afnemerindicatie') AND partij=32002 de volgende gegevens:
| veld  | waarde |
| indag | true   |

!-- Voor gemeente Standaard2 is een actueel en geldig voorkomen van persoon.afnemerindicatie
Then in autaut heeft select indag from autaut.persafnemerindicatie where pers=(select id from kern.pers where bsn='606417801') AND levsautorisatie=(select id from autaut.levsautorisatie where naam='levering op basis van afnemerindicatie') AND partij=32005 de volgende gegevens:
| veld  | waarde |
| indag | true   |


!-- additionele bevraging met GeefDetailsPersoon om de afnemerindicaties te valideren
Given verzoek voor leveringsautorisatie 'levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_4b2.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then is er voor xpath //brp:afnemerindicatie/brp:tijdstipRegistratie een node aanwezig in het antwoord bericht
Then is het antwoordbericht xsd-valide

Scenario:   5. Opnieuw plaatsen afnemerindicatie bij persoon door Partij A
               LT: R1408_LT01
               Verwacht resultaat: Verwerking foutief; afnemerindicatie voor afnemer reeds aanwezig bij persoon

Given verzoek voor leveringsautorisatie 'levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_5.xml

Then heeft het antwoordbericht verwerking Foutief

!-- Voor gemeente Standaard is een actueel en geldig voorkomen van persoon.afnemerindicatie
Then in autaut heeft select indag from autaut.persafnemerindicatie where pers=(select id from kern.pers where bsn='606417801') AND levsautorisatie=(select id from autaut.levsautorisatie where naam='levering op basis van afnemerindicatie') AND partij=32002 de volgende gegevens:
| veld  | waarde |
| indag | true   |


Scenario:   6. Verwijder afnemerindicatie bij persoon door Partij A
               LT: R1409_LT01
               Verwacht resultaat:
               - Voor voorkomen van Persoon.Afnemerindicatie voor afnemer A heeft 'Indicatie actueel en geldig' de waarde 'TRUE'
               - Voor voorkomen van Persoon.Afnemerindicatie voor afnemer B heeft 'Indicatie actueel en geldig' de waarde 'NULL' (FALSE)

Given verzoek voor leveringsautorisatie 'levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_6.xml
Then heeft het antwoordbericht verwerking Geslaagd

!-- Voor gemeente Standaard is GEEN actueel en geldig voorkomen van persoon.afnemerindicatie
Then in autaut heeft select indag from autaut.persafnemerindicatie where pers=(select id from kern.pers where bsn='606417801') AND levsautorisatie=(select id from autaut.levsautorisatie where naam='levering op basis van afnemerindicatie') AND partij=32002 de volgende gegevens:
| veld  | waarde |
| indag | false  |



Scenario:   7. Opnieuw Verwijder afnemerindicatie bij persoon door Partij A
               LT: R1409_LT01
               Verwacht resultaat:
               - Verwerking Foutief; Er is geen geldige afnemerindicatie gevonden bij de persoon

Given verzoek voor leveringsautorisatie 'levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_7.xml
Then heeft het antwoordbericht verwerking Foutief

!-- Voor gemeente Standaard is GEEN actueel en geldig voorkomen van persoon.afnemerindicatie
Then in autaut heeft select indag from autaut.persafnemerindicatie where pers=(select id from kern.pers where bsn='606417801') AND levsautorisatie=(select id from autaut.levsautorisatie where naam='levering op basis van afnemerindicatie') AND partij=32002 de volgende gegevens:
| veld  | waarde |
| indag | false  |


Scenario:   8. Verwijder afnemerindicatie bij persoon door Partij B
               LT: R1409_LT01
               Verwacht resultaat:
               - Voor voorkomen van Persoon.Afnemerindicatie voor afnemer A heeft 'Indicatie actueel en geldig' de waarde 'NULL' (FALSE)
               - Voor voorkomen van Persoon.Afnemerindicatie voor afnemer B heeft 'Indicatie actueel en geldig' de waarde 'NULL' (FALSE)

Given verzoek voor leveringsautorisatie 'levering op basis van afnemerindicatie' en partij 'Gemeente Standaard2'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_8.1.xml
Then heeft het antwoordbericht verwerking Geslaagd

!-- Voor gemeente Standaard is GEEN actueel en geldig voorkomen van persoon.afnemerindicatie
Then in autaut heeft select indag from autaut.persafnemerindicatie where pers=(select id from kern.pers where bsn='606417801') AND levsautorisatie=(select id from autaut.levsautorisatie where naam='levering op basis van afnemerindicatie') AND partij=32002 de volgende gegevens:
| veld  | waarde |
| indag | false  |


!-- Voor gemeente Standaard2 is GEEN actueel en geldig voorkomen van persoon.afnemerindicatie
Then in autaut heeft select indag from autaut.persafnemerindicatie where pers=(select id from kern.pers where bsn='606417801') AND levsautorisatie=(select id from autaut.levsautorisatie where naam='levering op basis van afnemerindicatie') AND partij=32005 de volgende gegevens:
| veld  | waarde |
| indag | false  |


!-- additionele bevraging met GeefDetailsPersoon om te valideren welke voorkomens van de afnemerindicaties mee komen
Given verzoek voor leveringsautorisatie 'levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_8.2.xml
Then heeft het antwoordbericht verwerking Geslaagd

Then is er voor xpath //brp:afnemerindicatie/brp:tijdstipRegistratie een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:afnemerindicatie/brp:tijdstipVerval een node aanwezig in het antwoord bericht

Scenario:  9. Opnieuw plaatsen afnemerindicatie bij persoon door Partij A
              LT:

!-- Opnieuw plaatsen afnemer indicatie bij persoon zodat er in geef details persoon ook een vervallen voorkomen mee komt
Given verzoek voor leveringsautorisatie 'levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_9.1.xml
Then heeft het antwoordbericht verwerking Geslaagd

Given verzoek voor leveringsautorisatie 'levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_9.2.xml
Then heeft het antwoordbericht verwerking Geslaagd

When alle berichten zijn geleverd
Then is er een volledigbericht ontvangen voor leveringsautorisatie levering op basis van afnemerindicatie

!-- additionele bevraging met GeefDetailsPersoon om te valideren welke voorkomens van de afnemerindicaties mee komen
Given verzoek voor leveringsautorisatie 'levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_9.3.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/expected/GDP_expected_scenario_9.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Then is het antwoordbericht xsd-valide


Scenario:   10. Attendering op wijziging in afnemerindicaties
                LT: R1402_LT02
            Attenderingscriterium: GEWIJZIGD(oud, nieuw, [Persoon.Afnemerindicatie.PartijCode])
            Verwacht resultaat: levering volledigbericht.
            Plaatsing dmv het attenderingscriterium heeft geen effect omdat er reeds een afnemerindicatie aanwezig is


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Expressietaal_afnemerindicaties' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_10.xml
Then heeft het antwoordbericht verwerking Geslaagd

When alle berichten zijn geleverd

Then zijn er per type bericht de volgende aantallen ontvangen:
| type                  | aantal
| volledigbericht       | 1
| mutatiebericht        | 0
