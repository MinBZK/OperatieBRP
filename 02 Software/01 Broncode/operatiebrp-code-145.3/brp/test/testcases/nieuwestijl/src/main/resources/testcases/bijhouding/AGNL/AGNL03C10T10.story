Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Registratie aangaan GP in NL tussen I-I en Pseudo-persoon

Scenario: datumAanvangGeldigheid van de hoofdactie verschilt van de datumAanvang van de relatie. Test de afleiding van DAG.
          LT: AGNL03C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL03C10T10-Libby.xls

When voer een bijhouding uit AGNL03C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL03C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 720983241 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 818468233 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then in kern heeft select dataanvgel from kern.his_persgeslachtsaand g join kern.pers p on g.pers=p.id where voornamen = 'Pieter' de volgende gegevens:
| veld       | waarde   |
| dataanvgel | 20160509 |

Then in kern heeft select dataanvgel from kern.his_perssamengesteldenaam where voornamen = 'Pieter' de volgende gegevens:
| veld       | waarde   |
| dataanvgel | 20160509 |

Then in kern heeft select dataanvgel from kern.his_persids i join kern.pers p on i.pers=p.id where voornamen = 'Pieter' de volgende gegevens:
| veld       | waarde   |
| dataanvgel | 20160509 |
