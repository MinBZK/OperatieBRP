Tags.txt wordt door de tool uitgelezen voordat de test gestart wordt.
Anr en bsn worden uit de PL'en gehaald door te zoeken naar element 1.10 en 1.20. Het eerste voorkomen hiervan 
in de 3e kolom van de PL zijn het anr en bsn van de persoon van de betreffende PL.

De rest van de tags worden gebruikt om de property files te vullen die nodig zijn voor de GBA mutatieverwerking.
- verzendendePartij: in de PL wordt gezocht naar het eerste voorkomen van element 9.10 in de 3e kolom (Gemeente
van inschrijving van actuele categorie 8 Verblijfplaats).
- ontvangendePartij: code van Verstrekkingspartij.
- zendendePartij: in BRP leveringsberichten(?) wordt het eerste voorkomen van de tag <brp:zendendePartij> gezocht,
de 6 cijferige code die hier achter staat is de zendende partij.
- leveringsautorisatie:
- dienst: 