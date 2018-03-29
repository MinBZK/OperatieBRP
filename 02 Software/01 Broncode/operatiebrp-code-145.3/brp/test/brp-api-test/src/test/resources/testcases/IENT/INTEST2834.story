Meta:
@status                 Klaar
@sleutelwoorden         Intaketest, Afnemerindicatie


Narrative:
Naamswijziging ouder
I&T Bug alleen toevoeging nieuwe naam ouder
Verwacht, ook vervallen oude naam

Scenario: 1.1   Initiele vulling voor naamswijziging ouder
                LT:
                Verwacht resultaat:
                - Geef details persoon startsituatie

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding

Given persoonsbeelden uit specials:specials/IV_naamwijziging_ouder_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|233619197
Then is het antwoordbericht gelijk aan  /testcases/IENT/expecteds/GDP_voor_naamswijziging_ouder.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1.2   Mutatiebericht na naamswijziging ouder
                LT:
                Verwacht resultaat:
                - Mutatiebericht

!-- Mutatiebericht door administratieve handeling naamswijziging ouder
Given persoonsbeelden uit specials:specials/mutatie_naamwijziging_ouder_xls
When voor persoon 233619197 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
!-- Then is het synchronisatiebericht gelijk aan /testcases/IENT/expecteds/Mutatiebericht_voor_afnemer.xml voor expressie //brp:lvg_synVerwerkPersoon
!-- Nog geen expected geplaatst vanwege BUG
!-- Alleen toevoeging nieuwe naam ouder
!-- Geen vervallen oude naam ouder

Scenario: 2.1   Initiele vulling voor naamswijziging ouder voor bijhouder
                LT:
                Verwacht resultaat:
                - Geef details persoon startsituatie

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding_bijhouder

Given persoonsbeelden uit specials:specials/IV_naamwijziging_ouder_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Minister'
|bsn|233619197

Scenario: 2.2   Mutatiebericht na naamswijziging ouder voor bijhouder
                LT:
                Verwacht resultaat:
                - Mutatiebericht

!-- Mutatiebericht door administratieve handeling naamswijziging ouder
Given persoonsbeelden uit specials:specials/mutatie_naamwijziging_ouder_xls
When voor persoon 233619197 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Minister en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/IENT/expecteds/Mutatiebericht_voor_bijhouder.xml voor expressie //brp:lvg_synVerwerkPersoon
