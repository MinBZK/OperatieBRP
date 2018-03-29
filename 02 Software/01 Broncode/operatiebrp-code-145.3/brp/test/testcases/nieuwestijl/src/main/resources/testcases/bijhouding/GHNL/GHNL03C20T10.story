Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2150
@usecase                UCS-BY.HG

Narrative:
R2150 Gemeente aanvang moet verwijzen naar bestaand stamgegeven

Scenario: R2150 Gemeente aanvang bestaat niet in de stamtabel
          LT: GHNL03C20T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Tim.xls

When voer een GBA bijhouding uit GHNL03C20T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL03C20T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 979562697 niet als PARTNER betrokken bij een HUWELIJK
