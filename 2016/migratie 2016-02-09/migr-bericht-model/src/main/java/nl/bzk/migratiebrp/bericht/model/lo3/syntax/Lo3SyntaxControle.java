/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.syntax;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Syntax controle.
 */
public final class Lo3SyntaxControle {

    private static final Pattern NUMERIEK_PATTERN = Pattern.compile("^[0-9]*$");

    private static final String GROEP_ERROR = "Categorie %s mag groep %s niet bevatten.";
    private static final String NUMERIEK_ERROR = "Element %s moet een numerieke waarde bevatten.";
    private static final String EXACTE_LENGTE_ERROR = "Element %s moet exact %d lang zijn.";
    private static final String MINIMUM_LENGTE_ERROR = "Element %s moet minimaal %d lang zijn.";
    private static final String MAXIMUM_LENGTE_ERROR = "Element %s mag maximaal %d lang zijn.";

    /**
     * Controleer bericht inhoud.
     *
     * @param categorieWaarden
     *            bericht inhoud
     * @throws BerichtInhoudException
     *             als een fout is aangetroffen
     */
    public void controleerInhoud(final List<Lo3CategorieWaarde> categorieWaarden) throws BerichtInhoudException {
        for (final Lo3CategorieWaarde categorieWaarde : categorieWaarden) {
            controleerCategorie(categorieWaarde);
        }
    }

    private void controleerCategorie(final Lo3CategorieWaarde categorieWaarde) throws BerichtInhoudException {
        controleerGroepen(categorieWaarde);
        controleerElementen(categorieWaarde);
    }

    private void controleerGroepen(final Lo3CategorieWaarde categorieWaarde) throws BerichtInhoudException {
        final List<Lo3GroepEnum> groepen = categorieWaarde.getCategorie().getGroepen();

        for (final Lo3ElementEnum definitie : categorieWaarde.getElementen().keySet()) {
            final Lo3GroepEnum groep = definitie.getGroep();

            if (!groepen.contains(groep)) {
                throw new BerichtInhoudException(String.format(GROEP_ERROR, categorieWaarde.getCategorie().getCategorie(), groep));
            }
        }
    }

    private void controleerElementen(final Lo3CategorieWaarde categorieWaarde) throws BerichtInhoudException {
        for (final Map.Entry<Lo3ElementEnum, String> element : categorieWaarde.getElementen().entrySet()) {
            controleerElement(element);
        }
    }

    private void controleerElement(final Entry<Lo3ElementEnum, String> element) throws BerichtInhoudException {
        if (element.getValue() == null) {
            return;
        }

        final Lo3ElementEnum definitie = element.getKey();

        if (definitie.getType() == Lo3ElementEnum.Type.NUMERIEK && !NUMERIEK_PATTERN.matcher(element.getValue()).matches()) {
            throw new BerichtInhoudException(String.format(NUMERIEK_ERROR, definitie.getElementNummer()));
        }

        if (definitie.getMinimumLengte() == definitie.getMaximumLengte() && element.getValue().length() != definitie.getMinimumLengte()) {
            throw new BerichtInhoudException(String.format(EXACTE_LENGTE_ERROR, definitie.getElementNummer(), definitie.getMinimumLengte()));
        } else {
            if (element.getValue().length() < definitie.getMinimumLengte()) {
                throw new BerichtInhoudException(String.format(MINIMUM_LENGTE_ERROR, definitie.getElementNummer(), definitie.getMinimumLengte()));
            }

            if (element.getValue().length() > definitie.getMaximumLengte()) {
                if (definitie.getAfkappen()) {
                    element.setValue(element.getValue().substring(0, definitie.getMaximumLengte()));
                } else {
                    throw new BerichtInhoudException(String.format(MAXIMUM_LENGTE_ERROR, definitie.getElementNummer(), definitie.getMaximumLengte()));
                }
            }
        }
    }
}
