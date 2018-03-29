Meta:
@auteur                 fuman
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Scenario 2

Scenario:   Huwelijkssluiting in BRP Gemeente, waarvan 1 huwelijkskandidaat woonachtig in een andere BRP gemeente (beide BRP gemeenten autofiattering aan)
            LT: VHNL08C10T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/PL1.xls
Given enkel initiele vulling uit bestand /LO3PL/PL2.xls

And de database is aangepast met: update kern.partij set datovergangnaarbrp = 20160101 where naam = 'Gemeente Amsterdam'
And de database is aangepast met: update kern.partij set datovergangnaarbrp = 20160101 where naam = 'Gemeente Eindhoven'

When voer een bijhouding uit VHNL08C10T10.xml namens partij 'Gemeente Amsterdam'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL08C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 118265799 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 589710217 wel als PARTNER betrokken bij een HUWELIJK

Given de database is aangepast met: update kern.partij set datovergangnaarbrp = null where naam = 'Gemeente Amsterdam'
Given de database is aangepast met: update kern.partij set datovergangnaarbrp = null where naam = 'Gemeente Eindhoven'
