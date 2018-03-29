Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Ontbinding huwelijk in Nederland. Personen Anne(I) en Jan(P) hebben een asymmetrisch BRP huwelijk, dit huwelijk wordt in de BRP ontbonden

Scenario:   datumAanvangGeldigheid van de actie is ongelijk aan de datumEinde in de ontbinding
            LT: OHNL05C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-OHNL/OHNL05C10T10-001.xls

When voer een bijhouding uit OHNL05C10T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL05C10T10a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Given pas laatste relatie van soort 1 aan tussen persoon 829675401 en persoon 590788681 met relatie id 2000101 en betrokkenheid id 2000102

Then is in de database de persoon met bsn 829675401 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 590788681 wel als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit OHNL05C10T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL05C10T10b.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 829675401 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 590788681 niet als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select dataanvgel from kern.his_perssamengesteldenaam where voornamen = 'Jan' de volgende gegevens:
| veld       | waarde   |
| dataanvgel | 20160602 |
