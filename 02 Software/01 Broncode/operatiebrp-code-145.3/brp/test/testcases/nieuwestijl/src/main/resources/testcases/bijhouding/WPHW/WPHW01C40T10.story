Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R2739 Waarschuwing Naamswijziging pseudo (ex)partner werkt niet door in Naamgebruik hoofdpersoon

Scenario:   Pseudo-partner wijzigt Samengestelde naam. Hoofdpersoon Naamgebruik afgeleid Nee en geen Eigen naamgebruik
            LT: WPHW01C40T10

Given alle personen zijn verwijderd

!-- Vulling van de hoofdpersoon met een ontbonden huwelijk
Given enkel initiele vulling uit bestand /LO3PL-WPHW/WPHW01C40T10-001.xls

Then heeft $HOOFD_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Hoofdpersoon'
Then heeft $EX_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Ex'

Given de database is aangepast met: update kern.his_persnaamgebruik
                                    set    indnaamgebruikafgeleid=false
				    where  pers in (select id from kern.pers where voornamen='Hoofdpersoon')

!-- Zet de technische sleutel van de relatie en de betrokkenheid van de ex-partner
Given pas laatste relatie van soort 1 aan tussen persoon 531276041 en persoon 727696713 met relatie id 50000001 en betrokkenheid id 50000001

!-- Zet de technische sleutel van de relatie en de betrokkenheid van de hoofdpersoon
Given pas laatste relatie van soort 1 aan tussen persoon 727696713 en persoon 531276041 met relatie id 50000002 en betrokkenheid id 50000002

!-- Wijziging partnergegevens huwelijk
When voer een bijhouding uit WPHW01C40T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/WPHW/expected/WPHW01C40T10.xml voor expressie /