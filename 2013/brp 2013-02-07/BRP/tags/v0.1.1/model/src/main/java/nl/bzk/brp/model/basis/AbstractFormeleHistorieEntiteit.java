/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * De basis voor alle historische gegevens.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractFormeleHistorieEntiteit {

    @Column(name = "TsReg")
    private Date datumTijdRegistratie;

    @Column(name = "TsVerval")
    private Date datumTijdVerval;

    /**
     * Retourneert (een kopie van) de timestamp van registratie.
     * @return (een kopie) van de timestamp van registratie.
     */
    public Date getDatumTijdRegistratie() {
        if (datumTijdRegistratie == null) {
            return null;
        } else {
            return (Date) datumTijdRegistratie.clone();
        }
    }

    /**
     * Zet (een kopie van) de timestamp van registratie.
     * @param datumTijdRegistratie de timestamp van registratie.
     */
    public void setDatumTijdRegistratie(final Date datumTijdRegistratie) {
        if (datumTijdRegistratie == null) {
            this.datumTijdRegistratie = null;
        } else {
            this.datumTijdRegistratie = (Date) datumTijdRegistratie.clone();
        }
    }

    /**
     * Retourneert (een kopie van) de timestamp van vervallen.
     * @return (een kopie) van de timestamp van vervallen.
     */
    public Date getDatumTijdVerval() {
        if (datumTijdVerval == null) {
            return null;
        } else {
            return (Date) datumTijdVerval.clone();
        }
    }

    /**
     * Zet (een kopie van) de timestamp van vervallen.
     * @param datumTijdVerval de timestamp van vervallen.
     */
    public void setDatumTijdVerval(final Date datumTijdVerval) {
        if (datumTijdVerval == null) {
            this.datumTijdVerval = null;
        } else {
            this.datumTijdVerval = (Date) datumTijdVerval.clone();
        }
    }

}
