Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Nietigverklaring huwelijk in Nederland

Scenario:   Registratie Geslachtsnaam voor 1 partner (Ingeschrevene) met niet NL nationaliteit
            LT: NHNL02C10T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-NHNL/NHNL02C10T40-001.xls
Given enkel initiele vulling uit bestand /LO3PL-NHNL/NHNL02C10T40-002.xls

When voer een bijhouding uit NHNL02C10T40a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 700423369 en persoon 311961897 met relatie id 43000104 en betrokkenheid id 43000104

Then is in de database de persoon met bsn 700423369 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 311961897 wel als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit NHNL02C10T40b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/NHNL/expected/NHNL02C10T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 700423369 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 311961897 niet als PARTNER betrokken bij een HUWELIJK


Then in kern heeft select voornamen, geslnaamstam from kern.pers where bsn = '700423369' de volgende gegevens:
| veld                 | waarde |
| voornamen            | Libby  |
| geslnaamstam         | Pietersen |

Then in kern heeft select stam, volgnr from kern.persgeslnaamcomp de volgende gegevens:
| veld                 | waarde |
| stam                 | Jansen |
| volgnr               | 1      |

Then lees persoon met anummer 1909013217 uit database en vergelijk met expected NHNL02C10T40-persoon1.xml













