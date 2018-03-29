Meta:
@status             Klaar
@usecase            SV.0.GS
@sleutelwoorden     Stuf bg vertaal


Narrative:
stuf e2e test voor controle goedpad in e2e ctx. Ook controle op archivering.

Scenario:   1. Stuf verzoek voor vertaling

Given verzoek voor leveringsautorisatie 'indicatie npb volledig geconverteerd isfalse Stuf' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SVGS_Stuf/verzoek/StufVerzoekGoedPad.xml

Then heeft het antwoordbericht verwerking Foutief
