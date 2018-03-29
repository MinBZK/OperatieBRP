Meta:
@status                 Klaar


Narrative:
Als afnemer
Wil ik een bevraging kunnen doen via de service geefDetailsPersoon
met als indentiteitsnummer BSN, ANR of objectsleutel
Zodat ik een


Scenario:   1. GeefDetailsPersoon service zonder Identiteitsnummer
            LT: R2192_LT08
            GeefdetailsPersoon zonder BSN geen ANR en geen object sleutel in het request
            Verwacht resultaat:
            Foutmelding: R2192 Er moet één identiteitsnummer zijn gevuld.

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R2192     | Er moet één identiteitsnummer zijn gevuld.


Scenario:   2. GeefDetailsPersoon service met een ongeldig identiteitsnummer BSN
            LT: R1587_LT08
            GeefdetailsPersoon met een ongeldig BSN
            Verwacht resultaat:
            1. Foutmelding: Het opgegeven BSN is niet geldig.

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|123456789

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1587     | Het opgegeven BSN is niet geldig.


Scenario:   3. GeefDetailsPersoon service met een ongeldig identiteitsnummer ANR
            LT: R1585_LT02
            GeefdetailsPersoon met een ongeldig AdministratieNummer
            Verwacht resultaat:
            1. Foutmelding: Het opgegeven Administratienummer is niet geldig.

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|anr|1543726322

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1585     | Het opgegeven administratienummer is niet geldig.


Scenario:   4. GeefDetailsPersoon service met meer dan 1 identiteitsnummer
            LT: R2192_LT04
            GeefdetailsPersoon met een geldig ANR en geldig BSN of object sleutel
            Verwacht resultaat: Foutmelding: R2192 Er moet één identiteitsnummer zijn gevuld.

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|anr|5398948626

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R2192     | Er moet één identiteitsnummer zijn gevuld.
