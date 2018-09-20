Meta:
@sprintnummer           74
@epic                   Afhankelijkheden migratie
@auteur                 devel
@jiraIssue              TEAMBRP-2842
@status                 Klaar
@regels                 VR00125, R1353

Narrative:
Als afnemer will ik geen voorkomens met vorkomenLeveringMutatie?=Ja geleverd krijgen in een VolledigBericht.
R1353	VR00125	Voorkomen voor levering mutatie niet opnemen in VolledigBericht

Scenario:   1. voorkomens niet leveren bij mutatielevering
            Verwacht resultaat:
            - Vervallen groep geboorte van het hoofdpersoon wordt niet getoond (dus 3 groepen 'geboorte i.p.v. 4)

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/Abo_op_basis_van_postcode_en_geboortedatum_persoon
Given de persoon beschrijvingen:
def adam    = Persoon.uitDatabase(bsn: 306867837)
def eva     = Persoon.uitDatabase(bsn: 306741817)

tester = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 20011112, toelichting: '1e kind', registratieDatum: 20011112) {
        op '2001/11/13' te 'Giessenlanden' gemeente 689
        geslacht 'MAN'
        namen {
            voornamen 'Petrus', 'Matheus'
            geslachtsnaam 'Smith'

        }
        ouders moeder: eva, vader: adam
        identificatienummers bsn: 389570667, anummer: 7260678418
    }

    verbeteringGeboorteakte(partij: 34401, aanvang: 20040102, toelichting:'Correctie datumGeboorte en wplGeboorte', registratieDatum: 20050909){
        op '2004/01/02' te 'Giessenlanden' gemeente 689
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
		WHERE pa.pers = (SELECT id FROM kern.pers WHERE bsn = 389570667)
		AND actieverval IS NULL
		)
	);


When voor persoon 389570667 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Abo op basis van postcode en geboortedatum persoon is ontvangen en wordt bekeken
Then heeft het bericht 3 groepen 'geboorte'


Scenario:   2. voorkomens niet leveren bij synchroniseer persoon
            Verwacht resultaat:
            - Vervallen groep geboorte van het hoofdpersoon wordt niet getoond (dus 3 groepen 'geboorte i.p.v. 4)

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand niet_leveren_leveringmutatie_voorkomens_in_volledigbericht_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then heeft het bericht 3 groepen 'geboorte'


Scenario:   3. voorkomens niet leveren bij plaatsen afnemerindicatie
            Verwacht resultaat:
            - Vervallen groep geboorte van het hoofdpersoon wordt niet getoond (dus 3 groepen 'geboorte i.p.v. 4)

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand niet_leveren_leveringmutatie_voorkomens_in_volledigbericht_opnieuw_plaatsen_afnemerindicatie_03.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then heeft het bericht 3 groepen 'geboorte'


Scenario:   4. voorkomens niet leveren bij geefDetailsPersoon voor Afnemers
            Verwacht resultaat:
            - Vervallen groep geboorte van het hoofdpersoon wordt niet getoond (dus 3 groepen 'geboorte i.p.v. 4)

Given leveringsautorisatie uit /levering_autorisaties/abo_geef_details_persoon
Given verzoek voor leveringsautorisatie 'Abo GeefDetailsPersoon' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand niet_leveren_leveringmutatie_voorkomens_in_volledigbericht_geefDetailsPersoon_Afnemer_04.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 3 groepen 'geboorte'

Scenario:   5. voorkomens niet leveren bij geefDetailsPersoon voor Bijhoudres
            Verwacht resultaat:
            - Vervallen groep geboorte van het hoofdpersoon wordt niet getoond (dus 3 groepen 'geboorte i.p.v. 4)

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
Given verzoek van bericht bhg_bvgGeefDetailsPersoon
And testdata uit bestand niet_leveren_leveringmutatie_voorkomens_in_volledigbericht_geefDetailsPersoon_Bijhouder_05.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 3 groepen 'geboorte'
