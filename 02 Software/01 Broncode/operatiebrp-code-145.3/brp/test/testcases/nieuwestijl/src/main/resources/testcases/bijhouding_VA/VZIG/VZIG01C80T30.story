Meta:
@status                 Onderhanden
@usecase                UCS-BY.HG

Narrative:
R2493 Waarschuwing indien er een feitdatum op de persoonslijst staat die recenter dan of gelijk is aan de Peildatum, DAG en DEG van de Administratieve handeling

Scenario:   De geboortedatum van een partner is gelijk aan de DEG van een huwelijk
            LT: VZIG01C80T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C80T30-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 935304137 en persoon 886378825 met relatie id 2000103 en betrokkenheid id 2000104

Then is in de database de persoon met bsn 935304137 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 886378825 wel als PARTNER betrokken bij een HUWELIJK

Given de database is aangepast met: update kern.his_persgeboorte set datgeboorte='20160101' where pers in (select id from kern.pers where voornamen='James');

When voer een bijhouding uit VZIG01C80T30a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C80T30a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

When voer een bijhouding uit VZIG01C80T30b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C80T30b.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R