Meta:
@status             Klaar
@usecase            SA.0.VA
@regels             R1401, R1403, R1409, R2061
@sleutelwoorden     Verwijder afnemerindicatie

Narrative:
Afnemerindicatie voor de opgegeven persoon moet bestaan.
Er moet een persoon bestaan met het opgegeven burgerservicenummer.
Plaatsen of verwijderen afnemerindicatie mag alleen op uniek burgerservicenummer.
Verstrek resultaatbericht bij geslaagd plaatsen of verwijderen van afnemerindicatie

Scenario: 1. Voor de persoon Jan wordt een afnemerindicatie verwijderd
            LT: R1401_LT05, R1262_LT09, R1264_LT05, R2056_LT05, R2130_LT05, R2061_LT03, R1587_LT03, R1983_LT31
            Voor de persoon wordt een afnemeridicatie geplaatst en vervolgens verwijderd.
            door dezelfde partij, met dezelfde Leveringsautorisatie, voor dezelfde persoon
            AL.1.AV Afhandelen Verzoek AL.1.AB:
            LT: Geen nieuwe testgevallen

            AL.1.AV Afhandelen Verzoek AL.1.XV:
            LT: Geen nieuwe testgevallen

            AL.1.AV Afhandelen Verzoek AL.1.AU:
            LT: Geen nieuwe testgevallen

            AL.1.AV Afhandelen Verzoek AL.1.AL: Datum ingang < systeem datum en datum einde = leeg voor leveringsautorisatie en dienst en beide (net als de dienstbundel) zijn niet geblokkeerd
            LT: R1262_LT09, R1264_LT05, R2056_LT05, R2130_LT05
            Verwacht resultaat: bericht komt door autorisatie heen, geen expliciete controles vereist

            SA.0.VA:
            LT: R2061_LT03
            Verwacht resultaat: afnemerindicatie verwijderen geslaagd

            SA.0.VA: Partij = Zendende partij + 1 identitietsnummer aanwezig + populatiebeperking = WAAR
            LT: R2061_LT03
             Verwacht resultaat: Verwijderen afnemerindicatie voor Jan met BSN 606417801 geslaagd
             1. Synchroon responsebericht
             Met vulling:
             -  Verwerking = geslaagd
             -  Hoogste meldings niveau = Geen
             -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
             -  BSN = 606417801
             2. XSD valide asynchroon resoponsebericht
             3. Verwerking geslaagd

            AL.1.AV Afhandelen Verzoek AL.1.MR Maak Resultaatbericht en AL.1.VR Verzend resultaatbericht
            LT: Geen nieuwe testgevallen

            AL.1.AV Afhandelen Verzoek AL.1.AB:
            LT: Geen nieuwe testgevallen

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|606417801|Geen pop.bep. levering op basis van afnemerindicatie|'Gemeente Utrecht'|30|2016-07-28 T16:11:21Z

Given verzoek verwijder afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801


Then heeft het antwoordbericht verwerking Geslaagd

Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

Then heeft in het antwoordbericht 'verwerking' in 'resultaat' de waarde 'Geslaagd'
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

Then heeft in het antwoordbericht 'partijCode' in 'verwijderingAfnemerindicatie' de waarde '034401'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'verwijderingAfnemerindicatie' nummer 1 ja
Then heeft in het antwoordbericht 'burgerservicenummer' in 'verwijderingAfnemerindicatie' de waarde '606417801'

Then is er voor persoon met bsn 606417801 en leveringautorisatie Geen pop.bep. levering op basis van afnemerindicatie en partij Gemeente Utrecht de afnemerindicatie verwijderd

!-- Additionele controle opdat er geen vulbericht wordt verstuurd bij het verwijderen van de afnemerindicatie
Then is het aantal ontvangen berichten 0

Then is het verzoek correct gearchiveerd

