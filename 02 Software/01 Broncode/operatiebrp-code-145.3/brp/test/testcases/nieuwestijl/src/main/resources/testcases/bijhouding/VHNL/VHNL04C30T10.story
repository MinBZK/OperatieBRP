Meta:
@auteur                 fuman
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Registratie aanvang huwelijk in NL tussen I-I en Onbekend met meegeven persoonsgegevens

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene) en Pieter Jansen (Onbekend persoon) gaan trouwen met meegeven pers. gegevens, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: VHNL04C30T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL04C30T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL04C30T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL04C30T10-persoon1.xml

!-- Controleer kern.pers tbv A-laag test
Then in kern heeft select indagsamengesteldenaam, voornamen from kern.pers where voornamen = 'Pieter' and srt = '2' de volgende gegevens:
| veld                   | waarde                         |
| indagsamengesteldenaam | true                           |
| voornamen              | Pieter                         |





