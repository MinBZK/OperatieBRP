Meta:
@status             Klaar
@usecase            BV.0.ZA
@sleutelwoorden     Zoek Persoon op adresgegevens
@regels             R2263

Narrative:
Zoekresultaatberichten bevatten alleen de verantwoordingsgegevens die wettelijk verplicht geleverd moeten worden
(zie R1545 - Verplicht leveren van ABO-partij en rechtsgrond.), ongeacht de autorisatie die is vastgelegd in Dienstbundel \ Groep.

Dit betekent dat er geen actieverwijzingen worden opgenomen in de groepen,
tenzij R1545 - Verplicht leveren van ABO-partij en rechtsgrond. dat anders bepaalt.

Scenario:   1.  Partijrol = Bijhoudingsorgaan College, administarieve handeling door partij = afnemer, Leveringsautorisatie met verantwoording
                LT: R2263_LT01
                Uitwerking:
                - Verhuizing naar Den Haag op 01-01-2010
                - Verhuizing naar Uithoorn op 01-01-2015
                Verwacht resultaat:
                - Geen actieverwijzingen in bericht voor adres


Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2263/Zoek_Persoon_Op_Adres
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T110_xls
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2252EB

Then heeft het antwoordbericht verwerking Geslaagd

Then is in antwoordbericht de aanwezigheid van 'actieInhoud' in 'afgeleidAdministratief' nummer 1 nee
Then is in antwoordbericht de aanwezigheid van 'actie' in 'bijgehoudenActies' nummer 1 nee

Scenario:   2.  Partijrol = Bijhoudingsorgaan College, administarieve handeling door partij = afnemer, Leveringsautorisatie ZONDER verantwoording
                LT: R2263_LT02
                Uitwerking:
                - Verhuizing naar Utrecht  op 31-12-2015
                - Verhuizing naar Den Haag op 01-01-2016
                - Verhuizing naar Uithoorn op 02-01-2016
                Verwacht resultaat:
                - Geen actieverwijzingen in bericht voor adres

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2263/Zoek_Persoon_Op_Adres_Zonder_Verantwoording
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T110_xls
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres Zonder Verantwoording'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2252EB

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'actieInhoud' in 'afgeleidAdministratief' nummer 1 nee
Then is in antwoordbericht de aanwezigheid van 'actie' in 'bijgehoudenActies' nummer 1 nee

