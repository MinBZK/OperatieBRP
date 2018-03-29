Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R1674
@usecase                UCS-BY.HG

Narrative:
R1674 Adellijke titel in Samengestelde naam moet verwijzen naar bestaand stamgegeven

Scenario:   Adellijke verwijst niet naar een bestaand stamgegeven
            LT: GHNL03C50T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL03C50T10.xml namens partij 'Migratievoorziening'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL03C50T10.txt

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 189975945 niet als PARTNER betrokken bij een HUWELIJK









