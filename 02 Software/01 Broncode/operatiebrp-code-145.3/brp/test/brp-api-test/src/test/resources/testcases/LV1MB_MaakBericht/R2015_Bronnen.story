Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R2015
@sleutelwoorden     Maak BRP bericht


Narrative:
In het verantwoordingsgedeelte van een levering mogen onder een Administratieve handeling geen bronnen
(d.w.z. Document of Rechtsgrond) worden opgenomen waarnaar geen enkele verwijzing (meer) bestaat vanuit
een Actie \ Bron binnen dezelfde bovenliggende Administratieve handeling.


Scenario: 1.    Huwelijk met document, doordat actie/bron verwijst naar administratieve handeling.
                LT: R2015_LT01
                Verwacht resultaat:
                - Document geleverd voor leveringsautorisatie:autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding


Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809

Then heeft het antwoordbericht verwerking Geslaagd
Then is er voor xpath //brp:document[brp:omschrijving = 'Huwelijksakte'] een node aanwezig in het antwoord bericht
Then is het antwoordbericht gelijk aan expecteds/R2015_expected_scenario_1.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 2.    Huwelijk met document NIET in bericht, doordat actie/bron NIET verwijst naar administratieve handeling, omdat
                LT: R2015_LT02
                verantwoording niet geautoriseerd is.
                Verwacht resultaat:
                - Document huwelijksakte NIET geleverd.

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding_Geen_Verantwoording
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|590984809|2008-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering obv doelbinding geen verantwoording'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|590984809
|historievorm|Geen

Then heeft het antwoordbericht verwerking Geslaagd

Then is er voor xpath //brp:document[brp:omschrijving = 'Huwelijksakte'] geen node aanwezig in het antwoord bericht
Then is het antwoordbericht gelijk aan expecteds/R2015_expected_scenario_2.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 3.    Huwelijk met document NIET in bericht, doordat actie/bron NIET verwijst naar administratieve handeling, omdat
                LT: R2015_LT02
                het peilmoment bij geef details persoon ligt op een punt voor het huwelijk.
                Verwacht resultaat:
                - Document huwelijksakte NIET geleverd.
                Uitwerking: Er wordt ingeprikt op een peilmoment welke voor de registratie van het huwelijk ligt, daardoor valt ook de verantwoording met de administratieve handeling weg


Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|590984809|2008-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809
|historievorm|Geen
|peilmomentFormeelResultaat|'2009-12-31T23:59:00Z'

Then heeft het antwoordbericht verwerking Geslaagd

Then is er voor xpath //brp:document[brp:omschrijving = 'Huwelijksakte'] geen node aanwezig in het antwoord bericht
Then is het antwoordbericht gelijk aan expecteds/R2015_expected_scenario_3.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R



