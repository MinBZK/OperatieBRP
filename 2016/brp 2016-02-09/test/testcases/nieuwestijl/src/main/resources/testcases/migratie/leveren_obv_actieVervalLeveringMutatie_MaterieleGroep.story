Meta:
@sprintnummer           74
@epic                   Afhankelijkheden migratie
@auteur                 anjaw
@jiraIssue              TEAMBRP-2837, TEAMBRP-2838, TEAMBRP-2840
@status                 Klaar
@regels                 VR00102, VR00079, VR00073


Narrative:
   Als afnemer wil ik dat de wel/niet leveren bepaling ook goed gaat bij bijhoudingen via het LO3 koppelvlak, zodat ik geen LO3 bijhoudingen misloop.
   Deze story gaat over het toepassen van attribuut actieVervalLeveringMutatie in de bepaling van WANNEER er geleverd moet worden (aanpassing VR00102).
   Dit betreft de diensten Mutatielevering op Doelbinding en Attendering (met en zonder plaatsen afnemerindicatie)

   R1557	VR00102	Reconstructie actueel persoonsbeeld tbv expressie-evaluatie
   R1973	VR00079	Een mutatiebericht bevat slechts groepen die in de administratieve handeling zijn geraakt, gemarkeerd zijn als 'in onderzoek' of een identificerende groep zijn
   R1317	VR00073	Bepalen verwerkingssoort van groepsvoorkomens

Scenario: 1. Mutatielevering op doelbinding gaat correct voor materiele groepen

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 201627504 zijn verwijderd
Given de persoon beschrijvingen:
def PapaLaar    = uitDatabase bsn: 306867837
def MamaVerheul = uitDatabase bsn: 306741817

testpersoon = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19911111, toelichting: '1e kind', registratieDatum: 19911111) {
        op '1991/11/11' te 'Delft' gemeente 503
        geslacht 'MAN'
        namen {
            voornamen 'Pjotr'
            geslachtsnaam 'Laar'
        }
        ouders moeder: MamaVerheul, vader: PapaLaar
        identificatienummers bsn: 201627504, anummer: 9370627858
    }

    verhuizing(partij: 'Gemeente Gorinchem', aanvang: 20120515, registratieDatum: 20150515) {
        naarGemeente 'Gorinchem',
            straat: 'Westwagenstraat', nummer: 25, postcode: '4201AD', woonplaats: "Gorinchem"
    }
}

slaOp(testpersoon)

Given de database is aangepast met:

--SELECT a.id, hp.tslaatstewijz
--FROM kern.admhnd a
--JOIN kern.his_persafgeleidadministrati hp ON (hp.admhnd = a.id)
--WHERE hp.pers = (SELECT id FROM kern.pers where bsn = 420178648)
--ORDER BY hp.tslaatstewijz DESC, hp.id DESC
--LIMIT 1

-- Zet de indVoorkomenTbvLevMuts van de vervallen Persoon/Adres groep op 'Ja'.
UPDATE kern.his_persadres
SET indvoorkomentbvlevmuts = true
WHERE id =
	(
	SELECT khpa.id
	FROM kern.his_persadres khpa
	WHERE actieverval =
		(
		SELECT hpa.actieinh
		FROM kern.persadres pa
		JOIN kern.his_persadres hpa ON (pa.id = hpa.persadres)
		WHERE pa.pers = (SELECT id FROM kern.pers WHERE bsn = 201627504)
		AND actieverval IS NULL
		AND actieaanpgel IS NULL
		)
	);

--Zet de tsReg van de gewijzigde/beëindigde Persoon/Adres groep naar de tsReg van de vervallen Persoon/Adres groep.
UPDATE kern.his_persadres
SET tsreg =
	(
	SELECT khpa.tsreg
	FROM kern.his_persadres khpa
	WHERE actieverval =
		(
		SELECT hpa.actieinh
		FROM kern.persadres pa
		JOIN kern.his_persadres hpa ON (pa.id = hpa.persadres)
		WHERE pa.pers = (SELECT id FROM kern.pers WHERE bsn = 201627504)
		AND actieverval IS NULL
		AND actieaanpgel IS NULL
		)
	)
WHERE id =
	(
	SELECT khpa.id
	FROM kern.his_persadres khpa
	WHERE actieaanpgel =
		(
		SELECT hpa.actieinh
		FROM kern.persadres pa
		JOIN kern.his_persadres hpa ON (pa.id = hpa.persadres)
		WHERE pa.pers = (SELECT id FROM kern.pers WHERE bsn = 201627504)
		AND actieverval IS NULL
		AND actieaanpgel IS NULL
		)
	);

-- Zet de tsVerval van de vervallen Persoon/Adres groep naar de waarde die reeds in tsReg staat.
UPDATE kern.his_persadres
SET tsverval = tsreg
WHERE id =
	(
	SELECT khpa.id
	FROM kern.his_persadres khpa
	WHERE actieverval =
		(
		SELECT hpa.actieinh
		FROM kern.persadres pa
		JOIN kern.his_persadres hpa ON (pa.id = hpa.persadres)
		WHERE pa.pers = (SELECT id FROM kern.pers WHERE bsn = 201627504)
		AND actieverval IS NULL
		AND actieaanpgel IS NULL
		)
	);

-- Vul actieVervalLeveringMutatie in de vervallen Persoon/Adres groep met de waarde uit actieVerval.
UPDATE kern.his_persadres
SET actievervaltbvlevmuts = actieverval
WHERE id =
	(
	SELECT khpa.id
	FROM kern.his_persadres khpa
	WHERE actieverval =
		(
		SELECT hpa.actieinh
		FROM kern.persadres pa
		JOIN kern.his_persadres hpa ON (pa.id = hpa.persadres)
		WHERE pa.pers = (SELECT id FROM kern.pers WHERE bsn = 201627504)
		AND actieverval IS NULL
		AND actieaanpgel IS NULL
		)
	);

-- Vul actieVerval in de vervallen Persoon/Adres groep met de waarde uit actieInhoud.
UPDATE kern.his_persadres
SET actieverval = actieinh
WHERE id =
	(
	SELECT khpa.id
	FROM kern.his_persadres khpa
	WHERE actieverval =
		(
		SELECT hpa.actieinh
		FROM kern.persadres pa
		JOIN kern.his_persadres hpa ON (pa.id = hpa.persadres)
		WHERE pa.pers = (SELECT id FROM kern.pers WHERE bsn = 201627504)
		AND actieverval IS NULL
		AND actieaanpgel IS NULL
		)
	);


When voor persoon 201627504 wordt de laatste handeling geleverd

!-- [TEAMBRP-2837] Controleer of een mutatiebericht geleverd wordt voor opgegeven abonnement
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then heeft het bericht 3 groepen 'adres'

!-- [TEAMBRP-2838] Controleer of de volgorde van voorkomens correct is adhv data
And hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep | nummer    | verwerkingssoort  |
| adres | 1         | Toevoeging        |
| adres | 2         | Wijziging         |
| adres | 3         | Verval            |

!-- [TEAMBRP-2838] Controleer of de volgorde van voorkomens correct is adhv data
And hebben attributen in voorkomens de volgende waardes:
| groep | nummer    | attribuut              | verwachteWaarde |
| adres | 1         | datumAanvangGeldigheid | 2012-05-15      |
| adres | 2         | datumAanvangGeldigheid | 1991-11-11      |
| adres | 3         | datumAanvangGeldigheid | 1991-11-11      |

!-- [TEAMBRP-2840] Controleer of de juiste verantwoording getoond wordt en juiste data velden via aanwezigheid van elementen
And hebben attributen in voorkomens de volgende aanwezigheid:
| groep | nummer | attribuut					| aanwezig 	|
| adres	| 1      | actieInhoud  				| ja       	|
| adres	| 1      | actieAanpassingGeldigheid	| nee      	|
| adres	| 1      | actieVerval  				| nee      	|
| adres	| 2      | actieInhoud				   	| nee		|
| adres	| 2      | actieAanpassingGeldigheid   	| ja 		|
| adres	| 2      | actieVerval				   	| nee 		|
| adres	| 2      | datumEindeGeldigheid		   	| ja 		|
| adres	| 3      | actieInhoud   				| nee		|
| adres	| 3      | actieAanpassingGeldigheid	| nee		|
| adres	| 3      | actieVerval   				| nee		|
| adres	| 3      | datumEindeGeldigheid			| nee		|
