Meta:
@status             Klaar
@usecase            BV.0.ZA
@sleutelwoorden     Zoek Persoon op adresgegevens
@regels             R2376

Narrative:
Indien de personen in het zoekresultaat niet hetzelfde Persoon \ Adres.Identificatiecode nummeraanduiding hebben dan
mag het zoekresultaat niet groter zijn dan Dienst.Maximaal aantal zoekresultaten.

Scenario: 1.    Zoek personen op adres met gelijke Adres.Identificatiecode nummeraanduiding
                LT: R2376_LT01
                Verwacht resultaat:
                - Alle personen gevonden
                Uitwerking:
                - Aantal personen met gelijke Adres.Identificatiecode nummeraanduiding = 3
                - Aantal personen met ONgelijke Adres.Identificatiecode nummeraanduiding = 0
                - Dienst.Maximaal aantal zoekresultaten = 2

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Henk.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Ronald.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon Adres Max resultaten max 2' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2376_1.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 3 groepen 'persoon'

Scenario: 2.    Zoek personen op adres met gelijke Adres.Identificatiecode nummeraanduiding op Peildatum 01-01-2014
                LT: R2376_LT02
                Verwacht resultaat:
                - Alle personen gevonden
                Uitwerking:
                - Anne Bakker woont nu op nummeraanduiding: 0626010010016002
                - Anne Bakker woonde in 2014 op nummeraanduiding: 0626010010016001 en voldoet aan zoekcriteria
                - Jan woont nu sinds 2011 op nummeraanduiding: 0626010010016001
                - Aantal personen met gelijke Adres.Identificatiecode nummeraanduiding = 2 (door peildatum)
                - Dienst.Maximaal aantal zoekresultaten = 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Anne_Bakker.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon Adres Max resultaten max 1' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2376_2.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 2 groepen 'persoon'


Scenario: 3.    Zoek personen op adres met gelijke Adres.Identificatiecode nummeraanduiding met Materiele periode 01-01-2016
                LT: R2376_LT03
                Verwacht resultaat:
                - Foutmelding: R2376
                Want Zoek Persoon Adres met MaterielePeriode kijkt niet naar nummeraanduiding
                Uitwerking:
                - Anne Bakker woont nu op nummeraanduiding: 0626010010016002
                - Anne Bakker woonde in 2014 op nummeraanduiding: 0626010010016001 en voldoet dus aan zoekcriteria
                - Jan woont nu sinds 2011 op nummeraanduiding: 0626010010016001
                - Aantal personen met gelijke Adres.Identificatiecode nummeraanduiding = 2 (door Materiele Periode)
                - Dienst.Maximaal aantal zoekresultaten = 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Anne_Bakker.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon Adres Max resultaten max 1' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2376_3.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2289     | De zoekopdracht heeft teveel resultaten opgeleverd        |


Scenario: 4.    Zoek personen op adres met ONgelijke Adres.Identificatiecode nummeraanduiding
                LT: R2376_LT04
                Verwacht resultaat:
                - Foutmelding: R2376
                Uitwerking:
                - Aantal personen met gelijke Adres.Identificatiecode nummeraanduiding = 0
                - Aantal personen met ONgelijke Adres.Identificatiecode nummeraanduiding = 3
                - Dienst.Maximaal aantal zoekresultaten = 2

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Henk_afwijkendeBAG.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Ronald_afwijkendeBAG.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon Adres Max resultaten max 2' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2376_4.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2289     | De zoekopdracht heeft teveel resultaten opgeleverd        |


Scenario: 5.    Zoek personen op adres met ONgelijke Adres.Identificatiecode nummeraanduiding op Peildatum 01-01-2014
                LT: R2376_LT05
                Verwacht resultaat:
                - Foutmelding: R2376
                Uitwerking:
                - Aantal personen met gelijke Adres.Identificatiecode nummeraanduiding = 0
                - Aantal personen met ONgelijke Adres.Identificatiecode nummeraanduiding = 3
                - Dienst.Maximaal aantal zoekresultaten = 2

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Henk_afwijkendeBAG.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Ronald_afwijkendeBAG.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon Adres Max resultaten max 2' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2376_5.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2289     | De zoekopdracht heeft teveel resultaten opgeleverd        |

Scenario: 6.    Zoek personen op adres met ONgelijke Adres.Identificatiecode nummeraanduiding met Materiele periode 01-01-2016
                LT: R2376_LT06
                Verwacht resultaat:
                - Foutmelding: R2376
                Uitwerking:
                - Aantal personen met gelijke Adres.Identificatiecode nummeraanduiding = 0
                - Aantal personen met ONgelijke Adres.Identificatiecode nummeraanduiding = 3
                - Dienst.Maximaal aantal zoekresultaten = 2

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Henk_afwijkendeBAG.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Ronald_afwijkendeBAG.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon Adres Max resultaten max 2' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2376_6.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2289     | De zoekopdracht heeft teveel resultaten opgeleverd        |

Scenario: 7.    Zoek personen op adres met ONgelijke Adres.Identificatiecode nummeraanduiding
                LT: R2376_LT07
                Verwacht resultaat:
                - 2 Personen gevonden
                Uitwerking:
                - Aantal personen met gelijke Adres.Identificatiecode nummeraanduiding = 0
                - Aantal personen met ONgelijke Adres.Identificatiecode nummeraanduiding = 2
                - Dienst.Maximaal aantal zoekresultaten = 2

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Henk_afwijkendeBAG.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon Adres Max resultaten max 2' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2376_7.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 2 groepen 'persoon'

Scenario: 8.    Zoek personen op adres met ONgelijke Adres.Identificatiecode nummeraanduiding op Peildatum 01-01-2014
                LT: R2376_LT08
                Verwacht resultaat:
                - 2 Personen gevonden
                Uitwerking:
                - Aantal personen met gelijke Adres.Identificatiecode nummeraanduiding = 0
                - Aantal personen met ONgelijke Adres.Identificatiecode nummeraanduiding = 2
                - Dienst.Maximaal aantal zoekresultaten = 2

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Henk_afwijkendeBAG.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon Adres Max resultaten max 2' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2376_8.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 2 groepen 'persoon'


Scenario: 9.    Zoek personen op adres met ONgelijke Adres.Identificatiecode nummeraanduiding met Materiele periode 01-01-2016
                LT: R2376_LT09
                Verwacht resultaat:
                - 2 Personen gevonden
                Uitwerking:
                - Aantal personen met gelijke Adres.Identificatiecode nummeraanduiding = 0
                - Aantal personen met ONgelijke Adres.Identificatiecode nummeraanduiding = 2
                - Dienst.Maximaal aantal zoekresultaten = 2

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Henk_afwijkendeBAG.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon Adres Max resultaten max 2' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2376_9.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 2 groepen 'persoon'


Scenario: 10.   Zoek personen op adres met gelijke en Ongelijke Adres.Identificatiecode nummeraanduiding
                LT: R2376_LT10
                Verwacht resultaat:
                - Foutmelding R2289
                - Want totaal aantal personen 3, met niet allemaal dezelfde nummeraanduiding
                Uitwerking:
                - Aantal personen met gelijke Adres.Identificatiecode nummeraanduiding = 2
                - Aantal personen met ONgelijke Adres.Identificatiecode nummeraanduiding = 2
                - Dienst.Maximaal aantal zoekresultaten = 2

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Henk.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Ronald_afwijkendeBAG.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon Adres Max resultaten max 2' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2376_10.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2289     | De zoekopdracht heeft teveel resultaten opgeleverd        |


Scenario: 11.   Zoek personen op adres met gelijke en ONgelijke Adres.Identificatiecode nummeraanduiding op peilmoment 01-01-2014
                LT: R2376_LT11
                Verwacht resultaat:
                - Foutmelding: R2376
                Want niet alle 3 de personen hebben dezelfde nummeraanduiding
                Uitwerking:
                - Anne Bakker woonde in 2014 op nummeraanduiding: 0626010010016001  en voldoet aan zoekcriteria
                - Jan woont nu sinds 2011 op nummeraanduiding:    0626010010016001  en voldoet aan zoekcriteria
                - Aantal personen met gelijke Adres.Identificatiecode nummeraanduiding = 2 (door peildatum)
                - Henk met afwijkende BAG heeft nummeraanduiding: 0626010010016003  en voldoet aan zoekcriteria
                - Aantal personen met ONgelijke Adres.Identificatiecode nummeraanduiding = 2
                - Dienst.Maximaal aantal zoekresultaten = 2

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Anne_Bakker.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Henk_afwijkendeBAG.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon Adres Max resultaten max 2' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2376_11.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2289     | De zoekopdracht heeft teveel resultaten opgeleverd        |

Scenario: 12.   Zoek personen op adres met ONgelijke Adres.Identificatiecode nummeraanduiding met Materiele periode 01-01-2016
                LT: R2376_LT12
                Verwacht resultaat:
                - Foutmelding: R2376
                Want Zoek Persoon Adres met MaterielePeriode kijkt niet naar nummeraanduiding
                Uitwerking:
                - Anne Bakker woonde in 2014 op nummeraanduiding: 0626010010016001  en voldoet aan zoekcriteria
                - Jan woont nu sinds 2011 op nummeraanduiding:    0626010010016001  en voldoet aan zoekcriteria
                - Aantal personen met gelijke Adres.Identificatiecode nummeraanduiding = 2
                - Dienst.Maximaal aantal zoekresultaten = 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Anne_Bakker.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon Adres Max resultaten max 1' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2376_12.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2289     | De zoekopdracht heeft teveel resultaten opgeleverd        |


Scenario:   13. Extra test. Wat gebeurt er bij Zoekbereik is Leeg, maar peilmoment wel gevuld.
                Geen logisch testgeval voor vanuit de regels
                Uitwerking:
                - Zoekbereik Leeg
                - Peilmoment 01-01-2012
                Verwacht resultaat:
                - 2 personen gevonden, want de default bij zoekbereik Leeg = zoekbereik peilmoment
                - Daardoor wordt Anne ook gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Anne_Bakker.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon Adres Max resultaten max 4' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2376_13.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 2 groepen 'persoon'

Scenario:   14. Extra test. Wat gebeurt er bij Zoekbereik is Peilmoment, maar peilmoment wel LEEG.
                Geen logisch testgeval voor vanuit de regels
                Uitwerking:
                - Zoekbereik Peilmoment
                - Peilmoment LEEG
                Verwacht resultaat:
                - 1 persoon gevonden, want default LEEG = systeemdatum
                - Daardoor wordt Anne niet gevonden


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Anne_Bakker.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon Adres Max resultaten max 4' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2376_14.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'

Scenario:   15. Extra test. Wat gebeurt er bij Zoekbereik is Leeg, maar peilmoment wel gevuld.
                Geen logisch testgeval voor vanuit de regels
                Uitwerking:
                - Zoekbereik Materiele periode
                - Peilmoment LEEG
                Verwacht resultaat:
                - 2 persoon gevonden, want default LEEG = systeemdatum
                - Daardoor wordt Anne gevonden, want ooit op adres gewoond.


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Anne_Bakker.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon Adres Max resultaten max 4' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2376_15.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 2 groepen 'persoon'

Scenario: 16.    Zoek personen op adres met gelijke adres waarvoor geldt Adres.Identificatiecode nummeraanduiding = LEEG
                LT: R2376_LT13
                Verwacht resultaat:
                - Foutief
                Uitwerking:
                - Aantal personen met gelijke Adres.Identificatiecode nummeraanduiding = 3
                - Aantal personen met ONgelijke Adres.Identificatiecode nummeraanduiding = 0
                - Dienst.Maximaal aantal zoekresultaten = 2

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Henk_CodeNummerAanduidingLeeg.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Jan_CodeNummerAanduidingLeeg.xls
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonAdres/R2376_Ronald_CodeNummerAanduidingLeeg.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon Adres Max resultaten max 2' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2376_16.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2289     | De zoekopdracht heeft teveel resultaten opgeleverd        |
