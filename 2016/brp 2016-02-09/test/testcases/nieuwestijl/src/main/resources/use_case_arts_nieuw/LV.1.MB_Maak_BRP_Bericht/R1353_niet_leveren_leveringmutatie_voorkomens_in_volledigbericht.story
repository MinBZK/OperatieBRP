Meta:
@sprintnummer           74
@epic                   Afhankelijkheden migratie
@auteur                 devel
@jiraIssue              TEAMBRP-2842
@status                 Klaar
@regels                 VR00125,R1353

Narrative:
Als afnemer will ik geen voorkomens met vorkomenLeveringMutatie?=Ja geleverd krijgen in een VolledigBericht.

Scenario:   1. voorkomens niet leveren bij mutatielevering
            Verwacht resultaat:
            - Vervallen groep geboorte van het hoofdpersoon wordt niet getoond (dus 3 groepen 'geboorte i.p.v. 4)

Given de database is gereset voor de personen 306867837, 306741817
Given de personen 167516681 zijn verwijderd
Given de persoon beschrijvingen:
def adam    = Persoon.uitDatabase(bsn: 306867837)
def eva     = Persoon.uitDatabase(bsn: 306741817)

tester = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19911112, toelichting: '1e kind') {
        op '1991/11/13' te 'Giessenlanden' gemeente 689
        geslacht 'MAN'
        namen {
            voornamen 'Petrus', 'Matheus'
            geslachtsnaam 'Smith'

        }
        ouders moeder: eva, vader: adam
        identificatienummers bsn: 167516681, anummer: 4864723474
    }

    verbeteringGeboorteakte(partij: 34401, aanvang: 19870103, toelichting:'Correctie datumGeboorte en wplGeboorte', registratieDatum: 19870103){
        op '2014/02/01' te 'Giessenlanden' gemeente 689
    }
}

slaOp(tester)

Given de database is aangepast met:

-- Zet de indVoorkomenTbvLevMuts van de vervallen Persoon/Geboorte groep op 'Ja'.
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
		WHERE pa.pers = (SELECT id FROM kern.pers WHERE bsn = 167516681)
		AND actieverval IS NULL
		)
	);


When voor persoon 167516681 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Abo op basis van postcode en geboortedatum persoon is ontvangen en wordt bekeken
Then heeft het bericht 3 groepen 'geboorte'


Scenario:   2. voorkomens niet leveren bij synchroniseer persoon
            Verwacht resultaat:
            - Vervallen groep geboorte van het hoofdpersoon wordt niet getoond (dus 3 groepen 'geboorte i.p.v. 4)

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand R1353_niet_leveren_leveringmutatie_voorkomens_in_volledigbericht_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then heeft het bericht 3 groepen 'geboorte'


Scenario:   3. voorkomens niet leveren bij plaatsen afnemerindicatie
            Verwacht resultaat:
            - Vervallen groep geboorte van het hoofdpersoon wordt niet getoond (dus 3 groepen 'geboorte i.p.v. 4)

Given verzoek van type plaatsingAfnemerindicatie
And testdata uit bestand R1353_niet_leveren_leveringmutatie_voorkomens_in_volledigbericht_opnieuw_plaatsen_afnemerindicatie_03.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then heeft het bericht 3 groepen 'geboorte'


Scenario:   4. voorkomens niet leveren bij geefDetailsPersoon voor Afnemers
            Verwacht resultaat:
            - Vervallen groep geboorte van het hoofdpersoon wordt niet getoond (dus 3 groepen 'geboorte i.p.v. 4)

Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand R1353_niet_leveren_leveringmutatie_voorkomens_in_volledigbericht_geefDetailsPersoon_Afnemer_04.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 3 groepen 'geboorte'

Scenario:   5. voorkomens niet leveren bij geefDetailsPersoon voor Bijhoudres
            Verwacht resultaat:
            - Vervallen groep geboorte van het hoofdpersoon wordt niet getoond (dus 3 groepen 'geboorte i.p.v. 4)

Given verzoek van bericht bhg_bvgGeefDetailsPersoon
And testdata uit bestand R1353_niet_leveren_leveringmutatie_voorkomens_in_volledigbericht_geefDetailsPersoon_Bijhouder_05.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 3 groepen 'geboorte'
