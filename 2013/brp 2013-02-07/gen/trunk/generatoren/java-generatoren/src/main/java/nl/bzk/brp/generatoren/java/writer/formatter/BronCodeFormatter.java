/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.writer.formatter;

/**
 * Interface voor broncode formatters.
 */
public interface BronCodeFormatter {

    /**
     * Formatteert de code volgens een bepaald code format conventie.
     *
     * @param code De te formatteren code.
     * @return Geformatteerde code.
     */
    String formatteerCode(final String code);
}
