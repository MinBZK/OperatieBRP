Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1748 Erkend kind jonger dan 7 jaar moet Nederlander worden indien erkenner Nederlander is

Scenario: 1. DB init
          preconditie

Given voer enkele update uit: update kern.partij set datingang='20000101' where code='507013'
Given voer enkele update uit: update kern.gem set dataanvgel='20000101' where code='7112'

Given maak bijhouding caches leeg

Scenario:   2. Kind van 6 zonder NL nationaliteit heeft NL erkenner
            LT: ERKE01C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C40T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C40T10-002.xls

Then heeft $OUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Moeder'
Then heeft $NOUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Vader'

When voer een bijhouding uit ERKE01C40T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $KIND_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Kind'

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen = 'Kind' and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen = 'Kind' and r.srt=3

When voer een bijhouding uit ERKE01C40T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE01C40T10.xml voor expressie //brp:bhg_afsRegistreerErkenning_R

Scenario: 3. DB reset
          postconditie

Given de database is aangepast met: update kern.partij set datingang='20160101' where code='507013'
Given voer enkele update uit: update kern.gem set dataanvgel='20160101' where code='7112'

Given maak bijhouding caches leeg