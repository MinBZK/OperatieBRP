Meta:
@auteur                 aapos
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus14
@status                 Klaar
@regels                 VR00114,R1563,R1561

Narrative: Casus 14. Een administratieve handeling die een onderzoek beëindigd op een ontbrekend gegeven.

Scenario: 1. Beeindiging onderzoek naar een ontbrekend gegeven, er wordt NIET geleverd

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 411372233 zijn verwijderd
Given de standaardpersoon Olivia met bsn 411372233 en anr 1086049514 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(411372233)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op datum aanvang huwelijk/partnerschap', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('HuwelijkGeregistreerdPartnerschap.DatumAanvang')
    }
}
slaOp(persoon)

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(411372233)
nieuweGebeurtenissenVoor(persoon) {
   onderzoek(partij: 59401, registratieDatum: 20151012) {
           afgeslotenOp(eindDatum:'2015-10-12')
       }
}
slaOp(persoon)

When voor persoon 411372233 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

