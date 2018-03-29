Meta:
@status             Klaar
@sleutelwoorden     Selectie

Scenario:   1. Succesvolle selectierun met alleen foutieve en gewiste personen niet leveren
            LT: R1539_LT21, R1539_LT22
            Uitwerking:
            Een selectie op geboortedatum 19600821 Jan_naderebijhoudingsaard_fout voldoen hier aan.
            Jan_naderebijhoudingsaard_fout mag niet geleverd worden volgens R1539.
            Jan gewijzigde bsn wordt geupdate naar status gewist, mag niet geleverd worden volgens R1539
            Piet heeft geboortedatum 19600821 maar nadere bijhoudingsaard wordt omgezet naar onbekend, deze mag niet geleverd worden.
            Pseudo_gebdd 19600821 mag niet geleverd worden
            Geen personen in het resultaat


Given een selectierun met de volgende selectie taken:
|datplanning 	|status      | dienstSleutel
|vandaag       |Uitvoerbaar     | selectiebundel1

And alle selectie personen zijn verwijderd
!-- Nadere bijhouding foutief mag niet geleverd worden R1539, /LO3PL/Jan_naderebijhoudingsaard_fout.xls
!-- Nadere bijhouding gewist dmv update - mag niet geleverd worden R1539, /LO3PL/Jan_met_BSN_wijziging.xls
!-- Nadere bijhouding Onbekend, /LO3PL/Janna.xls
!-- Pseudo persoon voldoet aan selectiecriteria, wordt niet geleverd., /LO3PL/Pseudo_gebdd_19600821.xls
Given selectie personen uit bestanden:
|filenaam
|/LO3PL/Jan_naderebijhoudingsaard_fout.xls
|/LO3PL/Jan_met_BSN_wijziging.xls
|/LO3PL/Janna.xls
|/LO3PL/Pseudo_gebdd_19600821.xls

And de selectiedatabase is aangepast met: update kern.pers set naderebijhaard=9 where bsn='163013949';
And de selectiedatabase is aangepast met: update kern.his_persbijhouding set naderebijhaard=9 where pers=(select id from kern.pers where bsn='163013949');
!-- Nadere bijhouding Onbekend
And de selectiedatabase is aangepast met: update kern.pers set naderebijhaard=8 where bsn='984747977';
And de selectiedatabase is aangepast met: update kern.his_persbijhouding set naderebijhaard=8 where pers=(select id from kern.pers where bsn='984747977');


When start selectie run
And wacht tot selectie run gestart
And wacht maximaal 2 minuten tot selectie run klaar

Then zijn de volgende resultaat files aanwezig voor selectietaak met dienstsleutel 'selectiebundel1' en datumuitvoer 'vandaag':
|type                         |aantal
|Resultaatset personen        |==0
|Resultaatset totalen         |==1


