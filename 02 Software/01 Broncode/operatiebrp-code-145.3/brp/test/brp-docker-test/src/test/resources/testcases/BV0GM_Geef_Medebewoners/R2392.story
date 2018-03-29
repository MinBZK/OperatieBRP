Meta:
@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Geef Medebewoner van Persoon
@regels             R2392


Narrative:
Indien de verstrekking de Soort dienst Geef medebewoners van persoon betreft en Bericht.Identificatiecriteria is gevuld met een of meer van de volgende adresgegevens:

Bericht.Gemeente code,
Bericht.Afgekorte naam openbare ruimte,
Bericht.Huisnummer,
Bericht.Huisletter,
Bericht.Huisnummertoevoeging,
Bericht.Locatie ten opzichte van adres,
Bericht.Postcode,
Bericht.Woonplaatsnaam
dan
mogen de identificatiecriteria op 'Peilmoment materieel' (R2395) niet herleidbaar zijn tot meer dan één Identificatiecode nummeraanduiding.
Toelichting: Om aan de afnemende partij een specifieke foutmelding te tonen ingeval de identificatiecriteria niet specifiek genoeg zijn
voor de BRP om dit tot één adres te herleiden, is deze regel opgenomen.


Scenario:   1.  Het opgegeven identiteitsnummer levert op het peilmoment 1 identificatiecode nummeraanduiding
            LT: R2392_LT01
            Uitwerking:
            Gezocht wordt op postcode 2252EB en peilmoment 2011-04-01
            2 personen op hetzelfde adres worden ingeladen Jan.xls met pc 2252EB, Jan_met_historie_op_adres en jan met historie op peilmoment actueel pc 2252EB
            1 persoon Anne_met_historie wordt ingeladen heeft actuele pc en bag id als Jan_met_historie maar verschilt op het peilmoment van adres / bag
            Verwacht resultaat:
            Verwerking Geslaagd
            2 personen in het antwoordbericht Jan en Jan_met_historie_op_adres.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan_met_historie_op_adres.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2393_LT01.xml

Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 2 groepen 'persoon'
And is het antwoordbericht xsd-valide

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2392_scenario_1.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario:   2.  Het opgegeven identiteitsnummer levert op het peilmoment 1 identificatiecode nummeraanduiding
            LT: R2392_LT02
            Uitwerking:
            Gezocht wordt op postcode 2252EB en peilmoment 201-04-01
            2 personen op hetzelfde adres worden ingeladen Jan.xls met pc 2252EB, Jan_met_historie_op_adres en jan met historie op peilmoment actueel pc 2252EB
            1 persoon Jan_afwijkend_BAG wordt ingeladen heeft zelfde pc maar ANDER bag id
            Verwacht resultaat:
            Verwerking Foutief
            R2392 - Adresidentificatie is herleidbaar tot meer dan één adres

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_afwijkende_BAG.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan_met_historie_op_adres.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2393_LT01.xml

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2392     | Adresidentificatie is herleidbaar tot meer dan één adres   |

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2392_scenario_2.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario:   3.  Het opgegeven identiteitsnummer levert op het peilmoment 1 identificatiecode nummeraanduiding
            LT: R2392_LT01
            Uitwerking:
            Gezocht wordt op postcode 2252EB en peilmoment 2014-04-01
            2 personen op hetzelfde adres worden ingeladen Jan.xls met pc 2252EB, Jan_met_historie_op_adres en jan met historie op peilmoment actueel pc 2252EB
            1 persoon Jan_geen_BAG wordt ingeladen heeft zelfde pc maar GEEN bag id
            Verwacht resultaat:
            Verwerking Geslaagd
            Persoon zonder BAG zou uit het resultaat gefilterd moeten worden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_geen_BAG.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan_met_historie_op_adres.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2393_LT01.xml

Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 2 groepen 'persoon'
And is het antwoordbericht xsd-valide

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2392_scenario_3.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario:   4.  Het opgegeven identiteitsnummer levert 1 identificatiecode nummeraanduiding
            LT: R2392_LT03
            Uitwerking:
            Gezocht wordt op postcode 2252EB en geen peilmoment
            Jan.xls - heeft actueel adres met pc 2252EB = BAGID 0626200010016001
            Jan_geb_dat_1966.xls - heeft actueel adres met pc 2252EB = BAGID 0626200010016001
            Jan_met_historie_op_adres.xls - geen actueel adres met pc 2252EB - wordt niet geleverd
            Verwacht resultaat:
            Verwerking Geslaagd
            2 personen geleverd Jan en Jan met geb dd 1966
            Jan met historie wordt niet geleverd.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_geb_dat_1966.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan_met_historie_op_adres.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2393_LT03.xml

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 2 groepen 'persoon'

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2392_scenario_4.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario:   5.  Het opgegeven identiteitsnummer levert meer dan 1 identificatiecode nummeraanduiding
            LT: R2392_LT04
            Uitwerking:
            Gezocht wordt op postcode 2252EB en geen peilmoment
            Jan.xls - heeft actueel adres met pc 2252EB = BAGID 0626200010016001
            Jan_met_historie_op_adres.xls - geen actueel adres met pc 2252EB
            Jan_afwijkende_BAG.xls - heeft actueel adres met pc 2252EB = BAGID 0626200010016002
            Verwacht resultaat:
            Verwerking Foutief
            R2393 - Adresidentificatie is herleidbaar tot meer dan één adres

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_afwijkende_BAG.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan_met_historie_op_adres.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2393_LT03.xml

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2392     | Adresidentificatie is herleidbaar tot meer dan één adres   |

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2392_scenario_5.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R
