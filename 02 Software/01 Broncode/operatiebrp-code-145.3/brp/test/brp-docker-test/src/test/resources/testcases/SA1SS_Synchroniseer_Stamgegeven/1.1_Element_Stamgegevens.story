Meta:
@status             Klaar
@usecase            SA.0.SS
@regels             R1331,R1332,R1979
@sleutelwoorden     Synchroniseer Stamgegeven


Narrative:
De element tabel wordt opgevraagd. Er wordt random op een aantal elementen gecontroleerd, omdat er in totaal meer dan 1000 elementen zijn.
Het gaat te ver om al deze elementen te controleren.

Scenario:   1. Vraag 1 stamtabel op (element), Alle voorkomens waar Element.Leveren als stamgegeven = 'TRUE',
            LT: R1262_LT11, R1264_LT07, R1269_LT05, R1270_LT07, R1331_LT01, R1332_LT01, R1332_LT03, R1979_LT01, R2056_LT07, R2130_LT07, R1266_LT05

            AL.1.AB:
            LT: R1269_LT05
            Verwacht resultaat:
            1. Geen persoonsreferentie in berpers voor inkomend bericht

            LV.1.AL:
            LT: R1262_LT11, R1264_LT07, R2056_LT07, R2130_LT07
            Verwacht resultaat:
            2. Succesvol door authorisatie, dus leveren bericht

            SA.0.SS Synchronisser Stamgegevens:
            LT: R1331_LT01, R1332_LT01, R1332_LT03, R1979_LT01
            Verwacht resultaat: Leveren stamtabel Element
            3. responsebericht
                Met volgorde:
                -  Element.soort
                -  Element.naam
                -  DatumAanvangGeldigheid
                -  DatumEindeGeldigheid
                -  Persoon.Indicatie.Identiteit komt niet voor inber<>true (R1332_LT03)

            AL.1.AB:
            LT: R1270_LT07
            4. Geen persoonsreferentie in berpers voor uitgaand bericht

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SS_Synchroniseer_Stamgegeven/Requests/1.1_Element_Stamgegevens_scenario_1.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

!-- R1266_LT05 Controle op responsebericht
Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

!-- R1331_LT01
Then is in antwoordbericht de aanwezigheid van 'naam' in 'element' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'soortNaam' in 'element' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'datumAanvangGeldigheid' in 'element' nummer 1 ja

!-- R1332_LT01 Controle op element welke inber=true heeft in de kern.elementtabel.
!-- volgorde check dmv volledige expected volledigheidscheck dmv volledige expected bij bmr wijziging controleren
Then is het antwoordbericht gelijk aan  /testcases/SA1SS_Synchroniseer_Stamgegeven/expected/1.1_Expected_scenario_1_response.xml voor expressie //brp:lvg_synGeefSynchronisatieStamgegeven_R
!-- Check elementen van het type object geleverd worden
Then is er voor xpath //brp:element/brp:naam[text()='Persoon'] een node aanwezig in het antwoord bericht
!-- Check elementen van het type attribuut geleverd worden
Then is er voor xpath //brp:element/brp:naam[text()='Persoon.Geboorte'] een node aanwezig in het antwoord bericht
!-- Check elementen van het type attribuut geleverd worden
Then is er voor xpath //brp:element/brp:naam[text()='Persoon.Geboorte.Datum'] een node aanwezig in het antwoord bericht
!-- R1332_LT03 Persoon.Indicatie.Identiteit komt niet voor kern.element inber<>true
Then is er voor xpath //brp:naam[text()='Persoon.Indicatie.Identiteit'] geen node aanwezig in het antwoord bericht
!-- R1269_LT05
Then bestaat er geen voorkomen in berpers tabel voor referentie 0000000A-3000-7000-FFFF-000000000041 en srt lvg_synGeefSynchronisatieStamgegeven
!-- R1270_LT07
Then bestaat er geen voorkomen in berpers tabel voor crossreferentie 0000000A-3000-7000-FFFF-000000000041 en srt lvg_synGeefSynchronisatieStamgegeven_R
