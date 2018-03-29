Meta:
@status     Klaar
@usecase    UCS-BY.0.HGP

Narrative:  R2737 Waarschuwing dat de naamswijziging niet doorwerkt in het Naamgebruik

Scenario: Geslachtsnaam wordt gewijzigd. Samengestelde naam afgeleid Nee en Naamgebruik afgeleid nee.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C460T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C460T30-002.xls

Then heeft $LIBBY_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Libby'
Then heeft $PIET_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Piet'

Given de database is aangepast met: update kern.his_perssamengesteldenaam
                                    set    indafgeleid=false
				    where  pers in (select id from kern.pers where voornamen='Libby' or voornamen='Piet')

Given de database is aangepast met: update kern.his_persnaamgebruik
                                    set    indnaamgebruikafgeleid=false
                                    where  pers in (select id from kern.pers where voornamen='Libby' or voornamen='Piet')

!-- Samengestelde naam afgeleid staat op Nee. Naamgebruik afgeleid op Nee.
When voer een bijhouding uit VHNL01C460T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C460T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R