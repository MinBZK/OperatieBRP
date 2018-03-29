Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland: Nevenactie Registratie nationaliteit

Scenario:   Registratie nationaliteit met waarde gelijk aan NL waarbij Bijzondere verblijfsrechtelijke positie aanwezig is
            LT: GNOG04C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNOG/GNOG04C30T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNOG/GNOG04C30T10-002.xls

When voer een bijhouding uit GNOG04C30T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer bijzonder verblijfsrechtelijke positie
Then in kern heeft select count(1)
                   from kern.srtindicatie si
                   left outer join kern.persindicatie pi on pi.srt = si.id
                   left outer join kern.his_persindicatie hpi on hpi.persindicatie = pi.id
                   left join kern.actie ainh on ainh.id = hpi.actieinh
                   left join kern.actie av on av.id = hpi.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left outer join kern.pers pe on pe.id = pi.pers
                   where sainh.naam ='Registratie geborene' and pe.voornamen = 'Hendrik Jan' de volgende gegevens:
| veld                      | waarde    |
| count                     | 0         |

!-- Controleer vastlegging in de database van de nationaliteit
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, n.naam as nationaliteit, pn.rdnverk, hpn.dataanvgel, hpn.dateindegel
                   from kern.persnation pn
                   left join kern.his_persnation hpn on hpn.persnation = pn.id
                   left join kern.actie ainh on ainh.id = hpn.actieinh
                   left join kern.actie av on av.id = hpn.actieverval
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left outer join kern.pers pe on pe.id = pn.pers
                   left join kern.nation n on n.id = pn.nation
                   where sainh.naam ='Registratie nationaliteit' and pe.voornamen = 'Hendrik Jan' de volgende gegevens:
| veld                      | waarde                            |
| actieInhoud               | Registratie nationaliteit         |
| actieVerval               | NULL                              |
| nationaliteit             | Nederlandse                       |
| rdnverk                   | 1                                 |
| dataanvgel                | 20161231                          |
| dateindegel               | NULL                              |