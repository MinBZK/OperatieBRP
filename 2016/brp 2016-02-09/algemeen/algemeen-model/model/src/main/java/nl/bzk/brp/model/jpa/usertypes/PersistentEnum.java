/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.jpa.usertypes;

/**
 * Interface voor persistent enums. Stelt het opvragen van een id verplicht.
 */
public interface PersistentEnum {

    /**
     * Geef het id terug van de enum instantie.
     * <p/>
     * NB: Dit is moet uniek zijn over alle enum instanties van het type!
     *
     * @return het id
     */
    Integer getId();

}
