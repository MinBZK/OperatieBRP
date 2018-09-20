Meta:
@auteur tools
@status Uitgeschakeld
@epic legacy
@module intake-mutatielevering

Narrative:
In order to verifieren dat de leveringsoftware naar behoren functioneert
As a lid van Team Delta
I want to een smoke test uitvoeren

!--LEGACY TESTGEVAL UITGESCHAKELD -- KOMT REEDS VOOR IN NIEUWE STIJL
Scenario: Een administatieve handeling resulteert in mutatieleveringen
Meta:
@soapEndpoint ${applications.host}/bijhouding/BijhoudingService/bhgAfstamming
@soapNamespace http://www.bzk.nl/brp/bijhouding/service

Given de database wordt gereset voor de personen 800068750,800068464,800004656
Given data uit excel /templatedata/art_input_001_met_Aanschrijving.xls , VR00009-TC0101
Given de sjabloon /berichtdefinities/KUC001-metAanschrijving_request_template.xml

When het bericht is naar endpoint verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And wacht tot alle berichten zijn ontvangen

When volledigbericht voor leveringautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then heeft 'burgerservicenummer' in 'identificatienummers' nummer 1 de waarde '800068750'

When mutatiebericht voor leveringautorisatie Geen pop.bep. levering op basis van doelbinding wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
groep                | nummer | attribuut           | verwachteWaarde
identificatienummers | 1      | burgerservicenummer | 800068464
identificatienummers | 3      | burgerservicenummer | 800004656


Scenario: Succesvol plaatsen van een afnemerindicatie leidt tot een volledig bericht
Meta:
@soapEndpoint ${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties
@soapNamespace http://www.bzk.nl/brp/levering/afnemerindicaties/service

Given data uit excel /templatedata/art_input_OnderhoudAfnemerindicaties.xls , VP.0-TC01
And de database wordt gereset voor de personen 677080426
And de sjabloon /berichtdefinities/LEV_afnemerindicaties_request_template_LB.xml

When het bericht is naar endpoint verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And wordt er 1 bericht geleverd

When volledigbericht voor abonnement Geen pop.bep. levering op basis van afnemerindicatie wordt bekeken
Then heeft 'burgerservicenummer' in 'identificatienummers' nummer 1 de waarde '677080426'

Then is de query /sqltemplates/selecteerHisPersafnemerindicatie_ART-Afnemerindicaties.sql gelijk aan /testcases/art_input_IntakeMutatielevering/data/VP.0-TC01-2-dataresponse.xml voor de expressie //Results
