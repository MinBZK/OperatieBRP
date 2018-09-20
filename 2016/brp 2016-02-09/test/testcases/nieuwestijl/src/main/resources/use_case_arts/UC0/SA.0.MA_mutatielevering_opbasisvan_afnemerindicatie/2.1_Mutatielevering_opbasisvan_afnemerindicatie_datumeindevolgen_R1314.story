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

Scenario:   5.1 Plaatsen afnemerindicatie daarna verhuizing, waardoor de afnemer een mutatiebericht op basis van afnemerindicatie ontvangt
                DatumEindeVolgen kleiner dan systeemdatum
            Logisch testgeval Use Case: R1314_01
            Verwacht resultaat: Leveringsbericht & Afnemerindicatie geplaatst
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given relevante abonnementen Geen pop.bep. levering op basis van afnemerindicatie
Given de personen 299054457, 743274313, 805461449 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 805461449 en anr 7905096978 zonder extra gebeurtenissen

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand 2.2_plaatsen_afnemerindicatie_03.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor abonnement Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   5.2 Plaatsen afnemerindicatie daarna verhuizing, waardoor de afnemer een mutatiebericht op basis van afnemerindicatie ontvangt
                datumEindeVolgen kleiner dan systeemdatum
            Logisch testgeval Use Case: R1314_01
            Verwacht resultaat:  synchroon responsebericht
                Met vulling:
                -  Verwerking = Geslaagd
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd
Bevinding: je mag je afnemersindicatie alleen plaatsen wanneer DatumEindeVolgen op vandaag of in de toekomst ligt. Wanneer je ervoor kiest de datum op vandaag te zetten, moet je nog steeds een dag wachten om het tweede scenario uit te voeren.
DatumEindeVolgen via een SQL statement vervangen in de database na scenario 5.1 om hem goed te zetten voor scenario 5.2 werkt niet.

Given de database is aangepast met: update autaut.persafnemerindicatie set dateindevolgen=20150202 where pers in (select id from kern.pers where bsn = 805461449)
Given relevante abonnementen Geen pop.bep. levering op basis van afnemerindicatie

Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 2.3_verhuizing_03.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor abonnement Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden


Scenario:   6.1 Plaatsen afnemerindicatie daarna verhuizing, waardoor de afnemer een mutatiebericht op basis van afnemerindicatie ontvangt
                DatumEindeGeldigheid = systeemdatum
            Logisch testgeval Use Case: R1314_02
            Verwacht resultaat: Leveringsbericht & Afnemerindicatie geplaatst
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given relevante abonnementen Geen pop.bep. levering op basis van afnemerindicatie
Given de personen 299054457, 743274313, 805461449 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 805461449 en anr 7905096978 zonder extra gebeurtenissen

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand 2.4_plaatsen_afnemerindicatie_04.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor abonnement Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   6.2 Plaatsen afnemerindicatie daarna verhuizing, waardoor de afnemer een mutatiebericht op basis van afnemerindicatie ontvangt
                DatumEindeGeldigheid = systeemdatum.
            Logisch testgeval Use Case: R1314_02
            Verwacht resultaat:  synchroon responsebericht
                Met vulling:
                -  Verwerking = geslaagd
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd
Bevinding: je mag je afnemersindicatie alleen plaatsen wanneer DatumEindeVolgen op vandaag of in de toekomst ligt. Wanneer je ervoor kiest de datum op vandaag te zetten, moet je nog steeds een dag wachten om het tweede scenario uit te voeren.
DatumEindeVolgen via een SQL statement vervangen in de database na scenario 6.1 om hem goed te zetten voor scenario 6.2 werkt niet.

Given relevante abonnementen Geen pop.bep. levering op basis van afnemerindicatie
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 2.3_verhuizing_03.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor abonnement Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

