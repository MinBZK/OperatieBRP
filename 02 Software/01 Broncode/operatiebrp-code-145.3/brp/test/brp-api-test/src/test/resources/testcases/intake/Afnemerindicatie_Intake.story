Meta:
@status                 Klaar
@sleutelwoorden         Intaketest, Afnemerindicatie


Narrative:
Als gebruiker
wil ik een afnemerindicatie kunnen plaatsen
Zodat ik een volledig bericht krijg.

Scenario:   1a. Gemeente Utrecht plaatst een afnemerindicatie op persoon UC_Kenny met bsn = 999646564,
            LT:
            vanuit de leverautorisatie Geen_pop.bep_levering_op_basis_van_afnemerindicatie
            Deze leverautorisatie beschikt over de diensten: Plaatsen afnemerindicatie, Verwijderen afnemerindicatie,
            Mutatielevering op basis van afnemerindicatie en Synchronisatie persoon
            Verwacht resultaat:
            1. Plaats afnemerIndicatie is geslaagd.
            2. Volledig bericht voor UC Kenny

Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T10_xls
Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie

Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|258096329

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan expected_afnemerindicatie_berichten/expected_response_scenario_1a.xml voor expressie //brp:lvg_synRegistreerAfnemerindicatie_R

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected_afnemerindicatie_berichten/expected_scenario_1a.xml voor expressie //brp:lvg_synVerwerkPersoon
Then is er voor persoon met bsn 258096329 en leveringautorisatie Geen pop.bep. levering op basis van afnemerindicatie en partij Gemeente Utrecht een afnemerindicatie geplaatst

Scenario:   1b. Na een mutatie wordt er een mutatiebericht geleverd obv afnemerindicatie
            LT:
            Verwacht resultaat:
            1. een mutatielevering wordt geleverd


Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|258096329|Geen pop.bep. levering op basis van afnemerindicatie|'Gemeente Utrecht'|30|2016-07-28 T16:11:21Z

When voor persoon 258096329 wordt de laatste handeling geleverd
Then is het aantal ontvangen berichten 1
When mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie wordt bekeken
Then is het synchronisatiebericht gelijk aan expected_afnemerindicatie_berichten/expected_response_scenario_1b.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   1c. Na verwijderen afnemerindicatie wordt er geen bericht meer geleverd
            LT:
            Verwacht resultaat:
            1. AfnemerIndicatie wordt verwijderd
            2. Geen bericht meer na een mutatie

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|258096329|Geen pop.bep. levering op basis van afnemerindicatie|'Gemeente Utrecht'|30|2016-07-28 T16:11:21Z

Given verzoek verwijder afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|258096329

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan expected_afnemerindicatie_berichten/expected_response_scenario_1c.xml voor expressie //brp:lvg_synRegistreerAfnemerindicatie_R

Then is er voor persoon met bsn 258096329 en leveringautorisatie Geen pop.bep. levering op basis van afnemerindicatie en partij Gemeente Utrecht de afnemerindicatie verwijderd
Then is het aantal ontvangen berichten 0


