Meta:
@status             Klaar
@usecase            LV.1.AL
@sleutelwoorden     Autorisatie & authenticatie


Narrative:
Als beheerder van het BRP wil ik dat geautoriseerde partijen toegang hebben tot het BRP.


Scenario:   1. Bevraging GeefDetailsPersoon met ondertekenaar en transporteur in de autorisatie
            LT:
            Ondertekenaar = Gemeente Haarlem (Geldig)
            Transporteur = Gemeente Alkmaar (Geldig)
            Verwacht resultaat:
            Verwerking Geslaagd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Abo GeefDetailsPersoon ondertekenaarTransporteur' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GD_Geef_Details_Persoon/verzoek/1._Geef_Details_Persoon_Story_1.xml

Then heeft het antwoordbericht verwerking Geslaagd

