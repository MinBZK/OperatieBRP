Meta:
@auteur             kedon
@status             Onderhanden
@usecase            LV.1.MB
@regels             R1328
@sleutelwoorden     Maak BRP bericht

Narrative:
In het leveringsresultaat mogen van een gerelateerde van de te leveren Persoon geen groepsvoorkomens worden opgenomen die aan minimaal één van de volgende voorwaarden voldoen:
•	Datum einde geldigheid is gevuld en is kleiner of gelijk aan aanvangsdatum van de Relatie tussen de te leveren persoon en de gerelateerde;
•	Datum/tijd verval is gevuld en het datum-deel ervan is kleiner of gelijk aan aanvangsdatum van de Relatie tussen de te leveren persoon en de gerelateerde.

Hierbij is aanvangsdatum van de Relatie als volgt gedefinieerd:
•	In geval van een Huwelijk of Geregistreerd partnerschap: Relatie.Datum aanvang.
•	In geval van een Familierechtelijke Betrekking: Ouder.Ouderschap.Datum aanvang geldigheid.

Opmerkingen
1.	Deze regel is alleen van toepassing op leveringen aan afnemers. Een bijhouder mag namelijk landelijk Persoon (personen) bevragen en heeft daarmee dus ook inzage in de materiële historie van een gerelateerde voor aanvang van de Relatie.
2.	Als de aanvangsdatum van de Relatie een (deels) onbekende datum is, dan dient hier bij het filteren "streng" mee om te worden gegaan. Dat wil zeggen dat alleen groepsvoorkomens van gerelateerden worden weggefilterd waarvan zeker is dat deze voor aanvang van de relatie waren beëindigd. Zie ook regel R1283 - Vergelijken (partiële) datums.
3.	In geval van meerdere Relaties of Ouder.Ouderschappen tussen dezelfde betrokkenen zal in het bericht de gerelateerde meerdere keren voor komen en er per "voorkomen" van deze gerelateerde een verschillend filterresultaat ontstaan.


DEZE REGEL STAA OP ONDERHANDEN OMDAT DE DSL VAN EEN STANDAARDPERSOON NOG NIET GENOEG BEWERKINGEN KAN BEVATTEN: JIRA ISSUE TEAMBRP-4674


Scenario: 1.1 Plaats afnemerindicatie
                Logische testgevallen: R1328_01, R1328_02, R1328_03, R1328_04, R1328_05, R1328_06,R1328_07, R1328_08
                Verwacht resultaat: Voorkomensgroepen vanaf 1990-05-05 geleverd, daarvoor niet

Given de personen 496264709, 593003214 zijn verwijderd
And de standaardpersoon UC_Wilma met bsn 593003214 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 4.3_R1328_Plaats_Afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 1.2 Synchroniseer Persoon
                Logische testgevallen: R1328_01, R1328_02, R1328_03, R1328_04, R1328_05, R1328_06,R1328_07, R1328_08
                Verwacht resultaat: Voorkomensgroepen vanaf 1990-05-05 geleverd, daarvoor niet

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 4.2_R1328_Synchroniseer_Persoon.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

