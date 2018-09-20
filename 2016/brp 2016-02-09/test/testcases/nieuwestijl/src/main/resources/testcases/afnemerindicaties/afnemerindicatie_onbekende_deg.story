Meta:
@sprintnummer           74
@epic                   Mutatielevering basis
@jiraIssue              TEAMBRP-1981
@auteur                 anjaw
@status                 Klaar
@regels                 R1283
@sleutelwoorden         Plaatsing afnemerindicatie, Onderhouden afnemerindicatie, onbekende datum, datum einde geldigheid

Narrative:
    Controleren of onbekende DEG correct wordt geinterpreteerd bij de datum aanvang materiele periode. De testpersonen
    binnen deze story zijn te vinden in de Excels 'SierraTestdata-PersoonMetOnbekendeDatums.xls' (scenarios 1,2,3) en
    'SierraTestdata-KUC055-Correctie adres.xls' (scenario 4).
    Scenario 1 controleert of alle (gedeeltelijk) onbekende datums worden getoond, want de datum
    die voor datum aanvang materiele periode wordt meegegeven is voor alle voorkomens van voornaam geldig.
    Scenario 2 heeft een afnemerindicatie met datum aanvang materiele periode waardoor het voorkomen met onbekende dag
    niet meer geldig is.
    Scenario 3 heeft een afnemerindicatie met datum aanvang materiele periode waardoor het voorkomen met onbekende maand
    niet meer geldig is.
    Scenario 4 is nog niet af en bedoeld voor het testen van een mutatielevering. Deze functionaliteit komt nog aan bod.


Scenario: 1. Plaats afnemerindicatie op persoon met onbekende datum einde geldigheid en alles geldig

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de database is gereset voor de personen 360381169

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand afnemerindicatie_onbekende_deg_1.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
groep 		                | attribuut             | verwachteWaardes
voornaam                    | naam                  | Joop,Jaap,Jopie,Joop,Jaap,Jopie,Joop
voornaam                    | datumEindeGeldigheid  | 0000,1984,1984-08

Scenario: 2. Plaats afnemerindicatie op persoon met onbekende datum einde geldigheid onbekende dag niet meer geldig

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de database is gereset voor de personen 360381169

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand afnemerindicatie_onbekende_deg_2.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
groep 		                | attribuut             | verwachteWaardes
voornaam                    | naam                  | Joop,Jaap,Jopie,Jaap,Jopie,Joop
voornaam                    | datumEindeGeldigheid  | 0000,1984

Scenario: 3. Plaats afnemerindicatie op persoon met onbekende datum einde geldigheid onbekende maand niet meer geldig

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de database is gereset voor de personen 360381169

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand afnemerindicatie_onbekende_deg_3.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
groep 		                | attribuut             | verwachteWaardes
voornaam                    | naam                  | Joop,Jaap,Jaap,Jopie,Joop
voornaam                    | datumEindeGeldigheid  | 0000

Scenario: 4. Plaats afnemerindicatie op persoon en krijg vervolgens mutatielevering over adrescorrectie
Meta:
@status     Onderhanden

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de database is gereset voor de personen 527163703

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand afnemerindicatie_onbekende_deg_4.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
Given administratieve handeling van type correctieAdres , met de acties correctieAdres
And testdata uit bestand afnemerindicatie_onbekende_deg_5.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
