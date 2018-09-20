Meta:
@auteur                 dihoe
@sprintnummer           63
@epic                   Mutatielevering op basis van afnemerindicatie
@jiraIssue              TEAMBRP-1797
@regels                 VR00093,R1554
@status                 Uitgeschakeld

Narrative:
    ** Na de BMR wijziging versie 42 van 15 september 2015 is deze story Uitgeschakeld       **
    ** Vervallen: Persoon.AfgeleidAdministratief.IndicatieOnderzoekNaarNietOpgenomenGegevens **
    As a afnemer
    I want to geen mutatiebericht ontvangen waarin alleen tijdstip laatste wijziging is aangepast
    So that I niet nodeloos berichten ontvang als er voor mij geen interessante informatie in staat

Scenario: 1. Afnemer ontvangt wel mutatiebericht indien tijdstip laatste wijziging en indicatieOnderzoekNaarNietOpgenomenGegevens is aangepast
Persoon staat in tabel kern.his_onderzoekafgeleidadminis, indicatieOnderzoekNaarNietOpgenomenGegevens is J

Given leveringsautorisatie uit /levering_autorisaties/R1554_levering_van_alleen_afgeleidadministratief
Given de database is gereset voor de personen 350019344
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verkrijgingReisdocument , met de acties registratieReisdocument
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het mutatiebericht voor leveringsautorisatie R1554-levering van alleen afgeleidadministratief is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                  | attribuut                                   | verwachteWaardes |
| afgeleidAdministratief | indicatieOnderzoekNaarNietOpgenomenGegevens | N,J              |

Scenario: 2. Afnemer ontvangt geen mutatiebericht indien alleen tijdstip laatste wijziging is aangepast

Given de personen 727750537,212208937,185786601 zijn verwijderd
Given de standaardpersoon Matilda met bsn 185786601 en anr 3841283090 zonder extra gebeurtenissen

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
Given administratieve handeling van type verkrijgingReisdocument , met de acties registratieReisdocument
And testdata uit bestand geen_mutatiebericht_waarin_alleen_tijdstip_laatste_wijziging_is_aangepast_02.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het mutatiebericht voor leveringsautorisatie R1554-levering van alleen afgeleidadministratief is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden





