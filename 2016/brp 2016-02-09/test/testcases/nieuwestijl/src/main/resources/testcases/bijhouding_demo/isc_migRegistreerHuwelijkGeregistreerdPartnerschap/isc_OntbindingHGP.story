Meta:
@epic                   POC Bijhouding
@auteur                 luwid
@status                 Onderhanden


Narrative:
   Als GBA-bijhouder wil ik dat gBA_OntbindingsHGP bericht wordt verstuurd.

Scenario: 1. Stuur OntbindingsHGP bericht uit

Given voor gemeente Utrecht autofiat aan staat
Given de personen 931665929,826933129,526521673 zijn verwijderd
And de standaardpersoon Sandy met bsn 931665929 en anr 9091384082 zonder extra gebeurtenissen

And administratieve handeling van type gBAOntbindingHuwelijkGeregistreerdPartnerschap , met de acties registratieEindeHuwelijkGeregistreerdPartnerschap
And testdata uit bestand isc_OntbindingHuwelijkGeregistreerdPartnerschap.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
