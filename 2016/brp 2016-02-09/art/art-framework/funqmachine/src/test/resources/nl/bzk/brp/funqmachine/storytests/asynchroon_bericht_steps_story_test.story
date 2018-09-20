Meta:
@epic Test


Scenario: Toon gebruik met Examples sectie
Meta:
@epic Demo

Then heeft '<attribuut>' in '<groep>' de waardes '<verwachteWaardes>'

Examples:
groep 		                | attribuut            | verwachteWaardes
voornamen	                | naam                 | Cees,Peter
voornamen	                | volgnummer           | 1,2
geslachtsnaamcomponenten	| stam                 | Vries,Rooij
geslachtsnaamcomponenten	| voorvoegsel          | de,de
geslachtsnaamcomponenten    | volgnummer           | 1,2
nationaliteit               | nationaliteitCode    | 0001,0002
nationaliteit               | redenVerkrijgingCode | 017,017
