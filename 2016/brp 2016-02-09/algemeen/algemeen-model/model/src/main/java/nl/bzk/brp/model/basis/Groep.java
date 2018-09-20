/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import java.util.List;

/**
 * Basis interface voor alle groepen.
 */
public interface Groep {

    /**
     * Geeft de attributen van de groep als lijst.
     *
     * @return lijst van attributen
     */
    List<Attribuut> getAttributen();

}
