Meta:
@auteur                 dihoe
@status                 Onderhanden
@sleutelwoorden         bijhouding,huwelijk,niet_ingeschrevene,diana78

Narrative: voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap met een niet ingeschrevene

Scenario: 1. voltrekking huwelijk in Nederland met een niet ingeschrevene

Given de personen 141901317,631512457,993208393 zijn verwijderd
Given de standaardpersoon Danny met bsn 993208393 en anr 2967649042 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand registratieAanvangHuwelijkGeregistreerdPartnerschap_NI_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep                          | attribuut           | verwachteWaardes |
| stuurgegevens                  | zendendePartij      | 199903           |
| stuurgegevens                  | zendendeSysteem     | BRP              |
| voltrekkingHuwelijkInNederland | partijCode          | 050301, 050301   |
| identificatienummers           | burgerservicenummer | 993208393        |
| document                       | soortNaam           | Huwelijksakte    |

Scenario: 2. R1861 - H/P mag alleen door betrokken gemeente worden geregistreerd
             R1862 - Gemeente aanvang H/P moet registergemeente zijn

Meta:
@regels                   BRAL2104,BRAL2110,R1861,R1862

Given de personen 141901317,631512457,993208393 zijn verwijderd
Given de standaardpersoon Danny met bsn 993208393 en anr 2967649042 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand registratieAanvangHuwelijkGeregistreerdPartnerschap_NI_02.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Then hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes   |
| melding | regelCode | BRAL2104, BRAL2110 |

Scenario: 3. R1809 - H/P mag alleen door betrokken gemeente worden geregistreerd

Meta:
@regels                   BRAL0502,R1809

Given de personen 141901317,631512457,993208393 zijn verwijderd
Given de standaardpersoon Danny met bsn 993208393 en anr 2967649042 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand registratieAanvangHuwelijkGeregistreerdPartnerschap_NI_03.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Then hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes |
| melding | regelCode | BRAL0502         |

Scenario: 4. R1865 - Minimumleeftijd NL partner bij voltrekking H/P in Nederland

Meta:
@regels                   BRBY0401,R1865

Given de personen 141901317,631512457,993208393 zijn verwijderd
Given de standaardpersoon Danny met bsn 993208393 en anr 2967649042 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand registratieAanvangHuwelijkGeregistreerdPartnerschap_NI_04.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

Then hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes |
| melding | regelCode | BRBY0401         |