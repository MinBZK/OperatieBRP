Meta:
@status             Klaar
@usecase            LV.1.AL
@regels             R2258
@sleutelwoorden     Autorisatie levering


Narrative:
Als beheerder wil ik dat alleen dienstbundels waarvoor geldt dat dienstbundel.indnaderepopbeperkingvolconv <> FALSE
worden geevalueerd tbv levering.


Scenario:   1. Bevraging op dienst in dienstbundel met onvolledig geconverteerde expressie
            LT:

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'indicatie npb volledig conv geefdetpers isfalse' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV0GD_Geef_Details_Persoon/verzoek/2._Geef_Details_Persoon_Story_1.1.xml


Then heeft het antwoordbericht verwerking Foutief
