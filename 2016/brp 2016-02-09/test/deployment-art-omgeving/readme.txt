ART OMGEVING
==============
Deze maven module is bedoeld om makkelijk een lokale test omgeving op te zetten zodat gebruik kan worden gemaakt van de
laatste ART-Engine, laatste BRP database versie e.d.
Dit project kent de volgende gebruik scenario's:

ART-Engine
----------
Om lokaal de laatste ART-Engine te gebruiken zonder dat je eraan ontwikkelt, kun je dit project gebruiken en de volgende instructie geven op de command prompt:
mvn clean validate -Pnieuwste
Hiermee krijg je de nieuwste ART-Engine die dan in de directory art-engine wortdt geplaatst. Voor het gemak is er een batch en shell script gemaakt om dit te doen:
artengine.bat
artengine.sh

Wil je de laatste release gebruiken, kan dit ook, dan moet je de volgende instructie gebruiken:
mvn clean validate -Prelease

ART-Database
------------
Om lokaal te kunnen testen is een database nodig met de testpersonen. Om de laatste BRP database te maken en te vullen kunnen de volgende instructies worden gebruikt:
mvn install -Pdatabase-aanmaken,nieuwste
mvn install -Pdatabase-aanmaken,database-vullen,nieuwste
mvn install -Pdatabase-vullen,nieuwste

Let op dat op dit moment bij het aanmaken van de database ook de ART-Engine wordt geupdate. Indien dit niet wenselijk is, moeten we dit wijzigen dat dit ook in een apart aan te roepen profiel komt.
