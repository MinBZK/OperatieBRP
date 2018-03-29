Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland: Nevenactie Registratie nationaliteit

Scenario:   Registratie met meerdere nationaliteiten en bronnen
            LT: GBNL04C30T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL04C30T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL04C30T30-002.xls

When voer een bijhouding uit GBNL04C30T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

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
                   where sainh.naam ='Registratie nationaliteit' and pe.voornamen = 'Hendrik Jan' order by nationaliteit de volgende gegevens:
| veld                      | waarde                            |
| actieInhoud               | Registratie nationaliteit         |
| actieVerval               | NULL                              |
| nationaliteit             | Slowaakse                         |
| rdnverk                   | NULL                              |
| dataanvgel                | 20161231                          |
| dateindegel               | NULL                              |
----
| actieInhoud               | Registratie nationaliteit         |
| actieVerval               | NULL                              |
| nationaliteit             | Tsjechische                       |
| rdnverk                   | NULL                              |
| dataanvgel                | 20161231                          |
| dateindegel               | NULL                              |

!-- Controleer vastlegging actiebron
Then in kern heeft select sainh.naam as actie, sd.naam as document
                   from kern.actiebron ab
                   left join kern.actie ainh on ainh.id = ab.actie
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.doc d on d.id = ab.doc
                   left join kern.srtdoc sd on sd.id = d.srt
                   where sainh.naam in ('Registratie nationaliteit', 'Registratie geborene') order by document de volgende gegevens:
| veld                      | waarde                                        |
| actie                     | Registratie geborene                          |
| document                  | Geboorteakte                                  |
----
| actie                     | Registratie nationaliteit                     |
| document                  | Overig                                        |
----
| actie                     | Registratie nationaliteit                     |
| document                  | Verklaring onder eed of belofte               |