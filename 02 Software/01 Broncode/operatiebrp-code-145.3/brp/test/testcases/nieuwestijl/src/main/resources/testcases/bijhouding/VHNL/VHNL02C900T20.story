Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R2458 De groep identificatienummers moet ten minste een administratienummer of een burgerservicenummer bevatten

Scenario: Identificatienummers zonder Administratienummer en met Burgerservicenummer
          LT: VHNL02C900T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C900T20.xls

When voer een bijhouding uit VHNL02C900T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C900T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 342281641 wel als PARTNER betrokken bij een HUWELIJK