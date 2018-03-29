Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1835
@sleutelwoorden         Foutief
@usecase                UCS-BY.HG

Narrative: R1835 Einde van een huwelijk met een AH einde GP in NL

Scenario: Einde van een huwelijk met een AH einde GP in NL
          LT: EGNL01C210T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C210T10-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 329680201 en persoon 189148433 met relatie id 2000071 en betrokkenheid id 4000072

When voer een bijhouding uit EGNL01C210T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C210T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 329680201 wel als PARTNER betrokken bij een HUWELIJK
