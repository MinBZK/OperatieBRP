Meta:
@auteur                 dihoe
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus20
@status                 Klaar
@regels                 VR00116,R2063,R2065,R1563,R1962,R1973

Narrative: Casus 20. Een administratieve handeling dat een ontbrekend gegeven toevoegt aan de persoonslijst.
                     Er is sprake van een lopend onderzoek naar een ontbrekend gegeven (gelijk aan de opgevoerde groep).

Scenario: 1. Er is een lopend onderzoek naar een ontbrekend gegeven, Dit gegeven wordt middels een AH opgevoerd

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 987281161, 516503625, 875271467, 814591139 zijn verwijderd
Given de standaardpersoon Olivia met bsn 987281161 en anr 9610531602 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(987281161)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op datum aanvang huwelijk/partnerschap en geboortedatum', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('HuwelijkGeregistreerdPartnerschap.DatumAanvang')
    }
}
slaOp(persoon)

When voor persoon 987281161 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 2. Er is een lopend onderzoek naar een ontbrekend gegeven, Dit gegeven wordt middels een AH opgevoerd, het mutatie bericht bevat geen onderzoek

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de standaardpersoon Gregory met bsn 516503625 en anr 2372318354 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(987281161)
def Johnny      = uitDatabase bsn: 516503625

nieuweGebeurtenissenVoor(persoon) {
    huwelijk(registratieDatum: 20150501) {
                  op 20150501 te 'Delft' gemeente 'Delft'
                  met Johnny
        }
}
slaOp(persoon)

When voor persoon 987281161 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep          | nummer | attribuut    | aanwezig |
| onderzoeken    | 1      | onderzoek    | nee      |


