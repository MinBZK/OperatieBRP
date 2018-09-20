Narrative:
Deze demo laat zien hoe het mogelijk is om een levering te 'resetten' en
daarmee opnieuw te leveren

Scenario: Succesvol herleveren van handelingen

Meta:
@auteur sasme
@status Uitgeschakeld
@sleutelwoorden demo

Given de database is gereset voor de personen 340014155
When voor persoon 340014155 worden de laatste 3 handelingen geleverd

Then worden er 16 berichten geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
groep 		                | attribuut             | verwachteWaardes
nationaliteit               | nationaliteitCode     | 0001,0001,0001
samengesteldeNaam           | geslachtsnaamstam     | Elk
samengesteldeNaam           | voornamen             | Jo
