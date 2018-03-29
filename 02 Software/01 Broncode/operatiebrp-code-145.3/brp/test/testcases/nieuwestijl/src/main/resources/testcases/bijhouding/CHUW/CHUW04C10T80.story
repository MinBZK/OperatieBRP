Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Verwerking Correctie Huwelijk

Scenario:   GBA-persoon leidt tot notificatie met toelichting teruggestuurd
            LT: CHUW04C10T80

Given alle personen zijn verwijderd
!-- PL met huwelijk
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW04C10T80.xls

Given pas laatste relatie van soort 1 aan tussen persoon 124957481 en persoon 532317385 met relatie id 30010001 en betrokkenheid id 30010001
And de database is aangepast met: update kern.his_relatie set id = 9999 where id = (select hr.id from kern.his_relatie hr join kern.relatie r on r.id = hr.relatie where r.srt = 1 and hr.dataanv = 20160510)

Then is in de database de persoon met bsn 124957481 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 532317385 wel als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit CHUW04C10T80.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CHUW/expected/CHUW04C10T80.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R

Then staat er 1 notificatiebericht voor bijhouders op de queue

!-- Controleer of de IST van het huwelijk is verwijderd
Then in kern heeft select count(*) from ist.stapel where categorie='05' de volgende gegevens:
| veld  | waarde |
| count | 0      |

Then in kern heeft select count(*) from ist.stapelrelatie where relatie=9999 de volgende gegevens:
| veld  | waarde |
| count | 0      |

Then in kern heeft select count(*) from ist.stapelvoorkomen where srtdoc=3 de volgende gegevens:
| veld  | waarde |
| count | 0      |

!-- Controleer of verconv is ongewijzigd
Then in kern heeft select count(*) from verconv.lo3voorkomen where lo3categorie='05' de volgende gegevens:
| veld  | waarde |
| count | 1      |
