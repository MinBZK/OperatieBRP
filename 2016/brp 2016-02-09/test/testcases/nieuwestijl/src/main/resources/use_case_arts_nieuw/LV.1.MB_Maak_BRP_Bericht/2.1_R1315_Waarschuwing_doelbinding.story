Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1315
@sleutelwoorden     Maak BRP bericht

Narrative:
Indien
een Bericht wordt aangemaakt omdat er een bijhouding heeft plaatsgevonden bij een Persoon waarbij een Persoon \ Afnemerindicatie bestaat voor een Leveringsautorisatie
EN
de Totale populatiebeperking (R2059) evalueert als ONWAAR of NULL

dan dient in het Bericht een waarschuwing opgenomen te worden.

Scenario: 1.1 Plaats afnemerindicatie

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_afnemerindicatie_Haarlem
Given verzoek voor leveringsautorisatie 'Populatiebeperking levering op basis van afnemerindicatie Haarlem' en partij 'Gemeente Haarlem'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

Given verzoek voor leveringsautorisatie 'Populatiebeperking levering op basis van afnemerindicatie Haarlem' en partij 'Gemeente Haarlem'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.2_R1315_plaatsen_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Populatiebeperking levering op basis van afnemerindicatie Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 1.2 Verhuizing naar buiten de doelbinding, waardoor afnemer een mutatiebericht met een waarschuwing krijgt
                Populatiebeperking evalueert op ONWAAR
                Logisch testgeval R1315_01
                Verwacht resultaat: A synchroon responsebericht met vulling
                    - Waarschuwing: de geleverde persoon valt niet meer binnen de populatiebeperking van de leveringsautorisatie.


Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Haarlem.txt
Given bijhoudingsverzoek voor partij 'Gemeente Haarlem'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 2.3_R1315_verhuizing.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Populatiebeperking levering op basis van afnemerindicatie Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide

And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| melding        	    | 1         | regelCode        	    | BRLV0027        |
| melding        	    | 1         | soortNaam      	    | Waarschuwing    |
| melding        	    | 1         | melding         	    | De geleverde persoon valt niet meer binnen de doelbindingspopulatie van de leveringsautorisatie. |

Scenario: 2.1 Plaats afnemerindicatie

Meta:
@status     Onderhanden

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_NULL_levering_op_basis_van_afnemerindicatie_Haarlem
Given verzoek voor leveringsautorisatie 'Populatiebeperking NULL levering op basis van afnemerindicatie Haarlem' en partij 'Gemeente Haarlem'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

Given verzoek voor leveringsautorisatie 'Populatiebeperking NULL levering op basis van afnemerindicatie Haarlem' en partij 'Gemeente Haarlem'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 2.2_R1315_plaatsen_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Populatiebeperking NULL levering op basis van afnemerindicatie Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 2.2 Verhuizing naar buiten de doelbinding, waardoor afnemer een mutatiebericht met een waarschuwing krijgt
                Populatiebeperking evalueert op NULL
                Logisch testgeval R1315_02
                Verwacht resultaat: A synchroon responsebericht met vulling
                    - Waarschuwing: de geleverde persoon valt niet meer binnen de populatiebeperking van de leveringsautorisatie.
                Bevinding: Er wordt geen mutatiebericht geleverd bij populatiebeperking is NULL
                JIRA ISSUE: TEAMBRP-4026
Meta:
@status Onderhanden

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Haarlem.txt
Given bijhoudingsverzoek voor partij 'Gemeente Haarlem'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 2.3_R1315_verhuizing.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Populatiebeperking NULL levering op basis van afnemerindicatie Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide

And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| melding        	    | 1         | regelCode        	    | BRLV0027        |
| melding        	    | 1         | soortNaam      	    | Waarschuwing    |
| melding        	    | 1         | melding         	    | De geleverde persoon valt niet meer binnen de doelbindingspopulatie van de leveringsautorisatie. |

Scenario: 3. Persoon blijft binnen doelbinding
                Logisch testgeval: R1315_03
                Verwacht resultaat: Geen waarschuwing
                Wordt getest in Mutatielevering op basis van afnemerindicatie scenario 1