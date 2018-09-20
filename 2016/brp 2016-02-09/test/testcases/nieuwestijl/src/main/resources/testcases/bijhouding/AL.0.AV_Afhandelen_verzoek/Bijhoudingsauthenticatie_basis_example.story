Meta:
@sprintnummer           90
@epic                   Authenticatie en autorisatie
@jiraIssue              TEAMBRP-4556
@status                 Onderhanden
@regels                 R2101, R2102, R2103, R2104, R2105, R2108, R2109

Narrative: Als BRP wil ik dat alleen geauthenticeerde partijen kunnen bijhouden.

Scenario: R2102. Er bestaat een toegang bijhoudingsautorisatie voor deze Partij en Rol

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij '<partij>'

Given de personen 826933129,526521673,141901317,631512457,182119865,951375945 zijn verwijderd
Given de standaardpersoon Sandy met bsn 182119865 en anr 5371089682 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 951375945 en anr 1375139474 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_example_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking <verwerking>

Examples:
| #testsituatie                                                  | partij           | verwerking |
| Partij heeft een voorkomen van Toegang_bijhoudingsautorisatie  | Gemeente Alkmaar | Geslaagd   |
| Partij heeft GEEN voorkomen van Toegang_bijhoudingsautorisatie | Gemeente Delft   | Foutief    |

Scenario: R2103. De toegang bijhoudingsautorisatie bestaat

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_A.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_B.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar' met ondertekenaar <ondertekenaar> en transporteur <transporteur>

Given de personen 826933129,526521673,141901317,631512457,182119865,951375945 zijn verwijderd
Given de standaardpersoon Sandy met bsn 182119865 en anr 5371089682 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 951375945 en anr 1375139474 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_example_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking <verwerking>

And heeft in het antwoordbericht 'melding' in 'melding' de waardes '<waarde>'

Examples:
| #testsituatie                                                  | ondertekenaar        | transporteur         | verwerking | waarde                                                                    |
| combinatie van ondertekenaar en transporteur komt overeen      | 00000001001005650000 | 00000001001005650000 | Geslaagd   |                                                                           |
| combinatie van ondertekenaar en transporteur komt NIET overeen | 00000001001005650000 | 00000001001101857000 | Foutief    | Authenticatie: de combinatie van ondertekenaar en transporteur is onjuist |
| combinatie van ondertekenaar en transporteur komt NIET overeen | 00000001001721926000 | 00000001001005650000 | Foutief    | Authenticatie: de combinatie van ondertekenaar en transporteur is onjuist |
| combinatie van ondertekenaar en transporteur komt NIET overeen | 00000001001005650000 | 00000001001721926000 | Foutief    | Authenticatie: de combinatie van ondertekenaar en transporteur is onjuist |


Scenario: R2104. De toegang bijhoudingsautorisatie is niet geblokkeerd

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_A.txt,
/bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_C_Geblokkeerd.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar' met ondertekenaar <ondertekenaar> en transporteur <transporteur>

Given de personen 826933129,526521673,141901317,631512457,182119865,951375945 zijn verwijderd
Given de standaardpersoon Sandy met bsn 182119865 en anr 5371089682 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 951375945 en anr 1375139474 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_example_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking <verwerking>

And heeft in het antwoordbericht 'melding' in 'melding' de waardes '<waarde>'

Examples:
| #testsituatie                                                    | ondertekenaar        | transporteur         | verwerking | waarde                                                                    |
| toegang bijhoudingsauthorisatie van ondertekenaar is geblokkeerd | 00000001001569417000 | 00000001001569417000 | Foutief    | De toegang tot de bijhoudingsautorisatie is geblokkeerd door de beheerder |


