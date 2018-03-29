Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Hoofdactie verwerken einde geregistreerd partnerschap in het buitenland

Scenario: Hoofdactie verwerken einde geregistreerd partnerschap in het buitenland
          LT: EGBL02C20T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGBL/EGBL02C20T10.xls

Given pas laatste relatie van soort 2 aan tussen persoon 150521133 en persoon 517008841 met relatie id 2000085 en betrokkenheid id 2000086

When voer een bijhouding uit EGBL02C20T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGBL/expected/EGBL02C20T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 150521133 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then lees persoon met anummer 5019235602 uit database en vergelijk met expected EGBL02C20T10-persoon1.xml
