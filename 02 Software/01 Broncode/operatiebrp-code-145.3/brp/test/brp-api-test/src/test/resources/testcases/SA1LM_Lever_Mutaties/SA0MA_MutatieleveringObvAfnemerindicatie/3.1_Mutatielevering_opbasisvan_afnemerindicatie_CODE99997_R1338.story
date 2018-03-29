Meta:
@epic               Verbeteren testtooling
@status             Klaar
@usecase            SA.0.MA
@regels             R2016, R2129, R1314, R1315, R1338, R1343, R1544, R1989, R1990, R1991, R1993, R1994, R2057, R2060, R2062
@sleutelwoorden     Mutatielevering o.b.v. afnemerindicatie

Narrative:
Als er door een Administratieve handeling één of meer personen zijn bijgehouden waarbij een Persoon \ Afnemerindicatie aanwezig is
voor de Leveringsautorisatie horende bij de Mutatielevering op basis van afnemerindicatie,
dan moet voor de Toegang leveringsautorisatie behorende bij de Leveringsautorisatie van de partij die ook gekoppeld is aan
de Persoon \ Afnemerindicatie:
Een MutatieBericht aangemaakt en verstrekt worden waarin deze Persoonslijsten zijn opgenomen,
als de Administratieve handeling.Soort ongelijk is aan CODE 99997 (GBA - Bijhouding overig)
Een VolledigBericht aangemaakt en verstrekt worden waarin deze Persoonslijsten zijn opgenomen,
als de Administratieve handeling.Soort gelijk is aan CODE 99997 (GBA - Bijhouding overig)


Scenario:   1.  Verhuizing binnen gemeente admhnd = 99997
                LT: R1338_LT02
                Verwacht resultaat: Volledig bericht wordt geleverd nav admhnd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie

Given persoonsbeelden uit oranje:Bijhouding/DELTABIJHC20T70_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|616902505|Geen pop.bep. levering op basis van afnemerindicatie|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z
When voor persoon 616902505 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Then verantwoording acties staan in persoon
!-- R1338_LT02
Then hebben attributen in voorkomens de volgende waardes:
| groep                    | nummer | attribuut | verwachteWaarde         |
| administratieveHandeling | 1      | soortNaam | GBA - Bijhouding overig |

