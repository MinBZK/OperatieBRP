Meta:
@status             Klaar
@usecase            SA.0.VA
@regels             R1983
@sleutelwoorden     Verwijder afnemerindicatie

Narrative:
Afnemerindicatie voor de opgegeven persoon moet bestaan.
Er moet een persoon bestaan met het opgegeven burgerservicenummer.
Plaatsen of verwijderen afnemerindicatie mag alleen op uniek burgerservicenummer.
Verstrek resultaatbericht bij geslaagd plaatsen of verwijderen van afnemerindicatie

Scenario: 1. Voor de persoon Jan wordt een afnemerindicatie verwijderd, verstrekkingsbeperking op partij
                LT: R1983_LT33
                Verwacht resultaat:
                -Afnemerindicatie verwijderd

Given leveringsautorisatie uit autorisatie/Verwijder_afnemerindicatie_verstrekkingsbeperking
Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|771168585|levering op basis van afnemerindicatie verstrekkingsbeperking|'Stichting Interkerkelijke Ledenadministratie'|30|2016-07-28 T16:11:21Z

When voor persoon 771168585 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie levering op basis van afnemerindicatie verstrekkingsbeperking is ontvangen en wordt bekeken

Given verzoek verwijder afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'levering op basis van afnemerindicatie verstrekkingsbeperking'
|zendendePartijNaam|'Stichting Interkerkelijke Ledenadministratie'
|bsn|771168585


Then heeft het antwoordbericht verwerking Geslaagd

Then is er voor persoon met bsn 771168585 en leveringautorisatie levering op basis van afnemerindicatie verstrekkingsbeperking en partij Stichting Interkerkelijke Ledenadministratie de afnemerindicatie verwijderd

!-- Additionele controle opdat er geen vulbericht wordt verstuurd bij het verwijderen van de afnemerindicatie
Then is het aantal ontvangen berichten 0

Then is het verzoek correct gearchiveerd

