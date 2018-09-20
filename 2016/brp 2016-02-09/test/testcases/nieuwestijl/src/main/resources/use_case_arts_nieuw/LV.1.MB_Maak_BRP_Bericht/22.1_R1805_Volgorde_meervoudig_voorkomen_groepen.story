Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1805
@sleutelwoorden     Maak BRP bericht

Narrative:

Een aantal gegevens kunnen bij een Persoon gelijktijdig meermalen aanwezig zijn, zoals voornamen en nationaliteiten. In uitgaande berichten willen we deze gegevens zo sorteren, dat gegevens die logisch bij elkaar horen ook bij elkaar blijven. Pas daarbinnen willen de normale sortering op groepen toepassen, zoals beschreven in R1812 - Verwerken Groep Samengestelde naam.

Dit betekent dat de volgende objecttypen eerst (oplopend) gesorteerd worden op de bijbehorende logische kenmerken:

Persoon \ Geslachtsnaamcomponent.Identiteit op Persoon \ Geslachtsnaamcomponent.Volgnummer
Persoon \ Indicatie.Identiteit op Persoon \ Indicatie.Soort
Persoon \ Nationaliteit.Identiteit op Persoon \ Nationaliteit.Nationaliteit
Onderzoek op Onderzoek.Datum aanvang
Persoon \ Verificatie.Identiteit op Persoon \ Verificatie.Partij en daarbinnen op Persoon \ Verificatie.Soort
Persoon \ Voornaam.Identiteit op Persoon \ Voornaam.Volgnummer

Dit met volgende sorteer-regels:
Onbekende delen (00) in een datum worden gesorteerd volgens de normale systematiek voor getallen

Scenario: 1 sortering meervoudige voorkomende groepen in bericht
            Logisch testgeval: R1805_01
            Verwacht resultaat: groep 		                | attribuut            | verwachteWaardes
                                nationaliteit               | nationaliteitCode    | 0001,0001,0001,0001,0001,0027
                                geslachtsnaamcomponent      | volgnummer           | 1,2,2,2
                                voornaam                    | volgnummer           | 1,2,3,3,3
                                verificatie                 | partijCode           | 199900,199900
                                verificatie                 | soort                | Attestie de vita,SoortVerificatie
                                onderzoek                   | datumAanvang         | 0000,2011,2011-12-31,2012-04

Given de database is gereset voor de personen 340014155

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 21.2_R1805_Plaats_afnemerindicatie.yml

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(340014155)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20110000', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(340014155)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(340014155)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20120400', omschrijving:'Onderzoek is gestart op huisletter', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisletter')
    }
}
slaOp(persoon)

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(340014155)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'000000', omschrijving:'Onderzoek is gestart op woonplaatsnaam', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Woonplaatsnaam')
    }
}
slaOp(persoon)

Given de database is aangepast met: update kern.persverificatie set partij = 2 where srt = 'Attestie de vita' AND geverifieerde = (select id from kern.pers where bsn = 340014155)

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
groep 		                | attribuut            | verwachteWaardes
nationaliteit               | nationaliteitCode    | 0001,0001,0001,0001,0001,0027
geslachtsnaamcomponent      | volgnummer           | 1,2,2,2
voornaam                    | volgnummer           | 1,2,3,3,3
verificatie                 | partijCode           | 199900,199900
verificatie                 | soort                | Attestie de vita,SoortVerificatie
onderzoek                   | datumAanvang         | 0000,2011,2011-12-31,2012-04

Scenario: 2 sortering meervoudige voorkomende groepen in bericht op verificatie partijcode
            Logisch testgeval: R1805_01
            Verwacht resultaat
            groep 		                | attribuut            | verwachteWaardesVerwacht resultaat:
            verificatie                 | partijCode           | 199900,??
            verificatie                 | soort                | SoortVerificatie, Attestie de vita,

Given de database is gereset voor de personen 340014155

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 21.2_R1805_Plaats_afnemerindicatie.yml

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(340014155)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20110000', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(340014155)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(340014155)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20120400', omschrijving:'Onderzoek is gestart op huisletter', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisletter')
    }
}
slaOp(persoon)

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(340014155)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'000000', omschrijving:'Onderzoek is gestart op woonplaatsnaam', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Woonplaatsnaam')
    }
}
slaOp(persoon)


Given de database is aangepast met: update kern.persverificatie set partij = 2434 where srt = 'Attestie de vita' AND geverifieerde = (select id from kern.pers where bsn = 340014155)

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
groep 		                | attribuut            | verwachteWaardes
verificatie                 | partijCode           | 199900,628601
verificatie                 | soort                | SoortVerificatie,Attestie de vita
