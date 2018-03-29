Narrative:
Verwerk een geboorte

SQL om later de database te kunnen controleren:
kind betrokkenheden:
select * from kern.betr where relatie in (
select relatie.id from kern.betr, kern.pers, kern.relatie
where pers.bsn = 907160633
and betr.pers = pers.id
and betr.relatie = relatie.id
)

kind his_relatie
select * from kern.his_relatie where relatie in (
select relatie.id from kern.betr, kern.pers, kern.relatie
where pers.bsn = 907160633
and betr.pers = pers.id
and betr.relatie = relatie.id
)

moeder his_relatie
select * from kern.his_relatie where relatie in (
select relatie.id from kern.betr, kern.pers, kern.relatie
where pers.bsn = 100000095
and betr.pers = pers.id
and betr.relatie = relatie.id
)


Scenario: een persoon wordt geboren

Meta:
@status Klaar
@auteur proef

Given verzoek van bericht bhg_afsRegistreerGeboorte
And testdata uit bestand geboorte_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Automatisch synchroniseren populatie NIET 036101 is ontvangen en wordt bekeken

