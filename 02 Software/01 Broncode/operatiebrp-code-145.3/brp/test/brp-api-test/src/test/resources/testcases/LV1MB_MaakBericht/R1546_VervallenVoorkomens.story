Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R1546
@sleutelwoorden     Maak BRP bericht

Narrative:
Een vervallen groep (TijdstipVerval en ActieVerval zijn gevuld met een waarde) wordt alleen in het te leveren bericht opgenomen indien:

Het niet een MutatieBericht betreft
EN
er autorisatie bestaat voor de formele historie van de betreffende groep (de groep komt voor in Dienstbundel \ Groep met Dienstbundel \ Groep.Formele historie? = 'Ja' bij de Dienst waarvoor geleverd wordt).
OF
Het een Mutatiebericht betreft
EN
het een voorkomen betreft dat in de te leveren Administratieve handeling is komen te vervallen (ActieVerval is een Actie
bij de onderhanden Administratieve handeling).


Scenario:   1. Vervallen groep (adres) met Datumtijd verval en ActieVerval gevuld en een corresponderend voorkomen van Dienstbundel/Groep met Formele historie = Ja
                LT: R1546_LT04, R1546_LT05
                Actuele adres met Datumtijd verval en ActieVerval en geen ongewijzigde groepen
                Verwacht resultaat: Mutatiebericht met vulling:
                - 3 groepen adres
                - R1546_LT04 - Vervallen groep adres wordt opgenomen omdat deze in de administratieve handeling zit
                - R1546_LT05 - 3 x Adres groep wordt opgenomen deze is niet vervallen (toevoeging en wijziging)
                - GEEN andere groepen (behalve identificerende)
                - Geen groep naamgebruik

Given persoonsbeelden uit specials:specials/Anne_Bakker_GBA_Bijhouding_xls
Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
When voor persoon 595891305 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken
Then heeft het bericht 3 groep 'adres'
Then heeft het bericht 2 groep 'onderzoek'
Then heeft het bericht 0 groep 'naamgebruik'

Then is het synchronisatiebericht gelijk aan /testcases/LV1MB_MaakBericht/expecteds/R1546_VervallenVoorkomens_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   2. Vervallen groep (naamgebruik) met Datumtijd verval en ActieVerval gevuld en een corresponderend voorkomen van
                LT: R1546_LT01
                Dienstbundel Groep met Formele historie = Ja, volledig bericht
                Verwacht resultaat: Volledig bericht met vulling
                - 2 groepen naamgebruik (inclusief vervallen groep!)

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Anne_Bakker_GBA_Bijhouding_xls
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken
Then heeft het bericht 2 groep 'naamgebruik'
Then is het synchronisatiebericht gelijk aan /testcases/LV1MB_MaakBericht/expecteds/R1546_VervallenVoorkomens_scenario_2.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   3. Administratieve handeling anders dan naamgebruik zodat vervallen groep niet geleverd wordt
            LT: R1546_LT06
            Verwacht resultaat:
            - R1546_LT06 - Geen vervallen groepen zijn opgenomen die niet gemuteerd zijn geen groep naamgebruik

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1546_Anne_Bakker_Trouwt_xls
When voor persoon 595891305 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken
Then heeft het bericht 0 groep 'naamgebruik'
Then is het synchronisatiebericht gelijk aan /testcases/LV1MB_MaakBericht/expecteds/R1546_VervallenVoorkomens_scenario_3.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   4. Vervallen groep (adres) met Datumtijd verval en ActieVerval gevuld en een corresponderend voorkomen van
                LT: R1546_LT02
                Diensbundel Groep met Formele historie = Nee
                Verwacht resultaat: mutatielevering met vulling
                - 3 groepen adres (inclusief vervallen groep)

Given leveringsautorisatie uit autorisatie/R1546_Populatiebeperking_levering_op_basis_van_doelbinding_Geen_Formele_historie
Given persoonsbeelden uit specials:specials/Anne_Bakker_GBA_Bijhouding_xls
When voor persoon 595891305 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Popbep levering obv doelbinding WAAR geen formele historie is ontvangen en wordt bekeken
Then heeft het bericht 3 groep 'adres'
Then is het synchronisatiebericht gelijk aan /testcases/LV1MB_MaakBericht/expecteds/R1546_VervallenVoorkomens_scenario_4.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   5. Vervallen groep (naamgebruik) met Datumtijd verval en ActieVerval gevuld en een corresponderend voorkomen van
                LT: R1546_LT03
                Diensbundel Groep met Formele historie = Nee
                Verwacht resultaat: volledig bericht met vulling
                - 1 groepen naamgebruik (vervallen groep naamgebruik wordt niet opgenomen)

Given persoonsbeelden uit specials:MaakBericht/R1546_Anne_Bakker_Trouwt_xls
Given leveringsautorisatie uit autorisatie/R1546_Populatiebeperking_levering_op_basis_van_doelbinding_Geen_Formele_historie
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Popbep levering obv doelbinding WAAR geen formele historie'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Popbep levering obv doelbinding WAAR geen formele historie is ontvangen en wordt bekeken
Then heeft het bericht 1 groep 'naamgebruik'
Then is het synchronisatiebericht gelijk aan /testcases/LV1MB_MaakBericht/expecteds/R1546_VervallenVoorkomens_scenario_5.xml voor expressie //brp:lvg_synVerwerkPersoon

