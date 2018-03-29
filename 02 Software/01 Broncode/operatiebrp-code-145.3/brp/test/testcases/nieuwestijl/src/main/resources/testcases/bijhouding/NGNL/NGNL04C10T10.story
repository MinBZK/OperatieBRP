Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: verwerking Nietigverklaring Geregistreerd Partnerschap NL tussen I-I en Pseudo-persoon

Scenario: Relatie.reden.einde Nietigverklaring Geregistreerd Partnerschap NL
          LT: NGNL04C10T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-NGNL/NGNL04C10T10-001.xls

Given pas laatste relatie van soort 2 aan tussen persoon 770740297 en persoon 547837513 met relatie id 43000111 en betrokkenheid id 43000111

When voer een bijhouding uit NGNL04C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/NGNL/expected/NGNL04C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 770740297 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then lees persoon met anummer 7275360545 uit database en vergelijk met expected NGNL04C10T10-persoon1.xml







