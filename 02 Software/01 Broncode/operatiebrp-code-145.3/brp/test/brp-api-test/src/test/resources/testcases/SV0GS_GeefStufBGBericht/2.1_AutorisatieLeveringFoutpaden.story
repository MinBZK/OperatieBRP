Meta:
@status             Klaar
@usecase            SV.0.GS
@sleutelwoorden     Stuf bg vertaal

Narrative:
Verstuur stuf vertaal verzoek:
Verstuur verzoek en ontvang vertaling
- AutorisatieLevering

!-- In deze story van alle autorisatie regels 1 foutgeval om te zien of de regels voor de dienst geimplementeerd zijn

Scenario:   1.  De toegang leveringsautorisatie bestaat, combinatie ondertekenaar, transporteur onjuist
                LT: R1257_LT02
                Verwacht resultaat:
                - R1257 gelogd

Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R1257_combinatie_ondertekenaar_transporteur_onjuist
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht
|ondertekenaar|'College'
|transporteur|'Gemeente Utrecht'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING
| R2343 | Er is een autorisatiefout opgetreden.

Then is er een autorisatiefout gelogd met regelcode R1257

Scenario:   2.  toegangleveringsautorisatie datum ingang is groter dan systeemdatum
                LT: R1258_LT05
                Verwacht resultaat:
                - R1258 gelogd

Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R1258_toegangleveringsautorisatie_datumingang_morgen
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht

Then is er een autorisatiefout gelogd met regelcode R1258

Scenario:   3.  Leveringsautorisatie is niet geldig, datumingang ligt na de systeemdatum
                LT: R1261_LT04
                Verwacht resultaat:
                - R1261 gelogd

Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R1261_leveringsautorisatie_morgen_geldig
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht

Then is er een autorisatiefout gelogd met regelcode R1261

Scenario:   4.  Dienst is niet geldig, datumingang ligt na de systeemdatum
                LT: R1262_LT28
                Verwacht resultaat:
                - R1262 gelogd

Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R1262_dienst_morgen_geldig
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht

Then is er een autorisatiefout gelogd met regelcode R1262

Scenario:   5.  Leveringsautorisatie is geblokkeerd
                LT: R1263_LT02
                Verwacht resultaat:
                - R1263 gelogd

Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R1263_LeveringsautorisatieGeblokkeerd
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht

Then is er een autorisatiefout gelogd met regelcode R1263

Scenario:   6.  Dienst is geblokkeerd
                LT: R1264_LT24
                Verwacht resultaat:
                - R1264 gelogd

Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R1264_DienstGeblokkeerd
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht

Then is er een autorisatiefout gelogd met regelcode R1264

Scenario:   6.  Toegang leveringsautorisatie is geblokkeerd
                LT: R2052_LT02
                Verwacht resultaat:
                - R2052 gelogd

Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R2052_ToegangLeveringsautorisatieGeblokkeerd
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht

Then is er een autorisatiefout gelogd met regelcode R2052

Scenario:   7.  Leveringautorisatie bestaat niet
                LT: R2053_LT02
                Verwacht resultaat:
                - R2053 gelogd


Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R2053_LeveringsautorisatieidBestaatNiet
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht
|leveringsautorisatieId|9999

Then is er een autorisatiefout gelogd met regelcode R2053

Scenario:   10. Dienstbundel geblokkeerd
                LT: R2056_LT24
                Verwacht resultaat:
                - R2056 gelogd


Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R2056_DienstbundelGeblokkeerd
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht

Then is er een autorisatiefout gelogd met regelcode R2056

Scenario:   11. Er moet een toegang leveringsautorisatie voor de opgegeven partij en rol bestaan
                LT: R2120_LT05
                Verwacht resultaat:
                - R2120 gelogd

Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R2120_geautoriseerde_partij_onjuist
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Olst'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht

Then is er een autorisatiefout gelogd met regelcode R2120

Scenario:   12. Ondertekenaar onjuist
                LT: R2121_LT04
                Verwacht resultaat:
                - R2121 gelogd


Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R2121_ondertekenaar_onjuist
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht
|ondertekenaar|'Gemeente Tiel'

Then is er een autorisatiefout gelogd met regelcode R2121

Scenario:   13. Transporteur onjuist
                LT: R2122_LT04
                Verwacht resultaat:
                - R2122 gelogd


Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R2122_transporteur_onjuist
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht
|rolNaam|'Bijhoudingsorgaan Minister'

Then is er een autorisatiefout gelogd met regelcode R2122

Scenario:   14. Dienst ontbreekt
                LT: R2130_LT24
                Verwacht resultaat:
                - R2130 gelogd

Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R2130_Gevraagde_dienst_ontbreekt
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht

Then is er een autorisatiefout gelogd met regelcode R2130

Scenario:   15. Dienstbundel ongeldig
                LT: R2239_LT05
                Verwacht resultaat:
                - R2239 gelogd


Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R2239_DienstbundelOngeldig
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht

Then is er een autorisatiefout gelogd met regelcode R2239

Scenario:   16. Partij is ongeldig
                LT: R2242_LT05
                Verwacht resultaat:
                - R2242 gelogd

Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R2242_Partij_Ongeldig
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'GemeenteDatumIngangDatumEindeInVerleden'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht

Then is er een autorisatiefout gelogd met regelcode R2242

Scenario:   17. Ondertekenaar ongeldig
                LT: R2243_LT04
                Verwacht resultaat:
                - R2243 gelogd


Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R2243_Ondertekenaar_Ongeldig
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht
|ondertekenaar|'GemeenteDatumIngangDatumEindeInVerleden'

Then is er een autorisatiefout gelogd met regelcode R2243

Scenario:   18. transporteur ongeldig
                LT: R2244_LT04
                Verwacht resultaat:
                - R2244 gelogd


Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R2244_Transporteur_Ongeldig
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht
|transporteur|'GemeenteDatumIngangDatumEindeInVerleden'

Then is er een autorisatiefout gelogd met regelcode R2244

Scenario:   19. partijrol ongeldig
                LT: R2245_LT04
                Verwacht resultaat:
                - R2245 gelogd


Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R2245_PartijrolOngeldig
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'PartijRolDatumIngangGisterenDatumEindeGisteren'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht


Then is er een autorisatiefout gelogd met regelcode R2245

Scenario:   20. Datum Overgang BRP groter dan systeemdatum
                LT: R2524_LT06
                Verwacht resultaat:
                - R2524 gelogd


Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R2524_DatumOvergangBRP
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'DatumOvergangBRPMorgen'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht


Then is er een autorisatiefout gelogd met regelcode R2524

Scenario:   21. Partij.Datum overgang naar BRP is Leeg, stelsel is GBA, ber definitie is BRP
                LT: R2585_LT03
                Verwacht resultaat:
                - R2585 gelogd

Meta:
@status Onderhanden

Given leveringsautorisatie uit autorisatie/AutorisatieLevering/R2585_GBA_Stelsel
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente GBA'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht

Then heeft het antwoordbericht verwerking Foutief

!-- Then is er een autorisatiefout gelogd met regelcode R2585
!-- Niet zo gelogd, maar regel ook nog niet geimplementeerd
