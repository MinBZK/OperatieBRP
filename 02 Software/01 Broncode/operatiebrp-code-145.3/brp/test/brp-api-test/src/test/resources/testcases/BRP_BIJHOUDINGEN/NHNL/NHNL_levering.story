Meta:
@status                 Klaar
@sleutelwoorden         NHNL

Narrative:
Nietigverklaring van een symmetrisch BRP huwelijk Ingeschrevene Ingeschrevene

Scenario: 0. Persoonsbeeld Init vulling
Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:NHNL02C10T50/Nevenactie_Registratie_geslachtsnaam_Eig/dbstate001

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|472969961

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1.  Nietigverklaring van een symmetrisch BRP huwelijk Ingeschrevene Ingeschrevene
              LT: NHNL02C10T50

Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Huwelijk

Given persoonsbeelden uit BIJHOUDING:NHNL02C10T50/Nevenactie_Registratie_geslachtsnaam_Eig/dbstate003
When voor persoon 472969961 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie_bericht_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering partnerschap is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig_bericht_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon