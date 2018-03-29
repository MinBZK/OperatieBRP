Meta:
@auteur                 fuman
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Registratie aanvang huwelijk in NL tussen I-I en Onbekend met GBA historie op personen

Scenario:   Personen Libby Thatcher met pers historie (Ingeschrevene-Ingezetene) en Pieter Jansen (Onbekend) gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: VHNL04C30T30

Gemeente BRP 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby_met_his_pers.xls

When voer een bijhouding uit VHNL04C30T30.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL04C30T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 wel als PARTNER betrokken bij een HUWELIJK





