Meta:
@auteur dihoe
@status Onderhanden
@regels diana11

Narrative:
In order to correct kunnen leveren aan de hand van een administratieve handeling
As a afnemer
I want to een vulbericht ontvangen als een persoon bij Mutatielevering op doebinding "nieuw in doelbinding" komt

Scenario: Een persoon is verhuisd naar een nieuwe gemeente, afnemer krijgt een vulbericht omdat hij hiervoor een abonnement heeft

Given de database is gereset voor de personen 677080426
Given de persoon beschrijvingen:
def testpersoon = uitDatabase persoon: 1003
def tienDagenTerug = (new Date() - 10).format('yyyy/MM/dd')

nieuweGebeurtenissenVoor(testpersoon) {
    verhuizing(partij: 'Gemeente Haarlem', aanvang: tienDagenTerug) {
        naarGemeente 'Haarlem',
           straat: 'Dorpstraat', nummer: 13, postcode: '2000AA', woonplaats: "Haarlem"
    }
}

slaOp(testpersoon)

When voor persoon 677080426 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie postcode gebied Haarlem 2000 - 2099 is ontvangen en wordt bekeken
Then is het bericht xsd-valide
