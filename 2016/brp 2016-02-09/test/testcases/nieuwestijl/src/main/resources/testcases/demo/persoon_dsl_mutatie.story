Narrative:
Deze demo laat zien hoe het mogelijk is om een persoon te beschrijven met
zijn/haar handelingen zonder dat een bijhouding nodig is om deze
wijzigingen door te voeren.

Scenario: Succesvol beschrijven van een persoon

Meta:
@auteur sasme
@status Uitgeschakeld
@sleutelwoorden demo

Given de database is gereset voor de personen 210010587
And de persoon beschrijvingen:
demo = Persoon.uitDatabase(persoon: 1)
Persoon.slaOp(demo)
When voor persoon 210010587 wordt de laatste handeling geleverd
