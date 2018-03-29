Meta:
@status                 Klaar
@regels                 R1865
@usecase                UCS-BY.HG

Narrative:
R1865 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1865 Bijhouding 1 partner LO3 Geboortedatum maand en dag onbekend 1996-00-00 18j tov  Relatie.datum aanvang
          LT: VHNL02C350T80



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C350T80-Libby-18.xls

When voer een bijhouding uit VHNL02C350T80.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C350T80.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 590468649 wel als PARTNER betrokken bij een HUWELIJK
