Meta:

@status             Klaar
@usecase            BV.0.ZA
@sleutelwoorden     Zoek Persoon Op Adresgegevens
@regels             R2264

Narrative:
Zoekresultaatberichten bevatten alleen onderzoeksgegevens die betrekking hebben op in het bericht aanwezige gegevens
(dus alleen voor zover wettelijk verplicht mee te leveren, ongeacht of de geautoriseerde Partij in de Rol bijhouder of afnemer zoekt)

Scenario:   1.  Onderzoek gestart op Naamgebruik.Code, Leveringsautorisatie heeft autorisatie op Naamgebruik.Code, Partijrol = 1 (afnemer)
                LT: R2264_LT07
                Verwacht resultaat:
                - Onderzoek in bericht

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2264/Zoek_Persoon_Op_Adresgegevens
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T10_xls
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adresgegevens'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2252EB

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 1 groepen 'onderzoeken'

Scenario:   2.  Onderzoek gestart op Naamgebruik.Code, Leveringsautorisatie heeft GEEN autorisatie op Naamgebruik.Code, Partijrol = 1 (afnemer)
                LT: R2264_LT08
                Verwacht resultaat:
                - Onderzoek NIET in bericht

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2264/Zoek_Persoon_op_adres_naamgebruikcode_niet_geautoriseerd
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T10_xls
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon op adres naamgebruikcode niet geautoriseerd'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2252EB

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 0 groepen 'onderzoeken'

Scenario:   3.  Onderzoek gestart op Naamgebruik.Code, Leveringsautorisatie heeft autorisatie op Naamgebruik.Code, Partijrol = 2 (College)
                LT: R2264_LT09
                Verwacht resultaat:
                - Onderzoek in bericht

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2264/Zoek_Persoon_op_adres_partijrol_college
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T10_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon op adres College'
|zendendePartijNaam|'College'
|rolNaam|'Bijhoudingsorgaan College'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2252EB

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 1 groepen 'onderzoeken'

Scenario:   4.  Onderzoek gestart op Naamgebruik.Code, Leveringsautorisatie heeft GEEN autorisatie op Naamgebruik.Code, Partijrol = 2 (College)
                LT: R2264_LT10
                Verwacht resultaat:
                - Onderzoek NIET in bericht

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2264/Zoek_Persoon_op_adres_naamgebruikcode_niet_geautoriseerd_partijrol_college
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T10_xls
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon op adres naamgebruikcode niet geautoriseerd partijrol college'
|zendendePartijNaam|'College'
|rolNaam|'Bijhoudingsorgaan College'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2252EB

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 0 groepen 'onderzoeken'

Scenario:   5.  Onderzoek gestart op Naamgebruik.Code, Leveringsautorisatie heeft autorisatie op Naamgebruik.Code, Partijrol = 3 (Minister)
                LT: R2264_LT11
                Verwacht resultaat:
                - Onderzoek in bericht


Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2264/Zoek_Persoon_op_adres_partijrol_minister
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T10_xls
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon op adres Minister'
|zendendePartijNaam|'Minister'
|rolNaam|'Bijhoudingsorgaan Minister'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2252EB

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 1 groepen 'onderzoeken'

Scenario:   6.  Onderzoek gestart op Naamgebruik.Code, Leveringsautorisatie heeft GEEN autorisatie op Naamgebruik.Code, Partijrol = 3 (Minister)
                LT: R2264_LT12
                Verwacht resultaat:
                - Onderzoek NIET in bericht

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2264/Zoek_Persoon_op_adres_naamgebruikcode_niet_geautoriseerd_partijrol_minister
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T10_xls
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon op adres naamgebruikcode niet geautoriseerd partijrol minister'
|zendendePartijNaam|'Minister'
|rolNaam|'Bijhoudingsorgaan Minister'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2252EB

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 0 groepen 'onderzoeken'