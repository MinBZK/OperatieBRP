Meta:
@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Geef Medebewoner van Persoon
@regels             R2399

Narrative:
Het resultaatbericht bevat alleen Persoon(en) die op
'Peilmoment materieel' (R2395) actuele ingezetene zijn;
Persoon.Soort = "Ingeschrevene" (I)
EN
Persoon.Nadere bijhoudingsaard = "Actueel" (A).

Scenario: 1.    Jan en Janna voldoen aan totale populatiebeperking
                LT: R2399_LT01
                Verwacht resultaat:
                Jan en Janna beide in het bericht

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/Janna.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewonersMetPopulatiebep' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2397_LT01.xml

Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 2 groepen 'persoon'
And is het antwoordbericht xsd-valide
