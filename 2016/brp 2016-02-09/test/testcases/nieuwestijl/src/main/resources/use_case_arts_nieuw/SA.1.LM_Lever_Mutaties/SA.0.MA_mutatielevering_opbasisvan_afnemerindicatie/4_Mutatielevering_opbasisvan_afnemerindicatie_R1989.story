Meta:
@epic               Verbeteren testtooling
@auteur             kedon
@status             Klaar
@usecase            SA.0.MA
@regels             R1314,R1315,R1338,R1343,R1544,R1989,R1990,R1993,R1994,R2000,R2001,R2002
@sleutelwoorden     Mutatielevering o.b.v. afnemerindicatie

Narrative:  Als afnemer
            wil ik geen mutatieBericht ontvangen, in het geval dat geen enkele inhoudelijke of onderzoeksgroep in het bericht over blijft,
            met een verwerkingssoort anders dan identificerend of
            alleen Persoon Ageleid Administratief voorkomt met ongewijzigde verwerkingsoorten toegevoegd en vervallen



Scenario: 9.1   Alleen Persoon Ageleid Administratief komt voor met verwerkingsoorten toegevoegd en vervallen,
                Persoon.Onverwerkt bijhoudingsvoorstel niet-ingezetene aanwezig NIET gewijzigd.
                Plaatsen afnemerindicatie
                Logische testgevallen Use Case: R1989_05, R1990_02
                Verwacht resultaat: Geen mutatiebericht

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie, /levering_autorisaties/R1554_levering_van_alleen_afgeleidadministratief
Given de personen 299054457, 743274313, 981661129 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 981661129 en anr 8545465106 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1.2_plaatsen_afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then ingaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004560
Then uitgaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004560

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 9.2   Alleen Persoon Ageleid Administratief komt voor met verwerkingsoorten toegevoegd en vervallen
                Persoon.Onverwerkt bijhoudingsvoorstel niet-ingezetene aanwezig NIET gewijzigd.
                Bijhouding: Verhuizing
                Logische testgevallen Use Case: R1989_05, R1990_02
                Verwacht resultaat: Geen mutatiebericht
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 1.3_verhuizing_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
Then heeft in het antwoordbericht 'soortNaam' in 'document' de waarde 'Aangifte met betrekking tot verblijfplaats'

When het mutatiebericht voor leveringsautorisatie R1554-levering van alleen afgeleidadministratief is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden