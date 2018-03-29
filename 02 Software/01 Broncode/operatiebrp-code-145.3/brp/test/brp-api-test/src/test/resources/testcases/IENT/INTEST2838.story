Meta:
@status                 Klaar
@sleutelwoorden         INTEST-2838


Narrative:
Beschrijving testgeval: Een persoon is geëmigreerd en heeft een documentindicatie op zijn PL staan. Vervolgens komt hij terug in Nederland wonen, waarbij een huwelijk wordt geregistreerd en de documentindicatie wordt weggehaald. In de database is de indicatie 'Onverwerkt document aanwezig?' vervallen.
Verwacht resultaat: BRP levering met daarin de vervallen indicatie 'Onverwerkt document aanwezig?'.
Actueel resultaat: BRP levering met daarin alleen '<indicaties/>'

Scenario: 1.1   Initiele vulling voor hervestiging in Nederland, dus buitenlands adres met Indicatie Document
                LT:
                Verwacht resultaat:
                - Geef details persoon startsituatie

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding

Given persoonsbeelden uit specials:specials/IV_LG01-BB12002-P1 (3)_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|230614528
Then is het antwoordbericht gelijk aan  /testcases/IENT/expecteds/Intest2838_IV_GDP.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1.2   Volledigbericht na hervestiging in Nederland, dus vervallen buitenlands adres met vervallen Document
                LT:
                Verwacht resultaat:
                - Volledigbericht
                - Vervallen document niet in bericht

Given persoonsbeelden uit specials:specials/M01_LG01-BB12002-P1 (5)_xls
When voor persoon 230614528 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/IENT/expecteds/Intest2838_volledig_bericht.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Zelfde test maar dan met een Bijhouderspartij Minister
!-- Zelfde resultaat want vervallen documenten bestaan niet meer

Scenario: 2.1   Initiele vulling voor hervestiging in Nederland, dus buitenlands adres met Indicatie Document
                LT:
                Verwacht resultaat:
                - Geef details persoon startsituatie

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding_bijhouder

Given persoonsbeelden uit specials:specials/IV_LG01-BB12002-P1 (3)_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Minister'
|bsn|230614528

Scenario: 2.2   Volledigbericht na hervestiging in Nederland, dus vervallen buitenlands adres met vervallen Document
                LT:
                Verwacht resultaat:
                - Volledigbericht
                - Vervallen document WEL in bericht

Given persoonsbeelden uit specials:specials/M01_LG01-BB12002-P1 (5)_xls
When voor persoon 230614528 wordt de laatste handeling geleverd
When het volledigbericht voor partij Minister en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
