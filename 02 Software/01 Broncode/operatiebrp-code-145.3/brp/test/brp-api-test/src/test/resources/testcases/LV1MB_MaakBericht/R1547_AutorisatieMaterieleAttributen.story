Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R1547
@sleutelwoorden     Maak BRP bericht

Narrative:
Het attribuut DatumEindeGeldigheid van een inhoudelijke groep ('Inhoudelijke groep' (R1540)) mag alleen worden opgenomen in het te leveren resultaat
als er bij de Dienst waarvoor geleverd wordt een corresponderend voorkomen bestaat van Dienstbundel \ Groep met Dienstbundel \ Groep.Materiële historie? = 'Ja'

Opmerking: Het betreft hier het wissen van materiële historie gegevens. Deze regel heeft dus geen betrekking op formele historie gegevens met een materieel aspect
(zoals Onderzoek met de datum Einde Onderzoek en Huwelijk met de datum einde Huwelijk).

NB: Deze regel leidt momenteel niet tot zichtbaar gedrag: als DatumEindeGeldigheid gevuld is wordt onder dezelfde conditie de hele groep niet opgenomen in het bericht
en als hij leeg is dan wordt het attribuut op die grond niet in het bericht opgenomen. Functioneel bestaat deze regel echter nog steeds en om redenen van toekomstbestendigheid wordt deze gehandhaafd.

Scenario:   1. Levering mutatiebericht beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht
            LT:  R1547_LT01
            beëindigde Inhoudelijke groepen (adres en Persoon.bijhouding) DatumEindeGeldigheid gevuld en een corresponderend voorkomen van dienstbundel
            groep, met materiele historie = ja. Persoon verhuist waardoor groepen Adres en Persoon.Bijhouding elk ÉÉN voorkomen hebben met
            DatumEindeGeldigheid gevuld.
            Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding

Given persoonsbeelden uit specials:MaakBericht/R1547_ElisaBeth_Verhuizing_Haarlem_Beverwijk_xls

When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken
Then heeft het bericht 3 groep 'adres'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut              | aanwezig |
| adres   | 2      | datumEindeGeldigheid   | ja       |

Then heeft het bericht 3 groep 'bijhouding'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut            | aanwezig |
| bijhouding | 2      | datumEindeGeldigheid | ja       |

Then is het synchronisatiebericht gelijk aan expecteds/R1547_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   2. Volledigbericht met beëindigde Inhoudelijke groepen worden NIET opgenomen in het te leveren bericht
            LT: R1547_LT02
            beëindigde Inhoudelijke groepen (adres en Persoon.bijhouding) met DatumEindeGeldigheid gevuld en een corresponderend voorkomen van
            dienstbundel groep, met materiele historie = nee. Persoon verhuist waardoor groepen Adres en Persoon.Bijhouding elk ÉÉN voorkomen hebben met
            DatumEindeGeldigheid gevuld.
            Verwacht Resultaat: beëindigde Inhoudelijke groepen worden NIET opgenomen in het te leveren bericht

Given leveringsautorisatie uit autorisatie/R1547_levering_op_basis_van_doelbinding_Geen_Materiele_HIS

Given persoonsbeelden uit specials:MaakBericht/R1547_ElisaBeth_Verhuizing_Haarlem_Beverwijk_xls
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van doelbinding geen mat his'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding geen mat his is ontvangen en wordt bekeken
Then heeft het bericht 1 groep 'adres'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut              | aanwezig |
| adres   | 1      | datumEindeGeldigheid   | nee      |
| adres   | 2      | datumEindeGeldigheid   | nee      |

Then heeft het bericht 1 groep 'bijhouding'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut            | aanwezig |
| bijhouding | 1      | datumEindeGeldigheid | nee      |
| bijhouding | 2      | datumEindeGeldigheid | nee      |

Then is het synchronisatiebericht gelijk aan expecteds/R1547_expected_scenario_2.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   3. Mutatiebericht met meerdere beëindigde Inhoudelijke groepen worden opgenomen in het te leveren bericht
            LT:  R1547_LT03
            beëindigde Inhoudelijke groepen (adres en Persoon.bijhouding) met DatumEindeGeldigheid gevuld en een corresponderend
            voorkomen van dienstbundel groep, met materiele historie = ja. Persoon verhuist voor de 2de keer waardoor groepen Adres en Persoon.Bijhouding elk
            TWEE voorkomen hebben met DatumEindeGeldigheid gevuld.
            Verwacht Resultaat: beëindigde Inhoudelijke groepen worden opgenomen in het te leveren bericht

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1547_ElisaBeth_Verhuizing_Haarlem_Beverwijk_Amsterdam_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken
Then heeft het bericht 3 groep 'adres'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut              | aanwezig |
| adres   | 2      | datumEindeGeldigheid   | ja       |
Then heeft het bericht 3 groep 'bijhouding'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut            | aanwezig |
| bijhouding | 2      | datumEindeGeldigheid | ja       |

Then is het synchronisatiebericht gelijk aan expecteds/R1547_expected_scenario_3.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   4. Volledigbericht waarin beëindigde Inhoudelijke groepen worden NIET opgenomen
            LT:  R1547_LT04
            beëindigde Inhoudelijke groepen (adres en Persoon.bijhouding) met DatumEindeGeldigheid gevuld en een corresponderend voorkomen van
            dienstbundel groep, met materiele historie = nee. Persoon UC_Kenny is voor de 2de keer verhuisd waardoor groepen Adres en Persoon.Bijhouding elk TWEE
            voorkomen hebben met DatumEindeGeldigheid gevuld.
            Verwacht Resultaat: beëindigde Inhoudelijke groepen worden NIET opgenomen in het te leveren bericht

Given leveringsautorisatie uit autorisatie/R1547_levering_op_basis_van_doelbinding_Geen_Materiele_HIS

Given persoonsbeelden uit specials:MaakBericht/R1547_ElisaBeth_Verhuizing_Haarlem_Beverwijk_Amsterdam_xls
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van doelbinding geen mat his'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding geen mat his is ontvangen en wordt bekeken
Then heeft het bericht 1 groep 'adres'

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut              | aanwezig |
| adres   | 1      | datumEindeGeldigheid   | nee      |
| adres   | 2      | datumEindeGeldigheid   | nee      |
| adres   | 3      | datumEindeGeldigheid   | nee      |

Then heeft het bericht 1 groep 'bijhouding'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut            | aanwezig |
| bijhouding | 1      | datumEindeGeldigheid | nee      |
| bijhouding | 2      | datumEindeGeldigheid | nee      |
| bijhouding | 3      | datumEindeGeldigheid | nee      |

Then is het synchronisatiebericht gelijk aan expecteds/R1547_expected_scenario_4.xml voor expressie //brp:lvg_synVerwerkPersoon
