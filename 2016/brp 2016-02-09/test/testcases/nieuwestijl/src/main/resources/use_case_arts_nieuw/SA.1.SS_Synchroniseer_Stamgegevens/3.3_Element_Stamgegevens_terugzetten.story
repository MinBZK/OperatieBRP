Meta:
@epic               Verbeteren testtooling
@auteur             kedon
@status             Klaar
@usecase            SA.0.SS, SA.1.SS, SA.0.AV, LV.1.AV
@regels             R1331,R1332,R1979
@sleutelwoorden     Synchronisatie stamgegeven, Synchroniseer stamgegeven, Afhandeling verzoek, Afhandelen verzoek


Narrative:
De nieuwe Element tabel zal worden gebruikt om verwijzingen te communiceren naar afnemers. Bijvoorbeeld om aan te geven welke gegevens
in onderzoek staan (waaronder ook onderzoek naar ontbrekende gegevens, die dus zelf niet in het bericht staan). Mogelijk gaan afnemers
in de toekomst dit ook gebruiken om zelf te verwijzen (bijvoorbeeld voor terugmelding of scoping van bevraging). Het is dus noodzakelijk
dat de afnemers de nieuwe Elementtabel kennen zodat ze die verwijzingen kunnen interpreteren.

Echter de nieuwe Element tabel bevat ook veel inhoud die voor de afnemers niet relevant is. Dat betreft zowel kolommen (o.a. technische
verwijzingen naar de database) als rijen (gegevens die geen onderdeel van een levering kunnen zijn). Verwerkingsregel VR00111 beschrijft
de juiste inperking en sortering bij het leveren van de inhoud van de nieuwe Element tabel aan Afnemers. Daarnaast is in BRLV0024 de
opsomming van potentieel te leveren stamtabellen uitgebreid met de tabel Element.

Alleen een aantal willekeurige elementen worden gecontroleerd aangezien het wat ver gaat om 1500 elementen te gaan controleren.

Scenario: 3.3   Succesvol uitvoeren geefSynchronisatieStamgegevens
                Een element terugzetten in de tabel
                    Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   R1268_07, R1269_05
                    Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.XV:   Geen testgevallen
                    Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AU:   R1257_01, R1258_03, R2052_01
                    Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AL:   R1260_01, R1261_03, R1262_03, R1263_01, R1264_01, R2053_01, R2055_01, R2056_01
                    Logische testgevallen SA.1.SS Synchronisser Stamgegevens:   R1331_02, R1332_03, R1979_03
                    Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.MR:   R1266_01, R1410_01
                    Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.VB:   R1985_01, R1991_01
                    Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   R1268_08, R1270_07
                Verwacht Resultaat: Identiek aan scenario 2

Given leveringsautorisatie uit /levering_autorisaties/synchronisatie_stamgegeven
Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
Given de database is aangepast met: update kern.element set leverenalsstamgegeven = TRUE where element.id=3010
And testdata uit bestand 1.2_synchronisatie_stamgegevens_element.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

