Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland met erkenning na geboortedatum: nevenactie Registratie nationaliteit (perspectief datum van geboorte)

Scenario:   Registratie nationaliteit via met Bijzondere Verblijfsrechtelijke Postie PROBAS
            LT: GNNG04C30T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C30T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C30T20-002.xls

When voer een bijhouding uit GNNG04C30T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer verantwoordingsgegevens van de ActieBron
Then in kern heeft select    count(1),
                             ab.doc
                   from      kern.actiebron ab
		   join      kern.actie ainh
		   on        ainh.id = ab.actie
		   left join kern.srtactie sainh
		   on        ainh.srt = sainh.id
                   where     sainh.naam in ('Registratie geborene')
		   group by  ab.doc de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

!-- Controleer de verantwoording van de bijzondere verblijfsrechtelijke positie van de NOUWKIG
!-- Voor het kind is deze link niet gelegd omdat het alleen voor GBA van toepassing is.
Then in kern heeft select sa.naam,
                          d.oms
		   from   kern.actiebron ab
		   join   kern.actie a
		   on     ab.actie=a.id
		   join   kern.srtactie sa
		   on     sa.id=a.srt
		   join   kern.doc d
		   on     d.id=ab.doc
		   where  d.oms='PROBAS' de volgende gegevens:
| veld | waarde        |
| naam | Conversie GBA |
| oms  | PROBAS        |

!-- Controleer de van NOUWKIG verkregen bijzondere verblijfsrechtelijke positie (PROBAS) voor het kind
Then in kern heeft select count(1)
                   from   kern.his_persindicatie
		   where  tsverval is null
		   and    actieverval is null
		   and    waarde=true
		   and    persindicatie in (
		                               select id
					       from   kern.persindicatie
					       where  waarde=true
					       and    srt in (
					                         select id
								 from   kern.srtindicatie
								 where  naam='Bijzondere verblijfsrechtelijke positie?'
							     )
					       and    pers in (
					                          select id
								  from   kern.pers
								  where  voornamen='Jan'
					                      )
					   ) de volgende gegevens:
| veld  | waarde |
| count | 1      |
