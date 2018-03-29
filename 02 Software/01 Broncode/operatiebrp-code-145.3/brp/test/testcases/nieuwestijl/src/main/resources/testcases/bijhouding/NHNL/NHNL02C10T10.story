Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: verwerking Nietigverklaring Huwelijk NL tussen I-I en Pseudo-persoon

Scenario: Relatie.reden.einde Nietigverklaring Huwelijk NL
          LT: NHNL02C10T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-NHNL/NHNL02C10T10-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 693108873 en persoon 597462185 met relatie id 43000101 en betrokkenheid id 43000101

When voer een bijhouding uit NHNL02C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/NHNL/expected/NHNL02C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 693108873 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then lees persoon met anummer 7973205281 uit database en vergelijk met expected NHNL02C10T10-persoon1.xml





