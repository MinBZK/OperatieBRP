Meta:
@sprintnummer           76
@epic                   Verbeteren testtooling
@auteur                 rarij
@jiraIssue              TEAMBRP-3080
@status                 Klaar
@sleutelwoorden         Intaketest, Mutatielevering, Automatisch_volgen

Narrative:  Als team BRP
            wil ik een geautomatiseerde intaketest die moet aantonen dat de functionaliteit, bij oplevering, aan de minimale eisen voldoet
            zodat ik kan bepalen of deze wel/niet testgereed is

Scenario:   1.  Hoofdpersoon zit niet in doelbinding van abonnement postcode gebied Heemstede 2100-2129
                Verwacht resultaat:
                - Geen levering daar persoon niet in doelbinding zit

Given de personen 875271467, 814591139, 690528978 zijn verwijderd
Given leveringsautorisatie uit /levering_autorisaties/postcode_gebied_heemstede_2100-2129
And de standaardpersoon Gregory met bsn 690528978 en anr 6191207698 zonder extra gebeurtenissen
When voor persoon 690528978 wordt de laatste handeling geleverd
And het volledigbericht voor leveringsautorisatie postcode gebied Heemstede 2100-2129 is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden


Scenario:   2.  Hoofdpersoon komt, middels een intergemeentelijk verhuizing, in doelbinding van 'abonnement postcode gebied Heemstede 2100-2129'
                Verwacht resultaat:
                - Deze dienst levert een volledigbericht als na een bijhouding blijkt dat een persoon, als gevolg van die bijhouding,
                  is gaan horen bij de populatie die de afnemer wenst te volgen

Given de personen 875271467, 814591139, 690528978 zijn verwijderd
Given de standaardpersoon Gregory met bsn 690528978 en anr 6191207698 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Heemstede', aanvang: 20101010, registratieDatum: 20101010) {
    naarGemeente 'Heemstede',
       straat: 'Manpadslaan', nummer: 4, postcode: '2105MA', woonplaats: "Heemstede"
}

When voor persoon 690528978 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie postcode gebied Heemstede 2100-2129 is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/intake/expected_levering_berichten/expected_scenario_2.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario:   3.  Hoofdpersoon blijft, middels een binnen-gemeentelijke verhuizing, in doelbinding van 'abonnement postcode gebied Heemstede 2100-2129'
                Verwacht resultaat:
                - Deze dienst bestaat uit het leveren van berichten naar de afnemer als er een bijhouding heeft plaatsgevonden
                  bij een persoon die behoort tot de te volgen populatie


Given de personen 875271467, 814591139, 690528978 zijn verwijderd
Given de standaardpersoon Gregory met bsn 690528978 en anr 6191207698 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Heemstede', aanvang: 20101010, registratieDatum: 20101010) {
    naarGemeente 'Heemstede',
       straat: 'Manpadslaan', nummer: 4, postcode: '2105MA', woonplaats: "Heemstede"
}

verhuizing(aanvang: 20120101, registratieDatum: 20120101) {
    binnenGemeente straat: 'Manpadslaan', nummer: 14, postcode: '2105QQ', woonplaats: "Heemstede"
}


When voor persoon 690528978 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie postcode gebied Heemstede 2100-2129 is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/intake/expected_levering_berichten/expected_scenario_3.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario:   4.  Hoofdpersoon verlaat, middels een intergemeentelijke verhuizing, in doelbinding van 'abonnement postcode gebied Heemstede 2100-2129'
                Verwacht resultaat:
                - Als na een bijhouding blijkt dat een persoon die gevolgd werd niet langer tot de populatie behoort, dan stuurt deze dienst nog
                  eenmaal een laatste mutatie bericht.
                - Dit laatste bericht hebben afnemers vaak nodig om lopende zaken met de persoon af te kunnen ronden


Given de personen 875271467, 814591139, 690528978 zijn verwijderd
Given de standaardpersoon Gregory met bsn 690528978 en anr 6191207698 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Heemstede', aanvang: 20101010, registratieDatum: 20101010) {
    naarGemeente 'Heemstede',
       straat: 'Manpadslaan', nummer: 4, postcode: '2105MA', woonplaats: "Heemstede"
}

verhuizing(partij: 'Gemeente Haarlem', aanvang: 20120101, registratieDatum: 20120101) {
    naarGemeente 'Haarlem',
       straat: 'Dorpstraat', nummer: 15, postcode: '2000AA', woonplaats: "Haarlem"
}

When voor persoon 690528978 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie postcode gebied Heemstede 2100-2129 is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/intake/expected_levering_berichten/expected_scenario_4.xml voor expressie //brp:lvg_synVerwerkPersoon