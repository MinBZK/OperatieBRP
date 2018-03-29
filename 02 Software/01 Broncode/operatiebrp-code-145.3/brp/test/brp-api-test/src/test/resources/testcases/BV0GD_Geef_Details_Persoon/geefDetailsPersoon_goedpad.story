Meta:
@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Bevraging GeefDetailsPersoon


Narrative:
Als afnemer
Wil ik een bevraging kunnen doen via de service geefDetailsPersoon
met als indentiteitsnummer BSN, ANR of objectsleutel
Zodat ik een response bericht krijg met de volledige persoonslijst binnen mijn autoristatie


Scenario:   1. GeefDetailsPersoon service met een geldig identiteitsnummer BSN
            LT: R2192_LT01, R1262_LT13, R1264_LT09, R1587_LT07, R2054_LT01, R2056_LT09, R1401_LT09, R1983_LT07
            GeefdetailsPersoon met een geldig BSN geen ANR of object sleutel
            Verwacht resultaat:
            1. Levering volgens expected
            2. de gevraagde dienst is geldig R1262_LT13
            3. de gevraagde dienst is NIET geblokkeerd R1264_LT09
            4. Burgerservicenummer moet voldoen aan het voorschrift

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd


Scenario:   2. GeefDetailsPersoon service met identiteitsnummer Administratienummer
            LT: R2192_LT02, R1585_LT01
            GeefdetailsPersoon met een geldig ANR geen BSN of object sleutel
            Verwacht resultaat: Levering volgens expected

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|anr|5398948626


Then heeft het antwoordbericht verwerking Geslaagd


Scenario:   3. GeefDetailsPersoon service met peilmoment Materieel check of inhoud groep wordt geleverd
            GeefdetailsPersoon met een datumeinde volgen
            Alleen identiteitsgroep, geen verandwoordingsgroep, test toont aan dat inhoud getoond wordt.
            Verwacht resultaat:
            Levering met datumeinde volgen in het bericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                               | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 606417801 | 'Geen pop.bep. levering op basis van afnemerindicatie' | 'Gemeente Utrecht' | 2016-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |



Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2016-08-05'

Then heeft in het antwoordbericht 'datumEindeVolgen' in 'afnemerindicatie' de waarde '2016-01-01'


