Meta:
@sprintnummer           76
@epic                   Afhankelijkheden migratie
@auteur                 luwid
@jiraIssue              TEAMBRP-2866
@status                 Klaar
@regels                 R1316, BRLV0028, VR00057, R1333

Narrative:  Als afnemer
            wil ik dat voor een persoon die de doelbinding verlaat vanwege een GBA bijhouding een volledigbericht in plaats van mutatiebericht wordt
            verstuurd, met daarin een melding dat de persoon de doelbinding heeft verlaten
            zodat ik gewaarschuwd ben als de betreffende persoon de doelbinding verlaat.

            R1333	VR00057	Mutatielevering op personen binnen doelgroep abonnement
            R1316	BRLV0028	Waarschuwing als persoon uit doelgroep abonnement valt


Scenario:   1. Een persoon verlaat de doelbinding vanwege een GBAbijhouding waarvan de Soort Administratieve Handeling gelijk is aan 99997 (verhuizing
naarGemeente).
    Verwacht resultaat:
    - Een volledigBericht i.p.v. een mutatieBericht met melding: 'De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering
    voor deze persoonslijst is gestopt.'

Given leveringsautorisatie uit /levering_autorisaties/postcode_gebied_Haarlem_2000-2099
Given de database is gereset voor de personen 814591139, 875271467
Given de personen 192235977 zijn verwijderd

Given de standaardpersoon Gregory met bsn 192235977 en anr 2763926162 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Haarlem', aanvang: 19930731, registratieDatum: 19930731) {
    naarGemeente 'Haarlem',
        straat: 'Dorpstraat', nummer: 13, postcode: '2000AA', woonplaats: "Haarlem"
}
GBABijhoudingOverig(partij: 'Gemeente Gorinchem', aanvang: 20150531, registratieDatum: 20150531) {
    naarGemeente 'Gorinchem',
               straat: 'Stationsweg', nummer: 21, postcode: '4207AA', woonplaats: "Gorinchem"
}

When voor persoon 192235977 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie postcode gebied Haarlem 2000 - 2099 is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                 | attribuut           | verwachteWaardes     |
| identificatienummers  | burgerservicenummer | 192235977, 814591139, 875271467   |
| melding               | regelCode           | BRLV0028         |
| melding               | soortNaam           | Waarschuwing     |
| melding               | melding             | De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt.|

When het mutatiebericht voor leveringsautorisatie postcode gebied Haarlem 2000 - 2099 is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
