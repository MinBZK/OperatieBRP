Meta:
@sprintnummer           74
@epic                   Afhankelijkheden migratie
@auteur                 devel
@jiraIssue              TEAMBRP-2837, TEAMBRP-2838, TEAMBRP-2840
@status                 Klaar
@regels                 VR00102, R1557, VR00079, R1973, VR00073, R1317

Narrative:
   Als afnemer wil ik dat de wel/niet leveren bepaling ook goed gaat bij bijhoudingen via het LO3 koppelvlak, zodat ik geen LO3 bijhoudingen misloop.
   Deze story gaat over het toepassen van attribuut actieVervalLeveringMutatie in de bepaling van WANNEER er geleverd moet worden (aanpassing VR00102).
   Dit betreft de diensten Mutatielevering op Doelbinding en Attendering (met en zonder plaatsen afnemerindicatie)

   R1557	VR00102	Reconstructie actueel persoonsbeeld tbv expressie-evaluatie
   R1973	VR00079	Een mutatiebericht bevat slechts groepen die in de administratieve handeling zijn geraakt, gemarkeerd zijn als 'in onderzoek' of een identificerende groep zijn
   R1317	VR00073	Bepalen verwerkingssoort van groepsvoorkomens


Scenario: 1. Mutatielevering op doelbinding gaat correct voor formele groepen

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 119920025 zijn verwijderd
Given de persoon beschrijvingen:
def PapaLaar    = uitDatabase bsn: 306867837
def MamaVerheul = uitDatabase bsn: 306741817

testpersoon = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19911111, toelichting: '1e kind', registratieDatum: 19911111) {
        op '1991/11/11' te 'Delft' gemeente 503
        geslacht 'MAN'
        namen {
            voornamen 'Paulus'
            geslachtsnaam 'Bos'
        }
        ouders moeder: MamaVerheul, vader: PapaLaar
        identificatienummers bsn: 119920025, anummer: 5269634081
    }

	verbeteringGeboorteakte(partij: 34401, aanvang: 19970103, toelichting:'Correctie datumGeboorte en wplGeboorte', registratieDatum: 19970103){
		op '1967/02/01' te 'Delft' gemeente 503
	}
}

slaOp(testpersoon)

Given de database is aangepast met:

-- Zet de indVoorkomenTbvLevMuts van de vervallen Persoon/Adres groep op 'Ja'.
UPDATE kern.his_persgeboorte
SET indvoorkomentbvlevmuts = true
WHERE id =
	(
	SELECT hpa.id
	FROM kern.his_persgeboorte hpa
	WHERE actieverval =
		(
		SELECT pa.actieinh
		FROM kern.his_persgeboorte pa
		WHERE pa.pers = (SELECT id FROM kern.pers WHERE bsn = 119920025)
		AND actieverval IS NULL
		)
	);

-- Zet de tsVerval van de vervallen Persoon/Adres groep naar de waarde die reeds in tsReg staat.
UPDATE kern.his_persgeboorte
SET tsverval = tsreg
WHERE id =
	(
	SELECT khpa.id
	FROM kern.his_persgeboorte khpa
	WHERE actieverval =
		(
		SELECT pa.actieinh
		FROM kern.his_persgeboorte pa
		WHERE pa.pers = (SELECT id FROM kern.pers WHERE bsn = 119920025)
		AND actieverval IS NULL
		)
	);

-- Vul actieVervalLeveringMutatie in de vervallen Persoon/Adres groep met de waarde uit actieVerval.
UPDATE kern.his_persgeboorte
SET actievervaltbvlevmuts = actieverval
WHERE id =
	(
	SELECT khpa.id
	FROM kern.his_persgeboorte khpa
	WHERE actieverval =
		(
		SELECT pa.actieinh
		FROM kern.his_persgeboorte pa
		WHERE pa.pers = (SELECT id FROM kern.pers WHERE bsn = 119920025)
		AND actieverval IS NULL
		)
	);

-- Vul actieVerval in de vervallen Persoon/Adres groep met de waarde uit actieInhoud.
UPDATE kern.his_persgeboorte
SET actieverval = actieinh
WHERE id =
	(
	SELECT khpa.id
	FROM kern.his_persgeboorte khpa
	WHERE actieverval =
		(
		SELECT pa.actieinh
		FROM kern.his_persgeboorte pa
		WHERE pa.pers = (SELECT id FROM kern.pers WHERE bsn = 119920025)
		AND actieverval IS NULL
		)
	);

When voor persoon 119920025 wordt de laatste handeling geleverd

!-- [TEAMBRP-2837] Controleer of een mutatiebericht geleverd wordt voor opgegeven abonnement
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then heeft het bericht 2 groepen 'geboorte'

!-- [TEAMBRP-2838] Controleer of de volgorde van voorkomens correct is mbt verwerkingssoort
And hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep 	| nummer    | verwerkingssoort  |
| geboorte 	| 1         | Toevoeging        |
| geboorte	| 2         | Verval            |

!-- [TEAMBRP-2838] Controleer of de volgorde van voorkomens correct is adhv data
And hebben attributen in voorkomens de volgende waardes:
| groep 	| nummer  	| attribuut     | verwachteWaarde |
| geboorte 	| 1         | datum 		| 1967-02-01      |
| geboorte	| 2         | datum 		| 1991-11-11      |

!-- [TEAMBRP-2840] Controleer of de juiste verantwoording getoond wordt en juiste data velden via aanwezigheid van elementen
And hebben attributen in voorkomens de volgende aanwezigheid:
| groep 	| nummer | attribuut					| aanwezig 	|
| geboorte	| 1      | actieInhoud  				| ja       	|
| geboorte	| 1      | actieAanpassingGeldigheid	| nee      	|
| geboorte	| 1      | actieVerval					| nee      	|
| geboorte	| 2      | actieInhoud					| nee      	|
| geboorte	| 2      | actieAanpassingGeldigheid	| nee      	|
| geboorte	| 2      | actieVerval					| nee      	|
