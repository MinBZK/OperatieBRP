Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1586 Burgerservicenummer mag niet reeds voorkomen in de BRP

Scenario: Kind Ingeschrevene met nadere bijhaard ongelijk aan F en bestaande niet-Ingeschrevene met nadere bijhaard is F
          LT: GBNL01C10T40

Given alle personen zijn verwijderd

!-- De ouders
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C10T40-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C10T40-002.xls

!-- Een niet-ingeschrevene persoon met bijhaard ongelijk aan F met een BSN dat wordt gebruikt voor het kind
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C10T40-003.xls

And de database is aangepast met: update kern.pers set srt=(select id from kern.srtpers where code='P') where bsn='460023913'

When voer een bijhouding uit GBNL01C10T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C10T40.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
