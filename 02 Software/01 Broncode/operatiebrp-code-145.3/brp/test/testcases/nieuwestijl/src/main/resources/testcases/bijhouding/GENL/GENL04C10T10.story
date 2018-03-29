Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative: GBA beeindiging Geregistreerd Partnerschap in Nederland

Scenario: Verwerking Ingeschrevene beindigt relatie met Pseudo
          LT: GENL04C10T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-GENL/GENL04C10T10-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 835864649 en persoon 255220297 met relatie id 4000073 en betrokkenheid id 4000074

When voer een GBA bijhouding uit GENL04C10T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GENL/expected/GENL04C10T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 835864649 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then lees persoon met anummer 8432967457 uit database en vergelijk met expected GENL04C10T10-persoon1.xml


