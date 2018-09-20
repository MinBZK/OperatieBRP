Meta:
@epic               Verbeteren testtooling
@auteur             kedon
@status             Onderhanden
@usecase            LV.0.AV
@regels             R1257, R1259, R1260, R1261, R1262, R1263, R1264, R1265, R1410, R1777, R1991
@sleutelwoorden     Afhandeling verzoek

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: 1     Afhandeling verzoek voor scenario 1 Plaatsen Afnemerindicatie
                Plaatsen afnemerindicatie met datum aanvang materiele periode is kleiner dan systeem datum EN datum einde volgen = gevuld
                            Logische testgevallen Use Case: R1257_01, R1258_01, R1259_01, R1260_01, R1261_01, R1262_01, R1263_01, R1264_01, R1265_01, R1777_01,
                                                            R1410_01, R1991_01

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand PA_datumAanvanMaterielePeriode_en_datumEindeVolgen_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
And heeft in het antwoordbericht 'partijCode' in 'plaatsingAfnemerindicatie' de waarde '017401'

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide





