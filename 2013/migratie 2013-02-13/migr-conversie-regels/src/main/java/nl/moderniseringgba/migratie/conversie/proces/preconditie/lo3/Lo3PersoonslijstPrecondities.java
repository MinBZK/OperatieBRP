/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;

import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor een volledige LO3 persoonslijst.
 * 
 * Maakt gebruik van de {@link nl.moderniseringgba.migratie.conversie.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3PersoonslijstPrecondities extends Lo3Precondities {

    @Inject
    private Lo3PersoonPrecondities persoonPrecondities;
    @Inject
    private Lo3Ouder1Precondities ouder1Precondities;
    @Inject
    private Lo3Ouder2Precondities ouder2Precondities;
    @Inject
    private Lo3NationaliteitPrecondities nationaliteitPrecondities;
    @Inject
    private Lo3HuwelijkPrecondities huwelijkPrecondities;
    @Inject
    private Lo3OverlijdenPrecondities overlijdenPrecondities;
    @Inject
    private Lo3InschrijvingPrecondities inschrijvingPrecondities;
    @Inject
    private Lo3VerblijfplaatsPrecondities verblijfplaatsPrecondities;
    @Inject
    private Lo3KindPrecondities kindPrecondities;
    @Inject
    private Lo3VerblijfstitelPrecondities verblijfstitelPrecondities;
    @Inject
    private Lo3GezagsverhoudingPrecondities gezagsverhoudingPrecondities;
    @Inject
    private Lo3ReisdocumentPrecondities reisdocumentPrecondities;
    @Inject
    private Lo3KiesrechtPrecondities kiesrechtPrecondities;

    /**
     * Voer de precondities voor een volledige persoonlijst uit.
     * 
     * @param persoonslijst
     *            persoonslijst
     */
    public void controleerPersoonslijst(final Lo3Persoonslijst persoonslijst) {
        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = persoonslijst.getPersoonStapel();
        final List<Lo3Stapel<Lo3OuderInhoud>> ouder1Stapels = persoonslijst.getOuder1Stapels();
        final List<Lo3Stapel<Lo3OuderInhoud>> ouder2Stapels = persoonslijst.getOuder2Stapels();
        final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel = persoonslijst.getInschrijvingStapel();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel = persoonslijst.getVerblijfplaatsStapel();

        if (isEmptyOrNull(persoonStapel)) {
            Foutmelding.logPreconditieFout(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, -1, -1), LogSeverity.ERROR,
                    Precondities.PRE047, "Categorie 01: Persoon moet verplicht voorkomen in een persoonslijst.");
        }

        if (isEmptyOrNull(ouder1Stapels)) {
            Foutmelding.logPreconditieFout(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, -1, -1),
                    LogSeverity.WARNING, Precondities.PRE065,
                    "Categorie 02: Ouder 1 moet verplicht voorkomen in een persoonslijst.");
        } else {
            if (ouder1Stapels.size() > 1) {
                Foutmelding.logStructuurFout(null, LogSeverity.WARNING, "Categorie 02: "
                        + "Ouder 1 mag slechts 1 keer voorkomen (dit kan alleen voorkomen tijdens de "
                        + "conversie).");
            }
        }

        if (isEmptyOrNull(ouder2Stapels)) {
            Foutmelding.logPreconditieFout(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_03, -1, -1),
                    LogSeverity.WARNING, Precondities.PRE066,
                    "Categorie 03: Ouder 2 moet verplicht voorkomen in een persoonslijst.");
        } else {
            if (ouder2Stapels.size() > 1) {
                Foutmelding.logStructuurFout(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_03, -1, -1),
                        LogSeverity.WARNING,
                        "Categorie 03: Ouder 2 mag slechts 1 keer voorkomen (dit kan alleen voorkomen"
                                + " tijdens de conversie).");
            }
        }

        if (isEmptyOrNull(inschrijvingStapel)) {
            Foutmelding.logPreconditieFout(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, -1, -1), LogSeverity.ERROR,
                    Precondities.PRE032, "Categorie 07: Inschrijving moet verplicht voorkomen in een persoonslijst.");
        }

        if (isEmptyOrNull(verblijfplaatsStapel)) {
            Foutmelding.logPreconditieFout(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, -1, -1), LogSeverity.ERROR,
                    Precondities.PRE033,
                    "Categorie 08: Verblijfplaats moet verplicht voorkomen in een persoonslijst.");
        }

        persoonPrecondities.controleerStapel(persoonStapel);
        ouder1Precondities.controleerStapels(ouder1Stapels);
        ouder2Precondities.controleerStapels(ouder2Stapels);
        nationaliteitPrecondities.controleerStapels(persoonslijst.getNationaliteitStapels());
        huwelijkPrecondities.controleerStapels(persoonslijst.getHuwelijkOfGpStapels());
        overlijdenPrecondities.controleerStapel(persoonslijst.getOverlijdenStapel());
        inschrijvingPrecondities.controleerStapel(inschrijvingStapel);
        verblijfplaatsPrecondities.controleerStapel(verblijfplaatsStapel);
        kindPrecondities.controleerStapels(persoonslijst.getKindStapels());
        verblijfstitelPrecondities.controleerStapel(persoonslijst.getVerblijfstitelStapel());
        gezagsverhoudingPrecondities.controleerStapel(persoonslijst.getGezagsverhoudingStapel());
        reisdocumentPrecondities.controleerStapels(persoonslijst.getReisdocumentStapels());
        kiesrechtPrecondities.controleerStapel(persoonslijst.getKiesrechtStapel());
    }

    private boolean isEmptyOrNull(final Lo3Stapel<? extends Lo3CategorieInhoud> categorie) {
        return categorie == null || categorie.isEmpty();
    }

    private boolean isEmptyOrNull(final List<Lo3Stapel<Lo3OuderInhoud>> categorie) {
        return categorie == null || categorie.isEmpty();
    }
}
