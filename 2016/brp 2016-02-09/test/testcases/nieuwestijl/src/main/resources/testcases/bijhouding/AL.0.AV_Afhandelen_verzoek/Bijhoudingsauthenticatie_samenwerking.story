Meta:
@auteur                 luwid
@sprintnummer           91
@epic                   Authenticatie en autorisatie
@jiraIssue              TEAMBRP-4562
@status                 Klaar
@regels                 R2106

Narrative: Als BRP wil ik dat geauthenticeerde partijen kunnen samenwerken allen voor toegestane Administratieve Handeling.

Scenario: 1.R2106_L01.Een Toegang_bijhoudingsautorisatie (met Geautoriseerde alkmaar zonder ondertekenaar of transporteur) heeft geen voorkomen in
Bijhoudingsautorisatie\Soort Administratieve Handeling. Alkmaar mag een willekeurige handeling uitvoeren. Er verschijnt geen melding in het
antwoordbericht en vindt bijhouding plaats.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt,
/bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_Tiel_VoltrekkingHuwNed_OntbindingHuwNed.txt
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

Scenario: 2.R2106_L02.Een Toegang_bijhoudingsautorisatie staat Voltrekking Huwelijk in Nederland toe. Er verschijnt geen melding in het
antwoordbericht. Er vindt bijhouding plaats.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt,
/bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_Haarlem_VoltrekkingHuwNed_OntbindingHuwNed.txt,
/bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_Tiel_NietigVerklHuw.txt
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

Scenario: 3.R2106_L03.Een Toegang_bijhoudingsautorisatie (met Geautoriseerde alkmaar en Ondertekenaar Haarlem) heeft geen voorkomen in
Bijhoudingsautorisatie\Soort Administratieve Handeling. Haarlem mag dus een willekeurige handeling uitvoeren voor Alkmaar. Er verschijnt geen melding in het antwoordbericht en vindt bijhouding plaats.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_en_Transporteur_A.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_Tiel_VoltrekkingHuwNed_OntbindingHuwNed.txt
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

Scenario: 4.R2106_L04.Ondertekenaar Tiel mag alleen Nietig Verklaring Huwelijk voor Alkmaar uitvoeren. Er verschijnt een melding in het antwoordbericht en
vindt geen bijhouding plaats.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt,
/bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_Haarlem_VoltrekkingHuwNed_OntbindingHuwNed.txt,
/bijhoudingsautorisaties/Gemeente_Alkmaar_Ondertekenaar_Tiel_NietigVerklHuw.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar' met ondertekenaar 00000001001101857000 en transporteur 00000001001101857000

Given de personen 826933129,526521673,141901317,631512457,708186313,441121961 zijn verwijderd
Given de standaardpersoon Sandy met bsn 708186313 en anr 1480001266 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 441121961 en anr 2448652050 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand Bijhoudingsauthenticatie_basis_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes                                                            |
| melding | melding   | De soort administratieve handeling is niet toegestaan voor deze autorisatie |
And is in de database de persoon met bsn 708186313 niet als PARTNER betrokken bij een HUWELIJK
