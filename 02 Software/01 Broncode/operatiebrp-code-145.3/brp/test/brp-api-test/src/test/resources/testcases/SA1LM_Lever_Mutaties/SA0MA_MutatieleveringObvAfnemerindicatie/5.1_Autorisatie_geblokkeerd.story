Meta:
@status             Klaar
@usecase            SA.0.MA
@regels             R2016, R2129, R1314, R1315, R1338, R1343, R1544, R1989, R1990, R1991, R1993, R1994, R2057, R2060, R2062
@sleutelwoorden     Mutatielevering o.b.v. afnemerindicatie, Lever mutaties

Narrative:
Bij het leveren naar aanleiding van een Administratieve handeling geldt dat er alleen berichten worden aangemaakt en afnemerindicaties worden onderhouden als
er geen indicatie Dienst.Geblokkeerd? is bij de Dienst waarvoor geleverd wordt,
er geen indicatie Dienstbundel.Geblokkeerd? is bij de Dienstbundel waarin deze dienst zit,
er geen indicatie Toegang leveringsautorisatie.Geblokkeerd? is bij de Toegang leveringsautorisatie waarvoor geleverd wordt,
er geen indicatie Leveringsautorisatie.Geblokkeerd? is bij de Leveringsautorisatie waarbij deze Toegang leveringsautorisatie hoort.


Scenario: 1.    Dienst.Geblokkeerd aanwezig op de afnemerindicatie
                LT: R1264_LT16
                Verwacht resultaat: geen bericht aangemaakt en afnemerindicatie niet onderhouden

Given leveringsautorisatie uit autorisatie/geblokkeerd/dienst_mutlev_afnemerindicatie_geblokkeerd

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Autorisatietest|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden


Scenario: 2.    Dienstbundel.Geblokkeerd aanwezig op de afnemerindicatie
                LT: R2056_LT16
                Verwacht resultaat: geen bericht aangemaakt en afnemerindicatie niet onderhouden

Given leveringsautorisatie uit autorisatie/geblokkeerd/dienstbundel_geblokkeerd

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Dienstbundel geblokkeerd|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden


Scenario: 3.    Toegangleverautorisatie.Geblokkeerd aanwezig op de afnemerindicatie
                LT: R2052_LT02
                Verwacht resultaat: geen bericht aangemaakt en afnemerindicatie niet onderhouden

Given leveringsautorisatie uit autorisatie/geblokkeerd/Toegang_Leveringsautorisatie_Geblokkeerd

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Toegang leveringsautorisatie geblokkeerd|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden


Scenario: 4.    leveringsautorisatie.Geblokkeerd aanwezig op de afnemerindicatie
                LT: R1263_LT02
                Verwacht resultaat: geen bericht aangemaakt en afnemerindicatie niet onderhouden

Given leveringsautorisatie uit autorisatie/geblokkeerd/Leveringsautorisatie_geblokkeerd

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|854820425|Leveringsautorisatie geblokkeerd|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|
When voor persoon 854820425 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden