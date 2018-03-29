Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Huwelijk en geregistreerd partnerschap, beeindig Geregistreerd Partnerschap in Nederland, actie beeindigingGeregistreerdPartnerschapInNederland

Scenario: Beeindiging van een geregistreerd partnerschap. Nevenactie Registratie Geslachtsnaam.
          LT: EGNL02C10T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL02C10T20-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 805832841 en persoon 784497801 met relatie id 2000075 en betrokkenheid id 2000076

When voer een bijhouding uit EGNL02C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL02C10T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 805832841 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then lees persoon met anummer 2584701074 uit database en vergelijk met expected EGNL02C10T20-persoon1.xml
