Meta:
@status             Klaar
@sleutelwoorden     Expressietaal

Narrative:
Testen voor expressie taal van de filter functies:
- ALS
- FILTER (lijst, variabele, conditie)
- ER_IS
- ALLE

Scenario:  01 ALS Functie Persoon.Naamgebruik.AdellijkeTitelCode
           LT:
           Expressie: ALS(Persoon.Naamgebruik.AdellijkeTitelCode <> NULL, WAAR, ONWAAR)
           Verwacht resultaat: Alle personen met een adelijke titel code
Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/functie_ALS

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Expressietaal'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:  02 FILTER Functie GerelateerdeOuder.Persoon
           LT:
           Expressie: AANTAL(FILTER(GerelateerdeOuder.Persoon, o, o.Geslachtsaanduiding.Code = "M"))=1
           Verwacht resultaat: Alle personen met 1 ouder waarbij geslacht = "M"
Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/functie_FILTER

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Expressietaal'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:  03 ER_IS Functie Persoon.Adres
           LT:
           Expressie: ER_IS(Persoon.Adres, a, a.Postcode="1422RZ")
           Verwacht resultaat: Persoon heeft nu of in het verleden adres met postcode 1422RZ gehad
Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/functie_ER_IS

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Expressietaal'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:  04 ALLE Functie GerelateerdeKind.Persoon
           LT:
           Expressie: ALLE(GerelateerdeKind.Persoon, k, k.Geboorte.Datum >= 2010/01/01)
           Verwacht resultaat: Alle kinderen geboren op of na 2010/01/01
Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/functie_ALLE

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Expressietaal'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd