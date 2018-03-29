Meta:
@status                 Klaar
@sleutelwoorden         Foutief
@usecase                UCS-BY.HG

Narrative: R2117 De persoon die met een objectsleutel wordt aangewezen moet een ingeschrevene zijn

Scenario:   Pseudo Persoon wordt aangewezen met objectsleutel
            LT: VHNL01C380T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C380T10-Libby.xls

When voer een bijhouding uit VHNL01C380T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C380T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK





