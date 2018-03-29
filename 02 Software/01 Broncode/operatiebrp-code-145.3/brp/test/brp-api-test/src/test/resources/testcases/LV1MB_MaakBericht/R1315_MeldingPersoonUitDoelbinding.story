Meta:

@status             Klaar
@usecase            LV.0.MB
@regels             R1315
@sleutelwoorden     Maak BRP bericht

Narrative:
Indien
een Bericht wordt aangemaakt omdat er een bijhouding heeft plaatsgevonden bij een Persoon waarbij een Persoon \ Afnemerindicatie bestaat voor een Leveringsautorisatie
EN
de Totale populatiebeperking (R2059) evalueert als ONWAAR of NULL

dan dient in het Bericht een waarschuwing opgenomen te worden.


Scenario: 1.   Huwelijk waardoor persoon buiten de populatie beperking valt, afnemer ontvangt een mutatiebericht met een waarschuwing krijgt
                LT: R1315_LT01
                Populatiebeperking evalueert van WAAR op ONWAAR
                Verwacht resultaat: mutatiebericht met vulling
                    - Waarschuwing: De geleverde persoon valt niet meer binnen de doelgroep van de leveringsautorisatie.


Given leveringsautorisatie uit autorisatie/Populatiebeperking_huweljk_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit BIJHOUDING:VHNL05C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                  | partijNaam         | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 422531881 | 'Populatiebeperking huwelijk aantal is 0' | 'Gemeente Haarlem' |                  |                              | 2014-01-01 T00:00:00Z | 1        |


When voor persoon 422531881 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Populatiebeperking huwelijk aantal is 0 is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| melding        	    | 1         | regelCode        	    | R1315           |
| melding        	    | 1         | soortNaam      	    | Waarschuwing    |
| melding        	    | 1         | melding         	    | De geleverde persoon valt niet meer binnen de doelgroep van de leveringsautorisatie. |

Then is het synchronisatiebericht gelijk aan expecteds/R1315_MeldingPersoonUitDoelbinding_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 2.   Verhuizing blijft buiten de populatiebeperking, waardoor afnemer een mutatiebericht met een waarschuwing krijgt
                LT: R1315_LT01
                Populatiebeperking evalueert van ONWAAR op ONWAAR
                Verwacht resultaat: mutatiebericht met vulling
                    - Waarschuwing: De geleverde persoon valt niet meer binnen de doelgroep van de leveringsautorisatie.


Given leveringsautorisatie uit autorisatie/Populatiebeperking_huwelijk_aantal_2
Given persoonsbeelden uit BIJHOUDING:VHNL05C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                  | partijNaam         | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 422531881 | 'Populatiebeperking huwelijk aantal is 2' | 'Gemeente Haarlem' |                  |                              | 2014-01-01 T00:00:00Z | 1        |

When voor persoon 422531881 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Populatiebeperking huwelijk aantal is 2 is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| melding        	    | 1         | regelCode        	    | R1315           |
| melding        	    | 1         | soortNaam      	    | Waarschuwing    |
| melding        	    | 1         | melding         	    | De geleverde persoon valt niet meer binnen de doelgroep van de leveringsautorisatie. |

Then is het synchronisatiebericht gelijk aan expecteds/R1315_MeldingPersoonUitDoelbinding_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 3.   Voltrekking Huwelijk, populatiebeperking evalueert op NULL, afnemer ontvangt een mutatiebericht met een waarschuwing
                LT: R1315_LT02
                Populatiebeperking met functie geeft als resultaat NULL
                Verwacht resultaat: mutatiebericht met vulling
                    - Waarschuwing: De geleverde persoon valt niet meer binnen de doelgroep van de leveringsautorisatie.

Given leveringsautorisatie uit autorisatie/Populatiebeperking_NULL
Given persoonsbeelden uit BIJHOUDING:VHNL05C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam  | partijNaam         | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 422531881 | 'Populatiebeperking null' | 'Gemeente Haarlem' |                  |                              | 2014-01-01 T00:00:00Z | 1        |


When voor persoon 422531881 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Populatiebeperking null is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| melding        	    | 1         | regelCode        	    | R1315           |
| melding        	    | 1         | soortNaam      	    | Waarschuwing    |
| melding        	    | 1         | melding         	    | De geleverde persoon valt niet meer binnen de doelgroep van de leveringsautorisatie. |

Then is het synchronisatiebericht gelijk aan expecteds/R1315_MeldingPersoonUitDoelbinding_1.xml voor expressie //brp:lvg_synVerwerkPersoon
