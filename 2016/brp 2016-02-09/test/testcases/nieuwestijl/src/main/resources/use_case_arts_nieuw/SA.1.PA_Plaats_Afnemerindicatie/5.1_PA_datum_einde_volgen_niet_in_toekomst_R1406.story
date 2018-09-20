Meta:
@auteur             jowil
@status             Klaar
@usecase            SA.0.PA,SA.1.PA,SA.1.PA.AL,SA.1.PA.CI,LV.1.PA,LV.1.VA,LV.0.AV,LV.1.AV,LV.1.AB,LV.1.XV,LV.1.AU,LV.1.AL,LV.1.MR,LV.1.VB,LV1.AB
@regels             R1336,R1339,R1350,R1402,R1403,R1404,R1405,R1406,R1407,R1408,R1538,R1539,R1984,R2016,R2061
@sleutelwoorden     Plaats afnemerindicatie, Controleer inhoud, Verwijder afnemerindicatie, Afhandeling verzoek, Afhandelen verzoek, Archiveer bericht, XSD validatie, Authenticatie, Autorisatie levering, Maak responsebericht, Verstuur bericht, Archiveer bericht


Narrative: 
Een opgegeven datum einde volgen moet in de toekomst liggen.
Wanneer datum einde volgen kleiner of gelijk is aan de systeemdatum, wordt de afnemerindicatie niet geplaatst

Scenario:   9. Afnemer plaatst afnemerindicatie op een persoon, waarbij DatumEindeVolgen is kleinder dan de systeemdatum.
            Logische testgevallen Use Case: R1406_01
            Verwacht resultaat: 1. Afnemerindicatie kan niet geplaatst worden op persoon
                                2. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                -  Melding = Datum einde volgen moet in de toekomst liggen.
                                3. Er wordt niet geleverd voor deze persoon


Given de personen 299054457, 743274313, 148047117 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 148047117 en anr 8704769298  zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 5.2_Plaats_afnemerindicatie_04.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft in het antwoordbericht 'melding' in 'melding' de waarde 'Datum einde volgen moet in de toekomst liggen.'

Then is er geen synchronisatiebericht gevonden


Scenario:   10. Afnemer plaatst afnemerindicatie op een persoon, waarbij de DatumEindeVolgen = systeemdatum.
            Logische testgevallen Use Case: R1406_02
            Verwacht resultaat: 1. Afnemerindicatie kan niet geplaatst worden op persoon
                                2. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                -  Melding = Datum einde volgen moet in de toekomst liggen.
                                3. Er wordt niet geleverd voor deze persoon

Given de personen 299054457, 743274313, 148047117 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 148047117 en anr 8704769298  zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 5.3_Plaats_afnemerindicatie_05.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'Datum einde volgen moet in de toekomst liggen.'

Then is er geen synchronisatiebericht gevonden