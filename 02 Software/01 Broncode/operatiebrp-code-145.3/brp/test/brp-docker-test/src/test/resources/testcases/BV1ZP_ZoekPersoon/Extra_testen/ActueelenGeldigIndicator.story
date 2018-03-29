Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon

Narrative:
Additionele story om te valideren dat de zoek dienst enkel resultaten levert met voorkomens waarvoor geldt dat 'Indag=TRUE'
ie. zoek op attributen uit de identiteit groep van het voorkomen zonder dat er actuele voorkomens van de standaardgroep is.


De testen worden uitgewerkt voor de volgende groepen:
- Nationaliteit (zoeken op attribuut uit identiteitsgroep);
- Verstrekkingsbeperking (want enkel identiteitsgroep met formele historie);
- Persoon \ Indicatie \ uitsluiting kiesrecht(identiteitsgroep en standaard groep met historie patroon F)
- Persoon \ Verificatie (identiteitsgroep en standaard groep met historie patroon F)


Scenario: 1 Zoeken op vervallen nationaliteit (nederlandse)
                LT:
                Uitwerking: Persoon heeft vervallen nederlandse nationaliteit met INDAG = FALSE
                Verwacht resultaat: Persoon niet in zoek resultaat


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonActueelGeldigIndicator/PersoonVervallenNationaliteit.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Scenario_1_indag.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 0 groepen 'persoon'

Scenario: 1A Zoeken op actuele nationaliteit (Liechtensteinse)
                LT: R2402_LT01
                Uitwerking:
                - Persoon heeft vervallen nederlandse nationaliteit met INDAG = FALSE
                - Persoon heeft Liechtensteinse nationaliteit met INDAG = TRUE
                Verwacht resultaat: Persoon in zoek resultaat


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonActueelGeldigIndicator/PersoonVervallenNationaliteit.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Scenario_1A_indag.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 1 groepen 'persoon'

Scenario: 1B Historisch op vervallen nationaliteit met peil moment op de DEG van het voorkomen
                LT: R2402_LT04
                Uitwerking:
                - Persoon heeft vervallen nederlandse nationaliteit met INDAG = FALSE
                - Persoon heeft Liechtensteinse nationaliteit met INDAG = TRUE
                Verwacht resultaat: Persoon niet in zoek resultaat want de standaardgroepsvoorkomen is vervallen en er is geen
                actueel voorkomen voor de nederlandse nationaliteit

                In dit geval is er een op het peil moment materieel bezien een geldig voorkomen van de nationaliteit, dus wordt de persoon gevonden
                Het record in de A-laag (persnation) heeft weliswaar INDAG = FALSE, in de  his_persnation is vastgelegd dat er op 'enig' moment
                een geldig voorkomen is geweest van deze nationaliteit. Omdat er gezocht wordt met een peilmoment < DEG en > DAG wordt de persoon gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonActueelGeldigIndicator/PersoonVervallenNationaliteit.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Scenario_1B_indag.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 1 groepen 'persoon'

Scenario: 1C Historisch op vervallen nationaliteit met peil moment na de DEG van het voorkomen, persoon niet gevonden
                LT: R2402_LT04
                Uitwerking:
                - Persoon heeft vervallen nederlandse nationaliteit met INDAG = FALSE
                - Persoon heeft Liechtensteinse nationaliteit met INDAG = TRUE
                Verwacht resultaat: Persoon niet in zoek resultaat want de standaardgroepsvoorkomen is vervallen en er is geen
                actueel voorkomen voor de nederlandse nationaliteit

                In dit geval is er een op het peil moment materieel bezien geen geldig voorkomen van de nationaliteit, dus wordt de persoon gevonden
                Het record in de A-laag (persnation) heeft weliswaar INDAG = FALSE, in de  his_persnation is vastgelegd dat er op 'enig' moment
                een geldig voorkomen is geweest van deze nationaliteit. Omdat het peilmoment na de geldigheidsperiode ligt van het voorkomen wordt deze niet gevonden.

Meta:
@status Onderhanden
!-- Persoon wordt nu gevonden op nederlandse nationaliteit, omdat nationaliteit code geen historie heeft wordt de persoon toch gevonden.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonActueelGeldigIndicator/PersoonVervallenNationaliteit.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Scenario_1C_indag.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 0 groepen 'persoon'

Scenario: 1D Historisch op vervallen nationaliteit met peil moment voor de DAG van het voorkomen, persoon niet gevonden
                LT: R2402_LT04
                Uitwerking:
                - Persoon heeft vervallen nederlandse nationaliteit met INDAG = FALSE
                - Persoon heeft Liechtensteinse nationaliteit met INDAG = TRUE
                Verwacht resultaat: Persoon niet in zoek resultaat want de standaardgroepsvoorkomen is vervallen en er is geen
                actueel voorkomen voor de nederlandse nationaliteit

                In dit geval is er een op het peil moment materieel bezien een geldig voorkomen van de nationaliteit, dus wordt de persoon gevonden
                Het record in de A-laag (persnation) heeft weliswaar INDAG = FALSE, in de  his_persnation is vastgelegd dat er op 'enig' moment
                een geldig voorkomen is geweest van deze nationaliteit. Omdat het peilmoment voor de geldigheidsperiode ligt van het voorkomen wordt deze niet gevonden.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonActueelGeldigIndicator/PersoonVervallenNationaliteit.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Scenario_1D_indag.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 0 groepen 'persoon'

Scenario: 2 Zoeken op vervallen verstrekkingsbeperking

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonActueelGeldigIndicator/PersoonVerstrekkingsBeperkingVervalt.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Scenario_2_indag.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 1 groepen 'persoon'

Scenario: 2A Historisch Zoeken op vervallen verstrekkingsbeperking
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonActueelGeldigIndicator/PersoonVerstrekkingsBeperkingVervalt.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Scenario_2A_indag.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 0 groepen 'persoon'

Scenario: 3 Zoeken op vervallen verificatie
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonActueelGeldigIndicator/PersoonVervallenVerificatie.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Scenario_3_indag.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 0 groepen 'persoon'

Scenario: 4  Zoeken op persoon met vervallen indicatie Uitsluitingkiesrecht, persoon heeft ook actueel voorkomen van Uitsluitingkiesrecht
             LT:
             Uitwerking: 1 vervallen voorkomen 1 actueel voorkomen, persoon wordt gevonden op basis van het actuele voorkomen

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonActueelGeldigIndicator/PersoonVervallenUitsluitingKiesrecht.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Scenario_4_indag.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 1 groepen 'persoon'

Scenario: 4A Historisch Zoeken op vervallen indicatie kiesrecht
             LT:
             Uitwerking: 1 vervallen voorkomen 1 actueel voorkomen, persoon wordt gevonden op basis van het actuele voorkomen
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonActueelGeldigIndicator/PersoonVervallenUitsluitingKiesrecht.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Scenario_4A_indag.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 1 groepen 'persoon'

Scenario: 5 Historisch Zoeken op indicatie Behandeld als nederlander (historie patroon F+M)
             LT:
             Uitwerking: DAG indicatie = 19761030 DEG indicatie = 20110909 peilmoment = 2010-01-01
             Persoon gevonden want er is een voorkomen dat geldig was op het gegeven peilmoment

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/ZoekPersoonActueelGeldigIndicator/PersoonVervallenIndicatieBehandeldAlsNederlander.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Extra_testen/Requests/Zoek_Persoon_Scenario_5_indag.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
