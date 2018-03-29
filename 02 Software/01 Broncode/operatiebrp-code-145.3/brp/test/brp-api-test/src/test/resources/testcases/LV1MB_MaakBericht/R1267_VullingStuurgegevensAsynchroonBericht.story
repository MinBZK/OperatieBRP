Meta:

@status             Klaar
@usecase            LV.0.MB
@regels             R1267
@sleutelwoorden     Maak BRP bericht

Narrative:
Bij een asynchroon bericht aan een afnemer dienen de stuurgegevens in het leveringsbericht als volgt gevuld te worden:

•	Bericht.Zendende partij
•	Bericht.Zendende systeem
•	Bericht.Ontvangende partij : Partij \ Rol.Partij (Partijcode van de afnemer voor wie het bericht bedoeld is, bepaald via de Toegang leveringsautorisatie.Geautoriseerde van hetToegang leveringsautorisatie waarvoor geleverd wordt)
•	Bericht.Referentienummer
•	Bericht.Datum/tijd verzending

Scenario: 1. A-synchroon bericht aan afnemer na plaatsing afnemerindicatie
             LT: R1267_LT01
             Verwacht resultaat: Stuur gegevens a-synchroon bericht correct gevuld

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| stuurgegevens 	    | 1         | zendendeSysteem 	    | BRP             |
| stuurgegevens 	    | 1         | zendendePartij 	    | 199903          |
| stuurgegevens         | 1         | ontvangendePartij     | 034401          |

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep 	            | nummer | attribuut					| aanwezig 	|
| stuurgegevens	        | 1      | referentienummer  			| ja       	|
| stuurgegevens	        | 1      | tijdstipVerzending  			| ja       	|

Then is het synchronisatiebericht gelijk aan expecteds/R1267_VullingStuurgegevensAsynchroonBericht_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 2. A-synchroon bericht aan afnemer na verzoek synchroniseer persoon
             LT: R1267_LT02
             Verwacht resultaat: Stuur gegevens a-synchroon bericht correct gevuld

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| stuurgegevens 	    | 1         | zendendeSysteem 	    | BRP             |
| stuurgegevens 	    | 1         | zendendePartij 	    | 199903          |
| stuurgegevens         | 1         | ontvangendePartij     | 034401          |

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep 	            | nummer | attribuut					| aanwezig 	|
| stuurgegevens	        | 1      | referentienummer  			| ja       	|
| stuurgegevens	        | 1      | tijdstipVerzending  			| ja       	|

Then is het synchronisatiebericht gelijk aan expecteds/R1267_VullingStuurgegevensAsynchroonBericht_2.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 3. A-synchroon bericht aan afnemer persoon komt in de doelbinding mutatielevering obv doelbinding
             LT: R1267_LT03
             Verwacht resultaat: Stuur gegevens a-synchroon bericht correct gevuld

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding

When voor persoon 595891305 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/R1267_VullingStuurgegevensAsynchroonBericht_3.xml voor expressie //brp:lvg_synVerwerkPersoon

Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| stuurgegevens 	    | 1         | zendendeSysteem 	    | BRP             |
| stuurgegevens 	    | 1         | zendendePartij 	    | 199903          |
| stuurgegevens             | 1         | ontvangendePartij     | 034401          |

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep 	            | nummer | attribuut					| aanwezig 	|
| stuurgegevens	        | 1      | referentienummer  			| ja       	|
| stuurgegevens	        | 1      | tijdstipVerzending  			| ja       	|

Then is het aantal ontvangen berichten 1

Scenario: 4. A-synchroon bericht aan afnemer dmv attendering
             LT: R1267_LT04
             Verwacht resultaat: Stuur gegevens a-synchroon bericht correct gevuld

Given persoonsbeelden uit specials:specials/Anne_Bakker_GBA_Bijhouding_xls
Given leveringsautorisatie uit autorisatie/attendering_met_plaatsing_afnemerindicatie_gebdd_groter_1975
When voor persoon 595891305 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing afnemerindicatie geboortedatum groter dan 1975 is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| stuurgegevens 	    | 1         | zendendeSysteem 	    | BRP             |
| stuurgegevens 	    | 1         | zendendePartij 	    | 199903          |
| stuurgegevens             | 1         | ontvangendePartij     | 034401          |

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep 	            | nummer | attribuut					| aanwezig 	|
| stuurgegevens	        | 1      | referentienummer  			| ja       	|
| stuurgegevens	        | 1      | tijdstipVerzending  			| ja       	|

Then is het synchronisatiebericht gelijk aan expecteds/R1267_VullingStuurgegevensAsynchroonBericht_4.xml voor expressie //brp:lvg_synVerwerkPersoon
