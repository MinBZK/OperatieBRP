Meta:

@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
@regels             R2300

Narrative:
Indien:
Bericht.Peilmoment formeel resultaat < Persoon.Tijdstip laatste wijziging GBA-systematiek
Dan geeft het systeem een melding dat de formele historie voor de migratie naar BRP niet betrouwbaar is.

Scenario: 1.    Historievorm = Materieel, Peilmoment materieel resultaat is datum x (01-01-2016), Peilmoment Formeel resultaat is datum y (31-12-2015)
                LT: R2300_LT01
                Verwacht Resultaat
                - Foutmelding R2300
                - Peilmoment formeel resultaat ligt voor de overgang naar BRP systematiek. Formele historie in niet betrouwbaar over de periode van de GBA systematiek


Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2016-01-01'
|peilmomentFormeelResultaat|'2015-12-31T23:59:00'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                                                                                               |
| R2300 | Peilmoment formeel resultaat ligt voor de overgang naar BRP systematiek. Formele historie is niet betrouwbaar over de periode van de GBA systematiek. |

Scenario: 2.   Historievorm = Materieel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is > Persoon.Tijdstip laatste wijziging GBA-systematiek
                LT: R2300_LT02
                Uitwerking:
                - GBA Oorspronkelijke woonplaats Oosterhout
                - GBA Verhuizing naar Utrecht  op 31-12-2015
                - GBA Verhuizing naar Den Haag op 01-01-2016
                - GBA Verhuizing naar Uithoorn op 02-01-2016
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - Den Haag in het bericht
                - Uithoorn in het bericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls


Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|590984809|2014-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809
|historievorm|Materieel
|peilmomentFormeelResultaat|'2015-12-31T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2300_LT02
Then heeft het antwoordbericht 3 groepen 'adres'


Scenario: 3.   Historievorm = Materieel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is LEEG
                LT: R2300_LT03
                Verwacht Resultaat:
                Geslaagd


Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809
|historievorm|Materieel

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2300_LT03
Then heeft het antwoordbericht 3 groepen 'adres'