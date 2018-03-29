Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative: R2738 Waarschuwing: Naamswijziging hoofdpersoon werkt niet door bij (ex)partner

Scenario:   Waarschuwing als als naamswijziging niet doorwerkt bij ex-partner
            LT: VHNL01C450T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C450T10-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C450T10-Piet.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C450T10-Jane.xls

Then heeft $LIBBY_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Libby'
Then heeft $PIET_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Piet'
Then heeft $JANE_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Jane'

Given de database is aangepast met: update kern.his_perssamengesteldenaam
                                    set    indafgeleid=false
				    where  pers in (select id from kern.pers where voornamen='Libby' or voornamen='Piet')

When voer een bijhouding uit VHNL01C450T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 650534153 en persoon 600485353 met relatie id 30030002 en betrokkenheid id 30030002

When voer een bijhouding uit VHNL01C450T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

When voer een bijhouding uit VHNL01C450T10c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C450T10.xml voor expressie /