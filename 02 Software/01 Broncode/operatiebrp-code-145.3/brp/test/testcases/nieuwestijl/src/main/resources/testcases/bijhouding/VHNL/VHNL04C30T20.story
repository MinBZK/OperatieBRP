Meta:
@status                 Klaar
@sleutelwoorden         voltrekkingHuwelijkInNederland
@usecase                UCS-BY.HG

Narrative:
Registratie aanvang huwelijk in NL tussen I-I en Onbekend zonder meegeven persoonsgegevens

Scenario:   R1625 R2181 Personen Libby Thatcher (Ingeschrevene-Ingezetene) en Onbekend gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: VHNL04C30T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL04C30T20.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL04C30T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R







