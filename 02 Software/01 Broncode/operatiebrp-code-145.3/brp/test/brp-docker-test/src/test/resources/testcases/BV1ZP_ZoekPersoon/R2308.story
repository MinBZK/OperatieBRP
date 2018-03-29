Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2308

Narrative:
Voor elke Zoekcriterium in een zoekvraag geldt dat de gebruikte waarde moet corresponderen met het datatype van betreffende element:
Als Zoekcriterium.Element van het type Datum is, dan dient Zoekcriterium.Waarde een geldige Datum (deels) onbekend (R1273) te bevatten.
Als Zoekcriterium.Element van het type Numeriek is, dan dient Zoekcriterium.Waarde een geldige numerieke waarde te bevatten.

Scenario: 1.    zoekcriterium.elementNaam Datatype = zoekcriterium.waarde
                LT: R2308_LT01
                Verwacht resultaat:
                - Geslaagd
                Uitwerking:
                - Er wordt met een string waarde naar een elementnaam dat als datatype ook string heeft

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2308_1.xml

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 2.    zoekcriterium.elementNaam Datatype ongelijk aan zoekcriterium.elementNaam Datatype
                LT: R2308_LT01
                Verwacht resultaat:
                - Foutief: De opgegeven waarde komt niet overeen met het datatype van het opgegeven element.
                Uitwerking:
                - Postcode bevat omgekeerde vulling (KK1888)

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2308_2.xml
Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3.    zoekcriterium.elementNaam Datatype = zoekcriterium.elementNaam Datatype
                LT: R2308_LT01
                Verwacht resultaat:
                - Geslaagd
                Uitwerking:
                - element dat gevuld moet worden met een VARCHAR, wordt gevuld met een getal
                - gaat dus goed, ook al voelt het niet logisch

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2308_3.xml
Then heeft het antwoordbericht verwerking Geslaagd

