Meta:
@status                 Klaar
@sleutelwoorden         Intaketest, Afnemerindicatie


Narrative:
Als gebruiker
wil ik een afnemerindicatie kunnen plaatsen
Zodat ik een volledig bericht krijg.

Scenario:   1a. Gemeente Utrecht plaatst een afnemerindicatie op persoon UC_Kenny met bsn = 999646564,
            LT:
            vanuit de leverautorisatie Geen_pop.bep_levering_op_basis_van_afnemerindicatie
            Deze leverautorisatie beschikt over de diensten: Plaatsen afnemerindicatie, Verwijderen afnemerindicatie,
            Mutatielevering op basis van afnemerindicatie en Synchronisatie persoon
            Verwacht resultaat:
            1. Plaats afnemerIndicatie is geslaagd.
            2. Volledig bericht voor UC Kenny

Given persoonsbeelden uit specials:IENT/INT2833_PL_Mutatie_xls
Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|
|226546019 |Geen pop.bep. levering op basis van afnemerindicatie|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z

When voor persoon 226546019 wordt de laatste handeling geleverd