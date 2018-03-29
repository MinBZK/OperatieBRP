Meta:
@status             Klaar
@usecase            LV.1.CPI
@regels             R1539
@sleutelwoorden     Zoek Persoon


Narrative:
Het systeem levert alleen niet vervallen personen (eis: Persoon.Soort = "Ingeschrevene" (I)
en Persoon.Nadere bijhoudingsaard <> "Fout" (F) of "Onbekend" (?))

Vanuit het perspectief van een gebruiker (afnemer of bijhouder) bezien bestaan deze personen dus niet.
Ontsluiting is slechts mogelijk via de beheerder.


Scenario:   8.  Hoofdpersoon heeft nadere.bijhoudingsaard Fout F
                LT: R1539_LT12
                Verwacht resultaat:
                - Geslaagd, maar geen persoon gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_naderebijhoudingsaard_fout.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R1539.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 0 groepen 'persoon'

Scenario:   9.  Hoofdpersoon heeft nadere.bijhoudingsaard Gewist W
                LT: R1539_LT16
                Verwacht resultaat:
                - Geslaagd, maar geen persoon gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given de database is aangepast met: update kern.pers set naderebijhaard=9 where bsn='606417801';
Given de database is aangepast met: update kern.his_persbijhouding set naderebijhaard=9 where pers=(select id from kern.pers where bsn='606417801');

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R1539.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 0 groepen 'persoon'
