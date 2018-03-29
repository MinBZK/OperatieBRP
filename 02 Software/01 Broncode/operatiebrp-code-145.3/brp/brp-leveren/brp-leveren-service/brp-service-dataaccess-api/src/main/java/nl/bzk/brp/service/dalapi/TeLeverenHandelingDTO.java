/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.dalapi;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusLeveringAdministratieveHandeling;

/**
 * TeLeverenHandeling.
 */
public final class TeLeverenHandelingDTO {
    private Long admhndId;
    private ZonedDateTime tijdstipRegistratie;
    private StatusLeveringAdministratieveHandeling status;
    private Long bijgehoudenPersoon;

    /**
     * Gets admhnd id.
     * @return the admhnd id
     */
    public Long getAdmhndId() {
        return admhndId;
    }

    /**
     * Sets admhnd id.
     * @param admhndId the admhnd id
     */
    public void setAdmhndId(final Long admhndId) {
        this.admhndId = admhndId;
    }

    /**
     * Gets tijdstip registratie.
     * @return the tijdstip registratie
     */
    public ZonedDateTime getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * Sets tijdstip registratie.
     * @param tijdstipRegistratie the tijdstip registratie
     */
    public void setTijdstipRegistratie(final ZonedDateTime tijdstipRegistratie) {
        this.tijdstipRegistratie = tijdstipRegistratie;
    }

    /**
     * Gets status.
     * @return the status
     */
    public StatusLeveringAdministratieveHandeling getStatus() {
        return status;
    }

    /**
     * Sets status.
     * @param status the status
     */
    public void setStatus(final StatusLeveringAdministratieveHandeling status) {
        this.status = status;
    }

    /**
     * Gets bijgehouden persoon.
     * @return the bijgehouden persoon
     */
    public Long getBijgehoudenPersoon() {
        return bijgehoudenPersoon;
    }

    /**
     * Sets bijgehouden persoon.
     * @param bijgehoudenPersoon the bijgehouden persoon
     */
    public void setBijgehoudenPersoon(final Long bijgehoudenPersoon) {
        this.bijgehoudenPersoon = bijgehoudenPersoon;
    }
}
