Meta:
@status                 Klaar
@regels                 R1882
@usecase                UCS-BY.HG

Narrative:
Nevenactie Registratie Gelsachtsnaam Datum aanvang geldigheid > Datum aanvang relatie -> Melding R1882

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Ingeschrevene-Ingezetene, NL Nat) gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: VHNL02C450T50

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T10-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T10-Piet.xls

When voer een bijhouding uit VHNL02C450T50.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C450T50.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK








