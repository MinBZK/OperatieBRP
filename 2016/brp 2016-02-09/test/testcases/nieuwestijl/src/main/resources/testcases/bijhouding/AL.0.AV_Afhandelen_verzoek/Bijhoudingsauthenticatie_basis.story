Meta:
@auteur                 luwid
@sprintnummer           90
@epic                   Authenticatie en autorisatie
@jiraIssue              TEAMBRP-4556
@status                 Klaar
@regels                 R2101, R2102, R2103, R2104, R2105, R2108, R2109

Narrative: Als BRP wil ik dat alleen geauthenticeerde partijen kunnen bijhouden.

Scenario: 1.R2102_L01.Indien een gemeente een correct voorkomen heeft van Toegang_bijhoudingsautorisatie dan verschijnt er geen melding in het
antwoordbericht. Er vindt bijhouding plaats.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 0 groep 'meldingen'
And is in de database de persoon met bsn 708186313 wel als PARTNER betrokken bij een HUWELIJK

Scenario: 2.R2102_L02.Indien een gemeente GEEN voorkomen heeft van Toegang_bijhoudingsautorisatie dan verschijnt er een melding in het
antwoordbericht. Er vindt geen bijhouding plaats.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Delft'
Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes                          |
| melding | melding   | De gebruikte authenticatie is niet bekend |
And is in de database de persoon met bsn 708186313 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 3.R2103_L01.De combinatie van Ondertekenaar en Transporteur komt overeen met die in de Toegang_bijhoudingautorisatie. Er verschijnt geen
melding en de bijhouding vindt plaats.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_A.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_B.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 0 groep 'meldingen'
And is in de database de persoon met bsn 708186313 wel als PARTNER betrokken bij een HUWELIJK

Scenario: 4.R2103_L02.De combinatie van Ondertekenaar en Transporteur komt NIET overeen met die in de Toegang_bijhoudingautorisatie. Er verschijnt een
melding en de bijhouding heeft niet plaatsgevonden.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_A.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_B.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar' met ondertekenaar 00000001001005650000 en transporteur 00000001001101857000

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes                                                |
| melding | melding   | Authenticatie: Combinatie ondertekenaar en transporteur onjuist |
And is in de database de persoon met bsn 708186313 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 5.R2103_L03.De combinatie van Ondertekenaar en Transporteur komt NIET overeen met die in de Toegang_bijhoudingautorisatie, context.ondertekernaar.OIN is de OIN van geautoriseerde. Erverschijnt een melding en de bijhouding heeft niet plaatsgevonden.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_A.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_B.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar' met ondertekenaar 00000001001721926000 en transporteur 00000001001005650000

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes                                                |
| melding | melding   | Authenticatie: Combinatie ondertekenaar en transporteur onjuist |
And is in de database de persoon met bsn 708186313 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 6.R2103_L04.De combinatie van Ondertekenaar en Transporteur komt NIET overeen met die in de Toegang_bijhoudingautorisatie, context.transporteur.OIN is de OIN van geautoriseerde. Erverschijnt een melding en de bijhouding heeft niet plaatsgevonden.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_A.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_B.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar' met ondertekenaar 00000001001005650000 en transporteur 00000001001721926000

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes                                                |
| melding | melding   | Authenticatie: Combinatie ondertekenaar en transporteur onjuist |
And is in de database de persoon met bsn 708186313 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 7a.R2103_L05.Scenario 7a-7d test de volgorde van toegang bijhoudingsautorisatie in de database. De combinatie van Ondertekenaar en
Transporteur komt overeen met die in de Toegang_bijhoudingautorisatie. Er verschijnt geen melding en de bijhouding vindt plaats.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_NULL_en_Transporteur_A.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_B_en_Transporteur_NULL.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 0 groep 'meldingen'
And is in de database de persoon met bsn 708186313 wel als PARTNER betrokken bij een HUWELIJK

Scenario: 7b.R2103_L06.Scenario 7a-7d test de volgorde van toegang bijhoudingsautorisatie in de database.De combinatie van Ondertekenaar en Transporteur komt overeen met die in de Toegang_bijhoudingautorisatie. Er verschijnt geen melding en de bijhouding vindt plaats.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_B_en_Transporteur_NULL.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_NULL_en_Transporteur_A.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 0 groep 'meldingen'
And is in de database de persoon met bsn 708186313 wel als PARTNER betrokken bij een HUWELIJK

Scenario: 7c.R2103_L07.Scenario 7a-7d test de volgorde van toegang bijhoudingsautorisatie in de database. De combinatie van Ondertekenaar en
Transporteur komt overeen met die in de Toegang_bijhoudingautorisatie. Er verschijnt geen melding en de bijhouding vindt plaats.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_B_en_Transporteur_NULL.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_NULL_en_Transporteur_A.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 0 groep 'meldingen'
And is in de database de persoon met bsn 708186313 wel als PARTNER betrokken bij een HUWELIJK

Scenario: 7d.R2103_L08.Scenario 7a-7d test de volgorde van toegang bijhoudingsautorisatie in de database. De combinatie van Ondertekenaar en
Transporteur komt overeen met die in de Toegang_bijhoudingautorisatie. Er verschijnt geen melding en de bijhouding vindt plaats.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_NULL_en_Transporteur_A.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_B_en_Transporteur_NULL.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 0 groep 'meldingen'
And is in de database de persoon met bsn 708186313 wel als PARTNER betrokken bij een HUWELIJK


!-- Geen story voor R2104_L01 gemaakt omdat het geval waarin Geblokkeerd? = leeg al in andere scenario's is getest.

Scenario: 8.R2104_L02.De Toegang_bijhoudingautorisatie is geblokkeerd. Er verschijnt een melding en vindt geen bijhouding plaats.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_A.txt,
/bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_C_Geblokkeerd.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar' met ondertekenaar 00000001001569417000 en transporteur 00000001001569417000

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes                                        |
| melding | melding   | De toegang autorisatie is geblokkeerd door de beheerder |
And is in de database de persoon met bsn 708186313 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 9.R2105_L01. Datum ingang is systeemdatum en datum einde is leeg in toegang Bijhoudingsautorisatie. Er verschijnt geen melding en vindt
bijhouding plaats.

Given bijhoudingautorisatie uit \bijhoudingsautorisaties\Gemeente_Alkmaar_datumingang-einde_leeg.txt
Given de database is aangepast met: update autaut.toegangbijhautorisatie set datingang ='${vandaagsql()}' where geautoriseerde in (select id from kern.partij where naam = 'Gemeente Alkmaar')
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 0 groep 'meldingen'
And is in de database de persoon met bsn 708186313 wel als PARTNER betrokken bij een HUWELIJK

Scenario: 10.R2105_L02. Datum ingang en datum einde zijn gelijk aan systeemdatum in toegang Bijhoudingsautorisatie. Er verschijnt een melding en vindt
geen bijhouding plaats.

Given bijhoudingautorisatie uit \bijhoudingsautorisaties\Gemeente_Alkmaar_datumingang-einde_leeg.txt
Given de database is aangepast met: update autaut.toegangbijhautorisatie set datingang ='${vandaagsql()}' where geautoriseerde in (select id from kern.partij where naam = 'Gemeente Alkmaar')
Given de database is aangepast met: update autaut.toegangbijhautorisatie set dateinde ='${vandaagsql()}' where geautoriseerde in (select id from kern.partij where naam = 'Gemeente Alkmaar')

Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes                      |
| melding | melding   | De toegang autorisatie is niet geldig |
And is in de database de persoon met bsn 708186313 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 11.R2105_L03. Datum ingang is vandaag en datum einde is morgen in toegang Bijhoudingsautorisatie. Er verschijnt een melding en vindt
bijhouding plaats.

Given bijhoudingautorisatie uit \bijhoudingsautorisaties\Gemeente_Alkmaar_datumingang-einde_leeg.txt
Given de database is aangepast met: update autaut.toegangbijhautorisatie set datingang ='${vandaagsql()}' where geautoriseerde in (select id from kern.partij where naam = 'Gemeente Alkmaar')
Given de database is aangepast met: update autaut.toegangbijhautorisatie set dateinde ='${vandaagsql(0,0,+1)}' where geautoriseerde in (select id from kern.partij where naam = 'Gemeente Alkmaar')
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 0 groep 'meldingen'
And is in de database de persoon met bsn 708186313 wel als PARTNER betrokken bij een HUWELIJK

Scenario: 12.R2105_L05. Datum ingang is in het verleden en datum einde is vandaag in toegang Bijhoudingsautorisatie. Er verschijnt een melding en vindt
geen bijhouding plaats.

Given bijhoudingautorisatie uit \bijhoudingsautorisaties\Gemeente_Alkmaar_datumingang-einde_leeg.txt
Given de database is aangepast met: update autaut.toegangbijhautorisatie set datingang ='${vandaagsql(0,0,-3)}' where geautoriseerde in (select id from kern.partij where naam = 'Gemeente Alkmaar')
Given de database is aangepast met: update autaut.toegangbijhautorisatie set dateinde ='${vandaagsql()}' where geautoriseerde in (select id from kern.partij where naam = 'Gemeente Alkmaar')
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes                      |
| melding | melding   | De toegang autorisatie is niet geldig |
And is in de database de persoon met bsn 708186313 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 13.R2105_L06. Datum ingang en einde toegang Bijhoudingsautorisatie in het verleden. Er verschijnt een melding en vindt geen bijhouding plaats.

Given bijhoudingautorisatie uit \bijhoudingsautorisaties\Gemeente_Alkmaar_datumingang-einde_leeg.txt
Given de database is aangepast met: update autaut.toegangbijhautorisatie set datingang ='${vandaagsql(0,0,-4)}' where geautoriseerde in (select id from kern.partij where naam = 'Gemeente Alkmaar')
Given de database is aangepast met: update autaut.toegangbijhautorisatie set dateinde ='${vandaagsql(0,0,-2)}' where geautoriseerde in (select id from kern.partij where naam = 'Gemeente Alkmaar')
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes                      |
| melding | melding   | De toegang autorisatie is niet geldig |
And is in de database de persoon met bsn 708186313 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 14.R2105_L07. Datum ingang en einde toegang Bijhoudingsautorisatie in de toekomst. Er verschijnt een melding en vindt geen bijhouding plaats.

Given bijhoudingautorisatie uit \bijhoudingsautorisaties\Gemeente_Alkmaar_datumingang-einde_leeg.txt
Given de database is aangepast met: update autaut.toegangbijhautorisatie set datingang ='${vandaagsql(0,0,+6)}' where geautoriseerde in (select id from kern.partij where naam = 'Gemeente Alkmaar')
Given de database is aangepast met: update autaut.toegangbijhautorisatie set dateinde ='${vandaagsql(0,0,+10)}' where geautoriseerde in (select id from kern.partij where naam = 'Gemeente Alkmaar')
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes                      |
| melding | melding   | De toegang autorisatie is niet geldig |
And is in de database de persoon met bsn 708186313 niet als PARTNER betrokken bij een HUWELIJK

!-- Geen story voor R2108_L01 gemaakt omdat het geval van Context.Ondertekenaar.OIN = OIN van O1 al in scenario 3 is getest.

Scenario: 15.R2108_L02. Ondertekenaar OIN is van de Geautoriseerde maar komt niet met die in de Toegang_bijhoudingautorisatie overeen. Er verschijnt een
melding en vindt geen bijhouding plaats.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_A.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar' met ondertekenaar 00000001001721926000 en transporteur 00000001001721926000

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes                     |
| melding | melding   | Authenticatie: Ondertekenaar onjuist |
And is in de database de persoon met bsn 708186313 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 16.R2108_L03. Ondertekenaar OIN is NIET van de Geautoriseerde en komt niet met die in de Toegang_bijhoudingautorisatie overeen. Er verschijnt
een melding en vindt geen bijhouding plaats.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_A.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar' met ondertekenaar 00000001001569417000 en transporteur 00000001001569417000

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes                     |
| melding | melding   | Authenticatie: Ondertekenaar onjuist |
And is in de database de persoon met bsn 708186313 niet als PARTNER betrokken bij een HUWELIJK

!-- Geen story voor R2109_L01 gemaakt omdat het geval van Context.Transporteur.OIN = OIN van T1 al in scenario 3 is getest.

Scenario: 17.R2109_L02. Transporteur OIN is de Geautoriseerde maar komt niet met die in de Toegang_bijhoudingautorisatie overeen. Er verschijnt
een melding en vindt geen bijhouding plaats.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_A.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar' met ondertekenaar 00000001001005650000 en transporteur 00000001001721926000

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes                    |
| melding | melding   | Authenticatie: Transporteur onjuist |
And is in de database de persoon met bsn 708186313 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 18.R2109_L03. Transporteur OIN is NIET van de Geautoriseerde en komt niet met die in de Toegang_bijhoudingautorisatie overeen. Er verschijnt
een melding en vindt geen bijhouding plaats.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt,
/bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_A.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar' met ondertekenaar 00000001001005650000 en transporteur 00000001001569417000

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes                    |
| melding | melding   | Authenticatie: Transporteur onjuist |
And is in de database de persoon met bsn 708186313 niet als PARTNER betrokken bij een HUWELIJK
