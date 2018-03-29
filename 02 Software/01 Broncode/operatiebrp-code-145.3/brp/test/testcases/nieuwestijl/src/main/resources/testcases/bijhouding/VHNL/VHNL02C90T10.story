Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R1630 Persoon is hoogstens één keer betrokken in dezelfde relatie

Scenario: 1. In een H-relatie is 1 partner 2 keer betrokken. Libby trouwt met zichzelf.
            LT: VHNL02C90T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C90T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C90T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL02C90T10-persoon1.xml
