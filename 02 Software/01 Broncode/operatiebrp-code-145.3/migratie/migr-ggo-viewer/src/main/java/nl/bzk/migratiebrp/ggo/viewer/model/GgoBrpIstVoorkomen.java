/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

import java.util.Map;

/**
 * Het IST voorkomen, welke ook een administratieve handeling bevat.
 */
public class GgoBrpIstVoorkomen extends GgoBrpVoorkomen {
    private static final long serialVersionUID = 1L;

    private Map<String, String> administratieveHandeling;

    /**
     * Geef administratieve handeling.
     * @return administratieve handeling
     */
    public final Map<String, String> getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Zet administratieve handeling.
     * @param administratieveHandeling administratieve handeling
     */
    public final void setAdministratieveHandeling(final Map<String, String> administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }
}
