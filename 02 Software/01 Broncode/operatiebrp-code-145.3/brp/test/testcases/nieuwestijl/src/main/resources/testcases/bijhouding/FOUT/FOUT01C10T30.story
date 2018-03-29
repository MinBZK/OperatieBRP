Meta:
@status                 Klaar
@sleutelwoorden         Fout

Narrative: Testen van expecteds

Scenario:   1. DB init scenario om uitgangssituatie te zetten
            preconditie

Given alle personen zijn verwijderd

Scenario:   2. Ontleningstoelichting is leeg meegestuurd inhoud expected xsd fout verschilt
            LT: FOUT01C10T30

Given enkel initiele vulling uit bestand /LO3PL/VHNL03C210-001.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C210-002.xls

When voer een bijhouding uit FOUT01C10T30.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/FOUT/expected/FOUT01C10T30.txt

Scenario:   3. Expected xsd fout niet aanwezig
            postconditie

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/FOUT/expected/FOUT01C10T40.txt


