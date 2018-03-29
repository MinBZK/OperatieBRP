/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.model;

import java.io.Serializable;

/**
 * Virtueel proces gerelateerde gegevens.
 */

public class VirtueelGerelateerdeGegevens implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private VirtueelProces virtueelProces;
    private String soortGegeven;
    private String gegeven;

    /**
     * Geeft het ID.
     * @return het ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet het ID.
     * @param id Het te zetten ID
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geeft het virtueel proces.
     * @return het virtueel proces
     */
    public VirtueelProces getVirtueelProces() {
        return virtueelProces;
    }

    /**
     * Zet het virtueel proces.
     * @param virtueelProces Het te zetten virtueel proces
     */
    public void setVirtueelProces(final VirtueelProces virtueelProces) {
        this.virtueelProces = virtueelProces;
    }

    /**
     * Geeft het soort gegeven.
     * @return het soort gegeven
     */
    public String getSoortGegeven() {
        return soortGegeven;
    }

    /**
     * Zet het soort gegeven.
     * @param soortGegeven Het te zetten soort gegeven
     */
    public void setSoortGegeven(final String soortGegeven) {
        this.soortGegeven = soortGegeven;
    }

    /**
     * Geeft het gegeven.
     * @return het gegeven
     */
    public String getGegeven() {
        return gegeven;
    }

    /**
     * Zet het gegeven.
     * @param gegeven Het te zetten gegeven
     */
    public void setGegeven(final String gegeven) {
        this.gegeven = gegeven;
    }

}
