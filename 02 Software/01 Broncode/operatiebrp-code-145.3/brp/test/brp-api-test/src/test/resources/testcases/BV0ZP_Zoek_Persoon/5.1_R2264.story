Meta:

@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2264

Narrative:
Zoekresultaatberichten bevatten alleen onderzoeksgegevens die betrekking hebben op in het bericht aanwezige gegevens
(dus alleen voor zover wettelijk verplicht mee te leveren, ongeacht of de geautoriseerde Partij in de Rol bijhouder of afnemer zoekt)

Scenario:   1.  Onderzoek gestart op Naamgebruik.Code, Leveringsautorisatie heeft autorisatie op Naamgebruik.Code, Partijrol = 1 (afnemer)
                LT: R2264_LT01
                Verwacht resultaat:
                - Onderzoek in bericht

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2266/Zoek_Persoon
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T10_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=948226249

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 1 groepen 'onderzoeken'

Scenario:   2.  Onderzoek gestart op Naamgebruik.Code, Leveringsautorisatie heeft GEEN autorisatie op Naamgebruik.Code, Partijrol = 1 (afnemer)
                LT: R2264_LT02
                Verwacht resultaat:
                - Onderzoek NIET in bericht

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2264/Zoek_Persoon_naamgebruikcode_niet_geautoriseerd
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T10_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon naamgebruikcode niet geautoriseerd'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=948226249

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 0 groepen 'onderzoeken'

Scenario:   3.  Onderzoek gestart op Naamgebruik.Code, Leveringsautorisatie heeft autorisatie op Naamgebruik.Code, Partijrol = 2 (College)
                LT: R2264_LT03
                Verwacht resultaat:
                - Onderzoek in bericht

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2264/Zoek_Persoon_partijrol_college
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T10_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon College'
|zendendePartijNaam|'College'
|rolNaam|'Bijhoudingsorgaan College'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=948226249

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 1 groepen 'onderzoeken'

Scenario:   4.  Onderzoek gestart op Naamgebruik.Code, Leveringsautorisatie heeft GEEN autorisatie op Naamgebruik.Code, Partijrol = 2 (College)
                LT: R2264_LT04
                Verwacht resultaat:
                - Onderzoek NIET in bericht

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2264/Zoek_Persoon_naamgebruikcode_niet_geautoriseerd_partijrol_college
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T10_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon naamgebruikcode niet geautoriseerd partijrol college'
|zendendePartijNaam|'College'
|rolNaam|'Bijhoudingsorgaan College'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=948226249

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 0 groepen 'onderzoeken'

Scenario:   5.  Onderzoek gestart op Naamgebruik.Code, Leveringsautorisatie heeft autorisatie op Naamgebruik.Code, Partijrol = 3 (Minister)
                LT: R2264_LT05
                Verwacht resultaat:
                - Onderzoek in bericht


Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2264/Zoek_Persoon_partijrol_minister
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T10_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Minister'
|zendendePartijNaam|'Minister'
|rolNaam|'Bijhoudingsorgaan Minister'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=948226249

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 1 groepen 'onderzoeken'

Scenario:   6.  Onderzoek gestart op Naamgebruik.Code, Leveringsautorisatie heeft GEEN autorisatie op Naamgebruik.Code, Partijrol = 3 (Minister)
                LT: R2264_LT06
                Verwacht resultaat:
                - Onderzoek NIET in bericht

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2264/Zoek_Persoon_naamgebruikcode_niet_geautoriseerd_partijrol_minister
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T10_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon naamgebruikcode niet geautoriseerd partijrol minister'
|zendendePartijNaam|'Minister'
|rolNaam|'Bijhoudingsorgaan Minister'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=948226249

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 0 groepen 'onderzoeken'