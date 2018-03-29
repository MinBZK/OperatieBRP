Meta:
@status             Klaar
@usecase            SA.0.MA
@regels             R2016, R2129, R1314, R1315, R1338, R1343, R1544, R1989, R1990, R1991, R1993, R1994, R2057, R2060, R2062
@sleutelwoorden     Mutatielevering o.b.v. afnemerindicatie, Lever mutaties

Narrative:
Bij het aanmaken van een Levering op basis van een Afnemersindicatie geldt het volgende:

Indien de Persoon \ Afnemerindicatie.Datum einde volgen in de Persoon \ Afnemerindicatie een waarde heeft,
wordt er uitsluitend een bericht geleverd als de datum Persoon \ Afnemerindicatie.Datum einde volgen ligt ná het tijdstip aanmaak van het bericht
(> Datum\tijd systeem)


Scenario:   1.  Verhuizing, waardoor de afnemer een mutatiebericht op basis van afnemerindicatie zou moeten ontvangen, MAAR
                LT: R1314_LT01, R1990_LT02
                datumEindeVolgen kleiner dan TijdstipRegistratie laatste handeling er wordt geen mutatiebericht geleverd
                DatumEindVolgen in blob is 20150805
                DatumTijdstip registratie laatste handeling is Thu, 04 Aug 2016 12:24:04 GMT
                Verwacht resultaat:  Geen mutatielevering einde volgen ligt in het verleden + geen persoon overgebleven na filters

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|datumEindeVolgen
|854820425|Geen pop.bep. levering op basis van afnemerindicatie|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|20150805

When voor persoon 854820425 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

Scenario:   2.  Plaatsen afnemerindicatie daarna verhuizing, waardoor de afnemer een mutatiebericht op basis van afnemerindicatie ontvangt
                LT: R1314_LT02
                DatumEindeGeldigheid = systeemdatum.
                DatumEindeVolgen in blob is 20160804
                DatumTijdStip registratie laatste handeling is Thu, 04 Aug 2016 14:40:05 GMT
                Verwacht resultaat:
                - Geen mutatiebericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|854820425|'2014-12-31 T23:59:00Z'

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|datumEindeVolgen
|854820425|Geen pop.bep. levering op basis van afnemerindicatie|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|20161011

When voor persoon 854820425 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden
