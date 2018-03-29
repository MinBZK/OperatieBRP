Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R2739 Waarschuwing Naamswijziging pseudo (ex)partner werkt niet door in Naamgebruik hoofdpersoon

Scenario:   Partner is geen pseudo-persoon
            LT: WPHW01C40T20

Given alle personen zijn verwijderd

!-- Vulling van de hoofdpersoon en partner
Given enkel initiele vulling uit bestand /LO3PL-WPHW/WPHW01C40T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-WPHW/WPHW01C40T20-002.xls

Then heeft $HOOFD_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Hoofdpersoon'
Then heeft $EX_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Ex'

Given de database is aangepast met: update kern.his_persnaamgebruik
                                    set    indnaamgebruikafgeleid=false
				    where  pers in (select id from kern.pers where voornamen='Hoofdpersoon')

!-- Voltrekking huwelijk
When voer een bijhouding uit WPHW01C40T20a.xml namens partij 'Gemeente BRP 1'

!-- Zet de technische sleutel van de relatie en de betrokkenheid van de ex-partner
Given pas laatste relatie van soort 1 aan tussen persoon 886841161 en persoon 723474953 met relatie id 50000001 en betrokkenheid id 50000001

!-- Zet de technische sleutel van de relatie en de betrokkenheid van de hoofdpersoon
Given pas laatste relatie van soort 1 aan tussen persoon 723474953 en persoon 886841161 met relatie id 50000002 en betrokkenheid id 50000002

!-- Ontbinding huwelijk
When voer een bijhouding uit WPHW01C40T20b.xml namens partij 'Gemeente BRP 1'

!-- Wijziging partnergegevens huwelijk
When voer een bijhouding uit WPHW01C40T20c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/WPHW/expected/WPHW01C40T20.xml voor expressie /