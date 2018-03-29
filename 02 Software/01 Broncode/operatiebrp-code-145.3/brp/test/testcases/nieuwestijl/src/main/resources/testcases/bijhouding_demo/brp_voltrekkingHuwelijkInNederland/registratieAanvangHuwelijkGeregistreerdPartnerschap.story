Meta:
@auteur                 dihoe
@status                 Onderhanden
@sleutelwoorden         bijhouding,huwelijk

Narrative: voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: 1. R1636 - In een H/P-relatie zijn twee partners betrokken
Meta:
@regels                 BRAL2111,R1636

Gemeente BRP 1
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,141901317,631512457,140678177,698938057 zijn verwijderd
Given de standaardpersoon Sandy met bsn 140678177 en anr 3679783698 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 698938057 en anr 7049482514 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand registratieAanvangHuwelijkGeregistreerdPartnerschap_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep                          | attribuut           | verwachteWaardes    |
| stuurgegevens                  | zendendePartij      | 199903              |
| stuurgegevens                  | zendendeSysteem     | BRP                 |
| voltrekkingHuwelijkInNederland | partijCode          | 017401,017401       |
| identificatienummers           | burgerservicenummer | 140678177,698938057 |

Scenario: 2. R1650 - Gemeente moet verwijzen naar bestaand stamgegeven
Meta:
@regels                 BRAL1002,R1650

Given de personen 826933129,526521673,141901317,631512457,140678177,698938057 zijn verwijderd
Given de standaardpersoon Sandy met bsn 140678177 en anr 3679783698 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 698938057 en anr 7049482514 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand registratieAanvangHuwelijkGeregistreerdPartnerschap_02.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Then hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes |
| melding | regelCode | BRAL1002         |

Scenario: 4. R1865 - Minimumleeftijd NL partner bij voltrekking H/P in Nederland
Meta:
@regels                 BRBY0401,R1865t

Given de personen 141901317,631512457,140678177,698938057 zijn verwijderd
Given de standaardpersoon Danny met bsn 698938057 en anr 7049482514 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
def sandy = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 20010801, toelichting: '1e kind') {
        op '2000-01-01' te 'Delft' gemeente 503
        geslacht 'VROUW'
        namen {
            voornamen 'Sandy'
            geslachtsnaam 'Olsson'
        }
        identificatienummers bsn: 140678177, anummer: 3679783698
    }
}
slaOp(sandy)

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand registratieAanvangHuwelijkGeregistreerdPartnerschap_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Then hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes |
| melding | regelCode | BRBY0401         |

