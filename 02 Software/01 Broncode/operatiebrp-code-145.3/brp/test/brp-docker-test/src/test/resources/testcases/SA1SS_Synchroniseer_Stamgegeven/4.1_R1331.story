Meta:
@status             Klaar
@usecase            SA.0.SS
@regels             R1331
@sleutelwoorden     Synchroniseer Stamgegeven


Narrative:
Bericht.Stamgegeven moet voorkomen in Element onder de volgende voorwaarden:
het stamgegeven moet een objecttype zijn (Element.Soort = 1)
EN
het stamgegeven mag geen dynamische tabel zijn (Element.Soort inhoud <> 'D')
EN
het stamgegeven moet een te leveren tabel zijn (Element.In bericht = 'TRUE')

Scenario:   1.  Stamtabel opvragen met Element.soort anders dan 1 EN Elementsoort Inhoud anders dan D EN Element.In bericht = TRUE
                LT: R1331_LT02
                Verwacht resultaat:
                - Foutmelding: De opgegeven stamtabel bestaat niet.
                Uitwerking: Persoon
                - Elementsoort = 1
                - Elementsoort inhoud = LEEG
                - Element.In bericht = TRUE
!-- Meta:
!-- @status     Backlog !-- TEAMBRP-4544 alfa is akkoord dat we in dit geval een andere foutmelding teruggeven

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/SA1SS_Synchroniseer_Stamgegeven/Requests/4.1_R1331_scenario_1.xml

Then is het antwoordbericht een soapfault

Scenario:   2.  Stamtabel opvragen met Element.soort = 1 EN Elementsoort Inhoud = D EN Element.In bericht = TRUE
                LT: R1331_LT03
                Verwacht resultaat:
                - Foutmelding: De opgegeven stamtabel bestaat niet.
                Uitwerking: Persoon
                - Elementsoort = 1
                - Elementsoort inhoud = D
                - Element.In bericht = TRUE
!-- Meta:
!-- @status     Backlog !-- TEAMBRP-4544 alfa is akkoord dat we in dit geval een andere foutmelding teruggeven

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SS_Synchroniseer_Stamgegeven/Requests/4.1_R1331_scenario_2.xml

Then is het antwoordbericht een soapfault

Scenario:   3.  Stamtabel opvragen met Element.soort = 1 EN Elementsoort Inhoud <> D EN Element.In bericht <> TRUE
                LT: R1331_LT04
                Verwacht resultaat:
                - Foutmelding: De opgegeven stamtabel bestaat niet.
                Uitwerking: SoortPartij
                - Elementsoort = 1
                - Elementsoort inhoud = S
                - Element.In bericht = LEEG
!-- Meta:
!-- @status     Backlog !-- TEAMBRP-4544 alfa is akkoord dat we in dit geval een andere foutmelding teruggeven

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SS_Synchroniseer_Stamgegeven/Requests/4.1_R1331_scenario_3.xml

Then is het antwoordbericht een soapfault

