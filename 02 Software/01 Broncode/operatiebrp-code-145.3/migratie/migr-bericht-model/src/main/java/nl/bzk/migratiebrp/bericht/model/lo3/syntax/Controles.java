/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.syntax;

import java.util.Map.Entry;
import java.util.regex.Pattern;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;

/**
 * Syntax controles.
 */
public final class Controles {

    private static final Pattern NUMERIEK_PATTERN = Pattern.compile("^[0-9]*$");

    private static final String NUMERIEK_ERROR = "Element %s moet een numerieke waarde bevatten.";
    private static final String EXACTE_LENGTE_ERROR = "Element %s moet exact %d lang zijn.";
    private static final String MINIMUM_LENGTE_ERROR = "Element %s moet minimaal %d lang zijn.";
    private static final String MAXIMUM_LENGTE_ERROR = "Element %s mag maximaal %d lang zijn.";
    private static final String LEEG_ERROR = "Element %s mag niet leeg zijn";

    private Controles() {
        // Niet instantieerbaar
    }

    /**
     * Controleer de exacte lengte van de meegegeven waarde aan de hand van de definitie.
     * @param element de LO3 elementen definitie en de te controleren waarde
     * @throws BerichtInhoudException als de waarde niet de exacte lengte heeft als de definitie dit specificeert
     */
    public static void controleerExacteLengte(final Entry<Lo3ElementEnum, String> element) throws BerichtInhoudException {
        final Lo3ElementEnum definitie = element.getKey();
        final String waarde = element.getValue();

        if (waarde.length() > 0 && definitie.getMinimumLengte() == definitie.getMaximumLengte() && waarde.length() != definitie.getMinimumLengte()) {
            throw new BerichtInhoudException(String.format(EXACTE_LENGTE_ERROR, definitie.getElementNummer(), definitie.getMinimumLengte()));
        }
    }

    /**
     * Controleer de minimum lengte van de meegegeven waarde aan de hand van de definitie.
     * @param element de LO3 elementen definitie en de te controleren waarde
     * @throws BerichtInhoudException als de lengte kleiner is dan de gedefinieerde minimum lengte
     */
    public static void controleerMinimumLengte(final Entry<Lo3ElementEnum, String> element) throws BerichtInhoudException {
        final Lo3ElementEnum definitie = element.getKey();
        final String waarde = element.getValue();

        if (waarde.length() > 0 && waarde.length() < definitie.getMinimumLengte()) {
            throw new BerichtInhoudException(String.format(MINIMUM_LENGTE_ERROR, definitie.getElementNummer(), definitie.getMinimumLengte()));
        }
    }

    /**
     * Controleer de maximum lengte van de meegegeven waarde aan de hand van de definitie. Indien de
     * waarde te lang is en afkappen is toegestaan, wordt de waarde afgekapt.
     * @param element de LO3 elementen definitie en de te controleren waarde
     * @throws BerichtInhoudException als de lengte groter is dan de gedefinieerde maximum lengte
     */
    public static void controleerMaximumLengte(final Entry<Lo3ElementEnum, String> element) throws BerichtInhoudException {
        final Lo3ElementEnum definitie = element.getKey();
        final String waarde = element.getValue();

        if (waarde.length() > definitie.getMaximumLengte()) {

            if (!definitie.getAfkappen()) {
                throw new BerichtInhoudException(String.format(MAXIMUM_LENGTE_ERROR, definitie.getElementNummer(), definitie.getMaximumLengte()));
            } else {
                element.setValue(element.getValue().substring(0, definitie.getMaximumLengte()));
            }
        }
    }

    /**
     * Controleer of het type niet leeg is.
     * @param element de LO3 elementen definitie en de te controleren waarde
     * @throws BerichtInhoudException als de lengte 0 is
     */
    public static void controleerNietLeeg(final Entry<Lo3ElementEnum, String> element) throws BerichtInhoudException {
        final Lo3ElementEnum definitie = element.getKey();
        final String waarde = element.getValue();

        if (waarde.length() == 0) {
            throw new BerichtInhoudException(String.format(LEEG_ERROR, definitie.getElementNummer()));
        }
    }

    /**
     * Controleer het type van de meegegeven waarde aan de hand van de definitie.
     * @param element de LO3 elementen definitie en de te controleren waarde
     * @throws BerichtInhoudException als de waarde niet overeenkomt met het gespecificeerde type
     */
    public static void controleerType(final Entry<Lo3ElementEnum, String> element) throws BerichtInhoudException {
        final Lo3ElementEnum definitie = element.getKey();
        final String waarde = element.getValue();

        if (waarde.length() > 0 && definitie.getType() == Lo3ElementEnum.Type.NUMERIEK && !NUMERIEK_PATTERN.matcher(waarde).matches()) {
            throw new BerichtInhoudException(String.format(NUMERIEK_ERROR, definitie.getElementNummer()));
        }
    }
}
