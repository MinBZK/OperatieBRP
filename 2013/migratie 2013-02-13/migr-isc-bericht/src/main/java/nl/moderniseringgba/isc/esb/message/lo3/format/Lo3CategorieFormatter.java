/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.format;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;

/**
 * Interface voor een Lo3 categorie formatter.
 * 
 * @param <T>
 *            Categorie die wordt geformat.
 * 
 */
public interface Lo3CategorieFormatter<T extends Lo3CategorieInhoud> {

    /**
     * Format een categorie.
     * 
     * @param categorie
     *            te formatteren categorie inhoud
     * @param formatter
     *            te gebruiken (output)formatter
     */
    void format(final T categorie, final Lo3Formatter formatter);

}
