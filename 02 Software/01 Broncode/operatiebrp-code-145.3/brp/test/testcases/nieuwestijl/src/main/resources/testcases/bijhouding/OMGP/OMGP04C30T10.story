Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: Verwerking naamgebruik

Scenario:   Registratie Partner naam voor Eigen naam
            LT: OMGP04C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-OMGP/OMGP04C30T10-001.xls

Given pas laatste relatie van soort 2 aan tussen persoon 844971145 en persoon 295486697 met relatie id 31000005 en betrokkenheid id 31000005

When voer een bijhouding uit OMGP04C30T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OMGP/expected/OMGP04C30T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 844971145 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 295486697 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 844971145 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 295486697 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 9564858145 uit database en vergelijk met expected OMGP04C30T10-persoon1.xml













