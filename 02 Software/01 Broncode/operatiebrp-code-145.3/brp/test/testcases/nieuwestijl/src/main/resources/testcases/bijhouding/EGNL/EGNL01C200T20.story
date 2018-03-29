Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R1875
@sleutelwoorden         Foutief
@usecase                UCS-BY.HG

Narrative: R1875 Datum einde relatie mag niet in de toekomst liggen

Scenario: Datum einde relatie is groter dan Systeemdatum
          LT: EGNL01C200T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C200-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 662705993 en persoon 721982281 met relatie id 4000008 en betrokkenheid id 4000008

When voer een bijhouding uit EGNL01C200T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C200T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 662705993 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
