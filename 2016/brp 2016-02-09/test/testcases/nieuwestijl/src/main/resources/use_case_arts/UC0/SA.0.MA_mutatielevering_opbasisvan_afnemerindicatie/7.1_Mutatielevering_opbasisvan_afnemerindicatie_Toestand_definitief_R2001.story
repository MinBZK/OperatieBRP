Meta:
@epic               Verbeteren testtooling
@auteur             kedon
@status             Uitgeschakeld
@usecase            SA.0.MA
@regels             R1314,R1315,R1338,R1343,R1544,R1989,R1990,R1993,R1994,R2000,R2001,R2002
@sleutelwoorden     Mutatielevering o.b.v. afnemerindicatie

Narrative:
Mutatielevering op basis van Plaatsing afnemersindicatie:
De afnemer ontvangt voor de opgegeven Persoon een eerste Volledig bericht bij de plaatsing van de afnemerindicatie.
Zolang de afnemerindicatie nog geldig is worden alle geautoriseerde wijzigingen ontvangen via een mutatiebericht.

Scenario:   15.1 Plaatsen afnemerindicatie daarna verhuizing, Toestand 1 concept,
                  waardoor de afnemer GEEN mutatiebericht op basis van afnemerindicatie ontvangt
             Logische testgevallen Use Case: R2001_02
             Verwacht resultaat: Geen bericht

Given de personen 299054457, 743274313, 981661129 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 981661129 en anr 8545465106 zonder extra gebeurtenissen

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand 1.2_plaatsen_afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then ingaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004560
Then uitgaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004560

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   15.2 Plaatsen afnemerindicatie daarna verhuizing, Toestand 1 concept,
                 waardoor de afnemer GEEN mutatiebericht op basis van afnemerindicatie ontvangt
            Logische testgevallen Use Case: R2001_02
            Verwacht resultaat: Geen bericht

Given de database is aangepast met: update autaut.abonnement set toestand = 1 where id=5670002
Given relevante abonnementen Geen pop.bep. levering op basis van afnemerindicatie
Given de database is aangepast met: update autaut.abonnement set toestand = 4 where id=5670002

Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 1.3_verhuizing_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
Then heeft in het antwoordbericht 'soortNaam' in 'document' de waarde 'Aangifte met betrekking tot verblijfplaats'

When het mutatiebericht voor abonnement Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario:   16.1 Plaatsen afnemerindicatie daarna verhuizing, Toestand 2 te fiatteren,
                  waardoor de afnemer GEEN mutatiebericht op basis van afnemerindicatie ontvangt
             Logische testgevallen Use Case: R2001_03
             Verwacht resultaat: Geen bericht

Given de personen 299054457, 743274313, 981661129 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 981661129 en anr 8545465106 zonder extra gebeurtenissen

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand 1.2_plaatsen_afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then ingaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004560
Then uitgaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004560

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   16.2 Plaatsen afnemerindicatie daarna verhuizing, Toestand 2 te fiatteren,
                 waardoor de afnemer GEEN mutatiebericht op basis van afnemerindicatie ontvangt
            Logische testgevallen Use Case: R2001_03
            Verwacht resultaat: Geen bericht

Given de database is aangepast met: update autaut.abonnement set toestand = 2 where id=5670002
Given relevante abonnementen Geen pop.bep. levering op basis van afnemerindicatie
Given de database is aangepast met: update autaut.abonnement set toestand = 4 where id=5670002

Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 1.3_verhuizing_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
Then heeft in het antwoordbericht 'soortNaam' in 'document' de waarde 'Aangifte met betrekking tot verblijfplaats'

When het mutatiebericht voor abonnement Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario:   17.1 Plaatsen afnemerindicatie daarna verhuizing, Toestand 3 te verbeteren,
                  waardoor de afnemer GEEN mutatiebericht op basis van afnemerindicatie ontvangt
             Logische testgevallen Use Case: R2001_04
             Verwacht resultaat: Geen bericht

Given de personen 299054457, 743274313, 981661129 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 981661129 en anr 8545465106 zonder extra gebeurtenissen

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand 1.2_plaatsen_afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then ingaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004560
Then uitgaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004560

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   17.2 Plaatsen afnemerindicatie daarna verhuizing, Toestand 3 te verbeteren,
                 waardoor de afnemer GEEN mutatiebericht op basis van afnemerindicatie ontvangt
            Logische testgevallen Use Case: R2001_04
            Verwacht resultaat: Geen bericht

Given de database is aangepast met: update autaut.abonnement set toestand = 3 where id=5670002
Given relevante abonnementen Geen pop.bep. levering op basis van afnemerindicatie
Given de database is aangepast met: update autaut.abonnement set toestand = 4 where id=5670002

Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 1.3_verhuizing_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
Then heeft in het antwoordbericht 'soortNaam' in 'document' de waarde 'Aangifte met betrekking tot verblijfplaats'

When het mutatiebericht voor abonnement Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario:   18.1 Plaatsen afnemerindicatie daarna verhuizing, Toestand 5 Inactief,
                  waardoor de afnemer GEEN mutatiebericht op basis van afnemerindicatie ontvangt
             Logische testgevallen Use Case: R2001_05
             Verwacht resultaat: Geen bericht

Given de personen 299054457, 743274313, 981661129 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 981661129 en anr 8545465106 zonder extra gebeurtenissen

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand 1.2_plaatsen_afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then ingaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004560
Then uitgaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004560

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   18.2 Plaatsen afnemerindicatie daarna verhuizing, Toestand 5 Inactief,
                 waardoor de afnemer GEEN mutatiebericht op basis van afnemerindicatie ontvangt
            Logische testgevallen Use Case: R2001_05
            Verwacht resultaat: Geen bericht

Given de database is aangepast met: update autaut.abonnement set toestand = 5 where id=5670002
Given relevante abonnementen Geen pop.bep. levering op basis van afnemerindicatie
Given de database is aangepast met: update autaut.abonnement set toestand = 4 where id=5670002

Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 1.3_verhuizing_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
Then heeft in het antwoordbericht 'soortNaam' in 'document' de waarde 'Aangifte met betrekking tot verblijfplaats'

When het mutatiebericht voor abonnement Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
