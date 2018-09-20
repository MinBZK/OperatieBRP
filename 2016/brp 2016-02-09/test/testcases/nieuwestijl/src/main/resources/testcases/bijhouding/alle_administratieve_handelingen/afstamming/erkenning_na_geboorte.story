Meta:
@status                 Onderhanden
@sleutelwoorden         erkenningNaGeboorte

Narrative:
Afstamming, adoptie ingezetene

Scenario: 1. erkenningNaGeboorte. Danny is de ouder en Matilda is het kind.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'

Given de personen 826933129,526521673,631512457,141901317,667381065,904394633,390896617,727750537,212208937 zijn verwijderd
Given de standaardpersoon Danny met bsn 667381065 en anr 9601207058 zonder extra gebeurtenissen
Given de standaardpersoon Matilda met bsn 390896617 en anr 8263408402 zonder extra gebeurtenissen

Given administratieve handeling van type erkenningNaGeboorte , met de acties registratieOuder
And testdata uit bestand erkenning_na_geboorte_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
