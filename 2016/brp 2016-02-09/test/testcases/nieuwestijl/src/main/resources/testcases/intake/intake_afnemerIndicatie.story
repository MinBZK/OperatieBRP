Meta:
@sprintnummer           76
@epic                   Verbeteren testtooling
@auteur                 dihoe
@jiraIssue              TEAMBRP-3080
@status                 Klaar
@sleutelwoorden         Intaketest, Mutatielevering, Afnemerindicatie, Attendering
@regels dianaintake

Narrative: Intake test afnemerindicatie

Scenario: 1a. Attendering met plaatsen afnemerindicatie, geen volledigbericht van voldoet niet aan attenderingscriterium

Given de personen 875271467, 814591139, 999646564 zijn verwijderd
Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie, /levering_autorisaties/postcode_gebied_heemstede_2100-2129_en_afnemerindicatie
Given de standaardpersoon Gregory met bsn 999646564 en anr 2671798546 zonder extra gebeurtenissen

When voor persoon 999646564 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie wordt bekeken
Then is er geen synchronisatiebericht gevonden


Scenario: 1b. Attendering met plaatsen afnemerindicatie, volledigbericht na voldoen aan attenderingscriterium
Given de persoon beschrijvingen:
def greg = uitDatabase bsn: 999646564
Persoon.nieuweGebeurtenissenVoor(greg) {
    verbeteringGeboorteakte(partij: 34401, aanvang: 20150103, toelichting:'Correctie wplGeboorte', registratieDatum: 20150103){
        op '1980/01/01' te 'Giessenlanden' gemeente 689
    }
}
slaOp(greg)

When voor persoon 999646564 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When het volledigbericht voor partij 028101 en leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/intake/expected_afnemerindicatie_berichten/expected_scenario_1b.xml voor expressie //brp:lvg_synVerwerkPersoon
When mutatiebericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie wordt bekeken
Then is er geen synchronisatiebericht gevonden


Scenario: 1c. Attendering met plaatsen afnemerindicatie, mutatiebericht bij in doelbinding blijven
Given de persoon beschrijvingen:
def greg  = uitDatabase bsn: 999646564
Persoon.nieuweGebeurtenissenVoor(greg) {
    naamswijziging(aanvang: 20150203, registratieDatum: 20150203) {
          geslachtsnaam(1) wordt stam:'Zonnig', voorvoegsel:'het'
    }
}
slaOp(greg)

When voor persoon 999646564 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When mutatiebericht voor partij 028101 en leveringsautorisatie Attendering met plaatsing afnemerindicatie wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/intake/expected_afnemerindicatie_berichten/expected_scenario_1c.xml voor expressie //brp:lvg_synVerwerkPersoon
When volledigbericht voor partij 028101 en leveringsautorisatie Attendering met plaatsing afnemerindicatie wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 2a. Plaatsen afnemerindicatie, niet leveren als indicatie niet bestaat
    - abo heeft dienst mutatielevering op afnemerindicatie
Given de personen 875271467, 814591139, 999646564 zijn verwijderd
Given de standaardpersoon Gregory met bsn 999646564 en anr 2671798546 zonder extra gebeurtenissen

When voor persoon 999646564 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie postcode gebied Heemstede 2100-2129 EN afnemerindicatie wordt bekeken
Then is er geen synchronisatiebericht gevonden
When mutatiebericht voor leveringsautorisatie postcode gebied Heemstede 2100-2129 EN afnemerindicatie wordt bekeken
Then is er geen synchronisatiebericht gevonden

Given verzoek voor leveringsautorisatie 'postcode gebied Heemstede 2100-2129 EN afnemerindicatie' en partij 'Gemeente Tiel'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Scenario: 2b. Plaatsen afnemerindicatie, niet leveren als indicatie niet bestaat
    - abo heeft dienst mutatielevering op afnemerindicatie en onderhoud afnemerindicatie
    - na plaatsen van de afnemerindicatie wordt een volledig bericht uit dienst 'onderhoud afnemerindicatie' ontvangen
Given de persoon beschrijvingen:
def greg  = uitDatabase bsn: 999646564
Persoon.nieuweGebeurtenissenVoor(greg) {
    verhuizing(partij: 'Gemeente Heemstede', aanvang: 20101010, registratieDatum: 20101010) {
        naarGemeente 'Heemstede',
            straat: 'Manpadslaan', nummer: 4, postcode: '2105MA', woonplaats: "Heemstede"
    }
}
slaOp(greg)

Given verzoek voor leveringsautorisatie 'postcode gebied Heemstede 2100-2129 EN afnemerindicatie' en partij 'Gemeente Tiel'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie postcode gebied Heemstede 2100-2129 EN afnemerindicatie wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/intake/expected_afnemerindicatie_berichten/expected_scenario_2b.xml voor expressie //brp:lvg_synVerwerkPersoon
When mutatiebericht voor leveringsautorisatie postcode gebied Heemstede 2100-2129 EN afnemerindicatie wordt bekeken
Then is er geen synchronisatiebericht gevonden


Scenario: 2c. Indien reeds afnemerindicatie geplaatst, wordt ook mutatiebericht geleverd
    - afnemerindicatie is bij b geplaatst nog geen minuut geleden dus geen aanvang en registratieDatum invullen
Given wacht 60 seconden
Given de persoon beschrijvingen:
def greg  = uitDatabase bsn: 999646564
Persoon.nieuweGebeurtenissenVoor(greg) {
    naamswijziging() {
          geslachtsnaam(1) wordt stam:'Zonnig', voorvoegsel:'het'
    }
}
slaOp(greg)

When voor persoon 999646564 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When volledigbericht voor leveringsautorisatie postcode gebied Heemstede 2100-2129 EN afnemerindicatie wordt bekeken
Then is er geen synchronisatiebericht gevonden
When mutatiebericht voor leveringsautorisatie postcode gebied Heemstede 2100-2129 EN afnemerindicatie wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/intake/expected_afnemerindicatie_berichten/expected_scenario_2c.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 2d. Na verwijderen afnemerindicatie wordt er geen bericht meer geleverd
Given verzoek voor leveringsautorisatie 'postcode gebied Heemstede 2100-2129 EN afnemerindicatie' en partij 'Gemeente Tiel'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_verwijder.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 999646564 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie postcode gebied Heemstede 2100-2129 EN afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
