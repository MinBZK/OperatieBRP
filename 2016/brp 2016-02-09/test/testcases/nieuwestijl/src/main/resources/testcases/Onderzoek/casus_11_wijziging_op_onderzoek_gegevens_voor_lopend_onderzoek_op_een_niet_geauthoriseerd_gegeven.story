Meta:
@auteur                 aapos
@regels                 VR00116,R2063,R2065,R1319,R1562
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus11
@status                 Klaar

Narrative: Casus 11. Een administratieve handeling die de onderzoeksgegevens wijzigt op een ongeautoriseerd gegeven
Scenario: 1. Er wordt een onderzoek gestart op huisnummer, vervolgens wordt de verwachte afhandeldatum voor het onderzoek gewijzigd,
             verwacht is dat er geen Mutatie bericht wordt ontvangen voor het abonnement dat geen autorisatie heeft op huisnummer

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 630151593 zijn verwijderd
Given de standaardpersoon Olivia met bsn 630151593 en anr 4313036562 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(630151593)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Casus 11. Wijziging onderzoek op een aanwezig gegeven', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)

When voor persoon 630151593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(630151593)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20150301) {
        wijzigOnderzoek(wijzigingsDatum:'2015-03-01', omschrijving:'Casus 11. Onderzoek gewijzigd', aanvangsDatum: '2015-01-01', verwachteAfhandelDatum: '2015-12-31')
    }
}
slaOp(persoon)

When voor persoon 630151593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
When het volledigbericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

