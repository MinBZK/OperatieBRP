/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.Calendar;


/**
 * De Class Verwerking.
 */
public class Verwerking {

    /** De verwerkingsmoment. */
    private Calendar verwerkingsmoment;

    /** De status. */
    private VerwerkingStatus status;

    /**
     * Instantieert een nieuwe verwerking.
     */
    public Verwerking() {
    }

    /**
     * Instantieert een nieuwe verwerking.
     *
     * @param verwerkingsmoment de verwerkingsmoment
     * @param status de status
     */
    public Verwerking(final Calendar verwerkingsmoment, final VerwerkingStatus status) {
        this.verwerkingsmoment = verwerkingsmoment;
        this.status = status;
    }

    /**
     * Haalt een verwerkingsmoment op.
     *
     * @return verwerkingsmoment
     */
    public Calendar getVerwerkingsmoment() {
        return verwerkingsmoment;
    }

    /**
     * Instellen van verwerkingsmoment.
     *
     * @param verwerkingsmoment de nieuwe verwerkingsmoment
     */
    public void setVerwerkingsmoment(final Calendar verwerkingsmoment) {
        this.verwerkingsmoment = verwerkingsmoment;
    }

    /**
     * Haalt een status op.
     *
     * @return status
     */
    public VerwerkingStatus getStatus() {
        return status;
    }

    /**
     * Instellen van status.
     *
     * @param status de nieuwe status
     */
    public void setStatus(final VerwerkingStatus status) {
        this.status = status;
    }

}
