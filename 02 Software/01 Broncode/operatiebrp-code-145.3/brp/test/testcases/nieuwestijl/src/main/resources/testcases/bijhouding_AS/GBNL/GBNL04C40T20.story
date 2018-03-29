Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland: Nevenactie Registratie verstrekkingsbeperking

Scenario:   Registratie specifieke verstrekkingsbeperking met partij
            LT: GBNL04C40T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL04C40T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL04C40T20-002.xls

When voer een bijhouding uit GBNL04C40T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer verstrekkingbeperking Kind
Then in kern heeft select vb.omsderde, vb. gemverordening, p.voornamen, pa.naam as naampartij, sa.naam as actieinh
                   from kern.persverstrbeperking vb
                   left outer join kern.his_persverstrbeperking hvb on hvb.persverstrbeperking = vb.id
                   left outer join kern.pers p on p.id = vb.pers
                   left outer join kern.actie a on a.id = hvb.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   left outer join kern.partij pa on pa.id = vb.partij
                   where sa.naam = 'Registratie verstrekkingsbeperking' de volgende gegevens:
| veld                      | waarde                                        |
| omsderde                  | NULL                                          |
| gemverordening            | NULL                                          |
| voornamen                 | Hendrik Jan                                   |
| naampartij                | Stichting Interkerkelijke Ledenadministratie  |
| actieinh                  | Registratie verstrekkingsbeperking            |


