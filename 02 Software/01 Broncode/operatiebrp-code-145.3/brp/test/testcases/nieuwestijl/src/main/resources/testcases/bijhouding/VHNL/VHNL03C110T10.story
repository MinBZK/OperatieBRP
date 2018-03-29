Meta:
@auteur                 jozon
@status                 Klaar
@sleutelwoorden         voltrekkingHuwelijkInNederland VHNL03C110T10
@usecase                UCS-BY.HG

Narrative:
Soort Document moet verwijzen naar bestaand stamgegeven

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Onbekende) gaan trouwen
            LT: VHNL03C110T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C110T10-Libby.xls

When voer een bijhouding uit VHNL03C110T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL03C110T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 690020041 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 8240349473 uit database en vergelijk met expected VHNL03C110T10-persoon1.xml
