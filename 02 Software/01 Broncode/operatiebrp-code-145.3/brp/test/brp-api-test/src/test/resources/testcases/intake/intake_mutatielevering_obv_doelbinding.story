Meta:
@sprintnummer           76
@epic                   Verbeteren testtooling
@status                 Klaar
@sleutelwoorden         Intaketest, Mutatielevering, Automatisch_volgen

Narrative:  Als team BRP
            wil ik een geautomatiseerde intaketest die moet aantonen dat de functionaliteit, bij oplevering, aan de minimale eisen voldoet
            zodat ik kan bepalen of deze wel/niet testgereed is

Scenario:   1.  Hoofdpersoon zit niet in doelbinding van abonnement postcode gebied Heemstede 2100-2129
                LT:
                Verwacht resultaat:
                - Geen levering daar persoon niet in doelbinding zit

Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C10T10_xls
Given leveringsautorisatie uit autorisatie/postcode_gebied_rotterdam_3000-3010
!-- And de standaardpersoon Gregory met bsn 690528978 en anr 6191207698 zonder extra gebeurtenissen
When voor persoon 270433417 wordt de laatste handeling geleverd
!-- And het volledigbericht voor leveringsautorisatie postcode gebied Heemstede 2100-2129 is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden


Scenario:   2.  Hoofdpersoon komt, middels een intergemeentelijk verhuizing, in doelbinding van 'abonnement postcode gebied Rotterdam 3000-3010'
                LT:
                Verwacht resultaat:
                - Deze dienst levert een volledigbericht als na een bijhouding blijkt dat een persoon, als gevolg van die bijhouding,
                  is gaan horen bij de populatie die de afnemer wenst te volgen

Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T70_xls
Given leveringsautorisatie uit autorisatie/postcode_gebied_rotterdam_3000-3010
!-- Given de standaardpersoon Gregory met bsn 690528978 en anr 6191207698 met extra gebeurtenissen:
!-- verhuizing(partij: 'Gemeente Heemstede', aanvang: 20101010, registratieDatum: 20101010) {
!--     naarGemeente 'Heemstede',
!--        straat: 'Manpadslaan', nummer: 4, postcode: '2105MA', woonplaats: "Heemstede"
!-- }

When voor persoon 383096777 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie postcode gebied Rotterdam 3000-3010 is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/intake/expected_levering_berichten/expected_scenario_2.xml voor expressie //brp:lvg_synVerwerkPersoon
