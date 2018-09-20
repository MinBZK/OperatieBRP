/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Virtueel proces.
 */
@SuppressWarnings("checkstyle:designforextension")
public class VirtueelProces implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Timestamp tijdstip;
    private List<VirtueelGerelateerdeGegevens> gerelateerdeGegevens = new ArrayList<>();

    /**
     * Geef het ID.
     *
     * @return het ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet het ID.
     *
     * @param id
     *            Het te zetten ID
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef het tijdstip.
     *
     * @return het tijdstip.
     */
    public Timestamp getTijdstip() {
        return tijdstip == null ? null : new Timestamp(tijdstip.getTime());
    }

    /**
     * Zet het tijdstip.
     *
     * @param tijdstip
     *            Het te zetten tijdstip
     */
    public void setTijdstip(final Timestamp tijdstip) {
        this.tijdstip = tijdstip == null ? null : new Timestamp(tijdstip.getTime());
    }

    /**
     * Geef de set met gerelateerde gegevens.
     *
     * @return de gerelateerde gegevens.
     */
    public List<VirtueelGerelateerdeGegevens> getGerelateerdeGegevens() {
        return gerelateerdeGegevens;
    }

    /**
     * Zet de set met gerelateerde gegevens.
     *
     * @param gerelateerdeGegevens
     *            De te zetten set met gerelateerde gegevens
     */
    public void setGerelateerdeGegevens(final List<VirtueelGerelateerdeGegevens> gerelateerdeGegevens) {
        this.gerelateerdeGegevens = gerelateerdeGegevens;
    }

}
