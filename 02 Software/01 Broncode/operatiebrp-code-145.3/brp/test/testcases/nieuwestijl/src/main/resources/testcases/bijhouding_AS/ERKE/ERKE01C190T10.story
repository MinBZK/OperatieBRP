Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1732 Ouder mag geen kinderen hebben met identieke personalia

Scenario:   De sibling is een ingeschrevene en de personalia van de kinderen zijn gelijk
            LT: ERKE01C190T10

Given alle personen zijn verwijderd
!-- Vulling van de OUWKIG-1
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C190T10-001.xls
!-- Vulling van de OUWKIG-2
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C190T10-002.xls
!-- Vulling van de NOUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C190T10-003.xls

!-- Geboorte van de sibling OUWKIG-1 en NOUWKIG
When voer een bijhouding uit ERKE01C190T10a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- Geboorte van het kind  OUWKIG-2
When voer een bijhouding uit ERKE01C190T10b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('410114121') and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('410114121') and r.srt=3

!-- Erkenning van het kind door NOUWKIG
When voer een bijhouding uit ERKE01C190T10c.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE01C190T10.xml voor expressie /

