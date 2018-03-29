Meta:
@status             Klaar
@usecase            BV.0.ZA
@sleutelwoorden     Zoek Persoon op Adres
@regels             R2374

Narrative:
Bij de zoekcriteria huisnummer, huisletter en/of huisnummertoevoeging moet verplicht ook een postcode of straatnaam opgegeven worden.


Scenario: 1.    Zoek persoon op adres met criteria Huisnummer en postcode
                LT: R2374_LT01
                Verwacht resultaat:
                - Persoon op adres gevonden
                - Personen die niet voldoen aan de zoek criteria worden niet gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/2374_Anne_Bakker_Adres1.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/2374_Anne_Bakker_Adres2.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/2374_Anne_Bakker_Adres3.xls

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2374_1.1.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groep 'persoon'

!-- ZoekPersoonAdres door bijhouder
Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente BRP 1'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2374_1.2.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groep 'persoon'

Scenario: 2.    Zoek persoon op adres met criteria Huisletter en Postcode
                LT: R2374_LT02
                Verwacht resultaat:
                - Persoon op adres gevonden
                - Personen die niet voldoen aan de zoek criteria worden niet gevonden

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2374_2.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groep 'persoon'

Scenario: 3.    Zoek persoon op adres met criteria Huisnummertoevoeging en Postcode
                LT: R2374_LT03
                Verwacht resultaat:
                - Persoon op adres gevonden
                - Personen die niet voldoen aan de zoek criteria worden niet gevonden

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2374_3.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groep 'persoon'

Scenario: 4.    Zoek persoon op adres met criteria Huisnummer en Woonplaatsnaam
                LT: R2374_LT04
                Verwacht resultaat:
                - Foutief

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2374_4.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                       |
| R2374 | De zoekvraag bevat onvoldoende adresgegevens. |

Scenario: 5.    Zoek persoon op adres met criteria Huisletter en Woonplaatsnaam
                LT: R2374_LT05
                Verwacht resultaat:
                - Foutief

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2374_5.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                       |
| R2374 | De zoekvraag bevat onvoldoende adresgegevens. |

Scenario: 6.    Zoek persoon op adres met criteria Huisnummertoevoeging en Woonplaatsnaam
                LT: R2374_LT06
                Verwacht resultaat:
                - Foutief

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2374_6.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                       |
| R2374 | De zoekvraag bevat onvoldoende adresgegevens. |

Scenario: 7.    Zoek persoon op adres met criteria Huisnummer en Postcode
                LT: R2374_LT07
                Verwacht resultaat:
                - Foutief

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2374_7.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                       |
| R2374 | De zoekvraag bevat onvoldoende adresgegevens. |

Scenario: 8.    Zoek persoon op adres met criteria Huisnummer en postcode
                LT: R2374_LT08
                Verwacht resultaat:
                - Persoon op adres gevonden
                - Personen die niet voldoen aan de zoek criteria worden niet gevonden

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2374_8.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groep 'persoon'

Scenario: 9.    Zoek persoon op adres met criteria Huisletter (optie:Exact) en Postcode (optie:Leeg)
                LT: R2374_LT09
                Verwacht resultaat:
                - Foutief

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2374_9.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                       |
| R2374 | De zoekvraag bevat onvoldoende adresgegevens. |

Scenario: 10.   Zoek persoon op adres met criteria Huisnummertoevoeging (optie:Leeg) en Postcode (optie:Exact)
                LT: R2374_LT10
                Verwacht resultaat:
                - Foutief

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2374_10.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                       |
| R2374 | De zoekvraag bevat onvoldoende adresgegevens. |

Scenario: 11.    Zoek persoon op adres met criteria Huisnummer(optie:exact) en Postcode (optie:vanaf)
                LT: R2374_LT11
                Verwacht resultaat:
                - Foutief

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2374_11.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                       |
| R2374 | De zoekvraag bevat onvoldoende adresgegevens. |