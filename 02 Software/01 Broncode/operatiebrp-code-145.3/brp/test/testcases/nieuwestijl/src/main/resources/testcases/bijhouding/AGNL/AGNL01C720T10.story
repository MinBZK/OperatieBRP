Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd AGNL01C720T10
@usecase                UCS-BY.HG

Narrative:
Nevenactie Registratie Gelsachtsnaam Datum aanvang geldigheid > Datum aanvang relatie -> Melding R1882

Scenario:   Personen Marjan en Victor starten een GP, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: AGNL01C720T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan-reg_geslnaam.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls

When voer een bijhouding uit AGNL01C720T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C720T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 221087849 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
