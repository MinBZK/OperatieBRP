Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Verwerken einde geregistreerd partnerschap in het buitenland

Scenario: Verwerken einde geregistreerd partnerschap in het buitenland. Nevenactie registratie geslachtsnaam.
          LT: EGBL02C10T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGBL/EGBL02C10T20.xls

Given pas laatste relatie van soort 2 aan tussen persoon 917674121 en persoon 838103753 met relatie id 2000089 en betrokkenheid id 2000090

When voer een bijhouding uit EGBL02C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGBL/expected/EGBL02C10T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 917674121 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then lees persoon met anummer 9735369490 uit database en vergelijk met expected EGBL02C10T20-persoon1.xml
