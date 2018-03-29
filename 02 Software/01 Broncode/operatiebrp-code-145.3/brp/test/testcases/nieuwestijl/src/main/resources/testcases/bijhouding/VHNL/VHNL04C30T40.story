Meta:
@status                 Klaar
@sleutelwoorden         voltrekkingHuwelijkInNederland, Geslaagd
@usecase                UCS-BY.HG

Narrative: Registratie aanvang huwelijk in NL tussen I-I en Onbekend met GBA historie op relatie/betrokkenheid

Scenario: Personen Libby Thatcher met relatie historie (Ingeschrevene-Ingezetene) en Pieter Jansen (Onbekend) gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief,
          LT: VHNL04C30T40

Gemeente BRP 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby_met_his_rel.xls


When voer een bijhouding uit VHNL04C30T40.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL04C30T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R










