Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Registratie aanvang huwelijk in NL tussen I-I en Onbekend met meegeven persoonsgegevens

Scenario:   datumAanvangGeldigheid verschillend van relatie datumAanvang. Controle van de afleiding van datumAanvangGeldigheid in groepen.
            LT: VHNL09C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL09C40T10.xls

When voer een bijhouding uit VHNL09C40T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL09C40T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 144067201 wel als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select dataanvgel from kern.his_persgeslachtsaand g join kern.pers p on g.pers=p.id where voornamen = 'Pieter' de volgende gegevens:
| veld       | waarde   |
| dataanvgel | 20160807 |

Then in kern heeft select dataanvgel from kern.his_perssamengesteldenaam where voornamen = 'Pieter' de volgende gegevens:
| veld       | waarde   |
| dataanvgel | 20160807 |

Then in kern heeft select dataanvgel from kern.his_persids i join kern.pers p on i.pers=p.id where voornamen = 'Pieter' de volgende gegevens:
| veld       | waarde   |
| dataanvgel | 20160807 |
