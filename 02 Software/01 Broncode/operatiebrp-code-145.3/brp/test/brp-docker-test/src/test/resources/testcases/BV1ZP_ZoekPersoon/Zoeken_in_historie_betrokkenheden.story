Meta:
@status             Klaar
@sleutelwoorden     Zoek Persoon
@regels             Betrokkenheden

Narrative:
Als een afnemer
wil ik kunnen zoeken naar personen met historische gegevens
zodat ik een kan achter halen of ik over de juiste gegevens beschik.

Historisch zoeken dmv:
Optie Zoekbereik vullen met 'Peilmoment' (of leeg) & peilmomentMaterieel leeg of systeemdatum
resulteert in actuele gegevens('A Laag')

Optie Zoekbereik vullen met 'Peilmoment' (of leeg) & peilmomentMaterieel gevuld met een datum in het verleden
resulteert in geldigheid op het opgegeven datum

Optie zoekbereik vullen met 'Materiele periode' & peilmomentMaterieel leeg of gelijk aan systeemdatum
resulteert in geldigheid op een willekeurig moment

Optie Zoekberiek vullen met 'Materiele periode' & peilmomentMaterieel gevuld met datum in verleden
resulteert in geldigheid op of voor de opgegeven datum

Zoekbereik = 'Peilmoment' (of leeg) en 'PeilmomentMaterieel' is leeg of gelijk aan de systeemdatum: zoeken naar de actuele gegevens ('A laag').
Zoekbereik = 'Peilmoment' (of leeg) en 'PeilmomentMaterieel' ligt in het verleden: zoeken naar gegevens zoals ze geldig waren op de opgegeven datum.
Zoekbereik = 'Materiele periode' en 'PeilmomentMaterieel' is leeg of gelijk aan de systeemdatum: zoeken naar geldigheid op een willekeurig moment.
Zoekbereik = 'Materiele periode' en 'PeilmomentMaterieel' ligt in het verleden: zoeken naar gegevens zoals ze geldig waren 'op of voor' de opgegeven datum.


Scenario: 1.    Zoeken naar een kind welke op peilmoment een indicatie ouder heeft gezag = J heeft
                Willem_Jansen_Breda_Zoekpaden_MetOuderlijkGezag2.xls = Piet wordt ingeladen,
                Ouder 2 heeft gezag. zijn ouder1 (wordt Willemijn) en 2 (wordt Jan) worden vervangen door een ingeschreven persoon in
                Jan.xls en Willemijn_Jansen_Breda_Zoekpaden.xls
                Verwacht resultaat:
                Piet Jansen wordt gevonden hij heeft ouderlijkgezag=J

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Willemijn_Jansen_Breda_Zoekpaden.xls
Given enkel initiele vulling uit bestand /LO3PL/Willem_Jansen_Breda_Zoekpaden2_MetOuderlijkGezag.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
!-- ouder 1 wordt vervangen
Given Pseudo-persoon 2940761234 is vervangen door ingeschreven persoon 2467279378
!-- ouder 2 wordt vervangen
Given Pseudo-persoon 6824523026 is vervangen door ingeschreven persoon 5398948626


Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Betrokkenheden_1.1.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '416858697'



Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Betrokkenheden_1.2.xml

Then heeft het antwoordbericht verwerking Geslaagd



Scenario: 2.    Zoeken naar een kind welke op peilmoment een indicatie ouder heeft gezag = J heeft op peilmoment geen geldig voorkomen
                Willem_Jansen_Breda_Zoekpaden_MetOuderlijkGezag2.xls = Piet wordt ingeladen,
                Ouder 2 heeft gezag. zijn ouder1 (wordt Willemijn) en 2 (wordt Jan) worden vervangen door een ingeschreven persoon in
                Jan.xls en Willemijn_Jansen_Breda_Zoekpaden.xls
                Verwacht resultaat:
                Geen persoon gevonden, peildatum ligt voor aanvang van de indicatieouderheeftgezag !

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Willemijn_Jansen_Breda_Zoekpaden.xls
Given enkel initiele vulling uit bestand /LO3PL/Willem_Jansen_Breda_Zoekpaden2_MetOuderlijkGezag.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
!-- ouder 1 wordt vervangen
Given Pseudo-persoon 2940761234 is vervangen door ingeschreven persoon 2467279378
!-- ouder 2 wordt vervangen
Given Pseudo-persoon 6824523026 is vervangen door ingeschreven persoon 5398948626

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Betrokkenheden_2.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 0 groepen 'persoon'


Scenario: 3.    Zoeken naar ouders welke op peilmoment een kind hebben met de voornaam = Willem
                Zoeken op GerelateerdeKind.Persoon.SamengesteldeNaam.Voornamen
                Willem_Jansen_Breda_Zoekpaden_MetOuderlijkGezag2.xls = Piet wordt ingeladen,
                Ouder 2 heeft gezag. zijn ouder1 (wordt Willemijn) en 2 (wordt Jan) worden vervangen door een ingeschreven persoon in
                Jan.xls en Willemijn_Jansen_Breda_Zoekpaden.xls
                Verwacht resultaat:
                Beide ouders worden gevonden, peilmoment ligt op moment dat Kind Willem heet

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Willemijn_Jansen_Breda_Zoekpaden.xls
Given enkel initiele vulling uit bestand /LO3PL/Willem_Jansen_Breda_Zoekpaden2_MetOuderlijkGezag.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
!-- ouder 1 wordt vervangen
Given Pseudo-persoon 2940761234 is vervangen door ingeschreven persoon 2467279378
!-- ouder 2 wordt vervangen
Given Pseudo-persoon 6824523026 is vervangen door ingeschreven persoon 5398948626


Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Betrokkenheden_3.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 2 groepen 'persoon'


Scenario: 4.    Zoeken naar ouders welke op peilmoment een kind hebben met de voornaam = Willem op peilmomemtn geen actueel voorkomen
                Willem_Jansen_Breda_Zoekpaden_MetOuderlijkGezag2.xls = Piet wordt ingeladen,
                Ouder 2 heeft gezag. zijn ouder1 (wordt Willemijn) en 2 (wordt Jan) worden vervangen door een ingeschreven persoon in
                Jan.xls en Willemijn_Jansen_Breda_Zoekpaden.xls
                Verwacht resultaat:
                Geen resultaten, beide ouders worden niet gevonden, peilmoment ligt op moment dat Kind Piet heet

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Willemijn_Jansen_Breda_Zoekpaden.xls
Given enkel initiele vulling uit bestand /LO3PL/Willem_Jansen_Breda_Zoekpaden2_MetOuderlijkGezag.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
!-- ouder 1 wordt vervangen
Given Pseudo-persoon 2940761234 is vervangen door ingeschreven persoon 2467279378
!-- ouder 2 wordt vervangen
Given Pseudo-persoon 6824523026 is vervangen door ingeschreven persoon 5398948626

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Betrokkenheden_4.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 0 groepen 'persoon'



