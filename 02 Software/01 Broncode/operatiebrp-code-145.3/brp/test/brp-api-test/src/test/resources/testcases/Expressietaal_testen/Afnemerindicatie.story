Meta:
@status             Klaar
@sleutelwoorden     Expressietaal

Narrative:
In deze story zijn scenario's opgenomen om de expressietaal af te testen mbt afnemerindicaties

Scenario:   1. expressie met een Afnemerindicatie op partij
            LT:
            Afnemerindicatie is geplaatst op Partij Utrecht, expressie checkt of er een afnemerindicatie is voor gemeente utrecht
            Verwacht resultaat:
            Levering laatste handeling voor Jan.


Given leveringsautorisatie uit autorisatie/Afnemerindicatie_partij
Given persoonsbeelden uit specials:specials/Jan_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|
|606417801|Afnemerindicatie partij|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|


Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Afnemerindicatie partij'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd


Scenario:   2. expressie met een Afnemerindicatie op partij met tsverval
            LT:
            Afnemerindicatie is geplaatst op Partij Utrecht, expressie checkt of er een afnemerindicatie is voor gemeente utrecht
            Verwacht resultaat:
            GEEN Levering laatste handeling voor Jan.

Given leveringsautorisatie uit autorisatie/Afnemerindicatie_partij
Given persoonsbeelden uit specials:specials/Jan_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|dienstIdVerval|tsVerval
|606417801|Afnemerindicatie partij|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|1|2016-08-28 T15:12:11Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Afnemerindicatie partij'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                  |
| R1403     | Met het opgegeven identificerend gegeven is geen persoon gevonden binnen uw autorisatie. |



Scenario:   3. expressie met een Afnemerindicatie op partij met datumeinde volgen
            LT:
            Afnemerindicatie is geplaatst op Partij Utrecht, expressie checkt of er een afnemerindicatie is voor gemeente utrecht
            Verwacht resultaat:
            GEEN Levering laatste handeling voor Jan.

Given leveringsautorisatie uit autorisatie/Afnemerindicatie_partij_datum_einde_volgen
Given persoonsbeelden uit specials:specials/Jan_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                    | partijNaam        | datumEindeVolgen | tsReg                 | dienstId |
| 606417801 | Afnemerindicatie partij datum einde volgen  | 'Gemeente Utrecht'| 2016-07-28       | 2016-07-28 T16:11:21Z | 1        |

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Afnemerindicatie partij datum einde volgen'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                  |
| R1403     | Met het opgegeven identificerend gegeven is geen persoon gevonden binnen uw autorisatie. |


Scenario:   4. expressie met een Afnemerindicatie op partij met tsverval
            LT:
            Afnemerindicatie is geplaatst op Partij Utrecht, expressie checkt of er een afnemerindicatie was voor gemeente utrecht in laaste historie record.
            Verwacht resultaat:
            Levering  laatste handeling voor Jan,

Meta:
@status     Onderhanden

!-- onderhanden historie nog niet correct, scenario nog aanpassen.

Given leveringsautorisatie uit autorisatie/Afnemerindicatie_historie_partij
Given persoonsbeelden uit specials:specials/Jan_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|dienstIdVerval|tsVerval
|606417801|Afnemerindicatie historie partij|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|1|2016-08-28 T15:12:11Z

When voor persoon 606417801 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Afnemerindicatie partij is ontvangen en wordt bekeken



