Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland met erkenning na geboortedatum: hoofdactie Registratie geborene

Scenario:   Verwerking van de nevenactie Beeindiging nationaliteit met redeneindecode
            LT: GNNG04C90T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C90T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG04C90T20-002.xls

When voer een bijhouding uit GNNG04C90T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer het einde van de nationaliteit
Then in kern heeft select (
                              select naam
			      from   kern.nation
			      where  id in (
			                       select nation
					       from   kern.persnation
					       where  id in (
					                        select persnation
								from   kern.his_persnation
								where  id=pn.id
						            )
					   )
			  ) as nationaliteit,
                          sa.naam as soortActie,
                          case
                              when pn.tsverval is not null then
			          'gevuld'
                              else
			          'leeg'
                          end as tsverval,
                          (
			      select naam
			      from   kern.srtactie
			      where  id in (
			                       select srt
					       from   kern.actie
					       where  id=pn.actieverval
					    )
		          ) as actieVerval,
                          pn.dataanvgel,
                          pn.dateindegel,
                          (
			      select naam
			      from   kern.srtactie
			      where  id in (
			                       select srt
					       from   kern.actie
					       where  id=pn.actieaanpgel
					   )
			  ) as actieAanpassingGeldigheid,
			  (
			      select code
			      from   kern.rdnverliesnlnation
			      where  id=pn.rdnverlies
			  ) as redenverlies
                   from kern.his_persnation pn
                   join kern.actie a
                   on   pn.actieinh=a.id
                   join kern.srtactie sa
                   on   a.srt=sa.id
		   where sa.naam='Registratie nationaliteit'
		   order by tsverval de volgende gegevens:
| veld                      | waarde                    |
| nationaliteit             | Nederlandse               |
| soortActie                | Registratie nationaliteit |
| tsverval                  | gevuld                    |
| actieVerval               | Beëindiging nationaliteit |
| dataanvgel                | 20160101                  |
| dateindegel               | NULL                      |
| actieAanpassingGeldigheid | NULL                      |
| redenverlies              | NULL                      |
----
| nationaliteit             | Nederlandse               |
| soortActie                | Registratie nationaliteit |
| tsverval                  | gevuld                    |
| actieVerval               | Registratie nationaliteit |
| dataanvgel                | 20160101                  |
| dateindegel               | 20160102                  |
| actieAanpassingGeldigheid | Beëindiging nationaliteit |
| redenverlies              | 034                       |
----
| nationaliteit             | Nederlandse               |
| soortActie                | Registratie nationaliteit |
| tsverval                  | leeg                      |
| actieVerval               | NULL                      |
| dataanvgel                | 20160102                  |
| dateindegel               | NULL                      |
| actieAanpassingGeldigheid | NULL                      |
| redenverlies              | NULL                      |