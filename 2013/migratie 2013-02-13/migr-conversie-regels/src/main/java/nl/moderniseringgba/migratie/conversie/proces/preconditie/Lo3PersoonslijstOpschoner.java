/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.model.logging.LogType;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;

import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public final class Lo3PersoonslijstOpschoner {

    /**
     * Schoon de persoonlijst op aan de hand van de logging.
     * 
     * Nota: LET OP! Gebruikt de Logging.context
     * 
     * @param persoonslijst
     *            op te schonen persoonslijst
     * @return opgeschoonde persoonlijst
     */
    public Lo3Persoonslijst opschonen(final Lo3Persoonslijst persoonslijst) {
        final Logging logging = Logging.getLogging();

        if (containsErrorLevel(logging)) {
            return opschonenPersoonslijst(persoonslijst, logging);
        } else {
            return persoonslijst;
        }
    }

    private boolean containsErrorLevel(final Logging logging) {
        for (final LogRegel regel : logging.getRegels()) {
            if (regel.hasSeverityLevelError()) {
                return true;
            }
        }

        return false;
    }

    private Lo3Persoonslijst opschonenPersoonslijst(final Lo3Persoonslijst persoonslijst, final Logging logging) {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(opschonenStapel(persoonslijst.getPersoonStapel(), logging));
        builder.ouder1Stapels(opschonenStapels(persoonslijst.getOuder1Stapels(), logging));
        builder.ouder2Stapels(opschonenStapels(persoonslijst.getOuder2Stapels(), logging));
        builder.nationaliteitStapels(opschonenStapels(persoonslijst.getNationaliteitStapels(), logging));
        builder.huwelijkOfGpStapels(opschonenStapels(persoonslijst.getHuwelijkOfGpStapels(), logging));
        builder.overlijdenStapel(opschonenStapel(persoonslijst.getOverlijdenStapel(), logging));
        builder.inschrijvingStapel(opschonenStapel(persoonslijst.getInschrijvingStapel(), logging));
        builder.verblijfplaatsStapel(opschonenStapel(persoonslijst.getVerblijfplaatsStapel(), logging));
        builder.kindStapels(opschonenStapels(persoonslijst.getKindStapels(), logging));
        builder.verblijfstitelStapel(opschonenStapel(persoonslijst.getVerblijfstitelStapel(), logging));
        builder.gezagsverhoudingStapel(opschonenStapel(persoonslijst.getGezagsverhoudingStapel(), logging));
        builder.reisdocumentStapels(opschonenStapels(persoonslijst.getReisdocumentStapels(), logging));
        builder.kiesrechtStapel(opschonenStapel(persoonslijst.getKiesrechtStapel(), logging));

        return builder.build();
    }

    private <T extends Lo3CategorieInhoud> List<Lo3Stapel<T>> opschonenStapels(
            final List<Lo3Stapel<T>> stapels,
            final Logging logging) {
        final List<Lo3Stapel<T>> result = new ArrayList<Lo3Stapel<T>>();

        for (final Lo3Stapel<T> stapel : stapels) {
            final Lo3Stapel<T> opgeschoondeStapel = opschonenStapel(stapel, logging);

            if (opgeschoondeStapel != null) {
                result.add(opgeschoondeStapel);
            }
        }

        return result;
    }

    private <T extends Lo3CategorieInhoud> Lo3Stapel<T> opschonenStapel(
            final Lo3Stapel<T> stapel,
            final Logging logging) {
        if (stapel == null) {
            return null;
        }

        final List<Lo3Categorie<T>> result = new ArrayList<Lo3Categorie<T>>();
        for (final Lo3Categorie<T> categorie : stapel) {
            final Lo3Categorie<T> geschoondeCategorie = opschonenCategorie(categorie, logging);
            if (geschoondeCategorie != null) {
                result.add(geschoondeCategorie);
            }
        }

        return result.isEmpty() ? null : new Lo3Stapel<T>(result);
    }

    private <T extends Lo3CategorieInhoud> Lo3Categorie<T> opschonenCategorie(
            final Lo3Categorie<T> categorie,
            final Logging logging) {
        // Als dit om een onjuiste rij gaat dan skippen we alleen deze en zetten we de logniveau naar
        // REMOVED.
        boolean regelRemoved = false;
        for (final LogRegel regel : logging.getRegels()) {
            if (regel.hasSeverityLevelError() && regel.getLo3Herkomst().equals(categorie.getLo3Herkomst())
                    && categorie.getHistorie().getIndicatieOnjuist() != null) {
                regel.setSeverity(LogSeverity.REMOVED);
                regelRemoved = true;
            }
        }
        if (regelRemoved) {
            Logging.log(categorie.getLo3Herkomst(), LogSeverity.WARNING, LogType.VERWERKING, "DELETE",
                    "Het onjuiste categorie voorkomen met fouten is verwijderd.");
            return null;
        }
        return categorie;
    }
}
