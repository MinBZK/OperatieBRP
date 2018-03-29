Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland: Nevenactie Registratie staatloos

Scenario:   Registratie van een geboorte in Nederland die staatloos is zonder bronnen
            LT: GBNL04C50T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL04C30T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL04C30T20-002.xls

When voer een bijhouding uit GBNL04C50T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer vastlegging staatloos
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, si.naam, pi.waarde, pi.indag
                   from kern.srtindicatie si
                   left outer join kern.persindicatie pi on pi.srt = si.id
                   left outer join kern.his_persindicatie hpi on hpi.persindicatie = pi.id
                   left join kern.actie ainh on ainh.id = hpi.actieinh
                   left join kern.actie av on av.id = hpi.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left outer join kern.pers pe on pe.id = pi.pers
                   where sainh.naam ='Registratie staatloos' and pe.voornamen = 'Hendrik Jan' de volgende gegevens:
| veld                      | waarde                                        |
| actieInhoud               | Registratie staatloos                         |
| actieVerval               | NULL                                          |
| naam                      | Staatloos?                                    |
| waarde                    | true                                          |
| indag                     | true                                          |