Meta:
@auteur             jowil
@status             Klaar
@usecase            SA.0.PA,SA.1.PA,SA.1.PA.AL,SA.1.PA.CI,LV.1.PA,LV.1.VA,LV.0.AV,LV.1.AV,LV.1.AB,LV.1.XV,LV.1.AU,LV.1.AL,LV.1.MR,LV.1.VB,LV1.AB
@regels             R1336,R1339,R1350,R1402,R1403,R1404,R1405,R1406,R1407,R1408,R1538,R1539,R1984,R2016,R2061
@sleutelwoorden     Plaats afnemerindicatie, Controleer inhoud, Verwijder afnemerindicatie, Afhandeling verzoek, Afhandelen verzoek, Archiveer bericht, XSD validatie, Authenticatie, Autorisatie levering, Maak responsebericht, Verstuur bericht, Archiveer bericht


Narrative:
Wanneer een persoon een verstrekkingsbeperking op een partij heeft geplaatst,
dan is het niet mogelijk voor de partij in kwestie om een afnemerindicatie te plaatsen

Scenario:   5. Afnemer plaatst afnemerindicatie op een persoon, deze persoon heeft een verstrekkingsbeperking voor de betreffende afnemer.
            Logische testgevallen Use Case:  R1339_04, R1342_02
            Verwacht resultaat: 1. Er wordt aan het verzoek tot het plaatsen van een afnemerindicatie, GEEN gevolg gegeven en krijgt de verzoekende Partij
                                een foutmelding retour. Persoon met verstrekkingsbeperking voor die afnemer wordt derhalve niet geleverd.
                                2. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                -  Hoogste meldings niveau = fout
                                -  Melding = De persoon heeft een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.
                                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                                3. Er wordt niet geleverd voor deze persoon

Given de personen 299054457, 743274313, 148047117 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 148047117 en anr 8704769298  met extra gebeurtenissen:
verstrekkingsbeperking() {
    registratieBeperkingen( partij: 502707 )
}

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_afn_ind/Mutaties_op_specifieke_personen_voor_afnemer_is_502707
Given verzoek voor leveringsautorisatie 'Mutaties op specifieke personen voor afnemer is 502707' en partij 'KUC033-PartijVerstrekkingsbeperking'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.2_Plaats_afnemerindicatie_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                                          |
| BRLV0031 | De persoon heeft een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden. |
And heeft in het antwoordbericht 'partijCode' in 'plaatsingAfnemerindicatie' de waarde '502707'

Then is er geen synchronisatiebericht gevonden

Scenario:   6. Afnemer plaatst afnemerindicatie op een persoon, deze persoon heeft een volledige verstrekkingsbeperking.
            Logische testgevallen Use Case:  R1339_04, R1342_01
            Verwacht resultaat: 1. Er wordt aan het verzoek tot het plaatsen van een afnemerindicatie, GEEN gevolg gegeven en krijgt de verzoekende Partij
                                een foutmelding retour. Persoon met volledige verstrekkingsbeperking wordt derhalve niet geleverd.
                                2. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                -  Hoogste meldings niveau = fout
                                -  Melding = De persoon heeft een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.
                                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                                3. Er wordt niet geleverd voor deze persoon

Given de personen 299054457, 743274313, 148047117 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 148047117 en anr 8704769298  met extra gebeurtenissen:
verstrekkingsbeperking() {
 volledig ja
}

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_afn_ind/Mutaties_op_specifieke_personen_voor_afnemer_is_502707
Given verzoek voor leveringsautorisatie 'Mutaties op specifieke personen voor afnemer is 502707' en partij 'KUC033-PartijVerstrekkingsbeperking'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.2_Plaats_afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'

And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                                    |
| BRLV0031 | De persoon heeft een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden. |

And heeft in het antwoordbericht 'partijCode' in 'plaatsingAfnemerindicatie' de waarde '502707'

Then is er geen synchronisatiebericht gevonden