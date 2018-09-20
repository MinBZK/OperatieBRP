/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx;

import nl.bzk.brp.metaregister.model.Attribuut;


/**
 * Filter interface. Implementerende klasses moeten bepalen of een attribuut wel of niet
 * meegenomen moet worden (in de binding).
 */
public abstract class AbstractAttribuutFilter {

    /**
     * Of een attribuut wel of niet meegenomen moet worden (in de binding).
     *
     * @param attribuut het attribuut
     * @return true, indien het attribuut meegenomen dient te worden, anders false
     */
    public abstract boolean neemAttribuutMee(final Attribuut attribuut);

    /**
     * Een filter dat altijd true terug geeft.
     *
     * @return een filter dat altijd true terug geeft
     */
    public static AbstractAttribuutFilter alwaysTrue() {
        return new AbstractAttribuutFilter() {
            @Override
            public boolean neemAttribuutMee(final Attribuut attribuut) {
                return true;
            }
        };
    }

}
