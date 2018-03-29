Meta:

@status             Klaar
@usecase            LV.0.MB
@regels             R1349
@sleutelwoorden     Maak BRP bericht

Narrative:
Een beëindigde Inhoudelijke groep (DatumEindeGeldigheid is gevuld met een waarde,
eventueel volledig onbekend) mag alleen worden opgenomen in het te leveren resultaat
(Persoonslijst of Mutatielevering) als er bij de Dienst waarvoor geleverd wordt een
corresponderend voorkomen bestaat van Dienstbundel \ Groep met Dienstbundel \ Groep.Materiële historie? = 'Ja'.


Scenario: 1 beëindigde Inhoudelijke groep (adres) met DatumEindeGeldigheid gevuld en een corresponderend voorkomen van
            LT:  R1349_LT01, R1349_LT05
            dienstbundel groep, met materiele historie = ja
            Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht, dus 3 groepen adres

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Haarlem_Beverwijk_xls

When voor persoon 270433417 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken

Then heeft het bericht 3 groepen 'adres'
Then heeft het bericht 3 groepen 'bijhouding'

!-- Geautoriseerd voor materiele historie dus voorkomens met een datumEindeGeldigheid worden geleverd
Then hebben attributen in voorkomens de volgende waardes:
| groep      | nummer | attribuut            | verwachteWaarde |
| adres      | 2      | datumEindeGeldigheid | 2012-03-16      |
| bijhouding | 2      | datumEindeGeldigheid | 2012-03-16      |

Then is het synchronisatiebericht gelijk aan expecteds/R1349_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 2 beëindigde Inhoudelijke groep (nationaliteit) met DatumEindeGeldigheid volledig onbekend en een corresponderend voorkomen van
            LT: R1349_LT02
            dienstbundel groep, met materiele historie = ja
            Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht, dus 3 groepen nationaliteit

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1349_Persoon_Voorkomen_met_onbekende_DEG_xls
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|963363529

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende waardes:
| groep         | nummer | attribuut            | verwachteWaarde |
| nationaliteit | 3      | datumEindeGeldigheid | 0000            |

Then is het synchronisatiebericht gelijk aan expecteds/R1349_expected_scenario_2.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 3 beëindigde Inhoudelijke groep (adres) met DatumEindeGeldigheid gevuld en een corresponderend voorkomen van
            LT: R1349_LT03
            dienstbundel groep, met materiele historie = nee
            Verwacht Resultaat: beëindigde Inhoudelijke groep wordt niet opgenomen in het te leveren bericht, dus 2 groepen adres

Given leveringsautorisatie uit autorisatie/Levering_op_basis_van_doelbinding_Geen_Materiele_historie
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Haarlem_Beverwijk_xls
When voor persoon 270433417 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Geen materiele historie is ontvangen en wordt bekeken
Then heeft het bericht 2 groepen 'adres'

!-- Controleer dat er geen datumEindeGeldigheid aanwezig is in een bericht aan een afnemer die geen autorisatie heeft voor Materiele Historie
Then is er voor xpath //brp:datumEindeGeldigheid geen node aanwezig in het levering bericht

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut            | aanwezig |
| adres      | 2      | datumEindeGeldigheid | nee      |
| bijhouding | 2      | datumEindeGeldigheid | nee      |

Then is het synchronisatiebericht gelijk aan expecteds/R1349_expected_scenario_3.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 3a.   Volledigbericht na plaatsing afnemerindicatie aan afnemer zonder materiele historie
                LT: R1349_LT03
                Verwacht Resultaat: beëindigde Inhoudelijke groep wordt niet opgenomen in het te leveren bericht, dus 1 adres (formele historie is niet aanweizg in volledigbericht)

Given leveringsautorisatie uit autorisatie/Geen_materiele_historie_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Haarlem_Beverwijk_xls

Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen materiele historie afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417

When het volledigbericht voor leveringsautorisatie Geen materiele historie afnemerindicatie is ontvangen en wordt bekeken

Then is er voor xpath //brp:datumEindeGeldigheid geen node aanwezig in het levering bericht
Then is het synchronisatiebericht gelijk aan expecteds/R1349_expected_scenario_3a.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 4 beëindigde Inhoudelijke groep (nationaliteit) met DatumEindeGeldigheid volledig onbekend en een corresponderend voorkomen van
            LT: R1349_LT04
            dienstbundel groep, met materiele historie = nee
            Verwacht Resultaat: beëindigde Inhoudelijke groep wordt niet opgenomen in het te leveren bericht, dus 2 groepen adres

Given leveringsautorisatie uit autorisatie/Levering_op_basis_van_doelbinding_Geen_Materiele_historie
Given persoonsbeelden uit specials:MaakBericht/R1349_Persoon_Voorkomen_met_onbekende_DEG_xls
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen materiele historie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|963363529

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen materiele historie is ontvangen en wordt bekeken
Then heeft het bericht 2 groep 'nationaliteit'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep         | nummer | attribuut            | aanwezig |
| nationaliteit | 2      | datumEindeGeldigheid | nee      |

Then is het synchronisatiebericht gelijk aan expecteds/R1349_expected_scenario_4.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 5 beëindigde Inhoudelijke groep (adres) met DatumEindeGeldigheid gedeeltelijk onbekend en een corresponderend voorkomen van
            LT: R1349_LT06
            dienstbundel groep, met materiele historie = JA
            Verwacht Resultaat: beëindigde Inhoudelijke groep opgenomen in mutatielevering

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Gedeeltelijk_onb_datum_xls
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken
Then heeft het bericht 2 groep 'adres'
Then hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut            | verwachteWaarde |
| adres | 2      | datumEindeGeldigheid | 2012            |

Then is het synchronisatiebericht gelijk aan expecteds/R1349_expected_scenario_5.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 6.1   Leveringsautorisatie met alleen materiele historie, mutatiebericht
                LT: R1349_LT07
                Verwacht resultaat:
                Mutatiebericht met 3 groepen adres
                - Toevoeging
                - Wijziging
                - Verval

Given leveringsautorisatie uit autorisatie/Enkel_materiele_historie
Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Haarlem_Beverwijk_xls
When voor persoon 270433417 wordt de laatste handeling geleverd

!-- Geautoriseerd voor alleen materiele historie, maar bij mutatiebericht ook de vervallen groep leveren
When het mutatiebericht voor leveringsautorisatie Enkel materiele historie is ontvangen en wordt bekeken
Then heeft het bericht 3 groepen 'adres'
Then hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut                   | verwachteWaarde                        |
| adres | 1      | afgekorteNaamOpenbareRuimte | Filterweg                              |
| adres | 2      | naamOpenbareRuimte          | Baron Schimmelpenninck van der Oyelaan |
| adres | 3      | naamOpenbareRuimte          | Baron Schimmelpenninck van der Oyelaan |

Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep     | nummer | verwerkingssoort |
| adres     | 1      | Toevoeging       |
| adres     | 2      | Wijziging        |
| adres     | 3      | Verval           |

Then is het synchronisatiebericht gelijk aan expecteds/R1349_expected_scenario_6_1.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 6.2   Leveringsautorisatie met alleen materiele historie
                LT: R1349_LT07
                Mutatiebericht met 2 groepen adres, de toevoeging en de wijziging, maar geen groep met verwerkingssoort Verval
                - Toevoeging
                - Wijziging

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Enkel materiele historie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Enkel materiele historie is ontvangen en wordt bekeken
Then heeft het bericht 2 groepen 'adres'
Then hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut                   | verwachteWaarde                        |
| adres | 1      | afgekorteNaamOpenbareRuimte | Filterweg                              |
| adres | 2      | naamOpenbareRuimte          | Baron Schimmelpenninck van der Oyelaan |

!-- De controle op aanwezigheid datumEindeGeldigheid, geeft aan dat dit dezelfde groep is die in het mutatiebericht als verwerkingssoort wijziging meekomt
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep | nummer | attribuut            | aanwezig |
| adres | 2      | datumEindeGeldigheid | ja       |

Then is het synchronisatiebericht gelijk aan expecteds/R1349_expected_scenario_6_2.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 6.3   Leveringsautorisatie met alleen materiele historie, in doelbinding verhuisd dus volledig bericht, met gewijzigd adres
                LT: R1349_LT07
                Verwacht resultaat:
                Mutatiebericht met 3 groepen adres
                - Toevoeging
                - Wijziging

Given persoonsbeelden uit specials:MaakBericht/R1349_ElisaBeth_Verhuizing_Haarlem_Beverwijk_xls
Given leveringsautorisatie uit autorisatie/Enkel_materiele_historie_afnemerindicatie

Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Enkel materiele historie afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417

!-- Geautoriseerd voor materiele historie dus voorkomens met een datumEindeGeldigheid worden geleverd
!-- Vervallen groep adres niet geleverd
When het volledigbericht voor leveringsautorisatie Enkel materiele historie afnemerindicatie is ontvangen en wordt bekeken
Then heeft het bericht 2 groepen 'adres'
Then hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut                   | verwachteWaarde                        |
| adres | 1      | afgekorteNaamOpenbareRuimte | Filterweg                              |
| adres | 2      | naamOpenbareRuimte          | Baron Schimmelpenninck van der Oyelaan |

!-- De controle op aanwezigheid datumEindeGeldigheid, geeft aan dat dit dezelfde groep is die in het mutatiebericht als verwerkingssoort wijziging meekomt
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut                   | aanwezig  |
| adres              | 2      | datumEindeGeldigheid        | ja        |

Then is het synchronisatiebericht gelijk aan expecteds/R1349_expected_scenario_6_3.xml voor expressie //brp:lvg_synVerwerkPersoon
