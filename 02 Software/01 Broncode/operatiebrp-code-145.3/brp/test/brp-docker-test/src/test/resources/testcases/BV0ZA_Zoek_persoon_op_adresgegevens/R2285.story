Meta:
@status             Klaar
@usecase            BV.0.ZA
@sleutelwoorden     Zoek Persoon op adres:
@regels             R2285

Narrative:
Zoekvragen mogen niet teveel tussenresultaten opleveren.
Het aantal resultaten uit de SQL van de zoekvraag mag niet zodanig groot zijn
dat het verder verwerken van dit resultaat teveel systeemresources vergt.


Scenario: 1.    Zoek Persoon met meer tussenresultaten dan toegestaan.
                LT: R2285_LT03
                Uitwerking: Er worden meer dan 12 personen gevonden, het maximum aantal tussenresultaten = 12
                Verwacht resultaat: Foutmelding R2285 De zoekvraag heeft teveel tussenresultaten opgeleverd.

!-- Inladen van een leveringsautorisatie zonder Dienst.maximum aantal zoek resultaten. Default = 10
!-- Inladen van 15 testpersonen, max aantal tussenresultaten is geconfigureerd op 12
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kim.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kanye.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Khloe.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kourtney.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kendall.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Caitlyn.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kris.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie2.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie3.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie4.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie5.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie6.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie7.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie8.xls

Given verzoek voor leveringsautorisatie 'ZoekPersoonOpAdres totale pop bep gevuld' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R2285.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                  |
| R2285     | De zoekvraag heeft teveel tussenresultaten opgeleverd.   |