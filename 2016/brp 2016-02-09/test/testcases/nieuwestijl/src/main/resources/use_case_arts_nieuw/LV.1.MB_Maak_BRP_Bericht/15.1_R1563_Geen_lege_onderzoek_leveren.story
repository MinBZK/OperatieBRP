Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1563
@sleutelwoorden     Maak BRP bericht


Narrative:
In het te leveren resultaat mogen geen lege Onderzoeken voorkomen. Dat wil zeggen: als er bij een Onderzoek geen enkel te leveren Gegeven in onderzoek meer aanwezig is, dan dient de hele groep uit het resultaat verwijderd te worden.
Als er geen enkele Groep Onderzoek meer resteert, dan zal het Onderzoeksdeel binnen het bericht niet meer aanwezig zijn.

Zie ook 'Onderzoeksgroep' (R1543) voor de definitie van de "Onderzoeksgroep" en de daarin gehanteerde begrippen.

Scenario: 1.1   Twee onderzoeken gestart, geen onderzoeken afgesloten
                Logisch testgeval: R1563_01
                Verwacht resultaat: 2 onderzoeken in bericht

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de personen 299054457, 743274313, 449809353 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 449809353 en anr 2476721810 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.5_R1544_plaats_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(UC_Kenny)

When voor persoon 449809353 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 1.2    Twee onderzoeken gestart, geen onderzoeken afgesloten
                Logisch testgeval: R1563_01
                Verwacht resultaat: 2 onderzoeken in bericht

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 6.6_R1544_Synchroniseer_Persoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 4 groepen 'onderzoek'

Scenario: 2.1    Twee onderzoeken gestart, 1 onderzoek afgesloten
                Logisch testgeval: R1563_02
                Verwacht resultaat: 1 onderzoek in bericht

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de personen 299054457, 743274313, 449809353 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 449809353 en anr 2476721810 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.5_R1544_plaats_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
    onderzoek(partij: 17401, registratieDatum: 20150324) {
        afgeslotenOp(eindDatum:'2015-03-24')
    }
}
slaOp(UC_Kenny)

When voor persoon 449809353 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 2.2   Twee onderzoeken gestart, 1 onderzoek afgesloten
                Logisch testgeval: R1563_02
                Verwacht resultaat: 1 onderzoek in bericht
                Bevinding: Het afgesloten (LEGE) onderzoek wordt geleverd in volledig bericht
                JIRA ISSUE TEAMBRP-4722
Meta:
@status     Bug

!-- Volgens mij klopt het dat het onderzoek geleverd wordt in het volledig bericht, de voorkomensleutel verwijst naar een aanwezig gegeven en dan wordt deze niet weggefilterd

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 6.6_R1544_Synchroniseer_Persoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 1 groepen 'onderzoek'

Scenario: 3.1    Twee onderzoeken gestart,beide afgesloten
                Logisch testgeval: R1563_03
                Verwacht resultaat: 0 onderzoek in bericht
Meta:
@status     Onderhanden
!-- Het afsluiten van het onderzoek is ook opgenomen in een volledig bericht, volgens mij is het correct dat deze ook geleverd worden
Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de personen 299054457, 743274313, 449809353 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 449809353 en anr 2476721810 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.5_R1544_plaats_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
 }
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
 }
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20150324) {
            afgeslotenOp(eindDatum:'2015-03-24')
    }
    onderzoek(partij: 17401, registratieDatum: 20150324) {
            afgeslotenOp(eindDatum:'2015-03-24')
    }
 }
slaOp(UC_Kenny)


When voor persoon 449809353 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 3.2    Twee onderzoeken gestart,beide afgesloten
                Logisch testgeval: R1563_03
                Verwacht resultaat: 0 onderzoek in bericht

Meta:
@status     Onderhanden
!-- waarom wordt hier verwacht dat er geen onderzoek groepen geleverd worden?

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 6.6_R1544_Synchroniseer_Persoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And heeft het bericht 0 groepen 'onderzoek'