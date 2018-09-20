Meta:
@auteur                 aapos
@regels                 VR00116,R2063,R2065,R1319,R1562
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus4
@status                 Klaar

Narrative: Casus 4. Een administratieve handeling die een bestaand gegeven in onderzoek plaatst, waarvoor de afnemer niet is geautoriseerd.

Scenario: 1. start onderzoek op aanwezig gegeven zonder attribuut autorisatie, er wordt niet geleverd

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 562437897 zijn verwijderd
Given de standaardpersoon Olivia met bsn 562437897 en anr 2029101010 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(562437897)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Casus 4. Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)

When voor persoon 562437897 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
When het volledigbericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

