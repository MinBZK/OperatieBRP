Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2644 Bij beëindigde staatloosheid is registratie nationaliteit uitsluitend toegestaan als de DAG daarvan op of na de DEG van staatloos ligt

Scenario:   beëindigde staatloosheid en registratie nationaliteit DAG eerder dan staatloos DEG
            LT: ERKE01C110T30

Given alle personen zijn verwijderd

!-- Init. vulling van het kind met een materieel beëindigde staatloosheid en een OUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C110T30-001.xls

!-- Maak van de vorige NOUWKIG een kind zodat er niet teveel ouders zijn.
Given de database is aangepast met: update kern.betr set rol=1 where pers=(select id from kern.pers where voornamen='verwijder')

!-- Init. vulling van de erkennende NOUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C110T30-002.xls

!-- Controleer dat staatloos materieel is beëindigd
Then in kern heeft select dateindegel,
                          waarde
                   from   kern.his_persindicatie
		   where  persindicatie in (
		                               select id
					       from   kern.persindicatie
					       where  srt in  (
					                          select id
								  from   kern.srtindicatie
							          where  id=7
							      )
					       and    pers in (
					                          select id
								  from   kern.pers
								  where  voornamen='Kind'
						              )
					   ) de volgende gegevens:
| veld        | waarde   |
| dateindegel | 20160102 |
| waarde      | true     |

Then heeft $NOUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Vader'
Then heeft $KIND_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Kind'

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Kind' and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Kind' and r.srt=3

!-- Erkenning van het kind
When voer een bijhouding uit ERKE01C110T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE01C110T30.xml voor expressie //brp:bhg_afsRegistreerErkenning_R