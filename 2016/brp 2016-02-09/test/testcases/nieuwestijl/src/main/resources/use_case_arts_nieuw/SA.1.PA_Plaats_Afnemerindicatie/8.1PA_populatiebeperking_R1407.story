Meta:
@auteur             jowil
@status             Klaar
@usecase            SA.0.PA,SA.1.PA,SA.1.PA.AL,SA.1.PA.CI,LV.1.PA,LV.1.VA,LV.0.AV,LV.1.AV,LV.1.AB,LV.1.XV,LV.1.AU,LV.1.AL,LV.1.MR,LV.1.VB,LV1.AB
@regels             R1336,R1339,R1350,R1402,R1403,R1404,R1405,R1406,R1407,R1408,R1538,R1539,R1984,R2016,R2061
@sleutelwoorden     Plaats afnemerindicatie, Controleer inhoud, Verwijder afnemerindicatie, Afhandeling verzoek, Afhandelen verzoek, Archiveer bericht, XSD validatie, Authenticatie, Autorisatie levering, Maak responsebericht, Verstuur bericht, Archiveer bericht
Narrative:
Plaatsen afnemerindicatie alleen mogelijk indien persoon tot de doelgroep van het abonnement behoort.
Wanneer de expressie van de populatiebeperking niet op waar evalueert kan er geen afnemerindicatie geplaatst worden

Scenario:   14. Plaatsen afnemerindicatie op een persoon die buiten de doelbinding van het abonnement valt (Leveringsautoristie.Populatiebeperking = ONWAAR).
            Logische testgevallen: R1407_03, R1410_02
            Verwacht resultaat: 1. Afnemerindicatie kan niet geplaatst worden op persoon
                                2. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                -  Melding = De persoon valt niet binnen de doelgroep waarop de afnemer in dit abonnement een indicatie mag plaatsen.
                                3. Er wordt niet geleverd voor deze persoon


Given de personen 299054457, 743274313, 321619833 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 321619833 en anr 8717134610 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_afn_ind/postcode_gebied_Hillegom_2180-2182
Given verzoek voor leveringsautorisatie 'postcode gebied Hillegom 2180 - 2182' en partij 'SRPUC50151-5-Partij'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 8.2_Plaats_afnemerindicatie_09.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'De persoon valt niet binnen de populatie waarop de afnemer in deze leveringsautorisatie een indicatie mag plaatsen.'

Then is er geen synchronisatiebericht gevonden

Scenario:   15.Plaatsen afnemerindicatie op een persoon die buiten de doelbinding van het abonnement valt (Leveringsautorisatie.Populatiebeperking = leeg).
        Logische testgevallen:  R1407_02
            Verwacht resultaat: 1. Afnemerindicatie kan niet geplaatst worden op persoon
                                2. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                -  Melding = De persoon valt niet binnen de doelgroep waarop de afnemer in dit abonnement een indicatie mag plaatsen.
                                3. Er wordt niet geleverd voor deze persoon
            populatie beperking = leeg (null) wordt nu geevalueerd naar WAAR


Given de personen 299054457, 743274313, 321619833 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 321619833 en anr 8717134610 zonder extra gebeurtenissen

Given de database is aangepast met: update autaut.levsautorisatie set populatiebeperking=NULL
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'postcode gebied Hillegom 2180 - 2182' en partij 'SRPUC50151-5-Partij'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 8.2_Plaats_afnemerindicatie_09.yml
When het bericht wordt verstuurd

Then heeft het antwoordbericht verwerking Geslaagd