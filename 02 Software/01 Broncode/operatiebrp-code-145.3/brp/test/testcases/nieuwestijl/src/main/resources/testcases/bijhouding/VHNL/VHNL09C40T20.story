Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Afleiding datumAanvangGeldigheid van de actie

Scenario:   datumAanvangGeldigheid verschillend van relatie datumAanvang bij Deels verwerkt. Controle van de afleiding van datumAanvangGeldigheid in groepen.
            LT: VHNL09C40T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL09C40T20-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL09C40T20-Piet.xls

When voer een bijhouding uit VHNL09C40T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL09C40T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 187234401 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 577656041 wel als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select dataanvgel from kern.his_persgeslachtsaand g join kern.pers p on g.pers=p.id and voornamen = 'Piet' and p.srt=2 de volgende gegevens:
| veld       | waarde   |
| dataanvgel | 20160807 |

Then in kern heeft select dataanvgel from kern.his_perssamengesteldenaam s join kern.pers p on s.pers=p.id and s.voornamen = 'Piet' and p.srt=2 de volgende gegevens:
| veld       | waarde   |
| dataanvgel | 20160807 |

Then in kern heeft select dataanvgel from kern.his_persids i join kern.pers p on i.pers=p.id where voornamen = 'Piet' and p.srt=2 de volgende gegevens:
| veld       | waarde   |
| dataanvgel | 20160807 |