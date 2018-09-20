Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1976
@sleutelwoorden     Maak BRP bericht

Narrative:
Het te leveren resultaat bevat alleen Objecten die, binnen hun berichtdeel, enig geautoriseerd Attribuut kunnen bevatten.

De attribuut-autorisatie staat beschreven in R1974 - Alleen attributen waarvoor autorisatie bestaat worden geleverd.

Toelichting: De attribuut-autorisatie van een Dienstbundel wordt beschreven door de voorkomens van Dienstbundel \ Groep \ Attribuut.
Het Object neemt een bepaalde plek in binnen het bericht, waarbij een XSD beschrijft welke inhoud (een hiërarchische structuur van andere Objecten,
Groepen en Attributen) er binnen dat Object in het bericht aanwezig mogen zijn. Als er binnen die hiërarchische structuur geen enkel Attribuut
te vinden is waarvoor autorisatie bestaat, dan kunnen we daaruit afleiden dat er ook geen autorisatie voor dit Object bestaat op deze plek in
het bericht. We filteren dit Object (en de hele structuur er onder) dan weg uit het bericht.

Noot 1: Of de betreffende attributen daadwerkelijk voorkomen in het bewuste bericht is dus niet relevant.
Als bijvoorbeeld een afnemer geautoriseerd is voor attribuut "GerelateerdeOuder.OuderlijkGezag?" dan filteren we
Object GerelateerdeOuder niet weg, zelfs als de groep "Ouderlijk Gezag" niet daadwerkelijk voorkomt in het uiteindelijke bericht.

Noot 2: Deze hiërarchie voor deze afleiding verloopt dus via de structuur van het bericht!
Bijvoorbeeld: in een VolledigBericht bevat de betrokkenheid Kind van de hoofdpersoon de objecten
FamilierechtelijkeBetrekking, Ouder en Persoon ('GerelateerdeOuder.Persoon').
Als de afnemer geautoriseerd is voor het Geboorteland van de ouder, dan wordt het object betrokkenheid Kind op grond daarvan niet verwijderd
uit het bericht. De betrokkenheid Kind van zijn eigen kinderen bevatten binnen het bericht alleen Persoon ('GerelateerdKind').
Als de afnemer ook geautoriseerd is voor het BSN van het kind, dan wordt om die redenen de betrokkenheid Kind niet weggefilterd.
De afleiding is voor betrokkenheid Kind dus op verschillende plekken in de berichtstructuur verschillend qua uitwerking.
verschillend qua uitwerking.


Scenario: Testen onder R1975