Meta:
@status             Klaar
@usecase            BV.0.ZA
@sleutelwoorden     Zoek Persoon op adres:
@regels             R2289

Narrative:
Het uiteindelijk aantal resulterende persoonslijsten uit een zoekvraag mag niet groter zijn dan Dienst.Maximaal aantal zoekresultaten
Deze regel geldt niet voor de dienst Zoek persoon op adres.


Scenario: 1.    Zoek Persoon op adres met aantal resultaten > Max aantal zoek resultaten.
                LT: R2289_LT05, R1270_LT05
                Uitwerking: Er worden meer dan 12 personen gevonden, het maximum aantal tussenresultaten = 1
                Verwacht resultaat: Geslaagd
!-- Inladen van een leveringsautorisatie met Dienst.maximum aantal zoek resultaten = 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kim.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kanye.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Khloe.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kourtney.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kendall.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Caitlyn.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kris.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie2.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie3.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie4.xls


Given verzoek voor leveringsautorisatie 'Zoek Persoon op adres Max resultaten 1' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2289.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 9 groepen 'persoon'

!-- R1270_LT05
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| bsn                   | 349554985                            |

!-- R1270_LT05
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| bsn                   | 590796674                            |

!-- R1270_LT05
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| bsn                   | 606417801                            |

!-- R1270_LT05
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| bsn                   | 689274889                            |

!-- R1270_LT05
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| bsn                   | 692507401                            |

!-- R1270_LT05
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| bsn                   | 695179913                            |

!-- R1270_LT05
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| bsn                   | 787018697                            |

!-- R1270_LT05
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| bsn                   | 892082057                            |

!-- R1270_LT05
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| bsn                   | 975243913                            |