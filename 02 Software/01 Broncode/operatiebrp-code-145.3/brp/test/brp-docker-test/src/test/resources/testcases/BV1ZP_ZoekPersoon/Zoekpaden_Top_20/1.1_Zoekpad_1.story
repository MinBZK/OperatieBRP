Meta:
@status             Klaar
@sleutelwoorden     Zoekpaden
@regels             ZoekPersoon

Narrative:
Jacobi wordt ingeladen, met
ANR:2301342693
Jan wordt ingeladen, met
ANR:5398948626

Zoeken op ANR:2301342693, Historisch = Nee, Peildatum = Leeg

Scenario: 1. Jacobi heeft de ANR, Jan wordt ingeladen om extra persoon te hebben
             LT:
             Verwacht resultaat: Jacobi wordt geleverd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jacobi_Zoekpaden.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Zoekpaden_Top_20/Requests/Zoek_Persoon_Story_1.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'administratienummer' in 'identificatienummers' de waarde '2301342693'
Then is er voor xpath //brp:samengesteldeNaam[brp:geslachtsnaamstam = 'Jacobi'] een node aanwezig in het antwoord bericht
