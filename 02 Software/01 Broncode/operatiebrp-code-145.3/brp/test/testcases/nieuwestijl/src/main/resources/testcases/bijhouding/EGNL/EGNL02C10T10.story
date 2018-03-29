Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Huwelijk en geregistreerd partnerschap, beeindig Geregistreerd Partnerschap in Nederland, actie beeindigingGeregistreerdPartnerschapInNederland

Scenario: Beeindiging van een geregistreerd partnerschap. Nevenactie Registratie Naamgebruik.
          LT: EGNL02C10T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL02C10T10-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 608064361 en persoon 255220297 met relatie id 2000073 en betrokkenheid id 2000074

When voer een bijhouding uit EGNL02C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL02C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 608064361 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then lees persoon met anummer 3282542354 uit database en vergelijk met expected EGNL02C10T10-persoon1.xml
