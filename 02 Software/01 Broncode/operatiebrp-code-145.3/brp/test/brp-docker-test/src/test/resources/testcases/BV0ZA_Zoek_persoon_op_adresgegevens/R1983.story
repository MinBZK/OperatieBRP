Meta:
@status             Klaar
@usecase            BV.0.ZA
@sleutelwoorden     Zoek Persoon op adresgegevens
@regels             R1983

Narrative:
Het resultaatbericht bevat alleen Persoon(en) waarvoor GEEN verstrekkingsbeperking geldt voor de vragende partij;
hiervan is sprake als Persoon Niet voldoet aan 'Persoon heeft een verstrekkingsbeperking voor Partij' (R1342)


Scenario: 1.    Persoon met verstrekkingsbeperking wordt niet gevonden
                LT: R1983_LT14
                Veracht resultaat:
                - geslaagd, maar Jan niet in het bericht

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/R1983/Rick_volledige_verstrekkingsbeperking.xls

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdresVerstrekkingsBeperking' en partij 'Gemeente VerstrekkingsbepMogelijk'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/Zoek_Persoon_Op_Adres_R1983.xml

Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 1 groepen 'persoon'
And is het antwoordbericht xsd-valide
