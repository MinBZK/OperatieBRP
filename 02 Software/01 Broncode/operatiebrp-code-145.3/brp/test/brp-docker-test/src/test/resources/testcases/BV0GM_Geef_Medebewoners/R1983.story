Meta:
@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Geef Medebewoner van Persoon
@regels             R1983

Narrative:
Het resultaatbericht bevat alleen Persoon(en) die op
'Peilmoment materieel' (R2395) actuele ingezetene zijn;
Persoon.Soort = "Ingeschrevene" (I)
EN
Persoon.Nadere bijhoudingsaard = "Actueel" (A).

Scenario: 1.    Jan heeft geen verstrekkingsbeperking, Rick wel. Beide zelfde adres
                LT: R1339_LT09, R1339_LT10, R1983_LT17
                Verwacht resultaat:
                Alleen Jan in het bericht

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/R1983/Rick_volledige_verstrekkingsbeperking.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewonersVerstrekkingsBeperking' en partij 'Gemeente VerstrekkingsbepMogelijk'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2397_LT01.xml

Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 1 groepen 'persoon'
And is het antwoordbericht xsd-valide

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R1983_scenario_1.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R
