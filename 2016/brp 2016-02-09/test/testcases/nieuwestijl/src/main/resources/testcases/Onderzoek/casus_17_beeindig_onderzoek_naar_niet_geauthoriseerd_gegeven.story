Meta:
@auteur                 aapos
@regels                 VR00116,R2063,R2065,R1319,R1562
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus17
@status                 Klaar

Narrative: Casus 17. Een administratieve handeling dat een onderzoek beëindigd, waarvoor de afnemer niet is geautoriseerd.

Scenario: 12. Er wordt een onderzoek gestart naar het huisnummer, vervolgens wordt het onderzoek afgesloten,
              Verwacht is dat er geen mutatie bericht wordt ontvangen met het afgesloten onderzoek

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 557892041 zijn verwijderd
Given de standaardpersoon Olivia met bsn 557892041 en anr 6193070354 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(557892041)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Casus 11. Wijziging onderzoek op een aanwezig gegeven', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)

When voor persoon 557892041 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(557892041)
nieuweGebeurtenissenVoor(persoon) {
     onderzoek(partij: 34401, registratieDatum: 20151001) {
            afgeslotenOp(eindDatum:'2015-10-01')
        }
}
slaOp(persoon)

When voor persoon 557892041 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
When het volledigbericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

