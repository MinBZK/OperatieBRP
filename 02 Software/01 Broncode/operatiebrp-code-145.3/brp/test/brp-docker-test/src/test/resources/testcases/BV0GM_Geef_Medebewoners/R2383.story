Meta:
@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Geef Medebewoner van Persoon
@regels             R2383

Narrative:
Indien de verstrekking de Soort dienst Geef medebewoners van persoon betreft en Bericht.Identificatiecriteria is gevuld met
Persoonsidentificatie (Identiteitsnummer (R2191))
OF
Bericht.Identificatiecode nummeraanduiding
OF
een of meer van de volgende adresgegevens:
Bericht.Gemeente code,
Bericht.Afgekorte naam openbare ruimte,
Bericht.Huisnummer,
Bericht.Huisletter,
Bericht.Huisnummertoevoeging,
Bericht.Locatie ten opzichte van adres,
Bericht.Postcode,
Bericht.Woonplaatsnaam
dan
moeten de identificatiecriteria op 'Peilmoment materieel' (R2395) herleidbaar zijn tot één Identificatiecode nummeraanduiding.

!-- Testgevallen 1 tot en met 8 staan in de API-testen

!-- Jan woont op huisnummer 16, met .Identificatiecode nummeraanduiding ...01, sinds 2011
!-- Jan met afwijkend BAG woont op huisnummer 17, met .Identificatiecode nummeraanduiding ...02, sinds 2009
!-- Voor de rest zijn beide personen identiek

Scenario: 08.   Geef medebewoners van persoon op overig adresgegeven postcode EN huisnummer 16
                LT: R2383_LT08, R1356_LT01
                Verwacht resultaat:
                - Geslaagd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_afwijkende_BAG.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2383_LT08.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2383_scenario_8.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario: 09.   Geef medebewoners van persoon op overig adresgegeven postcode
                LT: R2383_LT09
                Verwacht resultaat:
                - Foutief
                - R2392 Adresidentificatie is herleidbaar tot meer dan één adres

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_afwijkende_BAG.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2383_LT09.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2392     | Adresidentificatie is herleidbaar tot meer dan één adres   |

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2383_scenario_9.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario: 10.   Geef medebewoners van persoon op overig adresgegeven huisnummer, Peilmoment gevuld 2015-01-01
                LT: R2383_LT10
                Verwacht resultaat:
                - Geslaagd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_afwijkende_BAG.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2383_LT10.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2383_scenario_10.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario: 11.a  Geef medebewoners van persoon op overig adresgegeven huisnummer, Peilmoment gevuld 1960-01-01
                LT: R2383_LT11
                Verwacht resultaat:
                - Foutief, R2383 Identificatiecriteria is niet herleidbaar tot één adres met een of meer personen.
                Uitwerking:
                - Personen pas na 1960 op gezochte adres, dus niemand gevonden op peilmoment

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_afwijkende_BAG.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2383_LT11.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                               |
| R2383 | De opgegeven identificatiecriteria leveren meer dan één BAG adres op. |

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2383_scenario_11a.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario: 11.b  Geef medebewoners van persoon op overig adresgegeven postcode, Peilmoment gevuld 2015-01-01
                LT: R2383_LT11, R2392_LT02
                - Foutief
                - R2392 Adresidentificatie is herleidbaar tot meer dan één adres
                Uitwerking:
                - Personen in 2015 op gezochte postcode, dus 2 .Identificatiecode nummeraanduiding, dus foutmelding

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_afwijkende_BAG.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2383_LT11b.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2392     | Adresidentificatie is herleidbaar tot meer dan één adres   |

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2383_scenario_11b.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario: 11.c  Geef medebewoners van persoon op overig adresgegeven postcodePeilmoment gevuld 2010-01-01
                LT: R2383_LT11
                Verwacht resultaat:
                - Geslaagd, Alleen Jan met afwijkend BAG gevonden
                Uitwerking:
                - In 2010 voldoet alleen Jan met afwijkende BAG aan identificatiecriteria
                - Jan_met_historie_op_adres2.xls heeft een vervallen record welke voldoet op het peilmoment. Deze wordt niet geleverd omdat we alleen naar materieel kijken.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_afwijkende_BAG.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan_met_historie_op_adres2.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2383_LT11c.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 1 groepen 'persoon'

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2383_scenario_11c.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario: 12.   Geef medebewoners van persoon op overig adresgegeven postcode, gemeentecode, huisletter, huisnummertoevoeging
                LT: R2383_LT13
                Verwacht resultaat:
                - Foutief, want adres gegevens leiden tot een lege BAG, dus niet herleidbaar tot 1 Identificatiecode nummeraanduiding

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_geen_BAG.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2383_LT12.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2383     | *  |

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2383_scenario_12.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario: 13.   Geef medebewoners van persoon met ongeldig verzoek (postcode is niet geldig)
                LT: R1356_LT02
                Verwacht resultaat:
                - Geslaagd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R1356_LT02.xml

Then is het antwoordbericht een soapfault

Scenario: 14.   Geef medebewoners van persoon op Identificatiecode adresseerbaar object, Peilmoment gevuld 1960-01-01
                LT: R2383_LT14
                Verwacht resultaat:
                - Foutief, R2383 Identificatiecriteria is niet herleidbaar tot één adres met een of meer personen.
                Uitwerking:
                - Personen pas na 1960 op gezochte adres, dus niemand gevonden op peilmoment

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2383_LT14.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2383     | *  |

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2383_scenario_14.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R
