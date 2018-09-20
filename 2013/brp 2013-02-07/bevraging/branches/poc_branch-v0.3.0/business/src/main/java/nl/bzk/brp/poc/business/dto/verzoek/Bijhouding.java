/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.business.dto.verzoek;

import java.util.Calendar;

public class Bijhouding {

    private long afzenderId;
    private long abonnementId;
    private Calendar tijdstipVerzonden;
    private int soortActieID;
    private Verhuizing verhuizing;

    public long getAfzenderId() {
        return afzenderId;
    }

    public void setAfzenderId(final long afzenderId) {
        this.afzenderId = afzenderId;
    }

    public long getAbonnementId() {
        return abonnementId;
    }

    public void setAbonnementId(final long abonnementId) {
        this.abonnementId = abonnementId;
    }

    public Calendar getTijdstipVerzonden() {
        return tijdstipVerzonden;
    }

    public void setTijdstipVerzonden(final Calendar tijdstipVerzonden) {
        this.tijdstipVerzonden = tijdstipVerzonden;
    }

    public int getSoortActieID() {
        return soortActieID;
    }

    public void setSoortActieID(final int soortActieID) {
        this.soortActieID = soortActieID;
    }

    public Verhuizing getVerhuizing() {
        return verhuizing;
    }

    public void setVerhuizing(final Verhuizing verhuizing) {
        this.verhuizing = verhuizing;
    }
}
