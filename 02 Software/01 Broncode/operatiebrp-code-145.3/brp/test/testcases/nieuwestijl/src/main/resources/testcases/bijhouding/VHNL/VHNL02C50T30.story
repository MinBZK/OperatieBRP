Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Onbekende) gaan trouwen, Geslachtsaanduiding wel in stamtabel

Scenario:   Prevalidatie. Valideer dat Piet NIET wordt opgeslagen en dat de bijhoudingsgegevens van de nevenactie voor Libby ook NIET worden opgeslagen. Libby krijgt Thatcher als geslnaamstamnaamgebruik.
            LT: VHNL02C50T30

Gemeente BRP 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Libby.xls

When voer een bijhouding uit VHNL02C50T30.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C50T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then in kern heeft select geslnaamstamnaamgebruik, voornamennaamgebruik from kern.pers where bsn = '690020041' de volgende gegevens:
| veld                    | waarde   |
| geslnaamstamnaamgebruik | Thatcher |
| voornamennaamgebruik    | Libby    |

Then is in de database de persoon met bsn 690020041 niet als PARTNER betrokken bij een HUWELIJK
