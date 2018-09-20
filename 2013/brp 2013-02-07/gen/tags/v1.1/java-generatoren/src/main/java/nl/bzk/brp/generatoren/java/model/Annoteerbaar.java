/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

import java.util.List;

/**
 * Interface voor Java-elementen die voorzien kunnen worden van annotaties.
 */
public interface Annoteerbaar {

    /**
     * Retourneert de annotaties die zijn geassocieerd met het object.
     * @return lijst met annotaties.
     */
    List<String> getAnnotaties();

    /**
     * Zet de annotaties die van toepassing zijn op het object.
     * @param annotaties annotaties behorende bij het object.
     */
    void setAnnotaties(final List<String> annotaties);

    /**
     * Voegt een annotatie toe aan het Java-element.
     * @param annotatie De toe te voegen annotatie.
     */
    void voegAnnotatieToe(final String annotatie);
}
