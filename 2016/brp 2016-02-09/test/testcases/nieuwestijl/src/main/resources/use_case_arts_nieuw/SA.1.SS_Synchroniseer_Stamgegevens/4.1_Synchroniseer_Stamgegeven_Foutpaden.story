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


Scenario: 4. Niet-succesvol uitvoeren geefSynchronisatieStamgegevens, onbekende tabel
                Logisch testgeval Use Case:  R1331_03
             Verwacht resultaat: Response bericht
                Met vulling:
                 -  Verwerking = Foutief
                 -  Foutmelding = De opgegeven stamtabel bestaat niet.
             Bevinding: JIRA-ISSUE TEAMBRP-4544. Antwoordbericht verkeerd opgebouwd, incorrecte foutmelding
Meta:
@status     Backlog !-- TEAMBRP-4544 alfa is akkoord dat we in dit geval een andere foutmelding teruggeven

Given leveringsautorisatie uit /levering_autorisaties/synchronisatie_stamgegeven
Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 4.2_synchronisatie_stamgegevens_onbekend.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'De opgegeven stamtabel bestaat niet.'