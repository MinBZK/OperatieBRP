Meta:
@sprintnummer           79
@auteur                 dihoe
@jiraIssue              TEAMBRP-3455
@status                 Klaar
@sleutelwoorden         archiveren

Narrative:
Archivering van berichten controleren
Controleert of er een bericht gearchiveerd is in de 'ber.ber'-tabel:
1. Then ingaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004444
   ingaand = request
   uitgaand =  response
   referentienr = het referentienummer van het bericht (meegeven via yml)
2. Then is het bericht gearchiveerd
   bericht = leveringsbericht

Scenario: 1.

Given de database is gereset voor de personen 340014155

And verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand archiveren_van_berichten_controleren_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
Then ingaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004444
Then uitgaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004444

Then wacht tot alle berichten zijn ontvangen
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht gearchiveerd


