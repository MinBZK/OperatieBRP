Meta:
@auteur                 aapos
@regels                 VR00116,R2063,R2065,R1319,R1562
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus18
@status                 Klaar

Narrative: Casus 18. Een administratieve handeling die het onderzoek naar een groep beëindigd, waarvoor de afnemer slechts voor een aantal specifieke attributen binnen die groep is geautoriseerd.

Scenario: 1. leveren en beeindig onderzoek naar een groep waarvoor slechts 1 attribuut is geautoriseerd

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 792937417 zijn verwijderd
Given de standaardpersoon Olivia met bsn 792937417 en anr 4919794962 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(792937417)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Casus 18. Wijziging onderzoek op een aanwezig gegeven', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)

When voor persoon 792937417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut   | verwachteWaarde        |
| synchronisatie     | 1      | naam        | Aanvang onderzoek      |
| onderzoek          | 2      | statusNaam  | In uitvoering          |

And heeft het bericht 1 groepen 'gegevenInOnderzoek'

Scenario: 2. Beeindigen onderzoek waarvoor slechts 1 attribuut is geautoriseerd

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(792937417)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20151001) {
        afgeslotenOp(eindDatum:'2015-10-01')
    }
}
slaOp(persoon)

When voor persoon 792937417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep          | nummer | attribuut  | verwachteWaarde       |
| synchronisatie | 1      | naam       | Beeindiging onderzoek |
| onderzoek      | 2      | statusNaam | Afgesloten            |
