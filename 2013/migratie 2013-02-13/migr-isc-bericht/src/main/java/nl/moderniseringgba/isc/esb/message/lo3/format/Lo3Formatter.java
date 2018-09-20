/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.format;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;

/**
 * Basis lo3 (output)formatter.
 */
public interface Lo3Formatter {

    /**
     * Start een nieuwe categorie.
     * 
     * @param categorie
     *            categorie
     */
    void categorie(final Lo3CategorieEnum categorie);

    /**
     * Voeg een element toe aan de huidige categorie.
     * 
     * @param element
     *            element
     * @param inhoud
     *            inhoud
     * @throws IllegalStateException
     *             als er nog geen categorie is aangemaakt
     */
    void element(final Lo3ElementEnum element, final String inhoud);
}
