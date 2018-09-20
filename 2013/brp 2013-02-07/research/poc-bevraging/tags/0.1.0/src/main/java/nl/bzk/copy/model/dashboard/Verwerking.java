/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.dashboard;

import java.util.Calendar;


/**
 * Resultaat van de bijhouding.
 */
public class Verwerking {

    private Calendar verwerkingsmoment;

    private VerwerkingStatus status;

    public Calendar getVerwerkingsmoment() {
        return verwerkingsmoment;
    }

    public void setVerwerkingsmoment(final Calendar verwerkingsmoment) {
        this.verwerkingsmoment = verwerkingsmoment;
    }

    public VerwerkingStatus getStatus() {
        return status;
    }

    public void setStatus(final VerwerkingStatus status) {
        this.status = status;
    }

}
