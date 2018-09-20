Meta:
@auteur             jowil
@status             Klaar
@usecase            SA.0.PA,SA.1.PA,SA.1.PA.AL,SA.1.PA.CI,LV.1.PA,LV.1.VA,LV.0.AV,LV.1.AV,LV.1.AB,LV.1.XV,LV.1.AU,LV.1.AL,LV.1.MR,LV.1.VB,LV1.AB
@regels             R1336,R1339,R1350,R1402,R1403,R1404,R1405,R1406,R1407,R1408,R1538,R1539,R1984,R2016,R2061
@sleutelwoorden     Plaats afnemerindicatie, Controleer inhoud, Verwijder afnemerindicatie, Afhandeling verzoek, Afhandelen verzoek, Archiveer bericht, XSD validatie, Authenticatie, Autorisatie levering, Maak responsebericht, Verstuur bericht, Archiveer bericht
Narrative:
Leveren van personen is binnen koppelvlak levering alleen mogelijk voor ingeschreven personen
Op een niet ingeschreven persoon kan geen afnemerindicatie geplaatst worden


Scenario: 13. Afnemer plaatst afnemerindicatie op een Niet ingeschreven persoon
            Logisch testgevallen: R1538_03
            Verwacht resultaat: 1. Afnemerindicatie kan niet geplaatst worden op persoon
                                2. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                -  Melding = De opgegeven persoon is een niet-ingeschreven persoon. Levering niet mogelijk.
                                3. Er wordt niet geleverd voor deze persoon


Given de personen 299054457, 743274313, 148047117 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 148047117 en anr 8704769298 zonder extra gebeurtenissen

And de database is aangepast met: update kern.pers set srt=2 where bsn = 148047117

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 7.2_Plaats_afnemerindicatie_08.yml
When het bericht wordt verstuurd
Then de database wordt opgeruimd met: update kern.pers set srt=1 where bsn = 148047117
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'De opgegeven persoon is een niet-ingeschreven persoon. Levering niet mogelijk.'

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

