Meta:
@status             Klaar
@regels             R2550, R2551
@sleutelwoorden     Oud Nieuw Bepaling, Doelbinding, Historievormen

Narrative:
Oud nieuw bepaling mbt attendering,
voor historie vormen:
- Geen
- Materieel
- Materieel Formeel

Als het oude persoonsbeeld buiten het attenderingscriterium valt en het nieuwe persoonsbeeld binnen het attenderingscriterium valt
dient er een volledig bericht geleverd.
Bij attendering met plaatsen afnemerindicatie zal er ook een afnemerindicatie geplaatst worden, waarna er geleverd dient te worden op basis van:
Mutatielevering op basis van afnemerindicatie. Deze leveringen zijn gelijk aan mutatielevering op basis van doelbinding.
Vandaar dat het in deze story alleen relevant is om de situatie:
Oud persoonsbeeld   vs.     Nieuw persoonsbeeld
Uit                 -       In
te testen voor de verschillende historie vormen.
De andere persoonsbeeld bepaling worden al getest in story 1:
1_In_en_Uit_Doelbinding.story

Scenario:   1. Van UIT het attenderingscriterium naar IN het attenderingscriterium (Na huwelijk in het attenderingscriterium gekomen)
            LT: R2550_LT01, R2550_LT02, R2551_LT01
            Verwacht resultaat:
            - Volledig bericht
            Uitwerking:
            Persoonsbeeld oud   = ONWAAR
            Persoonsbeeld nieuw = WAAR
            Historievorm:
            - Geen

Given leveringsautorisatie uit autorisatie/Attendering_huwelijk_Geen_Historie

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering huwelijk is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Attendering/Scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Historievorm Geen
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Attendering huwelijk'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|422531881
|historievorm|Geen

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Oud_Nieuw_Bepaling_Testen/Expecteds/Attendering/Scenario_1_GDP_Historievorm_Geen.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

!-- R2550 Actieverval is leeg, dus niet in bericht
Then is er voor xpath //brp:actieVerval geen node aanwezig in het antwoord bericht

Scenario:   2. Van UIT het attenderingscriterium naar IN het attenderingscriterium (Na huwelijk in het attenderingscriterium gekomen)
            LT: R2550_LT01, R2550_LT02, R2551_LT01
            Verwacht resultaat:
            - Volledig bericht
            Uitwerking:
            Persoonsbeeld oud   = ONWAAR
            Persoonsbeeld nieuw = WAAR
            Historievorm:
            - Materieel

Given leveringsautorisatie uit autorisatie/Attendering_huwelijk_Materiele_Historie

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering huwelijk is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Attendering/Scenario_2.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Historievorm Geen
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Attendering huwelijk'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|422531881
|historievorm|Materieel

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Oud_Nieuw_Bepaling_Testen/Expecteds/Attendering/Scenario_2_GDP_Historievorm_Materieel.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

!-- R2550 Actieverval is leeg, dus niet in bericht
Then is er voor xpath //brp:actieVerval geen node aanwezig in het antwoord bericht

Scenario:   3. Van UIT het attenderingscriterium naar IN het attenderingscriterium (Na huwelijk in het attenderingscriterium gekomen)
            LT: R2550_LT01, R2550_LT02, R2551_LT01
            Verwacht resultaat:
            - Volledig bericht
            Uitwerking:
            Persoonsbeeld oud   = ONWAAR
            Persoonsbeeld nieuw = WAAR
            Historievorm:
            - MaterieelFormeel

Given leveringsautorisatie uit autorisatie/Attendering_huwelijk_MaterieelFormeel_Historie

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering huwelijk is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Attendering/Scenario_3.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Historievorm MaterieelFormeel
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Attendering huwelijk'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|422531881
|historievorm|MaterieelFormeel

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Oud_Nieuw_Bepaling_Testen/Expecteds/Attendering/Scenario_3_GDP_Historievorm_MaterieelFormeel.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario:   4. Van UIT het attenderingscriterium naar IN het attenderingscriterium (Na huwelijk in het attenderingscriterium gekomen)
            LT: R2550_LT01, R2550_LT02, R2551_LT01
            Verwacht resultaat:
            - Volledig bericht
            Uitwerking:
            Persoonsbeeld oud   = ONWAAR
            Persoonsbeeld nieuw = WAAR
            Historievorm:
            - Formeel

Given leveringsautorisatie uit autorisatie/Attendering_huwelijk_Formele_Historie
Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering huwelijk is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Attendering/Scenario_4.xml voor expressie //brp:lvg_synVerwerkPersoon
