Meta:
@auteur             jowil
@status             Klaar
@usecase            SA.0.PA,SA.1.PA,SA.1.PA.AL,SA.1.PA.CI,LV.1.PA,LV.1.VA,LV.0.AV,LV.1.AV,LV.1.AB,LV.1.XV,LV.1.AU,LV.1.AL,LV.1.MR,LV.1.VB,LV1.AB
@regels             R1336,R1339,R1350,R1402,R1403,R1404,R1405,R1406,R1407,R1408,R1538,R1539,R1984,R2016,R2061
@sleutelwoorden     Plaats afnemerindicatie, Controleer inhoud, Verwijder afnemerindicatie, Afhandeling verzoek, Afhandelen verzoek, Archiveer bericht, XSD validatie, Authenticatie, Autorisatie levering, Maak responsebericht, Verstuur bericht, Archiveer bericht

Narrative:
Binnen abonnement hoogstens één afnemersindicatie per persoon,
wanneer er al een afnemerindicatie op de betreffendepersoon aanwezig is, kan er geen nieuwe afnemerindicatie geplaatst worden


Scenario:   7.1 Afnemer plaatst afnemerindicatie op een persoon, waarop al een afnemerindicatie is voor de desbetreffende afnemer.
            Logische testgevallen Use Case: R1402_02
            Verwacht resultaat: 1a. Er wordt succesvol een afnemerindicatie geplaatst op de persoon
                                1b. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = geslaagd
                                2. Er wordt geleverd voor deze persoon

Given de personen 299054457, 743274313, 148047117 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 148047117 en anr 8704769298  zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 3.1_Plaats_afnemerindicatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   7.2 Afnemer plaatst afnemerindicatie op een persoon, waarop al een afnemerindicatie is voor de desbetreffende afnemer.
            Logische testgevallen Use Case: R1402_02
            Verwacht resultaat: 1a. Bij het werderom plaatsen van een afnemerindicatie op deze persoon voor dezelfde abonnement, dient dit niet mogelijk te zijn daar
                                er reeds een afnemerindicatie is geplaatst op deze persoon
                                1b. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                -  Melding = Er bestaat al een afnemersindicatie voor de opgegeven persoon binnen het abonnement.
                                2. Er wordt niet geleverd voor deze persoon

Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 3.1_Plaats_afnemerindicatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft in het antwoordbericht 'melding' in 'melding' de waarde 'Er bestaat al een afnemersindicatie voor de opgegeven persoon binnen de leveringsautorisatie.'