Meta:
@status             Klaar
@usecase            BV.0.ZA
@sleutelwoorden     Zoek Persoon op adresgegevens
@regels             R2286


Narrative:
Het zoekresultaat bevat alle personen die voldoen aan de in de zoekvraag opgegeven zoekcriteria
(voor zover die voldoen aan de overige verwerkingsregels).

Scenario:   1. gezocht op adres van jan, waardoor Anne niet gevonden wordt
                LT: R2286_LT04, R2286_LT05
                Verwacht resultaat: Alleen Jan gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/Anne_Bakker.xls

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2286.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 1 groepen 'persoon'
!-- BSN van Jan
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '606417801'