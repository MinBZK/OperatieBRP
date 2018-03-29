Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R2458 De groep identificatienummers moet ten minste een administratienummer of een burgerservicenummer bevatten

Scenario: Identificatienummers zonder Administratienummer en zonder Burgerservicenummer
          LT: VHNL02C900T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C900T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C900T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK