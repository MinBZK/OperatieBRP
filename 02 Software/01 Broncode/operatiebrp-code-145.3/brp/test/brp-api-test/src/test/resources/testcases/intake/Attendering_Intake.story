Meta:
@status                 Klaar
@sleutelwoorden         Intaketest, Attendering

Narrative:
Intake test voor dienst 'Attendering' en 'Attendering met plaatsing afnemerindicatie'

Scenario:   1. Attendering op persoon met gewijzigde adres + woonplaats, zonder plaatsen afnemerindicatie
            LT:
            Verwacht resultaat:
            1. Volledig bericht voor afnemer na verhuizing (attenderingscriterium = GEWIJZIGD(oud, nieuw, [adressen], [woonplaatsnaam]))
            2. Er is geen afnemer indicatie geplaatst bij de persoon (Effect plaatsing afnemer indicatie = NULL)

Given leveringsautorisatie uit autorisatie/Attendering_Intake
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C10T10_xls

When voor persoon 270433417 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attenderingen zonder plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected_attendering_berichten/expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Given verzoek verwijder afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Attenderingen zonder plaatsing afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417

Then heeft het antwoordbericht verwerking Foutief

Scenario:   2. Attendering op persoon met gewijzigde adres + woonplaats, met plaatsen afnemerindicatie
            LT:
            Verwacht resultaat:
            1. Volledig bericht voor afnemer na verhuizing (attenderingscriterium = GEWIJZIGD(oud, nieuw, [adressen], [woonplaatsnaam]))
            2. Er is een afnemer indicatie geplaatst bij de persoon (Effect plaatsing afnemer indicatie = Plaatsen)

Given leveringsautorisatie uit autorisatie/Attendering_met_plaatsing_Intake
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C10T10_xls

When voor persoon 270433417 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attenderingen met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then is er voor persoon met bsn 270433417 en leveringautorisatie Attenderingen met plaatsing afnemerindicatie en partij Gemeente Utrecht een afnemerindicatie geplaatst

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|270433417|Attenderingen met plaatsing afnemerindicatie|'Gemeente Utrecht'|5|2016-03-10 T16:04:43Z
Given verzoek verwijder afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Attenderingen met plaatsing afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417

Then heeft het antwoordbericht verwerking Geslaagd
