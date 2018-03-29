Meta:
@status             Klaar
@sleutelwoorden     Zoekpaden
@regels             ZoekPersoon

Narrative:
Jacobi wordt ingeladen, met
BSN:103962438
Jan wordt ingeladen, met
BSN:606417801

Zoeken op BSN:103962438, Historisch = Ja, Peildatum = 2015-01-01

Scenario: 1. Jacobi heeft de BSN, Jan wordt ingeladen om extra persoon te hebben
             LT:
             Verwacht resultaat: Jacobi wordt geleverd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jacobi_Zoekpaden.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Zoekpaden_Top_20/Requests/Zoek_Persoon_Story_2.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '103962438'
Then is er voor xpath //brp:samengesteldeNaam[brp:geslachtsnaamstam = 'Jacobi'] een node aanwezig in het antwoord bericht
