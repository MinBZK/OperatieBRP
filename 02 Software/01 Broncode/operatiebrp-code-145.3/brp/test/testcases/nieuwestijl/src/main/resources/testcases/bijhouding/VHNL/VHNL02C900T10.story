Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R2458 De groep identificatienummers moet ten minste een administratienummer of een burgerservicenummer bevatten

Scenario: Identificatienummers met Administratienummer en zonder Burgerservicenummer
          LT: VHNL02C900T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C900T10.xls

When voer een bijhouding uit VHNL02C900T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C900T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 503038921 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 8340791570 uit database en vergelijk met expected VHNL02C900T10-persoon1.xml
