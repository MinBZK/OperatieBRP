Meta:
@auteur                 fuman
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1812
@usecase                UCS-BY.HG

Narrative:
Verwerken Groep Samengestelde naam (inschrijving O)

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Onbekende) gaan trouwen, Verwerken Groep Samengestelde naam (inschrijving O)
            LT: VHNL04C10T70



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Libby.xls

When voer een bijhouding uit VHNL04C10T70.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL04C10T70.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 690020041 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 793558025 wel als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select voornamen, geslnaamstam, indafgeleid from kern.his_perssamengesteldenaam where voornamen = 'Pieter' and geslnaamstam = 'Jansens' de volgende gegevens:
| veld                      | waarde |
| voornamen                 | Pieter  |
| indafgeleid               | false     |
| geslnaamstam              | Jansens   |
