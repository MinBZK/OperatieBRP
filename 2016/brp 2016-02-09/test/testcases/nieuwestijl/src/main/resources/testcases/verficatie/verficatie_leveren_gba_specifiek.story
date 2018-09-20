Meta:
@sprintnummer           75
@epic                   Afhankelijkheden migratie
@auteur                 devel
@jiraIssue              TEAMBRP-3083
@status                 Uitgeschakeld

Narrative:
Als afnemer wil ik GBA specifieke gegevens geleverd (kunnen) krijgen, dit zodat ik kan bepalen in hoeverre de GBA gegevens voor het
laatst zijn bijgewerkt en of de PL is geconverteerd of niet.

Uit de analyse volgt dat het voor de levering gaat om de volgende drie attributen (voor release 3.1):
– Datum/tijdstip laatste wijziging GBA-systematiek (Persoon.AfgeleidAdministratief.TijdstipLaatsteWijzigingGBASystematiek)
– Gemeente persoonskaart (Persoon.Persoonskaart.PartijCode)
– Persoonskaart volledig geconverteerd? (Persoon.Persoonskaart.IndicatieVolledigGeconverteerd)

Scenario: 1. Volledigbericht

Given de database is gereset voor de personen 306867837, 306741817
Given de personen 809783174 zijn verwijderd
Given de standaardpersoon Vanilla met bsn 809783174 en anr 7825265938 zonder extra gebeurtenissen


Given de database is aangepast met:
--maak initiele records aan voor de gba persoonskaart attributen, gebruikt geboorte actie
insert into kern.his_perspk  (pers, tsreg, actieinh, gempk, indpkvollediggeconv)
	SELECT hp.pers, hp.tslaatstewijz, hp.actieinh, p.bijhpartij, 'T'
	FROM kern.his_persafgeleidadministrati hp
	JOIN kern.pers p ON (hp.pers = p.id)
	WHERE hp.pers = (SELECT id FROM kern.pers where bsn = 809783174)
;

--vul tslaatstewijzgbasystematiek attribuut
update kern.his_persafgeleidadministrati
	set tslaatstewijzgbasystematiek = tsreg
	WHERE pers = (SELECT id FROM kern.pers where bsn = 809783174)
;


When voor persoon 809783174 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep 		| nummer    | attribuut              			| verwachteWaarde 	|
| persoonskaart | 1         | partijCode 						| 034401      		|
| persoonskaart | 1         | indicatieVolledigGeconverteerd 	| J      			|

Then is de aanwezigheid van 'tijdstipLaatsteWijzigingGBASystematiek' in 'afgeleidAdministratief' nummer 0 WAAR


Scenario: 2. Mutatiebericht

Given de persoon beschrijvingen:

def ice = Persoon.uitDatabase(bsn: 809783174)
Persoon.nieuweGebeurtenissenVoor(ice) {
    verbeteringGeboorteakte(partij: 34401, aanvang: 19870103, toelichting:'Correctie datumGeboorte en wplGeboorte', registratieDatum: 19870103){
        op '1967/02/01' te 'Delft' gemeente 503
    }
}
slaOp(ice)

Given de database is aangepast met:

--maak vervallen record aan voor gba persoonskaart, gebruik actie verbeteringGeboorteakte voor verval
update kern.his_perspk set tsverval = now(), actieverval = (SELECT hp.actieverval
	FROM kern.his_persafgeleidadministrati hp
	WHERE hp.pers = (SELECT id FROM kern.pers where bsn = 809783174)
	AND hp.actieverval is not null
);

--maak nieuw record aan voor de gba persoonskaart, gebruikt verbeteringGeboorteakte actie
insert into kern.his_perspk  (pers, tsreg, actieinh, gempk, indpkvollediggeconv)
	SELECT hp.pers, pk.tsverval, hp.actieverval, pk.gempk, 'F'
	FROM kern.his_perspk pk
	JOIN kern.his_persafgeleidadministrati hp ON (hp.pers = pk.pers)
	WHERE pk.pers = (SELECT id FROM kern.pers where bsn = 809783174)
	AND hp.actieverval is not null
	AND pk.actieverval is not null
;

--vul tslaatstewijzgbasystematiek attribuut van nieuwe record
update kern.his_persafgeleidadministrati
	set tslaatstewijzgbasystematiek = tsreg
	WHERE pers = (SELECT id FROM kern.pers where bsn = 809783174)
	AND actieverval is null
;

When voor persoon 809783174 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep 		| nummer    | attribuut              			| verwachteWaarde 	|
| persoonskaart | 1         | partijCode 						| 034401      		|
| persoonskaart | 1         | indicatieVolledigGeconverteerd 	| N      			|
| persoonskaart | 2         | partijCode 						| 034401      		|
| persoonskaart | 2         | indicatieVolledigGeconverteerd 	| J      			|
Then is de aanwezigheid van 'tijdstipLaatsteWijzigingGBASystematiek' in 'afgeleidAdministratief' nummer 0 WAAR
Then is de aanwezigheid van 'tijdstipLaatsteWijzigingGBASystematiek' in 'afgeleidAdministratief' nummer 1 WAAR
