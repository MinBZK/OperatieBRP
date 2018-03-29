Meta:
@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
@regels             R2279
@versie             2

Narrative:
Als in de berichtparameters een historiefilter is opgegeven, dienen de objecten weggefilterd te worden die,
na het toepassen van het historiefilter op de groepen:

Geen enkele groep meer bevatten

OF

alleen nog een groepen Identiteit bevatten met historiepatroon 'geen'

EN

in het bericht geen sub-objecten bevatten die niet weggefilterd worden.


Uitzondering: Objecten 'Gegeven in onderzoek' kennen alleen een groep identiteit met historiepatroon 'geen'.
Deze moeten alleen worden weggefilterd als het onderzoek waar ze naar verwijzen is weggefilterd uit het bericht.

Scenario: 0.    Geen filter, controle hoe het originele bericht eruit ziet
Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809
|historievorm|Geen

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/expected_13.1_R2279_scenario_0.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1     Historievorm geen, peilmoment formeel en materieel resulaat 2009-12-31
                LT: R2279_LT01
                Verwacht resultaat:
                - Geen object partner in bericht
                Uitwerking: Anne is op 01-01-2015 gescheiden, na die tijd had ze geen partner,
                dus op peilmoment 2016-09-28 wordt haar partner weggefilterd, voor materiele historie,
                want bij persoon onder huwelijk blijft alleen de groep Identiteit over met historiepatroon geen
                Maar einde huwelijk heeft geen tsreg, dus formele historie blijft staan
Meta:
@status Uitgeschakeld
!-- Test doet niet wat er beweerd wordt, oppakken in story ROOD-976
Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809
|historievorm|Geen
|peilmomentMaterieelResultaat|'2016-09-28'
|peilmomentFormeelResultaat|VANDAAG

Then heeft het antwoordbericht verwerking Geslaagd

!-- Naam Partner is Jan Pietersen
Then is er voor xpath //brp:geslachtsnaamstam[text()='Pietersen'] een node aanwezig in het antwoord bericht

Scenario: 2.    Leveren van objecten waar nog een subobject geleverd wordt
                LT: R2279_LT02
                Uitwerking:
                - Atribuut huisnummer in scope, dus groep adres leveren, dus subobject adressen leveren, dus object persoon leveren
                - Peilmomenten zijn LEEG, dus systeemdatum als filter
                - Hierdoor blijft het huidige adres in het bericht
                Verwacht resultaat:
                - Object Persoon in bericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|scopingElementen|Persoon.Adres.Huisnummer
|historievorm|Geen

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/expected_13.1_R2279_scenario_2.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 4.1   Onderzoek op niet geautoriseerd attribuut, dus gegeven in onderzoek weggefilterd
                LT: R2279_LT04
                Verwacht resultaat:
                Gegeven in onderzoek verwijderd uit resultaat, dus in dit geval geen levering
!-- Anne heeft een onderzoek op huisnummer
Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2279/Doelbinding_huisnummer_niet_geautoriseerd
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Doelbinding huisnummer niet geautoriseerd'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd

Then is er voor xpath //brp:onderzoek geen node aanwezig in het antwoord bericht

Scenario:   4.2   ter vergelijking
            LT:

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd

Then is er voor xpath //brp:onderzoek een node aanwezig in het antwoord bericht

Scenario: 5.1   Historievorm geen, peilmoment formeel en materieel resulaat 2009-12-31
                LT: R2279_LT05
                Verwacht resultaat:
                - Geen object Adressen in bericht
                Uitwerking: Persoon is op 01-01-210 ingeschreven op haar eerste adres

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|606417801|2008-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Geen
|peilmomentMaterieelResultaat|'2009-12-31'
|peilmomentFormeelResultaat|'2009-12-31T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 0 groepen 'adressen'

Scenario:   6. Huwelijk datum aanvang = 20160510
                LT: R2279_LT05
            Historievorm: Materieel
            peilmomentMaterieelResultaat: '2016-05-09'
            peilmomentFormeelResultaat: 2016-05-09T23:59:00'
            Verwacht resultaat:
            - Op de peilmomenten was het huwlijk nog niet bekend, dus geen huwelijk in bericht en ook geen partner

!-- (ROOD-962: Voorbeeld 1 uitgewerkt)
Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|422531881|'2015-12-31 T23:59:00Z'

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|159247913|'2015-12-31 T23:59:00Z'

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|422531881
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2016-05-09'
|peilmomentFormeelResultaat|'2016-05-09T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

Then is er voor xpath //brp:huwelijk geen node aanwezig in het antwoord bericht
!-- Volledige expected ter controle dat er buiten huwelijk er ook nog niks van de toekomstige partner geleverd wordt
Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/expected_13.1_R2279_scenario_6.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R
