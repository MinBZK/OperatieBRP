Meta:
@auteur             jowil
@status             Klaar
@usecase            SA.0.PA,SA.1.PA,SA.1.PA.AL,SA.1.PA.CI,LV.1.PA,LV.1.VA,LV.0.AV,LV.1.AV,LV.1.AB,LV.1.XV,LV.1.AU,LV.1.AL,LV.1.MR,LV.1.VB,LV1.AB
@regels             R1336,R1339,R1350,R1402,R1403,R1404,R1405,R1406,R1407,R1408,R1538,R1539,R1984,R2016,R2061
@sleutelwoorden     Plaats afnemerindicatie, Controleer inhoud, Verwijder afnemerindicatie, Afhandeling verzoek, Afhandelen verzoek, Archiveer bericht, XSD validatie, Authenticatie, Autorisatie levering, Maak responsebericht, Verstuur bericht, Archiveer bericht


Narrative:
De gegevens van de opgevraagde persoon mogen niet vervallen zijn.
Wanneer de nadere bijhoudingsaard gelijk is aan fout of onbekend kan de afnemerindicatie niet geplaatst worden

Scenario:   11. Afnemer plaatst afnemerindicatie op ingezetene, waarbij de bijbehorende bijhoudingsaard is onbekend.
            Logische testgevallen Use Case: R1539_02
            Verwacht resultaat: 1. Afnemerindicatie kan niet geplaatst worden op persoon
                                2. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                -  Melding =  'Blokkerend: Er is geen geldige persoon met het opgegeven burgerservicenummer.'
                                3. Er wordt niet geleverd voor deze persoon

Given de personen 299054457, 743274313, 148047117 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 148047117 en anr 8704769298  zonder extra gebeurtenissen
And de database is aangepast met: update kern.his_persbijhouding set naderebijhaard=8 where pers in (select id from kern.pers  where bsn = 148047117)

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.2_Plaats_afnemerindicatie_06.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'Blokkerend: Er is geen geldige persoon met het opgegeven burgerservicenummer.'

Then is er geen synchronisatiebericht gevonden


Scenario:   12. Afnemer plaatst afnemerindicatie op ingeschreve, waarbij de bijbehorende bijhoudingsaard is fout.
            Logische testgevallen Use Case: R1539_03
            Verwacht resultaat: 1. Afnemerindicatie kan niet geplaatst worden op persoon
                                2. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                -  Melding = 'Blokkerend: Er is geen geldige persoon met het opgegeven burgerservicenummer.'
                                3. Er wordt niet geleverd voor deze persoon

Given de personen 299054457, 743274313, 148047117 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 148047117 en anr 8704769298  zonder extra gebeurtenissen
And de database is aangepast met: update kern.his_persbijhouding set naderebijhaard=7 where pers in (select id from kern.pers  where bsn = 148047117)

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.3_Plaats_afnemerindicatie_07.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'regelCode' in 'melding' de waarde 'BRLV0022'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'Blokkerend: Er is geen geldige persoon met het opgegeven burgerservicenummer.'

Then is er geen synchronisatiebericht gevonden





























