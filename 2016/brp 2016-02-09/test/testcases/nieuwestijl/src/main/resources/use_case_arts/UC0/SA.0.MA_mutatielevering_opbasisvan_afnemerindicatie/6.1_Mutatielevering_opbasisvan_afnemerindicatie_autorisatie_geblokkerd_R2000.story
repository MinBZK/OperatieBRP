Meta:
@epic               Verbeteren testtooling
@auteur             kedon
@status             Onderhanden
@usecase            SA.0.MA
@regels             R1314,R1315,R1338,R1343,R1544,R1989,R1990,R1993,R1994,R2000,R2001,R2002
@sleutelwoorden     Mutatielevering o.b.v. afnemerindicatie

Narrative:
Mutatielevering op basis van Plaatsing afnemersindicatie:
De afnemer ontvangt voor de opgegeven Persoon een eerste Volledig bericht bij de plaatsing van de afnemerindicatie.
Zolang de afnemerindicatie nog geldig is worden alle geautoriseerde wijzigingen ontvangen via een mutatiebericht.

Scenario:   11.1 Plaatsen afnemerindicatie daarna verhuizing, GEEN autorisatie door Dienst.Geblokkeeerd,
                  waardoor de afnemer GEEN mutatiebericht op basis van afnemerindicatie ontvangt
             Logische testgevallen Use Case: R2000_02
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

Scenario:   11.2 Plaatsen afnemerindicatie daarna verhuizing, GEEN autorisatie door Dienst.Geblokkeeerd,
                 waardoor de afnemer GEEN mutatiebericht op basis van afnemerindicatie ontvangt
            Logische testgevallen Use Case: R2000_02
            Verwacht resultaat: Geen bericht

Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 1.3_verhuizing_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
Then heeft in het antwoordbericht 'soortNaam' in 'document' de waarde 'Aangifte met betrekking tot verblijfplaats'

When het mutatiebericht voor abonnement Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then verantwoording acties staan in persoon

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario:   12. Dienstbundel geblokkeerd

Scenario:   13. Toegang leveringsautorisatie geblokkeerd

Scenario:   14. Leveringsautorisatie geblokkeerd
