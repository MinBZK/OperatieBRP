Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1267
@sleutelwoorden     Maak BRP bericht

Narrative:
Bij een asynchroon bericht aan een afnemer dienen de stuurgegevens in het leveringsbericht als volgt gevuld te worden:

•	Bericht.Zendende partij : '199903'
•	Bericht.Zendende systeem : 'BRP'
•	Bericht.Ontvangende partij : Partij \ Rol.Partij (Partijcode van de afnemer voor wie het bericht bedoeld is, bepaald via de Toegang leveringsautorisatie.Geautoriseerde van hetToegang leveringsautorisatie waarvoor geleverd wordt)
•	Bericht.Ontvangende systeem : 'Leveringsysteem'
•	Bericht.Referentienummer: unieke ID genereren
•	Bericht.Datum/tijd verzending : Datum\tijd systeem bij aanmaken van het bericht inclusief de tijdzone (bijvoorbeeld: '2012-04-18T15:32:03.234+01:00')


Scenario: 1. Identiek aan scenario 1 plaats afnemerindicatie
             Logische testgeval R1267_01
             Verwacht resultaat: Asynchroon bericht met vulling
             •	Bericht.Zendende partij : '199903'
             •	Bericht.Zendende systeem : 'BRP'
             •	Bericht.Ontvangende partij : 017401
             •	Bericht.Ontvangende systeem : 'Leveringsysteem'
             •	Bericht.Referentienummer: unieke ID genereren
             •	Bericht.Datum/tijd verzending : Datum\tijd systeem bij aanmaken van het bericht inclusief de tijdzone (bijvoorbeeld: '2012-04-18T15:32:03.234+01:00')


Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 1.2_R1267_Plaats_Afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| stuurgegevens 	    | 1         | zendendeSysteem 	    | BRP             |
| stuurgegevens 	    | 1         | zendendePartij 	    | 199903          |
| stuurgegevens         | 1         | ontvangendePartij     | 017401          |
| stuurgegevens 	    | 1         | ontvangendeSysteem 	| Leveringsysteem |

And hebben attributen in voorkomens de volgende aanwezigheid:
| groep 	            | nummer | attribuut					| aanwezig 	|
| stuurgegevens	        | 1      | referentienummer  			| ja       	|
| stuurgegevens	        | 1      | tijdstipVerzending  			| ja       	|

Scenario: 2. Identiek aan scenario 1 Verwijder afnemerindicatie
             Logische testgeval R1267_02
             Verwacht resultaat: Geen Asynchroon bericht

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1.3_R1267_Verwijder_afnemerindicatie.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden