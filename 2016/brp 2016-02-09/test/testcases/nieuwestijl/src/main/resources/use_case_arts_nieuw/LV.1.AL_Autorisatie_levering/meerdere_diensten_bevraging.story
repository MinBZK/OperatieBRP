Meta:
@sprintnummer           91
@epic                   Autenticatie levering
@auteur                 aapos
@jiraIssue              TEAMBRP-4478
@status                 Klaar
@regels                 R1260,R1261

Narrative:
Als afnemer wil ik meerdere diensten voor bevraging kunnen gebruiken binnen de zelfde leveringsautorisatie
Let op! (Nadere) populatiebeperking werkt niet voor bevraging. Test valideert nu enkel dat de autorisaties voor de verschillende diensten kunnen afwijken.


Scenario:   1. Bevraging persoon die valt binnen NPB van de strikte bevraging

Given leveringsautorisatie uit /levering_autorisaties/meerdere_diensten_bevraging

Given de database is aangepast met: update autaut.dienst set id=2222 where dienstbundel=(select id from autaut.dienstbundel where naam='Abo GeefDetailsPersoon Breed');
Given de database is aangepast met: update autaut.dienst set id=1111 where dienstbundel=(select id from autaut.dienstbundel where naam='Abo GeefDetailsPersoon Strikt');

Given de cache is herladen
Given de personen 627129705, 304953337, 622578121 zijn verwijderd
Given de standaardpersoon Olivia met bsn 622578121 en anr 4158386194 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'meerdere autorisaties voor bevraging' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand geefDetailsPersoon_strikt.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes |
| identificatienummers | burgerservicenummer | 622578121        |

Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'identificatienummers' nummer 1 nee

Scenario:   2. Bevraging persoon die valt binnen NPB van de brede bevraging

Given de personen 175476457 zijn verwijderd
Given de standaardpersoon Remi_leerplichtig met bsn 175476457 en anr 3071323026 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'meerdere autorisaties voor bevraging' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand geefDetailsPersoon_breed.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes |
| identificatienummers | burgerservicenummer | 175476457        |

Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'identificatienummers' nummer 1 ja
