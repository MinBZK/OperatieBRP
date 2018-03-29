Meta:
@auteur                 fuman
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative: Registratie Geslachtsnaam voor 1 partner (Ingeschrevene) met niet NL nationaliteit + Predicaat

Scenario: Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Ingeschrevene-Ingezetene, NL Nat) gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief, predicaat
          LT: VHNL05C20T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL05C20T10-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T10-Piet.xls

When voer een bijhouding uit VHNL05C20T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL05C20T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 159247913 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 422531881 wel als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select voornamen, geslnaamstam, predicaat, voorvoegsel, scheidingsteken from kern.pers where bsn = '422531881' de volgende gegevens:
| veld                 | waarde |
| voornamen            | Libby Marie  |
| geslnaamstam         | Jansen |
| predicaat            | 1      |
| voorvoegsel          | s      |
| scheidingsteken      | '      |

Then in kern heeft select stam, volgnr from kern.persgeslnaamcomp de volgende gegevens:
| veld                 | waarde |
| stam                 | Jansen |
| volgnr               | 1      |
| stam                 | Jansen |
| volgnr               | 1      |

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL05C20T10-persoon1.xml
Then lees persoon met anummer 5398948626 uit database en vergelijk met expected VHNL05C20T10-persoon2.xml
