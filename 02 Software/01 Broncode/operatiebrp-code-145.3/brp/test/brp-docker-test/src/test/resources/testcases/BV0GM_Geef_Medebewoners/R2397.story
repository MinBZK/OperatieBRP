Meta:
@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Geef Medebewoner van Persoon
@regels             R2397

Narrative:
Het resultaatbericht bevat alleen Persoon(en) die op
'Peilmoment materieel' (R2395) actuele ingezetene zijn;
Persoon.Soort = "Ingeschrevene" (I)
EN
Persoon.Nadere bijhoudingsaard = "Actueel" (A) of Onbekend (?)

Scenario: 1.    Personen met verschillende nadere.bijhoudingsaarden, geen peilmoment
                LT: R2397_LT01, R2397_LT02, R2397_LT03, R2397_LT04, R2397_LT05, R2397_LT06, R2397_LT08, R1539_LT14
                Verwacht resultaat:
                Alleen Jan heeft nadere bijhoudingsaard Actueel, dus alleen Jan geleverd, geen pseudopersonen

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/R2397/Bob_naderebijhoudingsaard_R.xls
Given enkel initiele vulling uit bestand /LO3PL/R2397/Willem_nadere_bijhoudingsaard_Emigratie.xls
Given enkel initiele vulling uit bestand /LO3PL/R2397/Henk_naderebijhoudingsaard_overleden.xls
Given enkel initiele vulling uit bestand /LO3PL/R2397/Wilfred_nadere_bijhoudingsaard_ministerieel_besluit.xls
Given enkel initiele vulling uit bestand /LO3PL/R2397/Harry_naderebijhoudingsaard_fout.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2397_LT01.xml

Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 1 groepen 'persoon'
And is het antwoordbericht xsd-valide

Scenario: 2.    Personen met verschillende nadere.bijhoudingsaarden, peilmoment op datum dat alle Nadere.bijhoudinsaarden nog actueel waren
                LT: R2397_LT07
                Verwacht resultaat:
                - Alleen Jan komt terug

!-- Nadere bijhoudingsaarden worden allemaal als Onbekend geconverteerd vanuit GBA, dus alle personen met
!-- nadere bijhoudingsaard <> A=Actueel worden gefilterd
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/R2397/Bob_naderebijhoudingsaard_R.xls
Given enkel initiele vulling uit bestand /LO3PL/R2397/Willem_nadere_bijhoudingsaard_Emigratie.xls
Given enkel initiele vulling uit bestand /LO3PL/R2397/Henk_naderebijhoudingsaard_overleden.xls
Given enkel initiele vulling uit bestand /LO3PL/R2397/Wilfred_nadere_bijhoudingsaard_ministerieel_besluit.xls
Given enkel initiele vulling uit bestand /LO3PL/R2397/Harry_naderebijhoudingsaard_fout.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2397_LT02.xml

Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 4 groepen 'persoon'
And is het antwoordbericht xsd-valide

