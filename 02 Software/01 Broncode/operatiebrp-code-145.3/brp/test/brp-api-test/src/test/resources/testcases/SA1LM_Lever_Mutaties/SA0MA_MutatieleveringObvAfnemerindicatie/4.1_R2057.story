Meta:
@status             Klaar
@usecase            SA.0.MA
@regels             R2016, R2129, R1314, R1315, R1338, R1343, R1544, R1989, R1990, R1991, R1993, R1994, R2057, R2060, R2062
@sleutelwoorden     Mutatielevering o.b.v. afnemerindicatie, Lever mutaties

Narrative:
Bij het leveren naar aanleiding van een Administratieve handeling geldt dat er alleen berichten worden aangemaakt en afnemerindicaties onderhouden als:
de Dienst waarvoor geleverd wordt Geldig (R2129) op 'Systeemdatum' (R2016) is,
de Dienstbundel waarin deze dienst zit Geldig (R2129) op 'Systeemdatum' (R2016) is,
de Toegang leveringsautorisatie waarvoor geleverd wordt Geldig (R2129) is op 'Systeemdatum' (R2016) en
de Leveringsautorisatie waarbij deze Toegang leveringsautorisatie hoort Geldig (R2129) is op 'Systeemdatum' (R2016).


Scenario: 1.    Voer als administratieve handeling een verhuizing door
                LT: R2057_LT01, R2239_LT02
                Verwacht resultaat: Mutatiebericht
                Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 = systeemdatum
                Dienst.Datum einde                  > systeemdatum
                Dienstbundel.Datum ingang           = systeemdatum
                Dienstbundel.Datum einde            > systeemdatum
                Toegang Leveringsautorisatie ingang = systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum
                Leveringsautorisatie.Datum ingang   = systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Alles_geldig

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Alles geldig|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Alles geldig is ontvangen en wordt bekeken


Scenario: 2.    Voer als administratieve handeling een verhuizing door
                LT: R2057_LT02, R2239_LT01
                Verwacht resultaat: Mutatiebericht
                Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Alles_geldig_Gisteren_Morgen

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Alles geldig gisteren morgen|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Alles geldig gisteren morgen is ontvangen en wordt bekeken


Scenario: 3.    Dienst ongeldig
                LT: R2057_LT03
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 > systeemdatum
                Dienst.Datum einde                  > systeemdatum
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Dienst_Datum_Ingang_Morgen

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Dienst datum ingang morgen|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden


Scenario: 4.    Dienst ongeldig
                LT: R2057_LT04
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  < systeemdatum
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Dienst_Datum_Einde_Gisteren

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Dienst datum einde gisteren|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden


Scenario: 5.    Dienst ongeldig
                LT: R2057_LT05
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  = systeemdatum
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Dienst_Datum_Einde_Vandaag

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Dienst datum einde vandaag|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden


Scenario: 6.    Dienstbundel ongeldig
                LT: R2057_LT06
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum
                Dienstbundel.Datum ingang           > systeemdatum
                Dienstbundel.Datum einde            > systeemdatum
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Dienstbundel_Datum_Ingang_Morgen

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Dienstbundel datum ingang morgen|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 7.    Dienstbundel ongeldig
                LT: R2057_LT07
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            < systeemdatum
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Dienstbundel_Datum_Einde_Gisteren

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Dienstbundel datum ingang morgen|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 8.    Dienstbundel ongeldig
                LT: R2057_LT08
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            = systeemdatum
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Dienstbundel_Datum_Einde_Vandaag

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Dienstbundel datum ingang morgen|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 9.    Toegang Leveringsautorisatie ongeldig
                LT: R2057_LT09
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum
                Toegang Leveringsautorisatie ingang > systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Toegang_Leveringsautorisatie_Datum_Ingang_Morgen

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Toegang Leveringsautorisatie Datum Ingang Morgen|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 10.    Toegang Leveringsautorisatie ongeldig
                LT: R2057_LT10
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  < systeemdatum
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Toegang_Leveringsautorisatie_Datum_Einde_Gisteren

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Toegang Leveringsautorisatie Datum Einde Gisteren|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 11.    Toegang Leveringsautorisatie ongeldig
                LT: R2057_LT11
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  = systeemdatum
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Toegang_Leveringsautorisatie_Datum_Einde_Vandaag

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Toegang Leveringsautorisatie Datum Einde Vandaag|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 12.   Leveringsautorisatie ongeldig
                LT: R2057_LT12
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum  of LEEG
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum  of LEEG
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum  of LEEG
                Leveringsautorisatie.Datum ingang   > systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Leveringsautorisatie_Datum_Ingang_Morgen

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Leveringsautorisatie Datum Ingang Morgen|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 13.   Leveringsautorisatie ongeldig
                LT: R2057_LT13
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum  of LEEG
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum  of LEEG
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum  of LEEG
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    < systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Leveringsautorisatie_Datum_Einde_Gisteren

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Leveringsautorisatie Datum Einde Gisteren|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 14.   Leveringsautorisatie ongeldig
                LT: R2057_LT14
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum  of LEEG
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum  of LEEG
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum  of LEEG
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    = systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Leveringsautorisatie_Datum_Einde_Vandaag

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Leveringsautorisatie Datum Einde Vandaag|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 15.   Partijrol Ongeldig
                LT: R2057_LT15
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum  of LEEG
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum  of LEEG
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum  of LEEG
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum  of LEEG
                Partijrol.Datum ingang              > systeemdatum
                Partijrol.Datum einde               > systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Partij_Rol_Ingang_Morgen_Einde_Morgen

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|PartijrolMorgenMorgen|'PartijRolDatumIngangMorgenDatumEindeMorgen'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 16.   Partijrol Ongeldig
                LT: R2057_LT16
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum  of LEEG
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum  of LEEG
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum  of LEEG
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum  of LEEG
                Partijrol.Datum ingang              < systeemdatum
                Partijrol.Datum einde               < systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Partij_Rol_Ingang_Gisteren_Einde_Gisteren

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|PartijrolGisterenGisteren|'PartijRolDatumIngangGisterenDatumEindeGisteren'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 17.   Partijrol Ongeldig
                LT: R2057_LT17
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum  of LEEG
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum  of LEEG
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum  of LEEG
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum  of LEEG
                Partijrol.Datum ingang              < systeemdatum
                Partijrol.Datum einde               = systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Partij_Rol_Ingang_Gisteren_Einde_Vandaag

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|PartijrolGisterenVandaag|'PartijRolDatumIngangGisterenDatumEindeVandaag'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 18.   Partij Ongeldig
                LT: R2057_LT18
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum  of LEEG
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum  of LEEG
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum  of LEEG
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum  of LEEG
                Partijrol.Datum ingang              < systeemdatum
                Partijrol.Datum Einde               > systeemdatum  of LEEG
                Partij.Datum Ingang                 > systeemdatum
                Partij.Datum Einde                  > systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Partij_Datum_Ingang_Morgen_Datum_Einde_Morgen

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|PartijMorgenMorgen|'PartijDatumIngangMorgenDatumEindeMorgen'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 19.   Partij Ongeldig
                LT: R2057_LT19
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum  of LEEG
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum  of LEEG
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum  of LEEG
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum  of LEEG
                Partijrol.Datum ingang              < systeemdatum
                Partijrol.Datum Einde               > systeemdatum  of LEEG
                Partij.Datum Ingang                 < systeemdatum
                Partij.Datum Einde                  < systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Partij_Datum_Ingang_Gisteren_Datum_Einde_Gisteren

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|PartijGisterenGisteren|'PartijDatumIngangGisterenDatumEindeGisteren'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 20.   Partij Ongeldig
                LT: R2057_LT20
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum  of LEEG
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum  of LEEG
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum  of LEEG
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum  of LEEG
                Partijrol.Datum ingang              < systeemdatum
                Partijrol.Datum Einde               > systeemdatum  of LEEG
                Partij.Datum Ingang                 < systeemdatum
                Partij.Datum Einde                  = systeemdatum

Given leveringsautorisatie uit autorisatie/R2057/Partij_Datum_Ingang_Gisteren_Datum_Einde_Vandaag

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|PartijGisterenVandaag|'PartijDatumIngangGisterenDatumEindeVandaag'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 21.   PartijOndertekenaar Ongeldig
                LT: R2057_LT21
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum  of LEEG
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum  of LEEG
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum  of LEEG
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum  of LEEG
                Partijrol.Datum ingang              < systeemdatum
                Partijrol.Datum Einde               > systeemdatum  of LEEG
                Partij.Datum Ingang                 < systeemdatum
                Partij.Datum Einde                  > systeemdatum  of LEEG
                Partij.Ondertekenaar.Datum Ingang   > systeemdatum
                Partij.Ondertekenaar.Datum Einde    > systeemdatum

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2243/oin_ongelijk_Ongeldig_dat_ing_morgen
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'DatumIngangOngeldigPartij'
|transporteur|'Gemeente Alkmaar'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                   |
| R2343 | De ondertekenaar is geen geldige partij.  |

Then is er een autorisatiefout gelogd met regelcode R2243

Scenario: 22.   PartijOndertekenaar Ongeldig
                LT: R2057_LT22
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum  of LEEG
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum  of LEEG
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum  of LEEG
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum  of LEEG
                Partijrol.Datum ingang              < systeemdatum
                Partijrol.Datum Einde               > systeemdatum  of LEEG
                Partij.Datum Ingang                 < systeemdatum
                Partij.Datum Einde                  > systeemdatum  of LEEG
                Partij.Ondertekenaar.Datum Ingang   < systeemdatum
                Partij.Ondertekenaar.Datum Einde    < systeemdatum

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2243/oin_ongelijk_Ongeldig_dat_einde_gisteren
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'DatumEindeGisterenPartij'
|transporteur|'Gemeente Alkmaar'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                   |
| R2343 | De ondertekenaar is geen geldige partij.  |

Then is er een autorisatiefout gelogd met regelcode R2243

Scenario: 23.   PartijOndertekenaar Ongeldig
                LT: R2057_LT23
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum  of LEEG
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum  of LEEG
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum  of LEEG
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum  of LEEG
                Partijrol.Datum ingang              < systeemdatum
                Partijrol.Datum Einde               > systeemdatum  of LEEG
                Partij.Datum Ingang                 < systeemdatum
                Partij.Datum Einde                  > systeemdatum  of LEEG
                Partij.Ondertekenaar.Datum Ingang   < systeemdatum
                Partij.Ondertekenaar.Datum Einde    = systeemdatum

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2243/oin_ongelijk_Ongeldig_dat_einde_vandaag
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'DatumEindeVandaagPartij'
|transporteur|'Gemeente Alkmaar'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                   |
| R2343 | De ondertekenaar is geen geldige partij.  |

Then is er een autorisatiefout gelogd met regelcode R2243


Scenario: 24.   PartijTransporteur Ongeldig
                LT: R2057_LT24
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum  of LEEG
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum  of LEEG
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum  of LEEG
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum  of LEEG
                Partijrol.Datum ingang              < systeemdatum
                Partijrol.Datum Einde               > systeemdatum  of LEEG
                Partij.Datum Ingang                 < systeemdatum
                Partij.Datum Einde                  > systeemdatum  of LEEG
                Partij.Ondertekenaar.Datum Ingang   < systeemdatum
                Partij.Ondertekenaar.Datum Einde    > systeemdatum  of LEEG
                Partij.Transporteur.Datum Ingang    > systeemdatum
                Partij.Transporteur.Datum Einde     > systeemdatum  of LEEG


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2244/oin_ongelijk_Ongeldig_dat_ing_morgen
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'Gemeente Utrecht'
|transporteur|'DatumIngangOngeldigPartij'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                     |
| R2343 | De transporteur is geen geldige partij.     |

Then is er een autorisatiefout gelogd met regelcode R2244

Scenario: 25.   PartijTransporteur Ongeldig
                LT: R2057_LT25
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum  of LEEG
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum  of LEEG
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum  of LEEG
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum  of LEEG
                Partijrol.Datum ingang              < systeemdatum
                Partijrol.Datum Einde               > systeemdatum  of LEEG
                Partij.Datum Ingang                 < systeemdatum
                Partij.Datum Einde                  > systeemdatum  of LEEG
                Partij.Ondertekenaar.Datum Ingang   < systeemdatum
                Partij.Ondertekenaar.Datum Einde    > systeemdatum  of LEEG
                Partij.Transporteur.Datum Ingang    < systeemdatum
                Partij.Transporteur.Datum Einde     < systeemdatum

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2244/oin_ongelijk_Ongeldig_dat_einde_gisteren
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'Gemeente Utrecht'
|transporteur|'DatumEindeGisterenPartij'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                     |
| R2343 | De transporteur is geen geldige partij.     |

Then is er een autorisatiefout gelogd met regelcode R2244

Scenario: 26.   PartijTransporteur Ongeldig
                LT: R2057_LT26
                Verwacht resultaat: GEEN Mutatiebericht
                GEEN Levering na administratieve handeling
                Uitwerking:
                Dienst mutatielevering op basis van afnemerindicatie
                Dienst.Datum ingang                 < systeemdatum
                Dienst.Datum einde                  > systeemdatum  of LEEG
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum  of LEEG
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum  of LEEG
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum  of LEEG
                Partijrol.Datum ingang              < systeemdatum
                Partijrol.Datum Einde               > systeemdatum  of LEEG
                Partij.Datum Ingang                 < systeemdatum
                Partij.Datum Einde                  > systeemdatum  of LEEG
                Partij.Ondertekenaar.Datum Ingang   < systeemdatum
                Partij.Ondertekenaar.Datum Einde    > systeemdatum  of LEEG
                Partij.Transporteur.Datum Ingang    < systeemdatum
                Partij.Transporteur.Datum Einde     = systeemdatum

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2244/oin_ongelijk_Ongeldig_dat_einde_vandaag
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'Gemeente Utrecht'
|transporteur|'DatumEindeVandaagPartij'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                     |
| R2343 | De transporteur is geen geldige partij.     |

Then is er een autorisatiefout gelogd met regelcode R2244