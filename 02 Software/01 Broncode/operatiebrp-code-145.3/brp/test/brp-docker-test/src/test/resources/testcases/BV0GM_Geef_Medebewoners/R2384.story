Meta:
@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Geef Medebewoner van Persoon
@regels             R2384

Narrative:
Het resultaatbericht bevat alleen Persoon(en) die op

'Peilmoment materieel' (R2395) op exact hetzelfde adres wonen als
R2383 - Identificatiecriteria moet op peilmoment herleidbaar zijn tot minimaal één adres


Toelichting:
Voor een exact adres bij alle hoofdpersonen in het resultaatbericht geldt het volgende:
Persoon \ Adres.Identificatiecode nummeraanduiding is gevuld EN overal gelijk.

Scenario:   1.  Jan en Janna wonen op hetzelfde adres (alles is gelijk)
                LT: R2384_LT01
                Verwacht resultaat:
                - Jan en Janna, beide geleverd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/Janna.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2384_LT01.xml

Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 2 groepen 'persoon'
And is het antwoordbericht xsd-valide

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2384_scenario_1.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario:   2.  Jan met historie op adres woont niet meer op hetzelfde adres als Jan
                LT: R2384_LT02
                Verwacht resultaat:
                - Alleen Jan geleverd, want Jan met historie op adres is inmiddels verhuisd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan_met_historie_op_adres.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2384_LT02.xml

Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 1 groepen 'persoon'
And is het antwoordbericht xsd-valide

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2384_scenario_2.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario:   3.  Jan zonder BAG heeft zelfde adres als Jan, maar geen BAG
                LT: R2384_LT03, R2383_LT12
                Verwacht resultaat:
                - Alleen Jan geleverd, want Jan zonder BAG wordt weggefilterd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan_geen_BAG.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2384_LT03.xml

Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 1 groepen 'persoon'
And is het antwoordbericht xsd-valide

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2384_scenario_3.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario:   4.  Het opgegeven identiteitsnummer levert op het peilmoment 1 identificatiecode nummeraanduiding
            LT: R2384_LT04
            Uitwerking:
            Gezocht wordt op postcode 2252EB en peilmoment 2011-04-01
            2 personen op hetzelfde adres worden ingeladen Jan.xls met pc 2252EB, Jan_met_historie_op_adres en jan met historie op peilmoment 2011 pc 2252EB
            Verwacht resultaat:
            Verwerking Geslaagd
            2 personen in het antwoordbericht Jan en Jan_met_historie_op_adres.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan_met_historie_op_adres.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2384_LT04.xml

Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 2 groepen 'persoon'
And is het antwoordbericht xsd-valide

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2384_scenario_4.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario:   5. Het opgegeven identiteitsnummer levert op het peilmoment 1 identificatiecode nummeraanduiding
            LT: R2384_LT05
            Uitwerking:
            Gezocht wordt op postcode 2252EB en peilmoment 2014-04-01
            2 personen op hetzelfde adres worden ingeladen Jan.xls met pc 2252EB, Jan_met_historie_op_adres en jan met historie op peilmoment 2014 NIET pc 2252EB
            Verwacht resultaat:
            Verwerking Geslaagd
            Alleen Jan in bericht, want Jan met historie verhuisd naar nieuw adres

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan_met_historie_op_adres.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2384_LT05.xml

Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 1 groepen 'persoon'
And is het antwoordbericht xsd-valide

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2384_scenario_5.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R

Scenario:   6.  Jan zonder BAG heeft zelfde adres als Jan, maar geen BAG, Peilmoment gevuld
                LT: R2384_LT06
                Verwacht resultaat:
                - Alleen Jan geleverd, want Jan zonder BAG wordt weggefilterd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan_geen_BAG.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2384_LT06.xml

Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 1 groepen 'persoon'
And is het antwoordbericht xsd-valide

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2384_scenario_6.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R
