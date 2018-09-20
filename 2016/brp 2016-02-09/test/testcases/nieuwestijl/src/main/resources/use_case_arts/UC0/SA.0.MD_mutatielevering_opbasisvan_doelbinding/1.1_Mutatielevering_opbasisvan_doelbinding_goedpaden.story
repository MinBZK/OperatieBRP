Meta:
@epic               Verbeteren testtooling
@auteur             jowil
@status             Klaar
@usecase            SA.0.MD
@regels             R1316,R1333,R1343,R1348,R1989,R1990,R1993,R1994,R2000,R2001,R2002
@sleutelwoorden     Mutatielevering o.b.v. doelbinding

Narrative:
   Om correct te kunnen leveren aan de hand van een administratieve handeling.
    Als een afnemer wil ik een mutatiebericht ontvangen als een persoon bij Mutatielevering op doebinding "in de doelbinding" blijft
    'uit de doelbinding' gaat, en 'nieuw in doelbinding' komt.

Scenario:   1.1 Mutatiebericht als persoon door verhuizing in de doelbinding blijft.
               Persoon geboren binnen bepaald abonnement met doelbinding op postcode. Persoon verhuist binnen de gemeente.
            Logisch testgeval: R1316_03, R1333_01, R1348_06, R2000_01
            Verwacht resultaat: Leveringsbericht
                Met vulling:
                -  Soort bericht = Mutatiebericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given relevante abonnementen postcode gebied Haarlem 2000 - 2099
Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen

Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 1.2_Mutatielevering_opbasisvan_doelbinding_verhuizing1.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor abonnement postcode gebied Haarlem 2000 - 2099 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   1.2 Mutatiebericht als persoon door verhuizing in de doelbinding blijft.
               Persoon geboren binnen bepaald abonnement met doelbinding op postcode. Persoon verhuist binnen de gemeente.
            Logisch testgeval: R1316_03, R1333_01, R1348_08, R2000_01
            Verwacht resultaat: Leveringsbericht
                Met vulling:
                -  Soort bericht = Mutatiebericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given relevante abonnementen postcode gebied Haarlem 2000 - 2099
Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 met extra gebeurtenissen:
    verhuizing(partij: 'Gemeente Haarlem', aanvang: 20151214) {
                naarGemeente 'Haarlem',
                    straat: 'Plein', nummer: 64, postcode: '2000AA', woonplaats: "Haarlem"
        }

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor abonnement postcode gebied Haarlem 2000 - 2099 is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario:   2  Mutatiebericht als persoon uit doelbinding gaat.
               UC_kenny is geboren buiten doelbinding van het abonnement postcode gebied Haarlem 2000-2099, maar daarna verhuist
               naar binnen de doelbinding van het abonnement postcode gebied Haarlem 2000-2099 (administratieve handeling P)
               In dit testscenario verhuist UC_Kenny naar buiten de doelbinding (administratieve handeling Q)
               Op abonnement postcode gebied Haarlem 2000-2099 wordt voor de laatste keer een mutatiebericht ontvangen, met de waarschuwing
               dat de persoon vanaf nu buiten de doelbinding valt.
            Logisch testgeval: R1316_01, R1333_02, R2000_01
            Verwacht resultaat: Leveringsbericht
                Met vulling:
                -  Soort bericht = Mutatiebericht
                -  Waarschuwing = De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt.
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given relevante abonnementen postcode gebied Haarlem 2000 - 2099
Given de personen 299054457, 743274313, 692223113 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 692223113 en anr 4943734802 zonder extra gebeurtenissen

Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 1.3_Mutatielevering_opbasisvan_doelbinding_verhuizing2.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor abonnement postcode gebied Haarlem 2000 - 2099 is ontvangen en wordt bekeken
Then is het bericht xsd-valide
And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| melding	            | 1         | soortNaam 	        | Waarschuwing    |
| melding	            | 1         | melding    	        | De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt.    |


Scenario:   3 Persoon verhuist naar een andere gemeente, deze gemeente krijgt een volledig bericht, persoon komt nieuw in doelbinding.
            Logisch testgeval: R1348_01, R2000_01
            Verwacht resultaat: Leveringsbericht
                Met vulling:
                -  Soort bericht = Volledig bericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given relevante abonnementen postcode gebied Heemstede 2100-2129
Given de personen 299054457, 743274313, 671144649 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 671144649 en anr 7205730578 zonder extra gebeurtenissen

Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 1.4_Mutatielevering_opbasisvan_doelbinding_verhuizing4.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie postcode gebied Heemstede 2100-2129 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   4 Persoon verhuist naar een andere gemeente, deze gemeente krijgt een volledig bericht, persoon komt nieuw in doelbinding.
              Hierbij is de populatiebeperking van het bijbehorende voorgaande abonnement op NULL.
            Logisch testgeval: R1348_02, R2000_01
            Verwacht resultaat: Leveringsbericht
                Met vulling:
                -  Soort bericht = Volledig bericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given relevante abonnementen Abo op basis van geboortedatum persoon
Given de database is gereset voor de personen 360381169
And administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 1.5_Mutatielevering_opbasisvan_doelbinding_verhuizing6.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor abonnement Abo op basis van geboortedatum persoon is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   5 Persoon wordt geboren, deze gemeente krijgt een volledig bericht, persoon komt nieuw in doelbinding.
              Er zijn geen voorafgaande handelingen. Persoon wordt geboren en komt nieuw in doelbinding.
            Logisch testgeval: R1348_03, R2000_01
            Verwacht resultaat: Leveringsbericht
                Met vulling:
                -  Soort bericht = Volledig bericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd


Given relevante abonnementen postcode gebied Haarlem 2000 - 2099
Given de personen 299054457, 743274313, 671144649 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 671144649 en anr 7205730578 zonder extra gebeurtenissen
When voor persoon 671144649 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie postcode gebied Haarlem 2000 - 2099 is ontvangen en wordt bekeken

