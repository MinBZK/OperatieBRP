Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1606
@usecase                UCS-BY.HG

Narrative:
R1606 Brondocument moet geldig zijn voor de administratieve handeling en actie

Scenario: R1606 Het opgegeven documentsoort staat niet in de stamtabel
          LT: GHNL01C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL01C30T10-Nicolette.xls

When voer een GBA bijhouding uit GHNL01C30T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL01C30T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 598151497 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 6206465810 uit database en vergelijk met expected GHNL01C30T10-persoon1.xml
