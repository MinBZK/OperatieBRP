Meta:
@sprintnummer           89
@epic                   POC Bijhouding
@auteur                 dihoe
@status                 Onderhanden

Narrative:
GBA registratie van de omzetting van een geregistreerd partnerschap in een huwelijk
waarvoor een akte is ingeschreven in de registers van de Burgerlijke Stand
en minstens één betrokken persoon een ingezetene is.
*** Het is nog niet mogelijk om een geregistreerd partnerschap te sluiten vanuit ISC. ***

Scenario: 1. Foutmelding wordt getoond omdat geprobeerd wordt een huwelijk om te zetten

Given de personen 931665929,826933129,526521673 zijn verwijderd
And de standaardpersoon Sandy met bsn 931665929 en anr 9091384082 zonder extra gebeurtenissen

Given administratieve handeling van type gBASluitingHuwelijkGeregistreerdPartnerschap , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamVoornaam
And testdata uit bestand isc_SluitingHuwelijkGeregistreerdPartnerschap_01.yml
And testdata uit bestand isc_SluitingHuwelijkGeregistreerdPartnerschap_02.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given administratieve handeling van type gBAOmzettingHuwelijkGeregistreerdPartnerschap , met de acties registratieEindeHuwelijkGeregistreerdPartnerschap
And testdata uit bestand gBAOmzettingHuwelijkGeregistreerdPartnerschap_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
