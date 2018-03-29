Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1790 Melding dat reisdocument ongeldig wordt na wijziging personalia of verlies Nederlandse nationaliteit

Scenario:   Persoon.Voornamen en geen Reisdocument
            LT: ERKE01C50T40

Given alle personen zijn verwijderd

!-- Init. vulling van het kind met een OUWKIG en reisdocument met indicatie inhouding
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C50T40-001.xls

!-- Init. vulling van de NOUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C50T40-002.xls

Then heeft $NOUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Vader'
Then heeft $KIND_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Kind'

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Kind' and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Kind' and r.srt=3

!-- Erkenning van het kind met nevenactie registratie geslachtsnaam voornaam
When voer een bijhouding uit ERKE01C50T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE01C50T40.xml voor expressie //brp:bhg_afsRegistreerErkenning_R