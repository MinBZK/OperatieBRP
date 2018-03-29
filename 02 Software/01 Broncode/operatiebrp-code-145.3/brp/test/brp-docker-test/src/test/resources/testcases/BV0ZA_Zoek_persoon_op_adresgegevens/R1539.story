Meta:
@status             Klaar
@usecase            BV.0.ZA
@regels             R1539aa
@sleutelwoorden     Zoek Persoon op adresgegevens


Narrative:
Het systeem levert alleen niet vervallen personen (eis: Persoon.Soort = "Ingeschrevene" (I)
en Persoon.Nadere bijhoudingsaard <> "Fout" (F) of "Onbekend" (?))

Vanuit het perspectief van een gebruiker (afnemer of bijhouder) bezien bestaan deze personen dus niet.
Ontsluiting is slechts mogelijk via de beheerder.


Scenario:   1.  Hoofdpersoon heeft nadere.bijhoudingsaard Fout (F)
                LT: R1539_LT13, R1538_LT07
                Verwacht resultaat:
                - Geslaagd, maar geen persoon gevonden
                - Jan verkeerde nadere bijhoudingsaard
                - Familie Jan niet ingeschrevenen (pseudopersonen)

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_naderebijhoudingsaard_fout.xls

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R1539.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 0 groepen 'persoon'

Scenario:   2.  Hoofdpersoon heeft nadere.bijhoudingsaard Gewist (W)
                LT: R1539_LT20
                Verwacht resultaat:
                - Geslaagd, maar geen persoon gevonden
                - Jan verkeerde nadere bijhoudingsaard

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given de database is aangepast met: update kern.pers set naderebijhaard=9 where bsn='606417801';
Given de database is aangepast met: update kern.his_persbijhouding set naderebijhaard=9 where pers=(select id from kern.pers where bsn='606417801');

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R1539.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 0 groepen 'persoon'
