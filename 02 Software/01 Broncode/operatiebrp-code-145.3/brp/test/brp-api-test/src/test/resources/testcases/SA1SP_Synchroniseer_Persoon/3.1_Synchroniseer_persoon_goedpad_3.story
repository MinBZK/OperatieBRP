Meta:
@epic               Goedpad Synchroniseer Persoon
@status             Klaar
@usecase            SA.0.SP


Narrative:
Synchroniseer persoon:
Als afnemer wil ik een afnemerverzoek tot het synchroniseren van een persoon afhandelen,
met dienst 'Mutatielevering op basis van doelbinding' of 'Mutatielevering op basis van afnemerindicatie',
zodat de afnemer voor de opgegeven Persoon een Volledig bericht ontvangt

Scenario:   1   Verzoek synchronisatie persoon, met dienst Mutatielevering op basis van afnemerindicatie en
                LT: R1401_LT01, R1262_LT03, R1264_LT01, R2056_LT01, R2130_LT01, R1347_LT02
                Datum aanvang geldigheid dienst is < systeemdatum, einde is leeg


            AL.1.AV Afhandelen Verzoek AL.1.AB: Geen testgevallen
            AL.1.AV Afhandelen Verzoek AL.1.XV: Geen testgevallen
            AL.1.AV Afhandelen Verzoek AL.1.AU: Geen testgevallen
            AL.1.AV Afhandelen Verzoek LV.1.AL:
            R1262_LT03 Verwacht resultaat: De dienst synchroniseer persoon is geldig, datum ingang is kleiner dan systeemdatum, datum einde is leeg er wordt geleverd.
            R1264_LT01 Verwacht resultaat: De gevraagde dienst is niet geblokkeerd, er wordt geleverd.
            R2056_LT01 Verwacht resultaat: De dienstbundel is niet geblokkeerd er wordt geleverd.
            R2130_LT01 Verwacht resultaat: De leveringsautorisatie bevat de gevraagde dienst, er wordt geleverd.
            SA.1.SP Synchroniseer persoon:
            R1347_LT02 Verwacht resultaat: Partij heeft goede dienst en afnemer indicatie, er wordt geleverd.
            AL.1.AV Afhandelen Verzoek: Geen testgevallen
            AL.1.AV Afhandelen Verzoek: Geen testgevallen
            AL.1.AV Afhandelen Verzoek: Geen testgevallen
            LV.0.MB Maak BRP bericht: Geen testgevallen
            LV.0.MB Maak BRP bericht: Geen testgevallen
            AL.1.VZ Leveren LV.1.PB: Geen testgevallen
            AL.1.VZ Leveren AL.1.VB: Geen testgevallen
            AL.1.VZ Leveren AL.1.AB: Geen testgevallen
            Verwacht resultaat: Synchroon response bericht + Leveringsbericht
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T150_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|434587977|Geen pop.bep. levering op basis van afnemerindicatie|'Gemeente Utrecht'|30|2016-07-28 T16:11:21Z

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|434587977

Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

