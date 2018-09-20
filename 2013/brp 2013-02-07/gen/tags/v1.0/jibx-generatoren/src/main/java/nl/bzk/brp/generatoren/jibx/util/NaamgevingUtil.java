/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx.util;


/**
 * Utility class met hulp methodes voor de jibx generator mbt naamgeving.
 *
 */
public final class NaamgevingUtil {

    /**
     * Private constructor, want dit is een utility klasse.
     */
    private NaamgevingUtil() {
    }

    /**
     * Maak van een fully qualified class name een Java identifier.
     *
     * @param fullyQualifiedName de naam die omgezet moet worden
     * @return de identifier die vanuit de naam gemaakt is
     */
    public static String converteerFullyQualifiedNaamNaarIdentifier(final String fullyQualifiedName) {
        String klasseNaam = fullyQualifiedName.substring(fullyQualifiedName.lastIndexOf('.') + 1);
        return klasseNaam.substring(0, 1).toLowerCase() + klasseNaam.substring(1);
    }

}
