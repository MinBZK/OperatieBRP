Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2164
@usecase                UCS-BY.HG

Narrative:
R2164 Gemeente geboorte moet verwijzen naar bestaand stamgegeven

Scenario: R2164 Gemeente geboorte verwijst niet naar bestaand stamgegeven
          LT: GHNL03C30T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL03C30T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL03C30T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
