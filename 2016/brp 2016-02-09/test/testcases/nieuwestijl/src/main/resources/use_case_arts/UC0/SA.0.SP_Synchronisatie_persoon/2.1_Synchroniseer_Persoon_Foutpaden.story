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
Foutpaden


Scenario:   5.   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
                Populatiebeperking dienst mutatielevering op basis van doelbinding evalueert op ONWAAR
            Logische testgevallen Use Case: R1344_03, R1345_01, R1347_01, R1978_02, R1982_02
            Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Foutief
                -  Foutmelding = De opgegeven persoon valt niet te synchroniseren binnen het opgegeven abonnement.
            Bevinding: zie TEAMBRP 4026
Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen

Given de database is aangepast met: update autaut.abonnement set populatiebeperking='ONWAAR' where naam = 'Geen pop.bep. levering op basis van doelbinding'
Given relevante abonnementen Geen pop.bep. levering op basis van doelbinding
Given de database is aangepast met: update autaut.abonnement set populatiebeperking='WAAR' where naam = 'Geen pop.bep. levering op basis van doelbinding'

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'De opgegeven persoon valt niet te synchroniseren binnen het opgegeven abonnement.'

Scenario:   6.   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
                Populatiebeperking dienst mutatielevering op basis van doelbinding evalueert op NULL
            Logische testgevallen Use Case: R1344_04, R1345_01, R1347_01, R1978_02, R1982_02
            Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Foutief
                -  Foutmelding = De opgegeven persoon valt niet te synchroniseren binnen het opgegeven abonnement.
            Bevinding: zie TEAMBRP 4026

Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen

Given de database is aangepast met: update autaut.abonnement set populatiebeperking=NULL where naam = 'Geen pop.bep. levering op basis van doelbinding'
Given relevante abonnementen Geen pop.bep. levering op basis van doelbinding
Given de database is aangepast met: update autaut.abonnement set populatiebeperking='WAAR' where naam = 'Geen pop.bep. levering op basis van doelbinding'

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'De opgegeven persoon valt niet te synchroniseren binnen het opgegeven abonnement.'

Scenario:   7.   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
                DatumIngang dienst mutatielevering op basis van doelbinding > systeemdatum
            Logische testgevallen Use Case: R1344_05, R1345_01, R1347_03, R1978_02, R1982_02
            Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Foutief
                -  Foutmelding = Er is geen dienst mutatielevering aanwezig binnen het opgegeven abonnement.

Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen

Given de database is aangepast met: update autaut.dienst set datingang='${vandaagsql(0,0,1)}' where catalogusoptie = 1 and abonnement = (select id from autaut.abonnement where naam = 'Geen pop.bep. levering op basis van doelbinding')
Given relevante abonnementen Geen pop.bep. levering op basis van doelbinding
Given de database is aangepast met: update autaut.dienst set datingang=20130101 where catalogusoptie = 1 and abonnement = (select id from autaut.abonnement where naam = 'Geen pop.bep. levering op basis van doelbinding')

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'Er is geen dienst mutatielevering aanwezig binnen het opgegeven abonnement.'

Scenario:   8.   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
                DatumEinde volgen dienst mutatielevering op basis van doelbinding < systeemdatum
            Logische testgevallen Use Case: R1344_06, R1345_01, R1347_04, R1978_02, R1982_02
            Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Foutief
                -  Foutmelding = Er is geen dienst mutatielevering aanwezig binnen het opgegeven abonnement.

Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen

Given de database is aangepast met: update autaut.dienst set dateinde='${vandaagsql(-1,0,0)}' where catalogusoptie = 1 and abonnement = (select id from autaut.abonnement where naam = 'Geen pop.bep. levering op basis van doelbinding')
Given relevante abonnementen Geen pop.bep. levering op basis van doelbinding
Given de database is aangepast met: update autaut.dienst set dateinde=null where catalogusoptie = 1 and abonnement = (select id from autaut.abonnement where naam = 'Geen pop.bep. levering op basis van doelbinding')

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'Er is geen dienst mutatielevering aanwezig binnen het opgegeven abonnement.'


Scenario:   9.   Nadere.populatiebeperking dienst Mutatielevering op basis van doelbinding = onwaar
            Logische testgevallen Use Case: R1344_01, R1345_03, R1347_01, R1978_02, R1982_02
            Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Foutief
                -  Foutmelding = Er is geen dienst mutatielevering aanwezig binnen het opgegeven abonnement.
            Bevinding: zie TEAMBRP 4026

Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen

Given de database is aangepast met: update autaut.dienst set naderepopulatiebeperking='ONWAAR' where catalogusoptie = 1 and abonnement = (select id from autaut.abonnement where naam = 'Geen pop.bep. levering op basis van doelbinding')
Given relevante abonnementen Geen pop.bep. levering op basis van doelbinding
Given de database is aangepast met: update autaut.dienst set naderepopulatiebeperking=NULL where catalogusoptie = 1 and abonnement = (select id from autaut.abonnement where naam = 'Geen pop.bep. levering op basis van doelbinding')

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'De opgegeven persoon valt niet binnen de doelbinding van mutatielevering obv doelbinding.'


Scenario:   10.1   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van afnemerindicatie en
                DatumEinde volgen dienst mutatielevering op basis van afnemerindicatie < systeemdatum
            Logische testgevallen Use Case: R1345_02, R1346_02, R1347_05, R1978_02, R1982_02
            Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Foutief
                -  Foutmelding = Er is geen dienst mutatielevering aanwezig binnen het opgegeven abonnement.


Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand 1.4_Plaats_Afnemerindicatie.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario:   10.2   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van afnemerindicatie en
                DatumEinde volgen dienst mutatielevering op basis van afnemerindicatie < systeemdatum
            Logische testgevallen Use Case: R1345_02, R1346_02, R1347_05, R1978_02, R1982_02
            Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Foutief
                -  Foutmelding = Er is geen dienst mutatielevering aanwezig binnen het opgegeven abonnement.

Given de database is aangepast met: update autaut.dienst set dateinde='${vandaagsql(0,0,-1)}' where id = 6
Given relevante abonnementen Geen pop.bep. levering op basis van afnemerindicatie
Given de database is aangepast met: update autaut.dienst set dateinde=NULL where id = 6

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.3_Synchroniseer_Persoon_2.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'Er is geen dienst mutatielevering aanwezig binnen het opgegeven abonnement.'

Scenario:   11. Afnemer vraagt gegevens op van een persoon, deze persoon heeft een verstrekkingsbeperking voor de betreffende afnemer.
            Logisch testgevallen: R1339_01, R1342_02
            Verwacht resultaat: 1. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                2. Er wordt niet geleverd voor deze persoon
            Bevinding: JIRA ISSUE TEAMBRP-4500 ondanks de verstrekkingsbeperking wordt er toch geleverd


Given de database is aangepast met: update kern.partij set indverstrbeperkingmogelijk = 'TRUE' where id=347
Given relevante abonnementen Geen pop.bep. levering op basis van doelbinding
Given de database is aangepast met: update kern.partij set indverstrbeperkingmogelijk = 'FALSE' where id=347

Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906  met extra gebeurtenissen:
verstrekkingsbeperking() {
    registratieBeperkingen( partij: 34401 )
}

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.'
