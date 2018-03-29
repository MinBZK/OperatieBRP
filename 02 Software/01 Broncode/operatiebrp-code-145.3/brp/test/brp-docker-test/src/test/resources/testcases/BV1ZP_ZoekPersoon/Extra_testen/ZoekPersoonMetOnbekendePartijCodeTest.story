Meta:
@status             Klaar
@usecase            LV.1.CPI
@sleutelwoorden     Zoek Persoon

Narrative:
Hoewel Zoek persoon met name bedoeld is om een ingang naar andere diensten te verschaffen,
kan het zijn dat een gebruiker aan de beperkte resultaatset van deze dienst al voldoende informatie
over een persoon heeft (bijvoorbeeld als hij alleen zijn actuele adres nodig heeft).
In dat geval hoeft de gebruiker geen aanvullende diensten meer aan te roepen.
In het resultaat van Zoek persoon worden ook de verplichte gegevens meegeleverd
(o.a. onderzoeken, melding verstrekkingsbeperkingen, en (nadere) bijhoudingsaard)

Deze story is aangemaakt om extra testen uit te kunnen voeren met betrekking tot de xsd-validatie van het
antwoordbericht van zoek persoon.

Ook zijn hier testen toegevoegd met betrekking tot de levering van verplichte gegevens:
- Onderzoeken
- (nadere) bijhoudingsaard
- Verstrekkingbeperking zit in de API testen (Geconverteerde_Data_Test, GBA_Bijhouding_Omzetting_Partnerschap_in_huwelijk.story)

Verder wordt een zo compleet mogelijk antwoordbericht gecontroleerd op de te leveren groepen.
Zo wordt er getest dat adelijke titel niet meekomt in het bericht (met een synchronisatiebericht ter vergelijking (scenario 1.2)

Scenario:   1.1 Zoek Persoon met onbekende partijcode

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_onbekende_partij_code.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE  | MELDING                               |
| R2343 | Er is een autorisatiefout opgetreden. |