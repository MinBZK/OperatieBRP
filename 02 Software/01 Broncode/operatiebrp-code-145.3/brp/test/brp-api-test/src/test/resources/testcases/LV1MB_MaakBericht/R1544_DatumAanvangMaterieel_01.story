Meta:

@status             Klaar
@usecase            LV.0.MB
@regels             R1544
@sleutelwoorden     Maak BRP bericht

Narrative:
Indien een VolledigBericht of een MutatieBericht wordt aangemaakt voor een aanwezige Persoon \ Afnemerindicatie, en in die Afnemerindicatie is Persoon \ Afnemerindicatie.Datum aanvang materiële periode gevuld, dan geldt dat bepaalde historische voorkomens van groepen uit de juridische persoonslijst niet geleverd worden:

Van groepen die een materiële historie kennen, mogen alleen voorkomens waarbij geldt dat DatumEindeGeldigheid (voor Onderzoeken is dit Onderzoek.Datum einde) > Persoon \ Afnemerindicatie.Datum aanvang materiële periode in het Bericht worden opgenomen.
Deze regel geldt voor zowel de inhoudelijke groepen als voor de onderzoeksgroepen.

Toelichting:
Als Persoon \ Afnemerindicatie.Datum aanvang materiële periode leeg is, dan mogen dus alle voorkomens geleverd worden.
Als DatumEindeGeldigheid leeg is, dan mag dat voorkomen altijd geleverd worden.
Als DatumEindeGeldigheid geheel of gedeeltelijk onbekend is, dan dient de (reguliere) 'soepele' vergelijking gebruikt te worden: als de bovenstaande conditie mogelijk WAAR kan zijn, dan wordt het voorkomen in het bericht opgenomen.

Test persoon Anne Bakker:
- Naamswijziging:
1. Bakker   1976-04-01  geregistreerd op 1976-04-02
2. Gemerden 1976-04-01  geregistreerd op 1976-04-02
- Adreswijzigingen:
1. Uithoorn         2016-01-02
2. 's-Gravenhage    2016-01-01
3. Utrecht          2015-12-31
4. Oosterhout       2010-12-31
- Huwelijk:
Van 2010-01-01 tot 2015-01-01

Scenario: 1.        Datum aanvang materiele periode is LEEG, Datum einde volgen is LEEG
                    LT: R1544_LT01
                    Verwacht resultaat:
                    - Alle voorkomens in bericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1544_Anne_Bakker_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                  | partijNaam      | datumEindeVolgen | tsReg                 | dienstId |
| 595891305 | 'Geen pop.bep. levering op basis van afnemerindicatie'    | 'Gemeente Utrecht' |                  | 2010-01-01 T00:00:00Z | 1        |

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Then heeft het bericht 4 groepen 'adres'
Then hebben attributen in voorkomens de volgende waardes:
| groep 	    | nummer  	| attribuut             | verwachteWaarde   |
| adres 	    | 1         | woonplaatsnaam 	    | Uithoorn          |
| adres 	    | 2         | woonplaatsnaam 	    | 's-Gravenhage     |
| adres 	    | 3         | woonplaatsnaam 	    | Utrecht           |
| adres 	    | 4         | woonplaatsnaam 	    | Oosterhout        |

Then heeft het bericht 2 groepen 'geslachtsnaamcomponent'
Then hebben attributen in voorkomens de volgende waardes:
| groep 	                | nummer  	| attribuut     | verwachteWaarde   |
| geslachtsnaamcomponent 	| 1         | stam 	        | Bakker            |
| geslachtsnaamcomponent    | 2         | stam 	        | Gemerden          |

Then is het synchronisatiebericht gelijk aan expecteds/R1544_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 2.        Datum aanvang materiele periode is LEEG, Datum einde volgen is 2015-10-10
                    LT: R1544_LT02
                    Verwacht resultaat:
                    - Adres = Utrecht (Oosterhout als oude adres ook in het bericht)
                    - Persoon is getrouwd (als actueel in het bericht

!-- Zelfde volledige expected plaatsen als bij scenario 1, want datum einde volgen heeft geen invloed
Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1544_Anne_Bakker_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                  | partijNaam      | datumEindeVolgen | tsReg                 | dienstId |
| 595891305 | 'Geen pop.bep. levering op basis van afnemerindicatie'    | 'Gemeente Utrecht' | 2015-10-10       | 2010-01-01 T00:00:00Z | 1        |

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan expecteds/R1544_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 3.        Datum aanvang materiele periode is 2016-01-01, Datum einde volgen is LEEG
                    LT: R1544_LT03, R1544_LT04, R1544_LT05
                    Verwacht resultaat:
                    - Uithoorn        2016-01-02 WEL in bericht (als huidige adres)
                    -'s-Gravenhage    2016-01-01 WEL in bericht (als vorige adres)
                    - Utrecht         2015-12-31 NIET in bericht
                    - Oosterhout      2010-12-31 NIET in bericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1544_Anne_Bakker_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                  | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 595891305 | 'Geen pop.bep. levering op basis van afnemerindicatie'    | 'Gemeente Utrecht' |                  | 2016-01-01                   | 2016-01-01 T00:00:00Z | 1        |


Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Then heeft het bericht 2 groepen 'adres'
Then hebben attributen in voorkomens de volgende waardes:
| groep 	    | nummer  	| attribuut             | verwachteWaarde   |
| adres 	    | 1         | woonplaatsnaam 	    | Uithoorn          |
| adres 	    | 2         | woonplaatsnaam 	    | 's-Gravenhage     |

Then is het synchronisatiebericht gelijk aan expecteds/R1544_expected_scenario_3.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 4.        Datum aanvang materiele periode is 2016-01-01, Datum einde volgen is LEEG
                    LT: R1544_LT06, R1544_LT07
                    Verwacht resultaat:
                    - Uithoorn      in bericht
                    - 's-Gravenhage in bericht
                    - Utrecht       NIET  in bericht
                    - Oosterhout    in bericht
                    Uitwerking:
                    - Oosterhout:   Datum einde geldigheid  2016
                    - Utrecht:      Datum einde geldigheid  2016-01
                    - 's-Gravenhage:Datum einde geldigheid  2016-02
                    - Uithoorn:     Datum einde geldigheid  LEEG

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1544_Anne_met_Historie_gedeeltelijk_onb_dat_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                  | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 986096969 | 'Geen pop.bep. levering op basis van afnemerindicatie'    | 'Gemeente Utrecht' |                  | 2016-02-01                   | 2016-02-01 T00:00:00Z | 1        |


Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Then heeft het bericht 3 groepen 'adres'
Then hebben attributen in voorkomens de volgende waardes:
| groep 	    | nummer  	| attribuut             | verwachteWaarde   |
| adres 	    | 1         | woonplaatsnaam 	    | Uithoorn          |
| adres 	    | 2         | woonplaatsnaam 	    | 's-Gravenhage     |
| adres 	    | 3         | woonplaatsnaam 	    | Oosterhout        |

Then is het synchronisatiebericht gelijk aan expecteds/R1544_expected_scenario_4.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 5.        Datum aanvang materiele periode is 2016-01-01, Datum einde volgen is LEEG
                    LT: R1544_LT08
                    Verwacht resultaat:
                    - Beverwijk in bericht
                    - Haarlem in bericht
                    Uitwerking:
                    - Persoon heeft 3 groepen nationaliteit waarvan 1 met een geheel onbekende DEG

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1544_Persoon_Voorkomen_met_onbekende_DEG_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                  | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 963363529 | 'Geen pop.bep. levering op basis van afnemerindicatie'    | 'Gemeente Utrecht' |                  | 2016-02-01                   | 2016-02-01 T00:00:00Z | 1        |


Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|963363529

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

!-- 2 groepen nationaliteit, omdat de DEG bij de groep onbekend is, is het niet mogelijk voorkomens met een DEG < datumAanvangMateriele periode weg te filteren
Then heeft het bericht 3 groepen 'nationaliteit'
Then hebben attributen in voorkomens de volgende waardes:
| groep         | nummer | attribuut              | verwachteWaarde |
| nationaliteit | 1      | datumAanvangGeldigheid | 2001-01-01      |
| nationaliteit | 2      | datumAanvangGeldigheid | 0000            |
| nationaliteit | 3      | datumEindeGeldigheid   | 0000            |

Then is het synchronisatiebericht gelijk aan expecteds/R1544_expected_scenario_5.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 6.        Datum aanvang materiele periode is LEEG, Datum einde volgen is LEEG
                    LT: R1544_LT09
                    Verwacht resultaat:
                    - Onderzoek in bericht
                    Uitwerking:
                    - Onderzoek op adres Uithoorn (huidige adres) van datum aanvang = 2010-01-01 en datum einde = 2015-01-01

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1544_Anne_Bakker_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                  | partijNaam      | datumEindeVolgen | tsReg                 | dienstId |
| 595891305 | 'Geen pop.bep. levering op basis van afnemerindicatie'    | 'Gemeente Utrecht' |                  | 2010-01-01 T00:00:00Z | 1        |

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

!-- 2 groepen onderzoek betekend dat er 1 onderzoek geleverd wordt
Then heeft het bericht 2 groepen 'onderzoek'

Then is het synchronisatiebericht gelijk aan expecteds/R1544_expected_scenario_6.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 7.        Datum aanvang materiele periode is LEEG, Datum einde volgen is 2015-10-10
                    LT: R1544_LT10
                    Verwacht resultaat:
                    - Onderzoek in bericht
                    Uitwerking:
                    - Onderzoek op adres Uithoorn (huidige adres) van datum aanvang = 2010-01-01 en datum einde = 2015-01-01

!-- Zelfde volledige expected plaatsen als bij scenario 1, want datum einde volgen heeft geen invloed
Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1544_Anne_Bakker_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                  | partijNaam      | datumEindeVolgen | tsReg                 | dienstId |
| 595891305 | 'Geen pop.bep. levering op basis van afnemerindicatie'    | 'Gemeente Utrecht' | 2015-10-10       | 2010-01-01 T00:00:00Z | 1        |

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

!-- 2 groepen onderzoek betekend dat er 1 onderzoek geleverd wordt
Then heeft het bericht 2 groepen 'onderzoek'

Then is het synchronisatiebericht gelijk aan expecteds/R1544_expected_scenario_7.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 8.        Datum aanvang materiele periode is 2014-01-01, Datum einde volgen is LEEG
                    LT: R1544_LT11
                    Verwacht resultaat:
                    - Onderzoek in bericht
                    Uitwerking:
                    - Onderzoek op adres Uithoorn (huidige adres) van datum aanvang = 2010-01-01 en datum einde = 2015-01-01

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1544_Anne_Bakker_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                  | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 595891305 | 'Geen pop.bep. levering op basis van afnemerindicatie'    | 'Gemeente Utrecht' |                  | 2014-01-01                   | 2016-01-01 T00:00:00Z | 1        |


Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

!-- 2 groepen onderzoek betekend dat er 1 onderzoek geleverd wordt
Then heeft het bericht 2 groepen 'onderzoek'

Then is het synchronisatiebericht gelijk aan expecteds/R1544_expected_scenario_8.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 9.        Datum aanvang materiele periode is 2015-01-01, Datum einde volgen is LEEG
                    LT: R1544_LT12
                    Verwacht resultaat:
                    - Onderzoek NIET in bericht
                    Uitwerking:
                    - Onderzoek op adres Uithoorn (huidige adres) van datum aanvang = 2010-01-01 en datum einde = 2015-01-01

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1544_Anne_Bakker_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                  | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 595891305 | 'Geen pop.bep. levering op basis van afnemerindicatie'    | 'Gemeente Utrecht' |                  | 2015-01-01                   | 2016-01-01 T00:00:00Z | 1        |


Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Then heeft het bericht 0 groepen 'onderzoek'

Then is het synchronisatiebericht gelijk aan expecteds/R1544_expected_scenario_9.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10.        Datum aanvang materiele periode is 2016-01-01, Datum einde volgen is LEEG
                    LT: R1544_LT13
                    Verwacht resultaat:
                    - Onderzoek NIET in bericht
                    Uitwerking:
                    - Onderzoek op adres Uithoorn (huidige adres) van datum aanvang = 2010-01-01 en datum einde = 2015-01-01

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1544_Anne_Bakker_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                  | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 595891305 | 'Geen pop.bep. levering op basis van afnemerindicatie'    | 'Gemeente Utrecht' |                  | 2016-01-01                   | 2016-01-01 T00:00:00Z | 1        |


Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Then heeft het bericht 0 groepen 'onderzoek'

Then is het synchronisatiebericht gelijk aan expecteds/R1544_expected_scenario_10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11.       Datum aanvang materiele periode is 2015-01-03, Datum einde volgen is LEEG
                    LT: R1544_LT14
                    Verwacht resultaat:
                    - Onderzoek  in bericht want mogelijk waar via soepele vergelijking
                    Uitwerking:
                    - Onderzoek op adres Uithoorn (huidige adres) van datum aanvang = 2010-01-01 en datum einde = 2015-01-00

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1544_Anne_Bakker_Datum_Einde_Onderzoek_Gedeeltelijk_Onbekend_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                  | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 595891305 | 'Geen pop.bep. levering op basis van afnemerindicatie'    | 'Gemeente Utrecht' |                  | 2015-01-03                   | 2016-01-01 T00:00:00Z | 1        |


Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Then heeft het bericht 2 groepen 'onderzoek'

Then is het synchronisatiebericht gelijk aan expecteds/R1544_expected_scenario_11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12.       Datum aanvang materiele periode is 2015-02-01, Datum einde volgen is LEEG
                    LT: R1544_LT15
                    Verwacht resultaat:
                    - Onderzoek NIET in bericht want NIET mogelijk waar via soepele vergelijking
                    Uitwerking:
                    - Onderzoek op adres Uithoorn (huidige adres) van datum aanvang = 2010-01-01 en datum einde = 2015-01-00

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1544_Anne_Bakker_Datum_Einde_Onderzoek_Gedeeltelijk_Onbekend_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                  | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 595891305 | 'Geen pop.bep. levering op basis van afnemerindicatie'    | 'Gemeente Utrecht' |                  | 2015-02-01                   | 2016-01-01 T00:00:00Z | 1        |


Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Then heeft het bericht 0 groepen 'onderzoek'

Then is het synchronisatiebericht gelijk aan expecteds/R1544_expected_scenario_12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13.       Datum aanvang materiele periode is 2015-01-03, Datum einde volgen is LEEG
                    LT: R1544_LT16
                    Verwacht resultaat:
                    - Onderzoek  in bericht want mogelijk waar via soepele vergelijking
                    Uitwerking:
                    - Onderzoek op adres Uithoorn (huidige adres) van datum aanvang = 2010-01-01 en datum einde = 0000-00-00

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1544_Anne_Bakker_Datum_Einde_Onderzoek_Geheel_Onbekend_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                  | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 595891305 | 'Geen pop.bep. levering op basis van afnemerindicatie'    | 'Gemeente Utrecht' |                  | 2015-01-03                   | 2016-01-01 T00:00:00Z | 1        |


Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Then heeft het bericht 2 groepen 'onderzoek'

Then is het synchronisatiebericht gelijk aan expecteds/R1544_expected_scenario_13.xml voor expressie //brp:lvg_synVerwerkPersoon