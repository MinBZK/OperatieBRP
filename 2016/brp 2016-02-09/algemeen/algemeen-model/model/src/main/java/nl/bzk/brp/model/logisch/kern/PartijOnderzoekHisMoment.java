/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;

/**
 * De wijze waarop een Partij betrokken is bij een Onderzoek.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisMomentGenerator")
public interface PartijOnderzoekHisMoment extends PartijOnderzoek {

    /**
     * Retourneert Standaard van Partij \ Onderzoek.
     *
     * @return Standaard.
     */
    HisPartijOnderzoekStandaardGroep getStandaard();

}
