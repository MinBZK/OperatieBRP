Meta:
@status             Klaar
@usecase            SA.0.MA
@regels             R2016, R2129, R1314, R1315, R1338, R1343, R1544, R1989, R1990, R1991, R1993, R1994, R2057, R2060, R2062
@sleutelwoorden     Mutatielevering o.b.v. afnemerindicatie, Lever mutaties

Narrative:
Mutatielevering op basis van Plaatsing afnemersindicatie:
De afnemer ontvangt voor de opgegeven Persoon een eerste Volledig bericht bij de plaatsing van de afnemerindicatie.
Zolang de afnemerindicatie nog geldig is worden alle geautoriseerde wijzigingen ontvangen via een mutatiebericht.



Scenario:   1.  Plaatsen afnemerindicatie daarna verhuizing, waardoor de afnemer een mutatiebericht op basis van afnemerindicatie ontvangt. Datum einde volgen is gevuld met toekomstige datum 2050-07-30
            LT: R1262_LT19, R1264_LT15, R2130_LT13, R1314_LT03, R1315_LT03, R1338_LT01, R1542_LT01, R1990_LT01, R1980_LT02, R2056_LT15, R1983_LT28, R2550_LT01, R2551_LT01, R2589_LT03
            Verwacht resultaat: Leveringsbericht
                Met vulling:
                -  Soort bericht = Mutatiebericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  leveringsautorisatie = Het leveringsautorisatie waarbinnen de Dienst wordt geleverd
                -  DatumEindeGeldigheid = Komt niet terug in het antwoordbericht
                -  DatumEindeVolgen = Komt niet terug in het antwoordbericht

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_afnemerindicatie_huisnummer
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|datumEindeVolgen
|854820425|Geen pop.bep. levering op basis van afnemerindicatie|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|20500730

When voor persoon 854820425 wordt de laatste handeling geleverd

!-- R1314_LT03
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
!-- R1315_LT03
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                     | nummer    | attribuut    | aanwezig   |
| lvg_synVerwerkPersoon     | 1         | meldingen    | nee        |
!-- R1338_LT01 Administratieve handeling <> 99997.
Then verantwoording acties staan in persoon
Then hebben attributen in voorkomens de volgende waardes:
| groep                    | nummer | attribuut | verwachteWaarde          |
| administratieveHandeling | 1      | soortNaam | GBA - Bijhouding actueel |

!-- Controle op Identificerende groepen hoofdpersoon zonder  DatumEindeGeldigheid en zonder Datum\Tijd verval R1542_LT01
Then is het synchronisatiebericht gelijk aan Expecteds/Scenario_1_Mutatielevering_Goedpad.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario:   2.  Plaatsen afnemerindicatie daarna verhuizing, waardoor de afnemer een mutatiebericht op basis van afnemerindicatie ontvangt
            LT: R1314_LT04
            Datum einde volgen is LEEG
            Verwacht resultaat: Leveringsbericht
                Met vulling:
                -  Soort bericht = Mutatiebericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  leveringsautorisatie = Het leveringsautorisatie waarbinnen de Dienst wordt geleverd
                -  DatumEindeGeldigheid = Komt niet terug in het antwoordbericht
                -  DatumEindeVolgen = Komt niet terug in het antwoordbericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Geen pop.bep. levering op basis van afnemerindicatie|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|

When voor persoon 854820425 wordt de laatste handeling geleverd

!-- Datum einde volgen is leeg, dus mutatiebericht
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken



