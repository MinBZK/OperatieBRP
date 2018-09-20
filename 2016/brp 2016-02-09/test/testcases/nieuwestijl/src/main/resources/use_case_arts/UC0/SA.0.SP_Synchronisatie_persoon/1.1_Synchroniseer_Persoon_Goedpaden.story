Meta:
@epic               Verbeteren testtooling
@auteur             kedon
@status             Klaar
@usecase            SA.0.SP
@regels             R1339,R1344,R1345,R1346,R1347,R1403,R1538,R1539,R1978,R1982,R2002
@sleutelwoorden     Synchronisatie persoon


Narrative:
Synchroniseer persoon:
Als afnemer wil ik een afnemerverzoek tot het synchroniseren van een persoon afhandelen,
met dienst 'Mutatielevering op basis van doelbinding' of 'Mutatielevering op basis van afnemerindicatie',
zodat de afnemer voor de opgegeven Persoon een Volledig bericht ontvangt

Scenario:   1   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
                Datum einde volgen is gevuld met 1 dag > systeemdatum
            Logische testgevallen Use Case: R1344_01, R1345_01, R1347_01, R1978_01, R1982_01
            Logisch testgeval Plaats Afnemerindicatie: R1339_02
             Verwacht resultaat: Antwoordbericht
                Met vulling:
                - Zendende partij
                - Zendende systeem
                - referentienummer
                - tijdstip
                - abonnementnaam
                - burgerservicenummer
            Verwacht resultaat:Leveringsbericht
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd
            Bevinding: JIRA ISSUE TEAMBRP-4499 Abonnementnaam ontbreekt in antwoordbericht

Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen

Given de database is aangepast met: update autaut.dienst set dateinde='${vandaagsql(0,0,1)}' where catalogusoptie = 1 and abonnement = (select id from autaut.abonnement where naam = 'Geen pop.bep. levering op basis van doelbinding')
Given relevante abonnementen Geen pop.bep. levering op basis van doelbinding
Given de database is aangepast met: update autaut.dienst set dateinde=NULL where catalogusoptie = 1 and abonnement = (select id from autaut.abonnement where naam = 'Geen pop.bep. levering op basis van doelbinding')

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja
Then heeft in het antwoordbericht 'Abonnementnaam' in 'parameters' de waarde 'Geen pop.bep. levering op basis van doelbinding'
Then heeft in het antwoordbericht 'Burgerservicenummer' in 'verwijderingAfnemerindicatie' de waarde '412670409'

When het volledigbericht voor leveringautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario:   2.1     Verzoek synchronisatie persoon, met dienst Mutatielevering op basis van afnemerindicatie en
                    Datum einde volgen is gevuld met 1 dag > systeemdatum
            Logische testgevallen Use Case: R1345_02, R1346_01, R1347_02, R1978_01, R1982_01, R1973_01
            Verwacht resultaat: Synchroon response bericht + Leveringsbericht
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand 1.4_Plaats_Afnemerindicatie.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   2.2     Verzoek synchronisatie persoon, met dienst Mutatielevering op basis van afnemerindicatie en
                    Datum einde volgen is gevuld met 1 dag > systeemdatum
            Logische testgevallen Use Case: R1345_02, R1346_01, R1347_02, R1978_01, R1982_01, R1973_01
            Verwacht resultaat: Synchroon response bericht + Leveringsbericht
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given de database is aangepast met: update autaut.dienst set dateinde='${vandaagsql(0,0,1)}' where id = 6
Given relevante abonnementen Geen pop.bep. levering op basis van afnemerindicatie
Given de database is aangepast met: update autaut.dienst set dateinde=NULL where id = 6

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.3_Synchroniseer_Persoon_2.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   3.   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
                begindatum dienst = vandaag
            Logische testgevallen Use Case: R1344_02, R1345_01, R1347_01, R1978_01, R1982_01
            Verwacht resultaat: Synchroon response bericht + Leveringsbericht
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen

Given de database is aangepast met: update autaut.dienst set datingang='${vandaagsql(0,0,0)}' where catalogusoptie = 1 and abonnement = (select id from autaut.abonnement where naam = 'Geen pop.bep. levering op basis van doelbinding')
Given relevante abonnementen Geen pop.bep. levering op basis van doelbinding
Given de database is aangepast met: update autaut.dienst set datingang=20130101 where catalogusoptie = 1 and abonnement = (select id from autaut.abonnement where naam = 'Geen pop.bep. levering op basis van doelbinding')

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

When het volledigbericht voor leveringautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   4.1     Verzoek synchronisatie persoon, met dienst Mutatielevering op basis van afnemerindicatie en
                    Begindatum dienst = vandaag
            Logische testgevallen Use Case: R1345_02, R1346_01, R1347_02, R1978_01, R1982_01, R1973_01
            Verwacht resultaat: Synchroon response bericht + Leveringsbericht
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd
            Overige geraakte regels: R1257, R1258, R1259, R1260, R1261, R1262, R1263, R1264, R1265, R1266, R1410, R1777, R1973, R1985, R1991, R1997


Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand 1.4_Plaats_Afnemerindicatie.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   4.2     Verzoek synchronisatie persoon, met dienst Mutatielevering op basis van afnemerindicatie en
                    Begindatum dienst = vandaag
            Logische testgevallen Use Case: R1345_02, R1346_01, R1347_02, R1978_01, R1982_01, R1973_01
            Verwacht resultaat: Synchroon response bericht + Leveringsbericht
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given de database is aangepast met: update autaut.dienst set datingang='${vandaagsql(0,0,0)}' where id = 6
Given relevante abonnementen Geen pop.bep. levering op basis van afnemerindicatie
Given de database is aangepast met: update autaut.dienst set datingang=20130101 where id = 6

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.3_Synchroniseer_Persoon_2.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
