/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.basis.impl;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DatumTijd;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.BRPActie;
@MappedSuperclass
@Access(AccessType.FIELD)
public class AbstractFormeleHisTabel extends AbstractHisTabel {

    @AttributeOverride(name = "waarde", column = @Column(name = "TsReg"))
    protected DatumTijd datumTijdRegistratie;

    @AttributeOverride(name = "waarde", column = @Column(name = "TsVerval"))
    protected DatumTijd datumTijdVerval;

    @Transient
    protected BRPActie bRPActieInhoud;

    @Transient
    protected BRPActie bRPActieVerval;


    public DatumTijd getDatumTijdRegistratie() {
        return datumTijdRegistratie;
    }

    public void setDatumTijdRegistratie(final DatumTijd datumTijdRegistratie) {
        this.datumTijdRegistratie = datumTijdRegistratie;
    }

    public DatumTijd getDatumTijdVerval() {
        return datumTijdVerval;
    }

    public void setDatumTijdVerval(final DatumTijd datumTijdVerval) {
        this.datumTijdVerval = datumTijdVerval;
    }

    public BRPActie getBRPActieInhoud() {
        return bRPActieInhoud;
    }

    public void setBRPActieInhoud(final BRPActie bRPActieInhoud) {
        this.bRPActieInhoud = bRPActieInhoud;
    }

    public BRPActie getBRPActieVerval() {
        return bRPActieVerval;
    }

    public void setBRPActieVerval(final BRPActie bRPActieVerval) {
        this.bRPActieVerval = bRPActieVerval;
    }

}
