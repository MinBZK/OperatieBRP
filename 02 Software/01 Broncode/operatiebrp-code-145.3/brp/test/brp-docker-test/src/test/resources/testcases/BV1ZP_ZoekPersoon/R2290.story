Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2290

Narrative:
Voor alle zoekcriteria in een zoekvraag moet gelden dat:

Zoekcriterium.Element moet als geautoriseerd Element voorkomen in Dienstbundel \ Groep \ Attribuut
bij de betreffende Dienstbundel.

Scenario:   1.  Dienstbundel heeft GEEN autorisatie op huisnummer. Zoekcriterium = BSN
                LT: R2290_LT01
                Verwacht resultaat:
                - ongeautoriseerde attributen niet in zoekresultaat

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon Huisnummer niet geautoriseerd' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2290.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is er voor xpath //brp:huisnummer geen node aanwezig in het antwoord bericht
