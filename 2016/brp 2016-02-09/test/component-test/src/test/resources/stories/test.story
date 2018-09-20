Meta:
@auteur                 devel
@epic                   Levering onderzoek
@jiraIssue              TEAMBRP-4309
@sleutelwoorden         onderzoek
@status                 Klaar
@regels                 R1319
@componenten            database,routeringcentrale,mutatielevering

Narrative: blablalbalblalbalb

Scenario: 1. lever persoon1

Given de persoon beschrijvingen:
tester = uitGebeurtenissen {
    geboorte(partij: 'Gemeente Westland', aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Willem', 'Johannes'
            geslachtsnaam stam: 'Vorstenhove', voorvoegsel: 'van', adellijkeTitel: 'P'

            samengesteldeNaam voornamen: 'Manuèl', stam: 'Override', scheidingsteken: '-'
        }
        identificatienummers bsn: 123434538, anummer: 8934753756
        nationaliteiten 'Canadese', 'Turkse'
    }
}
tester = slaOp(tester)

Given leveringautorisatie uit '/autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding'
When voor persoon 826569353 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringautorisatie 'Geen pop.bep. levering op basis van doelbinding' is ontvangen en wordt bekeken
Then is het bericht xsd-valide



Scenario: 2. lever persoon2

Given de persoon beschrijvingen:
tester = uitGebeurtenissen {
    geboorte(partij: 'Gemeente Westland', aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Willem', 'Johannes'
            geslachtsnaam stam: 'Vorstenhove', voorvoegsel: 'van', adellijkeTitel: 'P'

            samengesteldeNaam voornamen: 'Manuèl', stam: 'Override', scheidingsteken: '-'
        }
        identificatienummers bsn: 123434538, anummer: 8934753756
        nationaliteiten 'Canadese', 'Turkse'
    }
}
tester = slaOp(tester)

Given leveringautorisatie uit '/autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding'
When voor persoon 826569353 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringautorisatie 'Geen pop.bep. levering op basis van doelbinding' is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 3. lever persoon3

Given de persoon beschrijvingen:
tester = uitGebeurtenissen {
    geboorte(partij: 'Gemeente Westland', aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Willem', 'Johannes'
            geslachtsnaam stam: 'Vorstenhove', voorvoegsel: 'van', adellijkeTitel: 'P'

            samengesteldeNaam voornamen: 'Manuèl', stam: 'Override', scheidingsteken: '-'
        }
        identificatienummers bsn: 123434538, anummer: 8934753756
        nationaliteiten 'Canadese', 'Turkse'
    }
}
tester = slaOp(tester)

Given leveringautorisatie uit '/autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding'
When voor persoon 826569353 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringautorisatie 'Geen pop.bep. levering op basis van doelbinding' is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario: 4. lever persoon4

Given de persoon beschrijvingen:
tester = uitGebeurtenissen {
    geboorte(partij: 'Gemeente Westland', aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Willem', 'Johannes'
            geslachtsnaam stam: 'Vorstenhove', voorvoegsel: 'van', adellijkeTitel: 'P'

            samengesteldeNaam voornamen: 'Manuèl', stam: 'Override', scheidingsteken: '-'
        }
        identificatienummers bsn: 123434538, anummer: 8934753756
        nationaliteiten 'Canadese', 'Turkse'
    }
}
tester = slaOp(tester)

Given leveringautorisatie uit '/autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding'
When voor persoon 826569353 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringautorisatie 'Geen pop.bep. levering op basis van doelbinding' is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 5. lever persoon5

Given de persoon beschrijvingen:
tester = uitGebeurtenissen {
    geboorte(partij: 'Gemeente Westland', aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Willem', 'Johannes'
            geslachtsnaam stam: 'Vorstenhove', voorvoegsel: 'van', adellijkeTitel: 'P'

            samengesteldeNaam voornamen: 'Manuèl', stam: 'Override', scheidingsteken: '-'
        }
        identificatienummers bsn: 123434538, anummer: 8934753756
        nationaliteiten 'Canadese', 'Turkse'
    }
}
tester = slaOp(tester)

Given leveringautorisatie uit '/autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding'
When voor persoon 826569353 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringautorisatie 'Geen pop.bep. levering op basis van doelbinding' is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario: 6. lever persoon6

Given de persoon beschrijvingen:
tester = uitGebeurtenissen {
    geboorte(partij: 'Gemeente Westland', aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Willem', 'Johannes'
            geslachtsnaam stam: 'Vorstenhove', voorvoegsel: 'van', adellijkeTitel: 'P'

            samengesteldeNaam voornamen: 'Manuèl', stam: 'Override', scheidingsteken: '-'
        }
        identificatienummers bsn: 123434538, anummer: 8934753756
        nationaliteiten 'Canadese', 'Turkse'
    }
}
tester = slaOp(tester)

Given leveringautorisatie uit '/autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding'
When voor persoon 826569353 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringautorisatie 'Geen pop.bep. levering op basis van doelbinding' is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 7. lever persoon7

Given de persoon beschrijvingen:
tester = uitGebeurtenissen {
    geboorte(partij: 'Gemeente Westland', aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Willem', 'Johannes'
            geslachtsnaam stam: 'Vorstenhove', voorvoegsel: 'van', adellijkeTitel: 'P'

            samengesteldeNaam voornamen: 'Manuèl', stam: 'Override', scheidingsteken: '-'
        }
        identificatienummers bsn: 123434538, anummer: 8934753756
        nationaliteiten 'Canadese', 'Turkse'
    }
}
tester = slaOp(tester)

Given leveringautorisatie uit '/autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding'
When voor persoon 826569353 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringautorisatie 'Geen pop.bep. levering op basis van doelbinding' is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 8. lever persoon8

Given de persoon beschrijvingen:
tester = uitGebeurtenissen {
    geboorte(partij: 'Gemeente Westland', aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Willem', 'Johannes'
            geslachtsnaam stam: 'Vorstenhove', voorvoegsel: 'van', adellijkeTitel: 'P'

            samengesteldeNaam voornamen: 'Manuèl', stam: 'Override', scheidingsteken: '-'
        }
        identificatienummers bsn: 123434538, anummer: 8934753756
        nationaliteiten 'Canadese', 'Turkse'
    }
}
tester = slaOp(tester)

Given leveringautorisatie uit '/autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding'
When voor persoon 826569353 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringautorisatie 'Geen pop.bep. levering op basis van doelbinding' is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario: 9. lever persoon9

Given de persoon beschrijvingen:
tester = uitGebeurtenissen {
    geboorte(partij: 'Gemeente Westland', aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Willem', 'Johannes'
            geslachtsnaam stam: 'Vorstenhove', voorvoegsel: 'van', adellijkeTitel: 'P'

            samengesteldeNaam voornamen: 'Manuèl', stam: 'Override', scheidingsteken: '-'
        }
        identificatienummers bsn: 123434538, anummer: 8934753756
        nationaliteiten 'Canadese', 'Turkse'
    }
}
tester = slaOp(tester)

Given leveringautorisatie uit '/autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding'
When voor persoon 826569353 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringautorisatie 'Geen pop.bep. levering op basis van doelbinding' is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario: 10. lever persoon10

Given de persoon beschrijvingen:
tester = uitGebeurtenissen {
    geboorte(partij: 'Gemeente Westland', aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Willem', 'Johannes'
            geslachtsnaam stam: 'Vorstenhove', voorvoegsel: 'van', adellijkeTitel: 'P'

            samengesteldeNaam voornamen: 'Manuèl', stam: 'Override', scheidingsteken: '-'
        }
        identificatienummers bsn: 123434538, anummer: 8934753756
        nationaliteiten 'Canadese', 'Turkse'
    }
}
tester = slaOp(tester)

Given leveringautorisatie uit '/autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding'
When voor persoon 826569353 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringautorisatie 'Geen pop.bep. levering op basis van doelbinding' is ontvangen en wordt bekeken
Then is het bericht xsd-valide
