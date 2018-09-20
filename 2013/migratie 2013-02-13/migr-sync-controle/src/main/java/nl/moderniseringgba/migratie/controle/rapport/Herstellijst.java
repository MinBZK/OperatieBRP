/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.controle.rapport;

import java.util.ArrayList;
import java.util.List;

/**
 * Herstellijst met alle herstelacties die ondernomen moeten worden.
 */
public class Herstellijst {

    private List<HerstelActie> herstelActies;

    /**
     * @return the herstelActies
     */
    public final List<HerstelActie> getHerstelActies() {
        if (herstelActies == null) {
            herstelActies = new ArrayList<HerstelActie>();
        }
        return herstelActies;
    }

    /**
     * @param herstelActies
     *            the herstelActies to set
     */
    public final void setHerstelActies(final List<HerstelActie> herstelActies) {
        this.herstelActies = herstelActies;
    }

}
