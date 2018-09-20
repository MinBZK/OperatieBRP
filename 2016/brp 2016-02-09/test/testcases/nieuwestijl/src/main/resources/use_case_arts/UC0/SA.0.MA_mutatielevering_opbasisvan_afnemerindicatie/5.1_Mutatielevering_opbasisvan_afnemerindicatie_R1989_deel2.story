Meta:
@epic               Verbeteren testtooling
@auteur             kedon
@status             Onderhanden
@usecase            SA.0.MA
@regels             R1314,R1315,R1338,R1343,R1544,R1989,R1990,R1993,R1994,R2000,R2001,R2002
@sleutelwoorden     Mutatielevering o.b.v. afnemerindicatie

Narrative:  Als afnemer
            wil ik geen mutatieBericht ontvangen, in het geval dat geen enkele inhoudelijke of onderzoeksgroep in het bericht over blijft,
            met een verwerkingssoort anders dan identificerend of
            alleen Persoon Ageleid Administratief voorkomt met ongewijzigde verwerkingsoorten toegevoegd en vervallen



Scenario: 10.1   Alleen Persoon Ageleid Administratief komt voor met verwerkingsoorten toegevoegd en vervallen,
                Persoon.Onverwerkt bijhoudingsvoorstel niet-ingezetene aanwezig WEL gewijzigd.
                Plaatsen afnemerindicatie
                Logsch testgeval: R1989_04, R1990_01, R1991_01
                Verwacht resultaat: Mutatiebericht

Meta:
@status     Onderhanden

Given de personen 299054457, 743274313, 981661129, 533829033 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 981661129 en anr 8545465106 zonder extra gebeurtenissen
Given de standaardpersoon UC_Lieze met bsn 533829033 en anr 1980904978 zonder extra gebeurtenissen


Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand 1.2_plaatsen_afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then ingaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004560
Then uitgaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000004560

When het volledigbericht voor abonnement Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 10.2   Alleen Persoon Ageleid Administratief komt voor met verwerkingsoorten toegevoegd en vervallen,
                Persoon.Onverwerkt bijhoudingsvoorstel niet-ingezetene aanwezig WEL gewijzigd.
                Emigratie
                Logische testgevallen Use Case: R1989_04, R1991_01, R1993_01
                Verwacht resultaat: Mutatiebericht

Meta:
@status     Onderhanden

Given administratieve handeling van type verhuizingNaarBuitenland , met de acties registratieMigratie
And testdata uit bestand 5.2_registratieMigratie.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor abonnement Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 10.3   Alleen Persoon Ageleid Administratief komt voor met verwerkingsoorten toegevoegd en vervallen,
                Persoon.Onverwerkt bijhoudingsvoorstel niet-ingezetene aanwezig WEL gewijzigd.
                Bijhouding: huwelijk geregistreerd partnerschap, met actie registratienaamgebruikt
                Logische testgevallen Use Case: R1989_04, R1991_01, R1993_01
                Verwacht resultaat: Mutatiebericht

Meta:
@status     Onderhanden

Given administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamgebruik
And testdata uit bestand 5.3_registratieAanvangHuwelijkGeregistreerdPartnerschap.yml
And testdata uit bestand 5.4_registratieNaamgebruik.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor abonnement Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
