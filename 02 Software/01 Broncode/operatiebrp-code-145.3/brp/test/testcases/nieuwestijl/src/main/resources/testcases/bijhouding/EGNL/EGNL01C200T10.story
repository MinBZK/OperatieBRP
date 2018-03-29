Meta:
@status                 Klaar
@regels                 R1875
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative: R1875 Datum einde relatie mag niet in de toekomst liggen

Scenario: Datum einde relatie is gelijk aan Systeemdatum
          LT: EGNL01C200T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C200-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 662705993 en persoon 721982281 met relatie id 4000007 en betrokkenheid id 4000007

When voer een bijhouding uit EGNL01C200T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C200T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 662705993 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
