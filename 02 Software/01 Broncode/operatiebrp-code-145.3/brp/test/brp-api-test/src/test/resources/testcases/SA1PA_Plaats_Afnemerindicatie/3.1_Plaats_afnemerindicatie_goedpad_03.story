Meta:
@status             Klaar
@usecase            SA.0.PA
@regels             R1350, R1401, R1402, R1403, R1405, R1406, R1408, R2061
@sleutelwoorden     Plaats afnemerindicatie

Narrative:
Plaatsing afnemersindicatie:
Het afhandelen van een afnemerverzoek tot het plaatsen van een afnemerindicatie,
zodat de afnemer voor de opgegeven Persoon een eerste Volledig bericht ontvangt
en zolang de afnemerindicatie nog geldig is alle geautoriseerde wijzigingen ontvangt via een mutatiebericht.

Scenario:   1.  Gemeente Utrecht plaatst een afnemerindicatie op persoon Jan met bsn = 606417801,
                LT: R1258_LT01, R1261_LT01, R1405_LT04, R1539_LT03, R1617_LT08
                Uitwerking:
                Gemeente Utrecht plaatst een afnemerindicatie op persoon Jan met bsn = 606417801,
                vanuit de leverautorisatie Geen_pop.bep_levering_op_basis_van_afnemerindicatie
                Deze leverautorisatie beschikt over de diensten: Plaatsen afnemerindicatie, Verwijderen afnemerindicatie,
                Mutatielevering op basis van afnemerindicatie en Synchronisatie persoon
                De datum aanvang materiele periode van de te plaatsen afnemerindicatie = leeg
                en de datum einde geldigheid = leeg

                AL.1.AV Afhandelen Verzoek AL.1.AB:
                LT: Geen nieuwe testgevallen

                AL.1.AV Afhandelen Verzoek AL.1.XV:
                Geen testgevallen

                AL.1.AV Afhandelen Verzoek AL.1.AU:
                Toegang leveringsautorisatie datum ingang < systeemdatum en datum einde > systeemdatum
                LT: R1258_LT01

                AL.1.AV Afhandelen Verzoek AL.1.AL:
                De leveringsautorisatie is geldig, datum ingang < systeemdatum, datum einde > systeemdatum
                De gevraagde dienst is geldig, datum ingang < systeemdatum, datum einde > systeemdatum
                LT: R1261_LT01
                Verwacht resultaat: bericht komt door autorisatie heen, geen expliciete controles vereist

                SA.1.PA Plaats Afnemerindicatie:
                LT: Geen nieuwe testgevallen

                SA.1.PA.CA Controleer autorisatie:
                LT: Geen nieuwe testgevallen

                SA.1.PA.CI Controleer inhoud:
                Datum aanvang materiele periode = LEEG, datum einde volgen = leeg, nadere bijhoudingsaard = 3
                LT: R1405_LT04, R1539_LT03
                Verwacht resultaat: Plaatsen afnemerindicatie geslaagd, geen expliciete controle van toepassing

                 Plaats afnemerindicatie
                LT: Geen nieuwe testgevallen

                AL.1.AV Afhandelen Verzoek AL.1.MR Maak Resultaatbericht en AL.1.VR Verzend resultaatbericht
                LT: Geen nieuwe testgevallen

                AL.1.AV Afhandelen Verzoek AL.1.AB:
                LT: Geen nieuwe testgevallen

                LV.0.MB Maak BRP bericht
                LT: Geen nieuwe testgevallen

                LV.0.MB.VB
                LT: Geen testgevallen van toepassing

                AL.1.VZ Verzending LV.1.PB Protocoleer bericht
                LT: Geen nieuwe testgevallen

                AL.1.VZ Verzending AL.1.VE Verzend bericht
                BRP bericht, gegarandeerd leveren op afleverpunt
                LT: Geen nieuwe testgevallen

                AL.1.AB - Archiveer bericht
                LT: Geen nieuwe testgevallen

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie

Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

!-- And testdata uit bestand 3.2_Plaats_Afnemerindicatie_goedpad_03.yml
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja
Then heeft in het antwoordbericht 'burgerservicenummer' in 'plaatsingAfnemerindicatie' de waarde '854820425'

Then heeft in het antwoordbericht 'verwerking' in 'resultaat' de waarde 'Geslaagd'
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

Then heeft in het antwoordbericht 'partijCode' in 'plaatsingAfnemerindicatie' de waarde '034401'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'plaatsingAfnemerindicatie' nummer 1 ja

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

!-- check datum aanvang materiele periode R1617_LT08
!-- Then in prot heeft select * from prot.levsaantek left join prot.levsaantekpers on (prot.levsaantek.id = prot.levsaantekpers.levsaantek) where prot
!--.levsaantekpers.pers = (select id from kern.pers where bsn = 606417801) de volgende gegevens:
!-- | veld                       | waarde   |
!-- | dataanvmaterieleperioderes | NULL     |