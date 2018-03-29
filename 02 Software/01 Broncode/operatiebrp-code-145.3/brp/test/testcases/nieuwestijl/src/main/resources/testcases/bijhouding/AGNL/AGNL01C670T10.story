Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
R1630 (BRAL0202)Persoon is hoogstens één keer betrokken in dezelfde relatie

Scenario: R1630 In een H-relatie is 1 partner 2 keer betrokken. Libby start een geregistreerd partnerschap met zichzelf.
            LT: AGNL01C670T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls

When voer een bijhouding uit AGNL01C670T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C670T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

