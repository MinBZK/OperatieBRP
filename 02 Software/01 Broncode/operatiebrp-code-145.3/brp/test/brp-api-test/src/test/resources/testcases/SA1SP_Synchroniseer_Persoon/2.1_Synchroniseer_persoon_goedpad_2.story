Meta:
@epic               Goedpad Synchroniseer Persoon
@status             Klaar
@usecase            SA.0.SP


Narrative:
Synchroniseer persoon:
Als afnemer wil ik een afnemerverzoek tot het synchroniseren van een persoon afhandelen,
met dienst 'Mutatielevering op basis van doelbinding' of 'Mutatielevering op basis van afnemerindicatie',
zodat de afnemer voor de opgegeven Persoon een Volledig bericht ontvangt


Scenario:   1.   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en begindatum dienst = vandaag
            LT: R1262_LT02, R2120_LT01

            AL.1.AV Afhandelen Verzoek AL.1.AB: Geen testgevallen
            AL.1.AV Afhandelen Verzoek AL.1.XV: Geen testgevallen
            AL.1.AV Afhandelen Verzoek AL.1.AU:
            R2120_LT01 - Verwacht resultaat: Er bestaat een toegang autorisatie voor deze partij en rol in request parameter is leeg.
            AL.1.AV Afhandelen Verzoek LV.1.AL:
            LT: R1262_LT02 De gevraagde dienst is geldig, datum ingang is gelijk aan systeemdatum, datum einde is groter dan systeemdatum
            SA.1.SP Synchroniseer persoon:
            Verwacht resultaat: Synchroon response bericht + Leveringsbericht
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given persoonsbeelden uit specials:specials/Jan_xls
!-- R1262_LT02
Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_ingang_vandaag
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken



