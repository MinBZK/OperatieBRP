/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.model;

import java.io.Serializable;

/**
 * Configuratie.
 */

public class Configuratie implements Serializable {
    private static final long serialVersionUID = 1L;
    private String configuratie;
    private String waarde;

    /**
     * Geeft de configuratie.
     * @return de configuratie
     */
    public String getConfiguratie() {
        return configuratie;
    }

    /**
     * Zet de configuratie.
     * @param configuratie De te zetten configuratie
     */
    public void setConfiguratie(final String configuratie) {
        this.configuratie = configuratie;
    }

    /**
     * Geeft de waarde.
     * @return de waarde
     */
    public String getWaarde() {
        return waarde;
    }

    /**
     * Zet de waarde.
     * @param waarde De te zetten waarde.
     */
    public void setWaarde(final String waarde) {
        this.waarde = waarde;
    }
}
