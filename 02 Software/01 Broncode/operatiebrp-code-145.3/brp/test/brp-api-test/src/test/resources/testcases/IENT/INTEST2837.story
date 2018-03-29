Meta:
@status                 Klaar
@sleutelwoorden         INTEST-2837


Narrative:
Beschrijving testgeval: Een persoon is geëmigreerd en heeft een documentindicatie op zijn PL staan. Vervolgens komt hij terug in Nederland wonen, waarbij een huwelijk wordt geregistreerd en de documentindicatie wordt weggehaald. In de database is de indicatie 'Onverwerkt document aanwezig?' vervallen.
Verwacht resultaat: BRP levering met daarin de vervallen indicatie 'Onverwerkt document aanwezig?'.
Actueel resultaat: BRP levering met daarin alleen '<indicaties/>'

Scenario: 1.1   Initiele vulling met kind op PL
                LT:
                Verwacht resultaat:
                - Geef details persoon startsituatie

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding

Given persoonsbeelden uit specials:IENT/IV_Vervallen_Kind_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|229676868
Then is het antwoordbericht gelijk aan  /testcases/IENT/expecteds/Intest2837_IV_GDP.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: 1.2   Mutatiebericht met vervallen kind relatie op PL
                LT:
                Verwacht resultaat:
                - Mutatiebericht
                - familierechtelijkeBetrekking (Kind) in bericht als referentie, ouderschap relatie vervalt in bericht

Given persoonsbeelden uit specials:IENT/IV_Vervallen_Kind_Mutatie_Kind_xls
When voor persoon 229676868 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/IENT/expecteds/Intest2837_Mutatiebericht.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 1.3   Volledigbericht na vervallen kind relatie op PL
                LT:
                Verwacht resultaat:
                - Volledigbericht
                - Kind niet in bericht (relatie heeft nooit bestaan)

Given persoonsbeelden uit specials:IENT/Mutatie_Vervallen_Kind_xls
When voor persoon 229676868 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/IENT/expecteds/Intest2837_Volledigbericht.xml voor expressie //brp:lvg_synVerwerkPersoon
