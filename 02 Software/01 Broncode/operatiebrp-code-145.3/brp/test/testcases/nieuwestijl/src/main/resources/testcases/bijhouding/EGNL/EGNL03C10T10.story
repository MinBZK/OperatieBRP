Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Afleiding van datumEinde op basis van datumAanvangGeldigheid

Scenario: Datum einde relatie is ongelijk aan Datum aanvang relatie. Controleer de afleiding van DAG.
          LT: EGNL03C10T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL03C10T10-April.xls

When voer een bijhouding uit EGNL03C10T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL03C10T10a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 311505545 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP


Given pas laatste relatie van soort 2 aan tussen persoon 311505545 en persoon 242876985 met relatie id 2000103 en betrokkenheid id 2000104

When voer een bijhouding uit EGNL03C10T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL03C10T10b.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 311505545 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then in kern heeft select dataanvgel from kern.his_perssamengesteldenaam where voornamen = 'Jan' de volgende gegevens:
| veld       | waarde   |
| dataanvgel | 20160514 |