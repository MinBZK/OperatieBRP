Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland: Nevenactie Registratie verstrekkingsbeperking

Scenario:   Registratie volledige verstrekkingsbeperking
            LT: GNOG04C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNOG/GNOG04C40T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNOG/GNOG04C40T10-002.xls

When voer een bijhouding uit GNOG04C40T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer Volledige verstrekkingbeperking indicatie
Then in kern heeft select si.naam, pi.waarde, sa.naam actieinh, pe.voornamen
                   from kern.srtindicatie si
                   left outer join kern.persindicatie pi on pi.srt = si.id
                   left outer join kern.his_persindicatie hpi on hpi.persindicatie = pi.id
                   left outer join kern.actie a on a.id = hpi.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   left outer join kern.pers pe on pe.id = pi.pers
                   where sa.naam ='Registratie verstrekkingsbeperking' and pe.voornamen = 'Hendrik Jan' de volgende gegevens:
| veld                      | waarde                                |
| naam                      | Volledige verstrekkingsbeperking?     |
| waarde                    | true                                  |
| actieinh                  | Registratie verstrekkingsbeperking    |
| voornamen                 | Hendrik Jan                           |


