Meta:
@auteur                 aapos
@regels                 R2063,R2065,R1319,R1562,R2051
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus5
@status                 Klaar

Narrative: Casus 5. Een administratieve handeling die 2 voorkomens in onderzoek plaatst, waarvoor de afnemer op 1 niet is geautoriseerd.

Scenario: 1. Er wordt een onderzoek gestart op huisnummer en geboortedatum, voor het mutatiebericht geldt dat er enkel autorisatie is voor geboortedatum

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 225252089 zijn verwijderd
Given de standaardpersoon Olivia met bsn 225252089 en anr 5817201938 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(225252089)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Casus 5. Onderzoek is gestart op huisnummer en geboortedatum', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
        gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
}
slaOp(persoon)

When voor persoon 225252089 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide
