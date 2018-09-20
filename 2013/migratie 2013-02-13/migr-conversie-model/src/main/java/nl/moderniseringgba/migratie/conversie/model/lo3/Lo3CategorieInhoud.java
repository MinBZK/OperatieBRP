/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3;

/**
 * Deze marker interface typeert alle LO3 categorie inhoud typen.
 * 
 */
public interface Lo3CategorieInhoud {

    // /**
    // * Valideer de inhoud van deze categorie.
    // *
    // * @throws IllegalArgumentException
    // * als een waarde niet een toegestane vulling heeft
    // * @throws NullPointerException
    // * als een verplichte waarde niet gevuld is
    // */
    // void valideer();

    /**
     * @return true als deze LO3 inhoud semantisch leeg is
     */
    boolean isLeeg();
}
