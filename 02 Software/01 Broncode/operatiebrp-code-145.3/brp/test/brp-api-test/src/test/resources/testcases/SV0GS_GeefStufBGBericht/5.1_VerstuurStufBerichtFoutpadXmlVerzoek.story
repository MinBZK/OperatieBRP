Meta:
@status             Klaar
@usecase            SV.0.GS
@sleutelwoorden     Stuf bg vertaal

Narrative:
Verstuur stuf vertaal verzoek:
Verstuur verzoek en ontvang vertaling

Scenario:   1.  StandaardStufAutorisatie verstuurt een stuf vertaal verzoek (xml verzoek test) xsd invalide brp verwerkpersoon bericht, correcte autorisatie maar geen xsd valide inhoud
                LT: R2443_LT02
                Verwacht resultaat:
                - Foutief R2443
                - Het te vertalen BRPXML-bericht moet XSD-valide zijn


Given leveringsautorisatie uit autorisatie/StandaardStufAutorisatie
Given verzoek stufbericht met xml xml_request/stuf_verzoek_ongeldig_brp_inhoud.xml transporteur 00000001002220647009 ondertekenaar 00000001002220647009

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                               |
| R2443    | Het te vertalen BRPXML-bericht moet XSD-valide zijn. |
