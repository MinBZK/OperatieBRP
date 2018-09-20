Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1316
@sleutelwoorden     Maak BRP bericht

Narrative:
Bij het verwerken van een Administratieve handeling door de Dienst 'Mutatielevering op doelbinding' geldt het volgende:

Indien er door de betreffende Administratieve handeling één of meer personen 'Uit doelbinding gegaan zijn' van Totale populatiebeperking (R2059) van de betreffende Toegang leveringsautorisatie en "Dienst", dan dient voor die personen een waarschuwing in het Bericht opgenomen te worden.

Een Persoon is 'Uit doelbinding gegaan' door een Administratieve handeling 'Q' bij een Toegang leveringsautorisatie en "Dienst" als:

Expressie Totale populatiebeperking (R2059) op de R1557 - Reconstructie actueel persoonsbeeld tbv expressie-evaluatie na handeling Q evalueert als ONWAAR of als NULL
EN
(Er een Administratieve handeling 'P' bestaat in de groep Persoon.Afgeleid administratief van de Persoon die direct voorafgaat aan Administratieve handeling 'Q'.
EN
Expressie Totale populatiebeperking (R2059) op de R1557 - Reconstructie actueel persoonsbeeld tbv expressie-evaluatie na handeling P evalueert als WAAR)

Scenario: 1.1   Persoon valt binnen de doelbinding van de leveringsautorisatie en blijft na verhuzing
                binnen de doelbinding
                Logisch testgeval: R1316_05
                Verwacht resultaat: Mutatiebericht op basis van doelbinding

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem
Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Haarlem.txt
Given bijhoudingsverzoek voor partij 'Gemeente Haarlem'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 3.2_R1316_verhuizing_binnen_Haarlem.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Populatiebeperking levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 1.2   Persoon valt binnen de doelbinding van de leveringsautorisatie na administratieve handeling P
                en valt buiten de doelbinding na administratieve handeling Q (verhuizing buiten de gemeente),
                waardoor de populatiebeperking op ONWAAR evalueert
                Logisch testgeval: R1316_01
                Verwacht resultaat: mutatiebericht met waarschuwing: De geleverde persoon valt niet meer binnen de doelbindingspopulatie van de leveringsautorisatie.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Haarlem.txt
Given bijhoudingsverzoek voor partij 'Gemeente Haarlem'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 3.3_R1316_verhuizing_buiten_Haarlem.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Populatiebeperking levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide

And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| melding        	    | 1         | regelCode        	    | BRLV0028        |
| melding        	    | 1         | soortNaam      	    | Waarschuwing    |
| melding        	    | 1         | melding         	    | De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt. |

Scenario: 2.1 R1316_02  Persoon valt binnen de doelbinding en verhuist naar buiten de doelbinding
                        waardoor de Populatiebeperking na handeling p op NULL evalueert
                        Logisch testgeval: R1316_02
                        Verwacht resultaat: geen bericht

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_NULL_levering_op_basis_van_doelbinding_Haarlem
Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Haarlem.txt
Given bijhoudingsverzoek voor partij 'Gemeente Haarlem'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 3.2_R1316_verhuizing_binnen_Haarlem.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Populatiebeperking NULL levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 2.2   Populatiebeperking na administratieve handeling p is NULL
                door middel van administratieve handeling Q verhuisd de persoon naar buiten Haarlem
                Logisch testgevalR1316_02
                Verwacht resultaat: Geen a synchroon bericht

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Haarlem.txt
Given bijhoudingsverzoek voor partij 'Gemeente Haarlem'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 3.3_R1316_verhuizing_buiten_Haarlem.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Populatiebeperking NULL levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 3 R1316_03:   Geen administratievehandeling P voor administratieve handeling Q
                        Dit is niet te testen, er is altijd een administratieve handeling voordat iemand het BRP inkomt

Scenario: 4.1           Populatiebeperking ONWAAR na administratieve handeling P
                        Logisch testgeval: R1316_04
                        Verwacht resultaat: Mutatiebericht

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem
Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Haarlem.txt
Given bijhoudingsverzoek voor partij 'Gemeente Haarlem'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 3.3_R1316_verhuizing_buiten_Haarlem.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Populatiebeperking levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 4.2               Populatiebeperking ONWAAR na administratieve handeling P
                            terug verhuizen naar doelbinding
                            Populatiebeperking evalueert op waar na administratieve handeling Q
                            Dus nieuw in de doelbinding
                            Logisch testgeval: R1316_04
                            Verwacht resultaat: volledig bericht

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Haarlem.txt
Given bijhoudingsverzoek voor partij 'Gemeente Haarlem'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 3.2_R1316_verhuizing_binnen_Haarlem.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Populatiebeperking levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide