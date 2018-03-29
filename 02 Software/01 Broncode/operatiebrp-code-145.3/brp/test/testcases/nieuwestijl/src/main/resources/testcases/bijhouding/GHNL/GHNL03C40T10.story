Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R1675
@usecase                UCS-BY.HG

Narrative:
R1675 Predicaat in Samengestelde naam moet verwijzen naar bestaand stamgegeven

Scenario:   Ongeldige predicaat xxxxxxxxxxxxxxxxxxxxxx
            LT: GHNL03C40T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL03C40T10.xml namens partij 'Migratievoorziening'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL03C40T10.txt

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 189975945 niet als PARTNER betrokken bij een HUWELIJK









