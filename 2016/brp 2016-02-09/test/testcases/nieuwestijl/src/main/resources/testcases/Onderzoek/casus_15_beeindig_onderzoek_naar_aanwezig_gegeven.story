Meta:
@auteur                 aapos
@regels                 VR00116,R2063,R2065,R1319,R1562
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus17
@status                 Klaar

Narrative: Casus 15. Een administratieve handeling die een onderzoek beëindigd op een bestaand gegeven.

Scenario: 1. Er wordt een onderzoek gestart naar geboortedatum, vervolgens wordt het onderzoek afgesloten,
             Verwacht is dat er een mutatie bericht wordt ontvangen met het afgesloten onderzoek

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 459232617 zijn verwijderd
Given de standaardpersoon Olivia met bsn 459232617 en anr 7484567314 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(459232617)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Casus 15. Wijziging onderzoek op een aanwezig gegeven', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
}
slaOp(persoon)

When voor persoon 459232617 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(459232617)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20151001) {
        afgeslotenOp(eindDatum:'2015-10-01')
    }
}
slaOp(persoon)

When voor persoon 459232617 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide

